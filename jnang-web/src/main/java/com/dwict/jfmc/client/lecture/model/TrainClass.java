package com.dwict.jfmc.client.lecture.model;

import java.util.List;

import javax.persistence.Column;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("trainClass")
public class TrainClass {

	@Column(name = "COMCD")
	private String comcd;
	
	@Column(name = "COMNM")
	private String comnm;
	
	@Column(name = "CLASS_CD")
	private String classCd;

	@Column(name = "USER_NO")
	private String userNo;
	private String userNm;

	@Column(name = "PART_CD")
	private String partCd;
	private String partCdNm;

	@Column(name = "SPORTS_CD")
	private String sportsCd;
	private String sportsCdNm;

	@Column(name = "MSPORTS_CD")
	private String msportsCd;
	private String msportsCdNm;

	@Column(name = "CLASS_NM")
	private String classNm;

	@Column(name = "PLACE_CD")
	private String placeCd;
	private String placeCdNm;

	@Column(name = "GRADE_CD")
	private String gradeCd;
	private String gradeCdNm;

	@Column(name = "PROGRESS_CD")
	private String progressCd;
	private String progressCdNm;

	@Column(name = "STYLE")
	private String style;
	private String styleNm;

	@Column(name = "TRAIN_DAY")
	private String trainDay;
	private String trainDayNm;

	@Column(name = "TRAIN_STIME")
	private String trainStime;
	private String trainStimeNm;

	@Column(name = "TRAIN_ETIME")
	private String trainEtime;
	private String trainEtimeNm;

	private String trainTimeNm;

	@Column(name = "CS_CAPA")
	private int csCapa;

	@Column(name = "WEB_CAPA")
	private int webCapa;

	@Column(name = "CAPA")
	private int capa;
	
	@Column(name = "WEB_USER")
	private int webUser;
	
	@Column(name = "REMAIN_CAPA")
	private int remainCapa;
	
	@Column(name = "CLASS_DESC")
	private String classDesc;

	@Column(name = "CLASS_NOTE")
	private String classNote;

	@Column(name = "CLASS_READY")
	private String classReady;

	@Column(name = "MAT_COST")
	private String matCost;

	@Column(name = "MAT_TEACH")
	private String matTeach;

	@Column(name = "BIGO")
	private String bigo;

	@Column(name = "CLASS_OBJ")
	private String classObj;

	@Column(name = "CLASS_SOBJ")
	private String classSobj;

	@Column(name = "CLASS_EOBJ")
	private String classEobj;

	@Column(name = "CHILD_YN")
	private String childYn;

	@Column(name = "DCRATE_YN")
	private String dcrateYn;

	@Column(name = "WEB_DISPLAY")
	private String webDisplay;

	@Column(name = "SORT_ORDER")
	private String sortOrder;

	@Column(name = "USE_YN")
	private String useYn;
	private String useYnNm;

	@Column(name = "CLASS_PHOTO")
	private String classPhoto;

	@Column(name = "NEW_CHECK")
	private String newCheck;

	@Column(name = "TDATE_USE_YN")
	private String tdateUseYn;

	@Column(name = "TC_SDATE")
	private String tcSdate;

	@Column(name = "TC_EDATE")
	private String tcEdate;

	@Column(name = "PRG_COUNT")
	private int prgCount;

	@Column(name = "REP_SDATE")
	private String repSdate;
	
	@Column(name = "REP_EDATE")
	private String repEdate;
	
	@Column(name = "WEB_TRAIN_ADD_TTL")
	private String webTrainAddTtl;
	
	private int saleCount;

	private List<ProgramItem> programItem;
	private Grpcd grpcd;

}
