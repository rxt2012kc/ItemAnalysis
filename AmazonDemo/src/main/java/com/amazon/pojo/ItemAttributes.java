package com.amazon.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemAttributes {

	@XmlElement(name="Title")
	private String title;

	@XmlElement(name="ListPrice")
	private Price listPrice;


}
