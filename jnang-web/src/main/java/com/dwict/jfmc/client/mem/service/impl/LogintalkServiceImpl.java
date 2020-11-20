package com.dwict.jfmc.client.mem.service.impl;

import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.com.util.FormatUtil;
import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.LogintalkService;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.security.model.Account;
import com.dwict.jfmc.client.security.service.AccountService;
import com.dwict.jfmc.client.security.service.ErrorService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("logintalkService")
public class LogintalkServiceImpl implements LogintalkService {

	@Value("#{appConfig['logintalk.api.url']}")
	private String apiUrl;

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "errorService")
	private ErrorService errorService;

	@Autowired
	private AccountService accountService;

	@Override
	public Object sendToken(String token) {
		final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(5000);
		final HttpClient httpClient = HttpClientBuilder.create().build();
		factory.setHttpClient(httpClient);
		final RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		final StringBuilder sb = new StringBuilder();
		final String url = (sb.append(apiUrl).append("?token=").append(token)).toString();
		return restTemplate.getForObject(url, String.class);
	}

	@Override
	public ModelAndView login(HttpServletRequest request, JsonObject response) {
		final ModelAndView modelAndView = new ModelAndView();
		final String memNm = response.get("name").getAsString();
		final String secBirthDate = response.get("birthday").getAsString();
		final String gender = response.get("sex").getAsString().equals("1") ? "M" : "F";
		final String hp = FormatUtil.phone(response.get("mobile_number").getAsString());
		Member member = new Member(memNm, secBirthDate, gender, hp);
		final boolean isExistingMember = memberService.isExistingMember(member);
		if (isExistingMember) {
			member = memberService.findExistingMember(member);
			final UserDetails account = accountService.loadUserByUsername(member.getId());
			final Authentication auth = new UsernamePasswordAuthenticationToken(account, account.getPassword(), account.getAuthorities());
			final SecurityContext sc = SecurityContextHolder.getContext();
			final HttpSession session = request.getSession(true);
			sc.setAuthentication(auth);
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
			modelAndView.setViewName("redirect:/");
		} else {
			final HttpSession session = request.getSession(true);
			session.setAttribute("find.exist", false);
			modelAndView.setViewName("redirect:/mem/find/id");
			return modelAndView;
		}
		return modelAndView;
	}

	@Override
	public ModelAndView verifyForJoin(HttpServletRequest request, JsonObject response) {
		final ModelAndView mav = new ModelAndView();
		if(response.get("result").getAsString().equals("L101")) {
			final HttpSession session = request.getSession(false);
			if(session != null && session.getAttribute("join.adult") != null) {
				final String memNm = response.get("name").getAsString();
				final String secBirthDate = response.get("birthday").getAsString();
				final String gender = response.get("sex").getAsString().equals("1") ? "M" : "F";
				final String hp = FormatUtil.phone(response.get("mobile_number").getAsString());
				Member member = new Member(memNm, secBirthDate, gender, hp);
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
		} else {
			return errorService.error("400");
		}
	}

	@Override
	public ModelAndView verifyForLogin(HttpServletRequest request, JsonObject response) {
		final ModelAndView mav = new ModelAndView();
		if(response.get("result").getAsString().equals("L101")) {
			final String memNm = response.get("name").getAsString();
			final String secBirthDate = response.get("birthday").getAsString();
			final String gender = response.get("sex").getAsString().equals("1") ? "M" : "F";
			final String hp = FormatUtil.phone(response.get("mobile_number").getAsString());
			Member member = new Member(memNm, secBirthDate, gender, hp);
			final boolean isExistingMember = memberService.isExistingMember(member);
			final HttpSession session = request.getSession(true);
			if (isExistingMember) {
				member = memberService.findExistingMember(member);
				session.setAttribute("member", member);
				if(member.getId() == null || member.getId().equals("")) {
					session.setAttribute("find.exist", false);
					mav.setViewName("redirect:/mem/find/id");
					return mav;
				} else {
					session.setAttribute("find.exist", true);
					mav.setViewName("redirect:/mem/find/id");
					return mav;
				}
			} else {
				session.setAttribute("find.exist", false);
				mav.setViewName("redirect:/mem/find/id");
				return mav;
			}
		} else {
			return errorService.error("400");
		}
	}

	@Override
	public ModelAndView verifyForChangePwd(HttpServletRequest request, Object response) {
		final ModelAndView mav = new ModelAndView();
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
		final HttpSession session = request.getSession(false);
		if(jsonObject.get("result").getAsString().equals("L101")) {
			final String memNm = jsonObject.get("name").getAsString();
			final String secBirthDate = jsonObject.get("birthday").getAsString();
			final String gender = jsonObject.get("sex").getAsString().equals("1") ? "M" : "F";
			final String hp = FormatUtil.phone(jsonObject.get("mobile_number").getAsString());
			final Member member = new Member(memNm, secBirthDate, gender, hp);
			final boolean isEqualMemNm = account.getMemNm().equals(memNm) ? true : false;
			final boolean isEqualSecBirthDate = account.getSecBirthDate().equals(secBirthDate) ? true : false;
			final boolean isEqualGender = account.getGender().equals(gender) ? true : false;
			final boolean isEqualHp = account.getHp().equals(hp) ? true : false;
			if(isEqualMemNm && isEqualSecBirthDate && isEqualGender && isEqualHp) {
				session.setAttribute("find.exist", true);
				session.setAttribute("member", member);
				mav.setViewName("redirect:/mem/find/password");
				return mav;
			} else {
				session.setAttribute("find.exist", false);
				mav.setViewName("redirect:/mem/isNotMatched");
				return mav;
			}
		} else {
			return errorService.error("400");
		}
	}
}
