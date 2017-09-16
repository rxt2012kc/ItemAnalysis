package com.amazon.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.amazon.helper.AppConfig;
import com.amazon.helper.HttpRequest;
import com.amazon.helper.SignedRequestsHelper;
import com.amazon.web.service.SearchService;

/**
 * @author RenXintao
 * @date 2017/09/13
 */
@Controller
@RequestMapping("amazonSearch")
public class SearchController {

	@Autowired
	private HttpServletRequest req;
	@Autowired
	private HttpServletResponse resp;
	@Autowired
	private HttpSession session;
	@Autowired
	private AppConfig appConfig;
	@Resource(name = "SignedRequestsHelper")
	private SignedRequestsHelper signedRequestsHelper;
	@Resource(name = "HttpRequest")
	private HttpRequest httpRequest;
	@Resource(name = "SearchService")
	private SearchService searchService;

	private Logger logger = LoggerFactory.getLogger(SearchController.class);


	/**
	 * Call PAAPI to search
	 * 
	 * @param ANSI
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 */
	@ResponseBody
	@RequestMapping(value = "searchItem", method = RequestMethod.GET)
	public Object search(HttpServletRequest request, HttpServletResponse response)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {

		String ANSI = request.getParameter("ANSI");
		logger.info("ANSI:" + ANSI);

		// 请求爬虫服务
		String crawlerUrl = "http://10.109.247.244:5000/spider";
		String crawlerParam = "itemId=" + ANSI;
		String reviewInfo = searchService.httpRequest(crawlerUrl, crawlerParam);

		// 请求提取关键字服务
		String keywordUrl = "http://10.109.247.244:5000/keyword";
		String reviewInfoEncoder = URLEncoder.encode(reviewInfo, "UTF-8");
		String keywordParam = "text=" + reviewInfoEncoder;
		String keywordInfo = searchService.httpRequest(keywordUrl, keywordParam);
		
		// 请求评论摘要服务
		String abstractUrl = "http://10.109.247.244:5000/abstract";
		String abstractInfoEncoder = URLEncoder.encode(reviewInfo, "UTF-8");
		String abstractParam = "text=" + abstractInfoEncoder;
		String abstractInfo = searchService.httpRequest(abstractUrl, abstractParam);

		// 请求情感分析服务
		String sentimentUrl = "http://10.109.247.244:5000/sentiment";
		String sentimentParam = "text=" + reviewInfoEncoder;
		String sentimentInfo = searchService.httpRequest(sentimentUrl, sentimentParam);

		// 调用API查找该商品图片
		Map<String, String> itemParams = new HashMap<String, String>();
		itemParams.put("Service", "AWSECommerceService");
		itemParams.put("Operation", "ItemLookup");
		itemParams.put("AWSAccessKeyId", appConfig.getAwsAccessKeyId());
		itemParams.put("AssociateTag", appConfig.getAssociateTag());
		itemParams.put("ItemId", "B00JK0BA8C");
		itemParams.put("IdType", "ASIN");
		itemParams.put("ResponseGroup", "Images");
		signedRequestsHelper = new SignedRequestsHelper(appConfig.getAwsAccessKeyId(), appConfig.getAwsSecretKey());
		String itemRequestUrl= signedRequestsHelper.sign(itemParams);
		String itemXml = searchService.httpRequest(itemRequestUrl);
		
		// 将得到的数据形成json格式传输给前端
		Map<String, Object> resultMap = new HashMap<String, Object>();
		searchService.parseReviewinfo(sentimentInfo, resultMap);
		searchService.parseKeywordInfo(keywordInfo, resultMap);
		searchService.itemXmlParser(itemXml, resultMap);
		searchService.parseReviewinfo(abstractInfo, resultMap);
		
		return resultMap;
	}

	
}
