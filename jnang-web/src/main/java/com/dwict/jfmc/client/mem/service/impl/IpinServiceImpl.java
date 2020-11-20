package com.dwict.jfmc.client.mem.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.IpinService;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.security.service.ErrorService;

import Kisinfo.Check.IPINClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("ipinService")
public class IpinServiceImpl implements IpinService {

	@Value("#{appConfig['nice.ipin.siteCode']}")
	private String sSiteCode;

	@Value("#{appConfig['nice.ipin.sitePwd']}")
	private String sSitePwd;

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "errorService")
	private ErrorService errorService;

	@Override
	public ModelAndView result(HttpServletRequest request, ModelAndView modelAndView) {

		final HttpSession session = request.getSession(false);
		final String enc_data = session.getAttribute("enc_data").toString();

		final IPINClient pClient = new IPINClient();
		final int iRtn = pClient.fnResponse(sSiteCode, sSitePwd, requestReplace(enc_data, "encodeData"));
		String sRtnMsg = null;
		//final String sVNumber			= pClient.getVNumber();			// 가상주민번호 (13byte, 영숫자 조합)
		final String sName = pClient.getName();							// 성명 (EUC-KR)
		//final String sAgeCode			= pClient.getAgeCode();			// 연령대코드 (0~7: 가이드 참조)
		final String sGenderCode = pClient.getGenderCode();				// 성별 (0:여성, 1: 남성)
		final String sBirthDate = pClient.getBirthDate();				// 생년월일 (YYYYMMDD)
		//final String sNationalInfo	= pClient.getNationalInfo();	// 내/외국인코드 (0:내국인, 1:외국인)
		//final String sCPRequestNum	= pClient.getCPRequestNO();		// CP 요청번호
		//final String sDupInfo			= pClient.getDupInfo();	

		final Member member = new Member(sName, sBirthDate, sGenderCode.equals("1") ? "M" : "F");

		switch (iRtn) {
		case 1:
			sRtnMsg = "정상 처리되었습니다.";
			modelAndView = verifyForJoin(request, member);
			break;
		case -1:
		case -4:
			sRtnMsg = "복호화 시스템 오류 :<br> 귀사 서버 환경에 맞는 모듈을 이용해주십시오.<br>오류가 지속되는 경우 iRtn 값, 서버 환경정보, 사이트코드를 기재해 문의주시기 바랍니다.";
			log.debug(sRtnMsg);
			modelAndView = errorService.error("500");
			break;
		case -6:
			sRtnMsg = "복호화 처리 오류: 당사에서 이용하는 charset인 EUC-KR이 정상적으로 받아지는 확인해주십시오. <br>오류가 지속되는 경우, 개발 가이드의 <b>\"결과 데이터 확인 방법\"</b>을 참고해주시기 바랍니다.";
			log.debug(sRtnMsg);
			modelAndView = errorService.error("500");
			break;
		case -9:
			sRtnMsg = "입력 정보 오류: 복호화 함수에 입력된 파라미터 값을 확인해주십시오.<br>오류가 지속되는 경우, 함수 실행 직전 각 파라미터 값을 로그로 출력해 발송해주시기 바랍니다.";
			log.debug(sRtnMsg);
			break;
		case -12:
			sRtnMsg = "CP 패스워드 불일치: IPIN 서비스 사이트패스워드를 확인해주시기 바랍니다.";
			log.debug(sRtnMsg);
			modelAndView = errorService.error("500");
			break;
		case -13:
			sRtnMsg = "CP 요청번호 불일치: 세션에 저장된 CP요청번호(sCPRequest) 값을 확인해주시기 바랍니다.";
			log.debug(sRtnMsg);
			modelAndView = errorService.error("500");
			break;
		default:
			sRtnMsg = "기타오류: iRtn 값 확인 후 NICE평가정보 전산 담당자에게 문의해주시기 바랍니다.";
			log.error(sRtnMsg);
			modelAndView = errorService.error("500");
			break;
		}
		return modelAndView;
	}

	private String requestReplace (String paramValue, String gubun) {
		String result = null;
		if (paramValue != null) {
			paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			paramValue = paramValue.replaceAll("\\*", "");
			paramValue = paramValue.replaceAll("\\?", "");
			paramValue = paramValue.replaceAll("\\[", "");
			paramValue = paramValue.replaceAll("\\{", "");
			paramValue = paramValue.replaceAll("\\(", "");
			paramValue = paramValue.replaceAll("\\)", "");
			paramValue = paramValue.replaceAll("\\^", "");
			paramValue = paramValue.replaceAll("\\$", "");
			paramValue = paramValue.replaceAll("'", "");
			paramValue = paramValue.replaceAll("@", "");
			paramValue = paramValue.replaceAll("%", "");
			paramValue = paramValue.replaceAll(";", "");
			paramValue = paramValue.replaceAll(":", "");
			paramValue = paramValue.replaceAll("-", "");
			paramValue = paramValue.replaceAll("#", "");
			paramValue = paramValue.replaceAll("--", "");
			paramValue = paramValue.replaceAll("-", "");
			paramValue = paramValue.replaceAll(",", "");
			if (gubun != "encodeData") {
				paramValue = paramValue.replaceAll("\\+", "");
				paramValue = paramValue.replaceAll("/", "");
				paramValue = paramValue.replaceAll("=", "");
			}
			result = paramValue;
		}
		return result;
	}

	public ModelAndView verifyForJoin(HttpServletRequest request, Member member) {
		final ModelAndView mav = new ModelAndView();
		final HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("join.adult") != null) {
			if((boolean) session.getAttribute("join.adult") == true) {
				final boolean isExistingMember = memberService.isExistingMember(member);
				if (isExistingMember) {
					member = memberService.findExistingMember(member);
					if(member.getId() == null || member.getId().equals("")) {
						session.setAttribute("join.exist", true);
						session.setAttribute("join.member", member);
						mav.setViewName("redirect:/mem/join4");
					} else {
						session.setAttribute("join.exist", true);
						session.setAttribute("join.member", member);
						mav.setViewName("redirect:/mem/join6");
					}
				} else {
					session.setAttribute("join.exist", false);
					session.setAttribute("join.member", member);
					mav.setViewName("redirect:/mem/join4");
				}
			} else {
				session.setAttribute("join.exist", false);
				session.setAttribute("join.member", member);
				mav.setViewName("redirect:/mem/join4");
			}
			return mav;
		} else {
			return errorService.error("403");
		}
	}
}
