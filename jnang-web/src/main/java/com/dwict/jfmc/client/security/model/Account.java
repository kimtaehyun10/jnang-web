package com.dwict.jfmc.client.security.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import lombok.Getter;

/**
 * Created by RGJ on 2020.09.10
 */
public class Account implements UserDetails, CredentialsContainer, Serializable {

	private static final long serialVersionUID = -7985608417598961034L;

	@Getter private final String username;
	@Getter private String password;
	private final Set<GrantedAuthority> authorities;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;
	@Getter private final String accessToken;

	@Getter private final String memNo;
	@Getter private final String memNm;
	@Getter private final String gender;
	@Getter private final String hp;
	@Getter private final String email;
	@Getter private final String secBirthDate;
	@Getter private int cardCnt;

	public Account(String username, String password, String memNo, String memNm, String gender, String hp, String email, String secBirthDate, long cardCnt) {
		if (((username == null) || "".equals(username))) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
		this.authorities = null;
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.accessToken = "";
		this.username = username;
		this.password = password;
		this.memNo = memNo;
		this.memNm = memNm;
		this.gender = gender;
		this.hp = hp;
		this.email = email;
		this.secBirthDate = secBirthDate;
		this.cardCnt = (int) cardCnt;
	}

	public Account(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String memNo, String memNm, String gender, String hp, String email, String secBirthDate) {
		if (((username == null) || "".equals(username))) { 
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
		this.username = username;
		this.password = password;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.accessToken = "";
		this.memNo = memNo;
		this.memNm = memNm;
		this.gender = gender;
		this.hp = hp;
		this.email = email;
		this.secBirthDate = secBirthDate;
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

		private static final long serialVersionUID = 986859161260017153L;

		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			if (g2.getAuthority() == null) {
				return -1;
			}
			if (g1.getAuthority() == null) {
				return 1;
			}
			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	@Override
	public void eraseCredentials() {
		password = null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");

		final SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());

		for (final GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof Account) {
			return username.equals(((Account) rhs).username);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

		if (!authorities.isEmpty()) {
			sb.append("Granted Authorities: ");

			boolean first = true;
			for (final GrantedAuthority auth : authorities) {
				if (!first) {
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}

}
