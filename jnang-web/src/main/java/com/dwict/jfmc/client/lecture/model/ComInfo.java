package com.dwict.jfmc.client.lecture.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("comInfo")
public class ComInfo {

	@Column(name = "COMCD")
	private String comcd;

	@Column(name = "COMNM")
	private String comnm;

	@Column(name = "TEL1")
	private String tel1;

	@Column(name = "TEL2")
	private String tel2;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "ZIPCD")
	private String zipcd;

	@Column(name = "ADDR")
	private String addr;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "BOSSNM")
	private String bossnm;

	@Column(name = "BIZNO")
	private String bizno;

	@Column(name = "UPTAE")
	private String uptae;

	@Column(name = "JONGMOK")
	private String jongmok;

	@Column(name = "CHARGER")
	private String charger;

	@Column(name = "CHARGERTEL")
	private String chargertel;

	@Column(name = "HEAD_COMCD")
	private String headComcd;

}
