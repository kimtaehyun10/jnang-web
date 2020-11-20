package com.dwict.jfmc.client.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.security.mapper.AccountMapper;
import com.dwict.jfmc.client.security.model.Account;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by RGJ on 2020.09.08
 */
@Slf4j
@Service
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountMapper accountMapper;

	@Override
	public UserDetails loadUserByUsername(final String userId) throws UsernameNotFoundException {
		final Account userData = accountMapper.findById(userId);
		final List<GrantedAuthority> roles = new ArrayList<>();
		String memNo = null;
		String memNm = null;
		String gender = null;
		String hp = null;
		String email = null;
		String secBirthDate = null;

		if (userData == null) {
			log.debug("사용자를 찾을 수 없습니다.");
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		} else {
			if (userData.getMemNo() != null) memNo = userData.getMemNo();
			else memNo = "";
			if (userData.getMemNm() != null) memNm = userData.getMemNm();
			else memNm = "";
			if (userData.getGender() != null) gender = userData.getGender();
			else gender = "";
			if (userData.getHp() != null) hp = userData.getHp();
			else hp = "";
			if (userData.getEmail() != null) email = userData.getEmail();
			else email = "";
			if (userData.getSecBirthDate() != null) secBirthDate = userData.getSecBirthDate();
			else secBirthDate = "";

			roles.add(new SimpleGrantedAuthority("ROLE_USER"));
			if(userData.getCardCnt() > 0) {
				roles.add(new SimpleGrantedAuthority("ROLE_CARD"));
			}
		}
		final Account account = new Account(userId, userData.getPassword(), roles, memNo, memNm, gender, hp, email, secBirthDate);
		log.info(account.toString());
		return account;
	}

	public void userVisitDt(String userId) {
		accountMapper.userVisitDt(userId);		
	}
}
