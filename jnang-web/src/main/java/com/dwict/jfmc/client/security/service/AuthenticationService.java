package com.dwict.jfmc.client.security.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dwict.jfmc.client.security.model.Account;

/**
 * Created by RGJ on 2020.09.08
 */
public class AuthenticationService {
	public Account getAccount() {
		try {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				return (Account) authentication.getPrincipal();
			}
		} catch (final AuthenticationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Account getAccount(Authentication authentication) {
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return (Account) authentication.getPrincipal();
		}
		return null;
	}

}
