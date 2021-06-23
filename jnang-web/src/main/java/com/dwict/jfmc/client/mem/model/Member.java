package com.dwict.jfmc.client.mem.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;

import org.apache.ibatis.type.Alias;

import com.dwict.jfmc.client.com.util.LocalDatePersistenceConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Alias("member")
public class Member {

	@Column(name = "MEM_NO")
	private String memNo;

	@Column(name = "COMCD")
	private String comcd;

	@Column(name = "MEM_NM")
	private String memNm;

	@Column(name = "ENG_NM")
	private String engNM;

	@Column(name = "MEM_TYPE")
	private String memType;

	@Column(name = "MEM_GBN")
	private String memGbn;

	@Column(name = "GRADE_CD")
	private String gradeCd;

	@Column(name = "RMEM_NO")
	private String rmemNo;

	@Column(name = "RMEM_GBN")
	private String rmemGbn;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "ID")
	private String id;

	@Column(name = "NICKNAME")
	private String nickname;

	@Column(name = "PW")
	private String pw;

	@Column(name = "JOIN_DATE")
	private String joinDate;

	@Column(name = "SEC_BIRTH_DATE")
	private String secBirthDate;

	@Column(name = "BIRTH_MMDD")
	private String birthMmdd;

	@Column(name = "BIRTH_SEC")
	private String birthSec;

	@Column(name = "WED_DATE")
	private String wedDate;

	@Column(name = "WED_MMDD")
	private String wedMmdd;

	@Column(name = "HP")
	private String hp;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "HOME_TEL")
	private String homeTel;

	@Column(name = "HOME_ZIP")
	private String homeZip;

	@Column(name = "HOME_ADDR")
	private String homeAddr;

	@Column(name = "OFFI_NM")
	private String offiNm;

	@Column(name = "OFFI_ADDR")
	private String offiAddr;

	@Column(name = "OFFI_TEL")
	private String offiTel;

	@Column(name = "OFFI_ZIP")
	private String offiZip;

	@Column(name = "OFFI_POSI")
	private String offiPosi;

	@Column(name = "DM_SEND")
	private String dmSend;

	@Column(name = "DM_YN")
	private String dmYn;

	@Column(name = "EMAIL_YN")
	private String emailYn;

	@Column(name = "SMS_YN")
	private String smsYn;

	@Column(name = "AGREE_YN")
	private String agreeYn;

	@Column(name = "CAR_NO")
	private String carNo;

	@Column(name = "BUS_CD")
	private String busCd;

	@Column(name = "HOBBY_CD")
	private String hobbyCd;

	@Column(name = "JOB_CD")
	private String jobCd;

	@Column(name = "JOIN_MOTIVE")
	private String joinMotive;

	@Column(name = "JOIN_INVITE")
	private String joinInvite;

	@Column(name = "MIL_MONEY")
	private long milMoney;

	@Column(name = "AREA_TYPE")
	private String areaType;

	@Column(name = "TICKET_MEM_NO")
	private String ticketMemNo;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "STATUS")
	private String status;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "VISIT_DT")
	private LocalDateTime visitDt;

	@Column(name = "DISCOUNT_CD")
	private String discountCd;

	@Column(name = "DCCONFIRM_YN")
	private String dcconfirmYn;

	@Column(name = "SPECIAL_CD")
	private String specialCd;

	@Column(name = "SPECIAL_REMARK")
	private String specialRemark;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "WRITE_DH")
	private LocalDateTime writeDh;

	@Column(name = "WRITER")
	private String writer;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "CHNG_DH")
	private LocalDateTime chngDh;

	@Column(name = "CHNGR")
	private String chngr;

	@Column(name = "IPIN")
	private String ipin;

	@Column(name = "DUPCHK_KEY")
	private String dupchkKey;

	@Column(name = "ETC_NO")
	private String etcNo;
	
	@Column(name = "DPSTR_NM")
	private String dpstrNm;
	
	@Column(name = "BANK_NM")
	private String bankNm;
	
	@Column(name = "ACOUNT_NUM")
	private String acountNum;

	@Column(name = "ADDRESS_YN")
	private String addressYn;

	@Column(name = "OLD_PW")
	private String oldPw;

	@Column(name = "CARD_NO")
	private String cardNo;

	public Member(String memNm, String secBirthDate, String gender) {
		this(memNm, secBirthDate, gender, null);
	}

	public Member(String memNm, String secBirthDate, String gender, String hp) {
		this.memNm = memNm;
		this.secBirthDate = secBirthDate;
		this.gender = gender;
		this.hp = hp;
	}



	

}
