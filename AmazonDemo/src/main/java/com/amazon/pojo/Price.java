package com.amazon.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Price {

	@XmlElement(name="Amount")
	private String amount;

	@XmlElement(name="CurrencyCode")
	private String currencyCode;

	@XmlElement(name="FormattedPrice")
	private String formattedPrice;

}
