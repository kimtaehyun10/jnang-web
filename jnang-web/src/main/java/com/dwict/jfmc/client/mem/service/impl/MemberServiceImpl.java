package com.dwict.jfmc.client.mem.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.com.util.SHA256PasswordEncoder;
import com.dwict.jfmc.client.mem.mapper.MemberMapper;
import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.MemberService;

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

}
