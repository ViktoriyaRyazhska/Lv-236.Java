package com.inva.hipstertest.freemarker.controllers;

import com.codahale.metrics.annotation.Timed;
import com.inva.hipstertest.security.jwt.JWTConfigurer;
import com.inva.hipstertest.security.jwt.TokenProvider;
import com.inva.hipstertest.web.rest.AccountResource;
import com.inva.hipstertest.service.util.CookieUtil;
import com.inva.hipstertest.web.rest.vm.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;

@Controller
public class UserJWTFreemarkerController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TokenProvider tokenProvider;

    private final AccountResource accountResource;

    private final AuthenticationManager authenticationManager;

    public UserJWTFreemarkerController(TokenProvider tokenProvider, AuthenticationManager authenticationManager,
                                       AccountResource accountResource) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.accountResource = accountResource;
    }

    @RequestMapping(value = "/freemarker/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        LoginVM loginVM = new LoginVM();
        return new ModelAndView("login", "loginVM", loginVM);
    }

    @PostMapping("/freemarker/authenticate")
    @Timed
    public String authenticate(HttpServletResponse httpServletResponse, @Valid LoginVM loginVM, BindingResult result) {
        if (result.hasErrors()) {
            return "login";
        }
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            CookieUtil.create(httpServletResponse, "JWT-TOKEN", jwt, false, -1);

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority("ROLE_PUPIL"))) {
                return "redirect:pupil-home";
            } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_HEAD_TEACHER"))) {
                return "redirect:teacher-mgmt/teacher-mgmt";
            }else if(authorities.contains(new SimpleGrantedAuthority("ROLE_TEACHER"))){
                return "redirect:teacher-schedule";
            } else {
                return "redirect:freemarkertest";
            }
        } catch (AuthenticationException ae) {
            log.trace("Authentication exception trace: {}", ae);
            return "login";
        }
    }

    @RequestMapping(value = "/freemarker/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        CookieUtil.clear(response, "JWT-TOKEN");
        return "redirect:login";
    }

}
