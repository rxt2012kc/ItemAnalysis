package com.amazon.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

@Repository("JsonParser")
public class JsonParser {
	public static void main(String[] args) { 
		//Json String of book detail.
		String jsonStr = "{\"total\":4,"
				+ "\"detail\":[{date:2017-1-3, format: KindleEdition, review:ffffffff, label:0, star:1.0},"
				+ "		   {date:2017-10-15, format: KindleEdition, review:qqqqqqqq, label:1, star:1.0},"
				+ "		   {date:2017-1-3, format:Paperback, review:eeeeeeee, label:0, star:1.0},"
				+ "		   {date:2017-9-1, format:Paperback, review:eeeeeeee, label:2, star:1.0},"
				+ "]"
				+ "}";

		// Json parser
		JsonParser jsonParser = new JsonParser(jsonStr);
		
		System.out.println("Parser Json:");
		System.out.println(jsonParser.getTotal());
		System.out.println(jsonParser.getDetail().get(0).getDate());
		System.out.println(jsonParser.getDetail().get(0).getFormat());
		System.out.println(jsonParser.getDetail().get(0).getReview());
		System.out.println(jsonParser.getDetail().get(0).getLabel());
		System.out.println();
		
		
		//Review distribution	
		HashMap<String, Integer> reviewDis = jsonParser.reviewDistribution();
		System.out.println("Review Distribution:");
		System.out.println(reviewDis);
		System.out.println("good：" + reviewDis.get("good"));
		System.out.println("medium：" + reviewDis.get("medium"));
		System.out.println("bad：" + reviewDis.get("bad"));
		System.out.println();
		
		
		//Review emotion trend: timeLine - good, medium,poor
		ArrayList<String> timeLine = jsonParser.reviewEmotionTrendTimeLine();
		System.out.print("timeLine：");
		for(int i=0;i<timeLine.size();++i) {
			System.out.print(timeLine.get(i) + ", ");
		}
		System.out.println();
		
		ArrayList<ArrayList<Integer>> timelineGoodMediumPoor = jsonParser.reviewEmotionTrend();
		
		System.out.print("goodReview：");
		for(int i=0;i<timelineGoodMediumPoor.get(0).size();++i) {
			System.out.print(timelineGoodMediumPoor.get(0).get(i) + ", ");
		}
		System.out.println();
		
		System.out.print("mediumReview：");
		for(int i=0;i<timelineGoodMediumPoor.get(1).size();++i) {
			System.out.print(timelineGoodMediumPoor.get(1).get(i) + ", ");
		}
		System.out.println();
		
		System.out.print("poorReview：");
		for(int i=0;i<timelineGoodMediumPoor.get(2).size();++i) {
			System.out.print(timelineGoodMediumPoor.get(2).get(i) + ", ");
		}
		System.out.println();
		System.out.println();
		
		
		//Recent Sells condition
		ArrayList<TwoTuple<String, Integer>> recentSell = jsonParser.sells();
		System.out.println("近期销量：");
		System.out.println(recentSell);
		List<String> date = new ArrayList<>();
		List<Integer> num = new ArrayList<>();
		for(int i=0;i<recentSell.size();++i) {
			date.add(recentSell.get(i).first);
			num.add(recentSell.get(i).second);
			System.out.print(" [" + recentSell.get(i).first + "," + recentSell.get(i).second + "],");
		}
		System.out.println();
		System.out.println();
		
		
		//Book format distribution
		HashMap<String, Integer> formatDis = jsonParser.formatDistribution();
		System.out.println("Format Distribution:");
		System.out.println("paper：" + formatDis.get("paper"));
		System.out.println("kindle：" + formatDis.get("kindle"));
		System.out.println();
			

		//Parser string of work cloud.
		String wordCloudStr = "{\"keywords\": \"[('dresses rarely fit', 9.0), ('princess seams give', 9.0)]\"}";
		jsonParser.WordCloud(wordCloudStr);
		
		HashMap<String, Integer> map = jsonParser.getWordCloud();
		Iterator<String> iter = map.keySet().iterator();
		  while (iter.hasNext()){
		   String word = iter.next();
		   Integer val = map.get(word);
		   System.out.println(word + ": " + val);
		  }
	}
	
