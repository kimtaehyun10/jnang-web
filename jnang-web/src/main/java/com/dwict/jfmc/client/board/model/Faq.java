package com.dwict.jfmc.client.board.model;

import java.util.List;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Alias("faq")
public class Faq {

	@Column(name = "CMS_CD")
	private String cmsCd;

	@Column(name = "BRD_NO")
	private int brdNo;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CONT")
	private String cont;

	@Column(name = "ATTACH_ID")
	private String attachId;

	private List<Attach> attach;

}
