package com.dwict.jfmc.client.board.model;

import java.time.LocalDateTime;

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
@Alias("attach")
public class Attach {

	@Column(name = "ATTACH_ID")
	private String attachId;

	@Column(name = "ATTACH_NO")
	private int attachNo;

	@Column(name = "FILE_NM")
	private String fileNm;

	@Column(name = "VIR_FILE_NM")
	private String virFileNm;

	@Column(name = "FILE_EXTSN")
	private String fileExtsn;

	@Column(name = "READ_CNT")
	private int readCnt;

	@Column(name = "REG_ID")
	private String regId;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "REG_DT")
	private LocalDateTime regDt;

}
