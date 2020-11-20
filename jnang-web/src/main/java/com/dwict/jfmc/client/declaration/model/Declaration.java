package com.dwict.jfmc.client.declaration.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Alias("declaration")
public class Declaration {

	@Column(name = "D_NO")
	private int dNo;
	
	@Column(name = "D_TYPE")
	private int dType;
	
	@Column(name = "D_TITLE")
	private String dTitle;
	
	@Column(name = "D_WHO")
	private String dWho;
	
	@Column(name = "D_WHEN")
	private String dWhen;
	
	@Column(name = "D_WHERE")
	private String dWhere;
	
	@Column(name = "D_CONTENTS")
	private String dContents;
	
	@Column(name = "D_ACQUAINTANCE")
	private String dAcquaintance;
	
	@Column(name = "D_CONFIRM")
	private String dConfirm;
	
	@Column(name = "D_INSPECTOR")
	private String dInspector;
	
	@Column(name = "D_TIME")
	private int dTime;
	
	@Column(name = "D_PASS")
	private String dPass;
	
	@Column(name = "D_DATE")
	private String dDate;
	
	@Column(name = "D_FILE")
	private String dFile;
	
	@Column(name = "D_AGREE")
	private String dAgree;
	
	@Column(name = "CMS_CD")
	private String cmsCd;
	
	@Column(name = "D_DEL")
	private String dDel;
	
	@Column(name = "D_EDIT")
	private String dEdit;
	
	@Column(name = "D_CODE")
	private String dCode;
}
