package com.amazon.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {
	
	@XmlElement(name="Item")
	List<Item> items;

	@XmlElement(name="TotalResults")
	int totalResults;
	
	@XmlElement(name="TotalPages")
	int totalPages;
}
