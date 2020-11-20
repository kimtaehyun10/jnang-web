package com.dwict.jfmc.client.board.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;

import com.dwict.jfmc.client.com.util.LocalDatePersistenceConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class Author {

	@Column(name = "IPS")
	private String ips;

	@Column(name = "REG_ID")
	private String regId;
	private String regNm;
	
	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "REG_OFF_NM")
	private String regOffNm;
	
	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "REG_OFFM_TELNO")
	private String regOffmTelno;
	
	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "REG_DT")
	private LocalDate regDt;

	@Column(name = "MOD_ID")
	private String modId;
	private String modNm;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "MOD_DT")
	private LocalDate modDt;

	@Column(name = "DEL_ID")
	private String delId;
	private String delNm;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "DEL_DT")
	private LocalDate delDt;

	private String regDtYmd;

	private String modDtYmd;

	private String delDtYmd;

}
