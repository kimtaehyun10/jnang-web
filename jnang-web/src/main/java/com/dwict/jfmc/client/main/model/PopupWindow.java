package com.dwict.jfmc.client.main.model;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import com.dwict.jfmc.client.com.model.AtchFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("popupWindow")
public class PopupWindow extends AtchFile {

	@Column(name = "NT_NO")
	private String ntNo;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "IMG_TXT")
	private String imgTxt;

}
