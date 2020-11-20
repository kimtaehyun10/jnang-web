package com.dwict.jfmc.client.board.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Alias("price")
public class Price extends BoardCommon {

	@Column(name = "COMPLETE_YN")
	private String completeYn;

	@Column(name = "ORDER_DEPART")
	private String orderDepart;

	@Column(name = "CEO")
	private String ceo;

	@Column(name = "BUSI_NUMBER")
	private String busiNumber;

	@Column(name = "COM_NAME")
	private String comName;

	@Column(name = "EXPECTED_PRICE")
	private int expectedPrice;

	@Column(name = "BASE_PRICE")
	private int basePrice;

	@Column(name = "PRICE_1")
	private int price1;

	@Column(name = "PRICE_2")
	private int price2;

	@Column(name = "PRICE_3")
	private int price3;
}
