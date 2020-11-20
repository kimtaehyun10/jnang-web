package com.dwict.jfmc.client.com.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Alias("atchFile")
public class AtchFile {

	@NonNull
	@Column(name = "ATCH_FILE_ID")
	private String atchFileId;

	@Column(name = "FILE_SN")
	private String fileSn;

	@Column(name = "FILE_STRE_COURS")
	private String fileStreCours;

	@Column(name = "STRE_FILE_NM")
	private String streFileNm;

	@Column(name = "ORIGNL_FILE_NM")
	private String orignlFileNm;

	@Column(name = "FILE_EXTSN")
	private String fileExtsn;

	@Column(name = "FILE_CN")
	private String fileCn;

	@Column(name = "FILE_SIZE")
	private String fileSize;

}
