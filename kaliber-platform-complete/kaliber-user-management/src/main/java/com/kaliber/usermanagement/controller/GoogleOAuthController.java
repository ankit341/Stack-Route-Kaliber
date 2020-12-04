package com.kaliber.usermanagement.controller;

import com.kaliber.usermanagement.exception.UserNotFoundException;
import com.kaliber.usermanagement.model.User;
import com.kaliber.usermanagement.service.UserService;
import com.kaliber.usermanagement.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/kaliberauth")
public class GoogleOAuthController {
    @Value("${google.base.url}")
    private String baseUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    String redirectUrl;

    @Value("${kaliber.server.location}")
    String serverLocation;

    @Value("${kaliber.client.redirectUrl}")
    String clientdashboardredirectUrl;

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public RedirectView login(){
        System.out.println("Inside login method of GoogleOAUth Controller");
        RedirectView redirectView = new RedirectView();
        String url = baseUrl + "?client_id="+this.clientId + "&redirect_uri=" + this.redirectUrl + "&response_type=code&scope=profile+email";
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping("/redirect/login")
    public RedirectView getUserDetails(@RequestParam("code") String code, HttpServletResponse httpServletResponse) throws IOException, UserNotFoundException {
        System.out.println("in auth/login pf Provider");
        String accessToken = userService.getAccessToken(code);
        User user = userService.getUserProfile(accessToken);
        String jwtToken = userService.generateToken(httpServletResponse,user);
        System.out.println("JWT TOKEN IS: " + jwtToken);
        Cookie cookie = CookieUtil.create(httpServletResponse, "JWT_TOKEN" , jwtToken, false, -1, serverLocation);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        httpServletResponse.addCookie(cookie);
        RedirectView redirectview=new RedirectView();
        redirectview.setUrl(clientdashboardredirectUrl);
        return  redirectview;
    }
}


