package com.dwict.jfmc.client.board.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Alias("brd")
public class Brd extends BoardCommon {

	@Column(name = "UP_BRD_NO")
	private int upBrdNo;

	@Column(name = "DEP")
	private int dep;

	@Column(name = "SECRET_YN")
	private String secretYn;

	@Column(name = "REP_YN")
	private String repYn;

}
