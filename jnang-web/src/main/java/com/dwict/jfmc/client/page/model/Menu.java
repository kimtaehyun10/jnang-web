package com.dwict.jfmc.client.page.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Alias("menu")
public class Menu {

	@Column(name = "CMS_CD")
	private String cmsCd;

	@Column(name = "UP_CMS_CD")
	private String upCmsCd;

	@Column(name = "SGRP")
	private String sgrp;

	@Column(name = "CMS_NM")
	private String cmsNm;

	@Column(name = "MTYPE")
	private String mType;

	@Column(name = "LTYPE")
	private String lType;

	@Column(name = "ODR")
	private int odr;

	@Column(name = "DEP")
	private int dep;

	@Column(name = "USE_YN")
	private String useYn;

	@Column(name = "WDP_YN")
	private String wdpYn;

}
