package com.dwict.jfmc.client.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dwict.jfmc.client.com.util.SHA256PasswordEncoder;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.security.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by RGJ on 2020.09.10
 */
@Slf4j
public class AccountProvider implements AuthenticationProvider {

	@Autowired
	private AccountService accountService;

	@Resource(name = "memberService")
	private MemberService memberService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String userId = (String) authentication.getPrincipal();
		final String userPw = (String) authentication.getCredentials();
		log.debug("==============> AccountProvider");
		log.debug("userId: {}", userId);
		log.debug("userPw: {}", userPw);
		final UserDetails account = accountService.loadUserByUsername(userId);
		final SHA256PasswordEncoder encoder = new SHA256PasswordEncoder();

		if(account.getPassword() == null || account.getPassword().trim().equals("")) {
			throw new BadCredentialsException("99");
		}
		if(!encoder.encrypt(userPw).equals(account.getPassword())) {
			throw new BadCredentialsException("01");
		}

		try {
			accountService.userVisitDt(userId);
		} catch (final Exception e) {
			log.error("loginUserVisitDtError: {}", e);
		}
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		final HttpSession session = request.getSession(true);
		memberService.updateLastLogin(userId);
		memberService.memSession(request, userId);
		session.setAttribute("member", session.getAttribute("member"));
		return new UsernamePasswordAuthenticationToken(account, account.getPassword(), account.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return true;
	}

}
