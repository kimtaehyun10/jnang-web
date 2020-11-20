package com.dwict.jfmc.client.lecture.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Alias("grpcd")
public class Grpcd {

	@Column(name = "COMCD")
	private String comcd;

	@Column(name = "GRP_CD")
	private String grpCd;

	@Column(name = "CD")
	private String cd;

	@Column(name = "CD_NM")
	private String cdNm;

	@Column(name = "SORT_ORDER")
	private int sortOrder;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "ITEM1")
	private String item1;

	@Column(name = "ITEM2")
	private String item2;

	@Column(name = "ITEM3")
	private String item3;

	@Column(name = "ITEM4")
	private String item4;

	@Column(name = "ITEM5")
	private String item5;

	@Column(name = "UPD_YN")
	private String updYn;

	@Column(name = "DEL_YN")
	private String delYn;

	@Column(name = "USE_YN")
	private String useYn;

	@Column(name = "REC_SDATE")
	private String recSdate;

	@Column(name = "REC_EDATE")
	private String recEdate;

	@Column(name = "REP_SDATE")
	private String repSdate;

	@Column(name = "REP_EDATE")
	private String repEdate;

	@Column(name = "STARTDATE")
	private String startdate;

	public Grpcd(String comcd, String cd) {
		this.comcd = comcd;
		this.cd = cd;
	}

}