	/*
	 * Possible values of label and format of detail in json string 
	 */
	final String GoodReview = "2";
	final String MediumReview = "1";
	final String PoorReview = "0";
	final String EBook = "KindleEdition";
	final String PaperBook = "Paperback";
	
	
	/*
	 * Fiels of Json: Total is the num of review, Detail are all reviews
	 */
	private Integer Total = new Integer(0);
	private ArrayList<GoodDetail> Detail=new ArrayList<GoodDetail>();
	private HashMap<String, Integer> WordCloud = new HashMap<String, Integer>();
	
	
	private JsonParser() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Parser the json string of book detail. 
	 */
	public JsonParser(String jsonBookDetail) {
		try {
			parseJsonToBeanInfo(jsonBookDetail);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void parseJsonToBeanInfo(String JsonInfo) throws Exception {
		if(!"".equals(JsonInfo) && JsonInfo != null){		   
			JSONObject json = new JSONObject(JsonInfo); 
			
			String total = json.getString("total");
			Total = Integer.valueOf(total);
		   
			JSONArray detailArray =json.getJSONArray("detail");
			for (int i = 0; i < detailArray.length(); i++) {
				JSONObject detailObject = detailArray.getJSONObject(i);
				
				String date = detailObject.getString("date"); 
				String format = detailObject.getString("format");
				String review = detailObject.getString("review"); 
				String label = detailObject.getString("label"); 
				
				Detail.add(new GoodDetail(date, format, review, label)); 
			} 
		} 
	}
	
	
	/*
	 * Count the distribution of good, medium, poor reviews
	 */
	public HashMap<String, Integer> reviewDistribution(){
		HashMap<String, Integer> reviewDis = new HashMap<String, Integer>();
		reviewDis.put("good", 0);
		reviewDis.put("medium", 0);
		reviewDis.put("bad", 0);
		
		for(int i=0;i<Detail.size();++i) {
			if(Detail.get(i).getLabel().equals(GoodReview)) {
				reviewDis.put("good", reviewDis.get("good")+1);
			}
			else if(Detail.get(i).getLabel().equals(MediumReview)) {
				reviewDis.put("medium", reviewDis.get("medium")+1);
			}
			else if(Detail.get(i).getLabel().equals(PoorReview)){
				reviewDis.put("bad", reviewDis.get("bad")+1);
			}
		}
	
		return reviewDis;
	}
	
	
	/*
	 * Count the tendency of good, medium, poor reviews changes with timeline
	 * reviewEmotionTrendTimeLine() get the timeline
	 */
	public ArrayList<String> reviewEmotionTrendTimeLine(){
		ArrayList<String> timeLine = new ArrayList<String>();
		
		for(int i=0;i<Detail.size();++i) {
			String date1 = Detail.get(i).getDate();
			if(!timeLine.contains(date1)) {
				timeLine.add(date1);
			}
		}
		
		//sort by date
		Collections.sort(timeLine, new Comparator<String>(){
			public int compare(String dateStr1, String dateStr2) {
				SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
				Date dt1 = null;
				Date dt2 = null;
				
				try{
					dt1 = sdf.parse(dateStr1);
					dt2 = sdf.parse(dateStr2);
				}catch(ParseException e) {
					e.printStackTrace();
				}		
				
				if(dt1.getTime() - dt2.getTime() > 0) {
					return 1;
				}
				else if(dt1.getTime() == dt2.getTime() ) {
					return 0;
				}
				else {
					return -1;
				}
		    }
		});
		
		return timeLine;
	}
	
	
	/*
	 * Count the tendency of good, medium, poor reviews changes with timeline
	 * reviewEmotionTrendTimeLine() get the num of good, medium, poor reviews changing with timeline.
	 */
	public ArrayList<ArrayList<Integer>> reviewEmotionTrend(){
		ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<String> timeLine = reviewEmotionTrendTimeLine();
		int size = timeLine.size();
		
		ArrayList<Integer> goodlist = new ArrayList<Integer>();
		ArrayList<Integer> mediumlist = new ArrayList<Integer>();
		ArrayList<Integer> poorlist = new ArrayList<Integer>();
		
		for(int i=0;i<size;++i) {
			goodlist.add(0);
			mediumlist.add(0);
			poorlist.add(0);
		}
		
		for(int i=0;i<Detail.size();++i) {
			String label1 = Detail.get(i).getLabel();
			String date1 = Detail.get(i).getDate();			
			int index = timeLine.indexOf(date1);
			
			if(label1.equals(GoodReview)) {
				goodlist.set(index, goodlist.get(index)+1);
			}
			else if(label1.equals(MediumReview)) {
				mediumlist.set(index, mediumlist.get(index)+1);
			}
			else if(label1.equals(PoorReview)) {
				poorlist.set(index, poorlist.get(index)+1);
			}
		}
		
		ret.add(goodlist);
		ret.add(mediumlist);
		ret.add(poorlist);
		
		return ret;
	}
	
	
	/*
	 * Count the recent sells condition: date and its sells num
	 */
	public ArrayList<TwoTuple<String, Integer>> sells(){
		ArrayList<TwoTuple<String, Integer>> ret = new ArrayList<TwoTuple<String, Integer>>();
		HashMap<String, Integer> sellsMap = new HashMap<String, Integer>();
		
		for(int i=0;i<Detail.size();++i) {
			String dd = Detail.get(i).getDate();
			
			if(sellsMap.containsKey(dd)) {
				sellsMap.put(dd, sellsMap.get(dd)+1);
			}
			else {
				sellsMap.put(dd, 1);
			}
		}
		
		Iterator<String> iter =sellsMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = (String)iter.next();
			Integer val = sellsMap.get(key);
			
			ret.add(new TwoTuple<String, Integer>(key, val));
		}
		
		//sort by date
		Collections.sort(ret, new Comparator<TwoTuple<String, Integer>>(){
			public int compare(TwoTuple<String, Integer> sell1, TwoTuple<String, Integer> sell2) {
				SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
				Date dt1 = null;
				Date dt2 = null;
				
				try{
					dt1 = sdf.parse(sell1.first);
					dt2 = sdf.parse(sell2.first);
				}catch(ParseException e) {
					e.printStackTrace();
				}		
				
				if(dt1.getTime() - dt2.getTime() > 0) {
					return 1;
				}
				else if(dt1.getTime() == dt2.getTime() ) {
					return 0;
				}
				else {
					return -1;
				}
		    }
		});
		
		return ret;
	}
	
	
	public class TwoTuple<A, B> {
	    public final A first;
	    public final B second;

	    public TwoTuple(A a, B b){
	        first = a;
	        second = b;
	    }
	}
	
	
	/*
	 * Count the distribution of e-book and paper book
	 */
	public HashMap<String, Integer> formatDistribution(){
		HashMap<String, Integer> formatDis = new HashMap<String, Integer>();
		formatDis.put("kindle", 0);
		formatDis.put("paper", 0);
		
		for(int i=0;i<Detail.size();++i) {
			if(Detail.get(i).getFormat().equals(EBook)) {
				formatDis.put("paper", formatDis.get("paper")+1);
			}
			else if(Detail.get(i).getFormat().equals(PaperBook)) {
				formatDis.put("kindle", formatDis.get("kindle")+1);
			}
		}
		return formatDis;
	}
	

	/*
	 * Get the words and its weight by spliting string.
	 */
	public void WordCloud(String wordCloudStr) {
		ArrayList<String> list = extractMessageByRegular(wordCloudStr);		
		String wordListStr = list.get(0);
		
		ArrayList<String> list2 = extractMessageByRegular2(wordListStr);		
		for(int i=0;i<list2.size();++i) {
			String oneWord = list2.get(i);
			
			ArrayList<String> wordList = extractMessageByRegular3(oneWord);
			String word = wordList.get(0); 
			
			int index = oneWord.lastIndexOf(',');
			String val = oneWord.substring(index+1, oneWord.length());			
			Double valdouble = Double.valueOf(val);			
			WordCloud.put(word, Integer.valueOf(valdouble.intValue()));
		}
	}
	
	/*
	 * Extract content between [].
	 */
	public static ArrayList<String> extractMessageByRegular(String msg){  
		ArrayList<String> list=new ArrayList<String>();  
        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");  
        Matcher m = p.matcher(msg);  
        while(m.find()){  
            list.add(m.group().substring(1, m.group().length()-1));  
        }  
        return list;  
    }  
	
	/*
	 * Extract content between ().
	 */
	public static ArrayList<String> extractMessageByRegular2(String msg){  
		ArrayList<String> list=new ArrayList<String>();  
        Pattern p = Pattern.compile("(\\([^\\)]*\\))");  
        Matcher m = p.matcher(msg);  
        while(m.find()){  
            list.add(m.group().substring(1, m.group().length()-1));  
        }  
        return list;  
    }  
	
	/*
	 * Get the string after the last "," .
	 */
	public static ArrayList<String> extractMessageByRegular3(String msg){  
		ArrayList<String> list=new ArrayList<String>();  
        Pattern p = Pattern.compile("(\\'[^\\']*\\')");  
        Matcher m = p.matcher(msg);  
        while(m.find()){  
            list.add(m.group().substring(1, m.group().length()-1));  
        }  
        return list;  
    }  
	
	
	public Integer getTotal() {
		return Total;
	}
	
	public ArrayList<GoodDetail> getDetail() {
		return Detail;
	}	
	
	public HashMap<String, Integer> getWordCloud(){
		return WordCloud;
	}
	
}
