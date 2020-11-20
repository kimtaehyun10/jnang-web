package com.dwict.jfmc.client.board.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;

import org.apache.ibatis.type.Alias;

import com.dwict.jfmc.client.com.util.LocalDatePersistenceConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Alias("boardCommon")
public class BoardCommon extends Author {

	@Column(name = "CMS_CD")
	private String cmsCd;

	@Column(name = "BRD_NO")
	private int brdNo;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "ROWNUM")
	private int rownum;

	@Column(name = "BRD_PWD")
	private String brdPwd;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CONT")
	private String cont;

	@Column(name = "HIT")
	private int hit;

	@Column(name = "DEL_YN")
	private String delYn;

	@Column(name = "ATTACH_ID")
	private String attachId;

	private List<Attach> attach;

}
