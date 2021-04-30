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
	@Override
	public String apiIsMember(HttpServletRequest request) {					        

		String name 		= request.getParameter("name").trim();
		String birthday 	= request.getParameter("birthday").trim();
		String mobile 		= request.getParameter("mobile").trim(); //폰번호
		

		if (mobile != null) {
			mobile = mobile.replace("-","");
		}
		if (birthday != null) {
			birthday = birthday.replace("-","");
		}
		
		Map <String,String> maps = new HashMap<>();
		maps.put("name", name);
		maps.put("birthday", birthday);
		maps.put("mobile", mobile);
		
		//생일 암호화 바로 DB에 replace오류 나서
		//List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();

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
	@Override
	public String apiJoinMember(Map<String, Object> requestMap,  HttpServletRequest request) {
		Member memVo = new Member();
		
		/*
		System.out.println("ㅎㅎㅎㅎ requestMap:"+ requestMap);
		System.out.println("ㅎㅎㅎㅎ requestMap:"+ request.getQueryString());
		String conninfo = (String) requestMap.get("conninfo");

		String rs = request.getParameter("request").trim();
		JsonObject jParser = JsonParser.parseString(rs).getAsJsonObject();
		JsonObject jObject1 = jParser.getAsJsonObject("request");
		System.out.println("service : " + jObject1.get("conninfo").toString());
		System.out.println("birthday : " + jObject1.get("birthday"));
			
		String GENDER	 	= jObject1.get("gender").toString();
		GENDER = (GENDER.equals("male")) ? "M" : "F";
		String PW	 		= jObject1.get("password").toString();
		final SHA256PasswordEncoder sha256 = new SHA256PasswordEncoder();
				String enPW = sha256.encrypt(PW);
		String birthday		= jObject1.get("birthday").toString();
				birthday = birthday.replace("-","");

		memMap.setDupchkKey(jObject1.get("conninfo").toString());
		memMap.setMemNm(jObject1.get("name").toString());
		memMap.setSecBirthDate(birthday);
		memMap.setGender(GENDER);
		memMap.setId(jObject1.get("id").toString());
		memMap.setPw(enPW);
		memMap.setHp(jObject1.get("mobile").toString());
		memMap.setEmail(jObject1.get("email").toString());
		memMap.setHomeZip(jObject1.get("postCode").toString());
		memMap.setHomeAddr(jObject1.get("address").toString());
		memMap.setSmsYn(jObject1.get("smsReceive").toString());
		memMap.setEmailYn(jObject1.get("emailReceive").toString());
		memMap.setEtcNo(jObject1.get("legalRepresenter").toString()); //14세미만 대리인성명
		memMap.setSpecialRemark("개인정보처리동의:"+requestMap.get("agreePrivacy") +", 개인정보 제3자 제공 동의:"+requestMap.get("agreeOffer3Party"));
		memMap.setWriter("서울시민API");
		*/
		
		String GENDER	 	= request.getParameter("gender");
							GENDER = (GENDER.equals("male")) ? "M" : "F";
		String PW	 		= request.getParameter("password");
		final SHA256PasswordEncoder sha256 = new SHA256PasswordEncoder();
							String enPW = sha256.encrypt(PW);
		String birthday		= request.getParameter("birthday");
							birthday = birthday.replace("-","");
		
		memVo.setDupchkKey(request.getParameter("conninfo"));
		memVo.setMemNm(request.getParameter("name"));
		memVo.setSecBirthDate(birthday);
		memVo.setGender(GENDER);
		memVo.setId(request.getParameter("id"));
		memVo.setPw(enPW);
		memVo.setHp(request.getParameter("mobile"));
		memVo.setEmail(request.getParameter("email"));
		memVo.setHomeZip(request.getParameter("postCode"));
		memVo.setHomeAddr(request.getParameter("address"));
		memVo.setSmsYn(request.getParameter("smsReceive"));
		memVo.setEmailYn(request.getParameter("emailReceive"));
		memVo.setEtcNo(request.getParameter("legalRepresenter")); //14세미만 대리인성명
		memVo.setSpecialRemark("개인정보처리동의:"+requestMap.get("agreePrivacy") +", 개인정보 제3자 제공 동의:"+requestMap.get("agreeOffer3Party"));
		memVo.setWriter("서울시민API");
		
		
		String rtn = "";
		Gson gson = new Gson();
		JsonObject jsonobject = new JsonObject();
		JsonObject name1Info = new JsonObject();	
		
		//중복가입 정보 첵크
		String sRtn = apiIsMember(request);
		if (sRtn.contains("\"200\"")) {
			name1Info.addProperty("responseCode", "100");
	        name1Info.addProperty("failCode", "1000");
	        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]시설에 가입된 회원입니다.");
			jsonobject.add("response", name1Info);
			rtn = gson.toJson(jsonobject);
			System.out.println(rtn);
			return rtn;
		}
		
		
		//}
		//14세미만 가입 항목이 있어서
		int rtnNo = 0;
		if (PW.length() >= 8) {
			rtnNo = memberMapper.insertForLessThan14(memVo);
		}
		
		if (rtnNo == 0) {
	        name1Info.addProperty("responseCode", "100");
	        name1Info.addProperty("failCode", "1000");
	        if (PW.length() < 8) {
	        	name1Info.addProperty("responseMessage", "회원 가입 실패 , 비빌번호는 8~16자 이어야 합니다. ");
	        } else {
	        	name1Info.addProperty("responseMessage", "회원 가입을 실패하였습니다.");
	        }
	        
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
	@Override
	public String apiCheckId(HttpServletRequest request) {					        

		String memID 		= request.getParameter("memberId").trim();
		
		Member param = new Member();
		param.setId(memID);
		Boolean chkIdYN = memberMapper.isDuplicatedId(param);
		String rtn = "";
		Gson gson = new Gson();
		JsonObject jsonobject = new JsonObject();
		JsonObject name1Info = new JsonObject();		
		if (chkIdYN == true || memID.length() < 5) {
	        name1Info.addProperty("responseCode", "100");
	        if (memID.length() < 5) {
	        	name1Info.addProperty("responseMessage", "[중랑구시설관리공단]아이디는 5~20자 이어야 합니다.");
	        } else {
	        	name1Info.addProperty("responseMessage", "[중랑구시설관리공단]사용할 수 없는 아이디 입니다.");
	        }
			/*
			name1Info.addProperty("conninfo", "269A96546C0E7D7EB6957405281063B4269A96546C0E7D7EB6957405281063B4");
			name1Info.addProperty("name", "이름2");
			name1Info.addProperty("birthday", "1978-12-19");
			name1Info.addProperty("gender", "male");
			name1Info.addProperty("password", "pwd1234");
			name1Info.addProperty("id", "powerjyc9");
			name1Info.addProperty("mobile", "010-4291-4292");
			name1Info.addProperty("email", "powerjyc@email");
			name1Info.addProperty("postCode", "11111");
			name1Info.addProperty("address", "주소주소주소주소ㅜㅈ소ㅜ 1");
			name1Info.addProperty("smsReceive", "Y");
			name1Info.addProperty("emailReceive", "Y");
			name1Info.addProperty("legalRepresenter", "대리인 이름");
			name1Info.addProperty("agreePrivacy", "Y");
			name1Info.addProperty("agreeOffer3Party", "Y");
			*/
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
	@Override
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


	@Override
	public int updateLastLogin(String userId) {
		return memberMapper.updateLastLogin(userId);
		
	}

	
}
