package com.amazon.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

	@XmlElement(name="ASIN")
	private String asin;

	@XmlElement(name="DetailPageURL")
	private String detailPageURL;

	@XmlElement(name="MediumImage")
	private Image mediumImage;

	@XmlElement(name="ItemAttributes")
	private ItemAttributes itemAttributes;

	@XmlElement(name="Offers")
	private Offers offers;



}
