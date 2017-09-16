# -*- coding: UTF-8 -*-
"""
Implementation of crawler for Amazon Review Information
@author renxintao
@date 2017/09/13
"""
from scrapy.http import Request
from scrapy.spiders import Spider
from scrapy.selector import Selector
import threading
import json
import re

_month_dict = {"January": 1, "February": 2, "March": 3, "April": 4, "May": 5, "June": 6,
               "July": 7, "August": 8, "September": 9, "October": 10, "November": 11, "December": 12}


class AmazonSpider(Spider):
    name = 'index_review_spider'
    url_pattern = r'(.*)/a/(\d{8})/(\d+)\.htm'

    def __init__(self, itemId=None):
        if itemId != None:
            self.start_urls = [
                'https://www.amazon.com/review/product/' + itemId + '/ref=cm_cr_getr_d_paging_btm_2?SubscriptionId=AKIAJBWJ4SPITVNJPDTQ&tag=renxintao-20&linkCode=xm2&camp=2025&creative=386001&creativeASIN=1539397831']
        else:
            self.start_urls = [
                'https://www.amazon.com/review/product/1539397831/ref=cm_cr_getr_d_paging_btm_2?SubscriptionId=AKIAJBWJ4SPITVNJPDTQ&tag=renxintao-20&linkCode=xm2&camp=2025&creative=386001&creativeASIN=1539397831']

    def start_requests(self):
        for url in self.start_urls:
            request = Request(url=url, callback=self.parse)
            yield request

    def parse(self, response):
        sel = Selector(response)
        temp = sel.xpath(u'//div[@id="cm_cr-review_list"]/div/span/text()').extract()[0]
        pattern = re.match(r'Showing (\d+)-(\d+) of (\d+) reviews', str(temp))  # 定位评论总数
        total = int(pattern.group(3))
        if (total <= 10):  # 根据总数求取页数（每页最多10评论）
            pagenum = 1
        else:
            pagenum = int(total / 10)

        self.cnt = pagenum  # 处理页计数
        self.lock = threading.Lock()  # 由于多线程操作临界区而做的互斥锁
        self.pglock = threading.Lock()  # 由于多线程操作临界区而做的互斥锁
        self.json = {"total": 0, "detail": []}  # 返回json结构

        for i in range(1, pagenum + 1):
            self.pglock.acquire()
            newurl = response.url + "&pageNumber=" + str(i)
            request = Request(url=newurl, meta={'url': response.url, 'total': total, 'pagenum': i},
                              callback=self.parse_reviews)
            yield request
            self.pglock.release()

    def parse_reviews(self, response):
        sel = Selector(response)
        i = 2
        itemlist = []
        while True:
            item = {}
            try:  # 尝试获取第i-1个评论的星级
                item['stars'] = sel.xpath(
                    u'//div[@id="cm_cr-review_list"]/div[%d]/div/div/a[@class="a-link-normal"]/@title' % i).extract()[0]
                item['stars'] = item['stars'].split(' ')[0]
            except:  # 获取不到，说明评论取完了
                break
            try:  # 尝试获取第i-1个评论的时间，并格式转化
                cm_date = sel.xpath(
                    u'//div[@id="cm_cr-review_list"]/div[%d]/div/div[2]/span[@data-hook = "review-date"]/text()' % i).extract()[
                    0]
                cm_date_month = cm_date.split(' ')[1]
                cm_date_month = _month_dict[cm_date_month]
                cm_date_year = cm_date.split(' ')[-1]
                cm_date_day = cm_date.split(' ')[-2].split(',')[0]
                item['date'] = cm_date_year + "-" + str(cm_date_month) + "-" + cm_date_day
            except:
                item['date'] = "0-0-0"

            # 尝试获取第i-1个评论对应的书类型
            item['format'] = sel.xpath(u'//div[@id="cm_cr-review_list"]/div[%d]/div/div[3]/a/text()' % i).extract()[0]
            item['format'] = item["format"].split(":")[-1].strip()
            # 尝试获取第i-1个评论的正文
            item['review'] = sel.xpath(
                u'//div[@id="cm_cr-review_list"]/div[%d]/div/div[4]/span[@data-hook = "review-body"]/text()' % i).extract()[
                0]
            i += 1
            itemlist.append(item)

        self.lock.acquire()
        self.cnt -= 1
        self.json["detail"].extend(itemlist)
        if self.cnt == 0:  # 所有页处理完毕，构造json串并输出给服务器
            self.json["total"] = len(self.json["detail"])
            encodejson = json.dumps(self.json)
            print(encodejson)
        self.lock.release()
