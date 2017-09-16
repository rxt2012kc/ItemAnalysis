package com.amazon.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException; 

import org.w3c.dom.Document; 
import org.w3c.dom.Element; 
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class xmlParser {
	public static void main(String[] args) { 
		String xml = "<?xml version=\"1.0\" ?><ItemLookupResponse xmlns=\"http://webservices.amazon.com/AWSECommerceService/2011-08-01\"><OperationRequest><HTTPHeaders><Header Name=\"UserAgent\" Value=\"Java/1.8.0_144\"></Header></HTTPHeaders><RequestId>8a93d28d-17f3-49c4-a5ab-9c8b0dc5ac11</RequestId><Arguments><Argument Name=\"AWSAccessKeyId\" Value=\"AKIAJBWJ4SPITVNJPDTQ\"></Argument><Argument Name=\"AssociateTag\" Value=\"renxintao-20\"></Argument><Argument Name=\"IdType\" Value=\"ASIN\"></Argument><Argument Name=\"ItemId\" Value=\"B00JK0BA8C\"></Argument><Argument Name=\"Operation\" Value=\"ItemLookup\"></Argument><Argument Name=\"ResponseGroup\" Value=\"Images\"></Argument><Argument Name=\"Service\" Value=\"AWSECommerceService\"></Argument><Argument Name=\"Timestamp\" Value=\"2017-09-13T16:31:09Z\"></Argument><Argument Name=\"Signature\" Value=\"/ZhVpydihpqB6uHNwegBQe0aqTNRuxs8CJB1f7ea9Kc=\"></Argument></Arguments><RequestProcessingTime>0.0044548260000000</RequestProcessingTime></OperationRequest><Items><Request><IsValid>True</IsValid><ItemLookupRequest><IdType>ASIN</IdType><ItemId>B00JK0BA8C</ItemId><ResponseGroup>Images</ResponseGroup><VariationPage>All</VariationPage></ItemLookupRequest></Request><Item><ASIN>B00JK0BA8C</ASIN><SmallImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL._SL75_.jpg</URL><Height Units=\"pixels\">75</Height><Width Units=\"pixels\">46</Width></SmallImage><MediumImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL._SL160_.jpg</URL><Height Units=\"pixels\">160</Height><Width Units=\"pixels\">97</Width></MediumImage><LargeImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL.jpg</URL><Height Units=\"pixels\">500</Height><Width Units=\"pixels\">304</Width></LargeImage><ImageSets><ImageSet Category=\"primary\"><SwatchImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL._SL30_.jpg</URL><Height Units=\"pixels\">30</Height><Width Units=\"pixels\">18</Width></SwatchImage><SmallImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL._SL75_.jpg</URL><Height Units=\"pixels\">75</Height><Width Units=\"pixels\">46</Width></SmallImage><ThumbnailImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL._SL75_.jpg</URL><Height Units=\"pixels\">75</Height><Width Units=\"pixels\">46</Width></ThumbnailImage><TinyImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL._SL110_.jpg</URL><Height Units=\"pixels\">110</Height><Width Units=\"pixels\">67</Width></TinyImage><MediumImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL._SL160_.jpg</URL><Height Units=\"pixels\">160</Height><Width Units=\"pixels\">97</Width></MediumImage><LargeImage><URL>https://images-na.ssl-images-amazon.com/images/I/51Gx9yhIRxL.jpg</URL><Height Units=\"pixels\">500</Height><Width Units=\"pixels\">304</Width></LargeImage></ImageSet></ImageSets></Item></Items></ItemLookupResponse>";
		xmlParser xmlparser = new xmlParser(xml);
		
		System.out.println("ASIN:" + xmlparser.getASIN());
		System.out.println("ImageURL:" + xmlparser.getImageUrl());
	}
	
	
	/*
	 * The ASIN and ImageURL of book which get by parsering the xml String
	 */
	private String ASIN = new String();
	private String ImageURL = new String();
	
	
	/*
	 * constructor and parser the xml string
	 */
	public xmlParser(String xml) {
		Document doc = stringToXml(xml);		
		Element rootElement = doc.getDocumentElement(); 
		
		NodeList itemsList = rootElement.getElementsByTagName("Items");
		if(itemsList != null) {
			for(int i=0;i<1;++i) {
				Element items = (Element)itemsList.item(i);
				
				NodeList itemList = items.getElementsByTagName("Item");
				if(itemList != null) {
					int itemNum = itemList.getLength();
					for(int j=0;j< itemNum ;++j){		
						Element item = (Element)itemList.item(j);
												
						ASIN = item.getElementsByTagName("ASIN").item(0).getFirstChild().getNodeValue();
						
						NodeList imageList = item.getElementsByTagName("LargeImage");
						if(imageList != null) {
							Element image = (Element)imageList.item(0);
							ImageURL = image.getElementsByTagName("URL").item(0).getFirstChild().getNodeValue();
							break;
						}
					}
				}
			}			
		}
	}
	
	
	/*
	 * Transform String to xml
	 */
	private Document stringToXml(String xml) {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder;
		try{
			builder  =  factory.newDocumentBuilder();
			doc  =  builder.parse(new  ByteArrayInputStream(xml.getBytes())); 
		}catch(ParserConfigurationException e) {
		    //  TODO Auto-generated catch block 
		    e.printStackTrace();
		}catch(SAXException e){
		    //  TODO Auto-generated catch block 
		    e.printStackTrace();
		}catch(IOException e){
		    //  TODO Auto-generated catch block 
		    e.printStackTrace();
		} 
		return doc;
	}
	
	
	public String getASIN() {
		return ASIN;
	}
	
	public String getImageUrl() {
		return ImageURL;
	}
	
}
