# -*- coding: utf-8 -*-
"""
Implementation of crawler, sentiment analysis and keyword analysis for Amazon Review Information
@author renxintao
@date 2017/09/14
"""
from flask import Flask, request, render_template, url_for, session, escape, redirect, make_response
import json
import urllib
import os

app = Flask(__name__)
app.config.from_object('config')

# 调用爬虫，爬取给定商品id的评论，返回json
@app.route('/spider/', methods=['GET', 'POST'])
def spider():
    print "spider begin!"
    if request.method == 'POST':
        itemId = request.form['itemId']
    else:
        itemId = request.args.get('itemId')
    print "spider ANSI:", itemId

    # 运行scrapy
    p = os.popen(
        'cd /home/renxintao/amazon_flask/indexspider/indexspider/spiders ; '
        'scrapy runspider spider.py -a itemId=' + itemId)
    resp = p.readlines()[0]
    p.close()

    # 检查爬虫程序返回的结果是否为成功执行的结果
    try:
        res = json.loads(resp)
        print res
    except:
        print "没有得到爬虫结果！"

    return resp


# 关键词分析，将评论内容拼接后提取关键词，返回对应的json
@app.route('/keyword/', methods=['GET', 'POST'])
def keyword():
    print "keyword analysis begin！"
    if request.method == 'POST':
        text = request.form['text']
    else:
        text = request.args.get('text')
    print "text:", text

    try:
        comments = json.loads(text)["detail"]
        text = ""
        for cm in comments:
            text += cm["review"] + " "  # 从json中抽取评论正文，合并构造为字符串作为关键词程序的参数
        text = urllib.quote(text)  # 将字符串编码，避免方/花括号等符号被shell特殊处理
        # 运行提取关键字模型
        p = os.popen(
            'python /home/renxintao/amazon_flask/keyword/keyword.py "' + text + '"')
        res = p.read()
        resp = {"keywords": res}  # 返回的json为提取得到的关键词序列
    except:
        resp = {"message": "recive a text not a json!"}

    resp = json.dumps(resp)
    return resp


# 情感分析，对各条评论输出情感标签，返回回应的json
# 情感三分类：0-差评，1-中评，2-好评
@app.route('/sentiment/', methods=['GET', 'POST'])
def sentiment():
    print "sentiment analysis begin!"
    if request.method == 'POST':
        text = request.form['text']
    else:
        text = request.args.get('text')
    print "text", text
    text = urllib.quote(text)  # 将字符串编码，避免方/花括号等符号被shell特殊处理

    try:
        # 运行情感分析模型
        p = os.popen(
            'python /home/renxintao/amazon_flask/emotion/emotionanalysis_amazon.py "' + text + '"')
        res = p.read()
        resp = res
    except:
        resp = {"message": "recive a text not a json!"}
        resp = json.dumps(resp)

    return resp


if __name__ == '__main__':
    app.run(host='0.0.0.0')
