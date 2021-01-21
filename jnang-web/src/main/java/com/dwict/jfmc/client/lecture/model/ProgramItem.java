package com.dwict.jfmc.client.lecture.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("programItem")
public class ProgramItem extends TrainClass {

	@Column(name = "ITEM_CD")
	private String itemCd;

	@Column(name = "ITEM_NM")
	private String itemNm;

	@Column(name = "COST_AMT")
	private int costAmt;
	
	@Column(name="SEQ")
	private int seq;

	@Column(name = "SALE_AMT")
	private int saleAmt;

	@Column(name = "MONTH_CNT")
	private int monthCnt;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "VAT_YN")
	private String vatYn;

	@Column(name = "USE_CNT")
	private int useCnt;

	@Column(name = "UPD_YN")
	private String updYn;

	@Column(name = "TIME_CHECKYN")
	private String timeCheckyn;

	@Column(name = "S_OLD")
	private String sOld;

	@Column(name = "E_OLD")
	private String eOld;

	@Column(name = "ADULT_GUBN")
	private String adultGubn;

}
