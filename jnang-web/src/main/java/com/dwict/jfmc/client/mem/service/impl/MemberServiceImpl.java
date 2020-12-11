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
	//회원가입 여부 조회	
	public String isMember(HttpServletRequest request) {					        

		String name 		= request.getParameter("name");
		String birthday 	= request.getParameter("birthday");
		String mobile 		= request.getParameter("mobile"); //폰번호
		mobile = mobile.replace("-","");
		
		Map <String,String> maps = new HashMap<>();
		maps.put("name", name);
		maps.put("birthday", birthday);
		maps.put("mobile", mobile);
		
		maps = memberMapper.isMember(maps);
		String rtn = "";
		Gson gson = new Gson();
		JsonObject jsonobject = new JsonObject();
		JsonObject name1Info = new JsonObject();		
		if (maps == null) {
	        name1Info.addProperty("responseCode", "100");
	        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]회원이 아닙니다.");
	        jsonobject.add("response", name1Info);
		} else {
	        name1Info.addProperty("responseCode", "200");
	        name1Info.addProperty("responseMessage", "[중랑구시설관리공단]시설에 가입된 회원입니다.");
		}

		jsonobject.add("response", name1Info);
		rtn = gson.toJson(jsonobject);
		System.out.println(rtn);
		return rtn;
        
	}
	
	
}
