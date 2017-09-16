package com.amazon.web.service;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.helper.JsonParser;
import com.amazon.helper.SignedRequestsHelper;
import com.amazon.helper.xmlParser;
import com.amazon.helper.JsonParser.TwoTuple;
import com.amazon.helper.AppConfig;

/**
 * @author RenXintao
 * @date 2017/09/16
 */
@Service("searchService")
public class SearchService {

	private static Logger logger = LoggerFactory.getLogger(SearchService.class);
	@Resource(name = "SignedRequestsHelper")
	private SignedRequestsHelper signedRequestsHelper;
	@Autowired
	private AppConfig appConfig;

	public String httpRequest(String url, String param)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		String requestUrl = url + "?" + param;
		logger.info(requestUrl);
		URL serviceUrl = null;
		HttpURLConnection httpConnection = null;
		String info = "";

		try {
			serviceUrl = new URL(requestUrl);
			httpConnection = (HttpURLConnection) serviceUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException(
						"HTTP GET Request For  Failed with Error code : " + httpConnection.getResponseCode());
			}
			info = signedRequestsHelper.convert(httpConnection.getInputStream(), Charset.forName("UTF-8"));
			logger.info(url + "info:" + info);
		} catch (Exception crawlerException) {
			crawlerException.printStackTrace();
			logger.debug("exception:  " + url + "is filed");
		}

		return info;
	}

	public  String httpRequest(String itemRequestUrl)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		signedRequestsHelper = new SignedRequestsHelper(appConfig.getAwsAccessKeyId(), appConfig.getAwsSecretKey());

		String requestUrl = itemRequestUrl;
		logger.info(requestUrl);
		URL serviceUrl = null;
		HttpURLConnection httpConnection = null;
		String info = "";

		try {
			serviceUrl = new URL(requestUrl);
			httpConnection = (HttpURLConnection) serviceUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException(
						"HTTP GET Request For  Failed with Error code : " + httpConnection.getResponseCode());
			}
			info = signedRequestsHelper.convert(httpConnection.getInputStream(), Charset.forName("UTF-8"));
			logger.info(itemRequestUrl + "info:" + info);
		} catch (Exception crawlerException) {
			crawlerException.printStackTrace();
			logger.debug("exception:  " + itemRequestUrl + "is filed");
		}

		return info;
	}

	public void parseReviewinfo(String sentimentInfo, Map<String, Object> resultMap) {
		JsonParser itemInfo = new JsonParser(sentimentInfo);
		// 获得差评，中评，好评的个数分布
		Map<String, Integer> reviewDistribution = itemInfo.reviewDistribution();
		resultMap.put("reviewDistribution", reviewDistribution);
		// 获得日期-评论情感分析
		List<String> reviewEmotionTrendTimeLine = itemInfo.reviewEmotionTrendTimeLine();
		resultMap.put("timeLine", reviewEmotionTrendTimeLine);
		ArrayList<ArrayList<Integer>> timelineGoodMediumPoor = itemInfo.reviewEmotionTrend();
		resultMap.put("good", timelineGoodMediumPoor.get(0));
		resultMap.put("medium", timelineGoodMediumPoor.get(1));
		resultMap.put("bad", timelineGoodMediumPoor.get(2));
		// 获得日期-销量分析
		ArrayList<TwoTuple<String, Integer>> recentSell = itemInfo.sells();
		List<String> date = new ArrayList<>();
		List<Integer> num = new ArrayList<>();
		for (int i = 0; i < recentSell.size(); ++i) {
			date.add(recentSell.get(i).first);
			num.add(recentSell.get(i).second);
		}
		resultMap.put("sellDate", date);
		resultMap.put("sellNum", num);
		// 获得购买类型-纸质书/电子书数量分布
		HashMap<String, Integer> formatDis = itemInfo.formatDistribution();
		resultMap.put("paper", formatDis.get("paper"));
		resultMap.put("kindle", formatDis.get("kindle"));

	}

	public void parseKeywordInfo(String keywordInfo, Map<String, Object> resultMap) {
		JsonParser keywordParser = new JsonParser(keywordInfo);
		HashMap<String, Integer> map = keywordParser.getWordCloud();
		Iterator<String> iter = map.keySet().iterator();
		ArrayList<String> words = new ArrayList<>();
		ArrayList<Integer> weights = new ArrayList<>();
		while (iter.hasNext()) {
			String word = iter.next();
			Integer val = map.get(word);
			words.add(word);
			weights.add(val);
		}
		resultMap.put("words", words);
		resultMap.put("weights", weights);

	}

	public void itemXmlParser(String xml, Map<String, Object> resultMap) {
		xmlParser xmlparser = new xmlParser(xml);
		resultMap.put("itemImage", xmlparser.getImageUrl());
	}

}
