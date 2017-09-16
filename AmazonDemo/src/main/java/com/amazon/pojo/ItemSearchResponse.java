package com.amazon.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ItemSearchResponse", namespace = "http://webservices.amazon.com/AWSECommerceService/2013-08-01")
public class ItemSearchResponse {
	
	@XmlElement(name="OperationRequest")
	private OperationRequest operationRequest;

	@XmlElement(name="Items")
	private Items items;

	public OperationRequest getOperationRequest() {
		return operationRequest;
	}

	public void setOperationRequest(OperationRequest operationRequest) {
		this.operationRequest = operationRequest;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	
}
