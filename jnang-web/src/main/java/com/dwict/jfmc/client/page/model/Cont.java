package com.dwict.jfmc.client.page.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;

import org.apache.ibatis.type.Alias;

import com.dwict.jfmc.client.com.util.LocalDatePersistenceConverter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Alias("cont")
public class Cont extends Menu {

	@Column(name = "UP_CMS_NM")
	private String upCmsNm;

	@Column(name = "CONT_NO")
	private int contNo;

	@Column(name = "OPER_ID")
	private String operId;

	@Column(name = "CONT")
	private String cont;

	@Column(name = "IPS")
	private String ips;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "REGDT")
	private LocalDate regdt;

	private int child;
	private List<Cont> childs;

}
