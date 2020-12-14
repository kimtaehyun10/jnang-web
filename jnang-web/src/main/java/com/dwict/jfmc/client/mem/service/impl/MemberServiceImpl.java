package com.dwict.jfmc.client.mem.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.com.util.SHA256PasswordEncoder;
import com.dwict.jfmc.client.mem.mapper.MemberMapper;
import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Resource(name = "memberMapper")
	private MemberMapper memberMapper;

	@Override
	public boolean isExistingMember(Member param) {
		return memberMapper.isExistingMember(param);
	}

	@Override
	public Member findExistingMember(Member param) {
		return memberMapper.findExistingMember(param);
	}

	@Override
	public boolean isDuplicatedId(Member param) {
		return memberMapper.isDuplicatedId(param);
	}

	@Override
	public int insert(Member param) {
		final SHA256PasswordEncoder sha256 = new SHA256PasswordEncoder();
		param.setPw(sha256.encrypt(param.getPw()));
		return memberMapper.insert(param);
	}

	@Override
	public int update(Member param) {
		final SHA256PasswordEncoder sha256 = new SHA256PasswordEncoder();
		param.setPw(sha256.encrypt(param.getPw()));
		return memberMapper.update(param);
	}

	@Override
	public int insertForLessThan14(Member param) {
		final SHA256PasswordEncoder sha256 = new SHA256PasswordEncoder();
		param.setPw(sha256.encrypt(param.getPw()));
		return memberMapper.insertForLessThan14(param);
	}

	@Override
	public int password(Member param) {
		final SHA256PasswordEncoder sha256 = new SHA256PasswordEncoder();
		param.setPw(sha256.encrypt(param.getPw()));
		return memberMapper.password(param);
	}

	//세션저장값을 다른곳에공유
	/**
	 * <pre>
	 * //멤버 세션값 (저장)불러오기
	 * request ==> HashMap 변환
	 * </pre>
	 * @param HttpServletRequest
	 * @return HashMap
	 */	
	@Override
	public void memSession(HttpServletRequest request, String userId) {					        
		final HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(120*60);
		final Member member = memberMapper.findById(userId);
		session.setAttribute("member", member);        
	}
	
	/**########################################################
	 * 혁산 API 시작 
	 *#########################################################
	 */
	//혁산 API 회원가입 여부 조회	
	public String apiIsMember(HttpServletRequest request) {					        

		String name 		= request.getParameter("name");
		String birthday 	= request.getParameter("birthday");
		String mobile 		= request.getParameter("mobile"); //폰번호

		if (mobile != null) {
			mobile = mobile.replace("-","");
		}
		
		Map <String,String> maps = new HashMap<>();
		maps.put("name", name);
		maps.put("birthday", birthday);
		maps.put("mobile", mobile);
		
		//회원조회
		maps = memberMapper.isMember(maps);
		String rtn = "";
		Gson gson = new Gson();
		JsonObject jsonobject = new JsonObject();
		JsonObject name1Info = new JsonObject();		
		if (maps == null) {
	        name1Info.addProperty("responseCode", "100");
	        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]회원이 아닙니다.");
		} else {
	        name1Info.addProperty("responseCode", "200");
	        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]시설에 가입된 회원입니다.");
		}
		jsonobject.add("response", name1Info);
		rtn = gson.toJson(jsonobject);
		System.out.println(rtn);
		return rtn;
        
	}
	
	//혁산 API 회원가입	
	public String apiJoinMember(Map<String, Object> requestMap,HttpServletRequest request) {

		/*
		String jsonBody = request.getParameter("request");
		JsonParser jParser = new JsonParser();
		
		JsonObject jObject1 = (JsonObject)jParser.parse(jsonBody);
		JsonObject jObject2 = (JsonObject)jParser.parse("json2");
		*/

		String GENDER	 	= (String) requestMap.get("gender");
							GENDER = (GENDER.equals("male")) ? "M" : "F";
		String PW	 		= (String) requestMap.get("password");
		final SHA256PasswordEncoder sha256 = new SHA256PasswordEncoder();
							PW = sha256.encrypt(PW);
		
		Member param = new Member();
		param.setDupchkKey((String) requestMap.get("conninfo"));
		param.setMemNm((String) requestMap.get("name"));
		param.setSecBirthDate((String) requestMap.get("birthday"));
		param.setGender(GENDER);
		param.setId((String) requestMap.get("id"));
		param.setPw(PW);
		param.setHp((String) requestMap.get("mobile"));
		param.setEmail((String) requestMap.get("email"));
		param.setHomeZip((String) requestMap.get("postCode"));
		param.setHomeAddr((String) requestMap.get("address"));
		param.setSmsYn((String) requestMap.get("smsReceive"));
		param.setEmailYn((String) requestMap.get("emailReceive"));
		param.setEtcNo((String) requestMap.get("legalRepresenter")); //14세미만 대리인성명
		param.setSpecialRemark("개인정보처리동의:"+requestMap.get("agreePrivacy") +", 개인정보 제3자 제공 동의:"+requestMap.get("agreeOffer3Party"));

		//14세미만 가입 항목이 있어서 
		int rtnNo = memberMapper.insertForLessThan14(param);
		
		String rtn = "";
		Gson gson = new Gson();
		JsonObject jsonobject = new JsonObject();
		JsonObject name1Info = new JsonObject();		
		if (rtnNo == 0) {
	        name1Info.addProperty("responseCode", "100");
	        name1Info.addProperty("failCode", "1000");
	        name1Info.addProperty("responseMessage", "회원 가입을 실패하였습니다.");
		} else {
	        name1Info.addProperty("responseCode", "200");
	        name1Info.addProperty("responseMessage", "회원 가입이 완료 되었습니다.");
		}
		jsonobject.add("response", name1Info);
		rtn = gson.toJson(jsonobject);
		System.out.println(rtn);
		return rtn;
		
	}
	
	//혁산 api 회원아이디 중복 조회
	public String apiCheckId(HttpServletRequest request) {					        

		String memID 		= request.getParameter("memberId");
		
		Member param = new Member();
		param.setId(memID);
		Boolean chkIdYN = memberMapper.isDuplicatedId(param);
		String rtn = "";
		Gson gson = new Gson();
		JsonObject jsonobject = new JsonObject();
		JsonObject name1Info = new JsonObject();		
		if (chkIdYN == true) {
	        name1Info.addProperty("responseCode", "100");
	        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]사용할 수 없는 아이디 입니다.");
		} else {
	        name1Info.addProperty("responseCode", "200");
	        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]사용 가능한 아이디 입니다.");
		}
		jsonobject.add("response", name1Info);
		rtn = gson.toJson(jsonobject);
		System.out.println(rtn);
		return rtn;
        
	}
	
	//혁산 API 정회원 전환처리(시설카드 발급)
		public String apiMembership(HttpServletRequest request) {					        

			/*
			String name 		= request.getParameter("name");
			String birthday 	= request.getParameter("birthday");
			String mobile 		= request.getParameter("mobile"); //폰번호
			mobile = mobile.replace("-","");
			
			Map <String,String> maps = new HashMap<>();
			maps.put("name", name);
			maps.put("birthday", birthday);
			maps.put("mobile", mobile);
			
			//회원조회
			maps = memberMapper.isMember(maps);
			String rtn = "";
			Gson gson = new Gson();
			JsonObject jsonobject = new JsonObject();
			JsonObject name1Info = new JsonObject();		
			if (maps == null) {
		        name1Info.addProperty("responseCode", "100");
		        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]회원이 아닙니다.");
			} else {
		        name1Info.addProperty("responseCode", "200");
		        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]시설에 가입된 회원입니다.");
			}
			jsonobject.add("response", name1Info);
			rtn = gson.toJson(jsonobject);
			System.out.println(rtn);
			*/
			return null;
	        
		}
		
	
}
