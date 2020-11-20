package com.dwict.jfmc.client.board.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Alias("ntc")
public class Ntc extends BoardCommon {

	@Column(name = "NTC_YN")
	private String ntcYn;

}
