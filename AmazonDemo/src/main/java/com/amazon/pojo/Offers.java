package com.amazon.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Offers {

	@XmlElement(name="MoreOffersUrl")
	private String moreOffersUrl;
	
	@XmlElement(name="Offer")
	private List<Offer> offers;
	
	
}
