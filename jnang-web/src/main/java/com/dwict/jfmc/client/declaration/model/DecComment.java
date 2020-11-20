package com.dwict.jfmc.client.declaration.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Alias("decComment")
public class DecComment {

	@Column(name = "CMS_CD")
	private String cmsCd;
	
	@Column(name = "D_NO")
	private int dNo;
	
	@Column(name = "RT_NO")
	private int rtNo;
	
	@Column(name = "PT_CD")
	private String ptCd;
	
	@Column(name = "OPER_ID")
	private String operId;
	
	@Column(name = "NM")
	private String nm;
	
	@Column(name = "TEL")
	private String tel;
	
	@Column(name = "CONTS")
	private String conts;
	
	@Column(name = "REGDT")
	private String regdt;
	
	@Column(name = "DEL_YN")
	private String delYn;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "EDIT_YN")
	private String editYn;
	
	@Column(name = "UP_FILE")
	private String upFile;
	
}
