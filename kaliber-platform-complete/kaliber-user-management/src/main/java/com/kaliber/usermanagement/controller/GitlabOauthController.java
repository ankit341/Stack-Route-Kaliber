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
@RequestMapping("/kaliberauthgitlab")
public class GitlabOauthController {

    @Value("${gitlab.base.url}")
    private String baseUrl;

    @Value("${spring.security.oauth2.client.registration.gitlab.client-id}")
    String clientId;

    @Value("${spring.security.oauth2.client.registration.gitlab.client-secret}")
    String clientSecret;

    @Value("${spring.security.oauth2.client.registration.gitlab.redirect-uri}")
    String redirectUrl;

    @Value("${kaliber.server.location}")
    String serverLocation;

    @Value("${kaliber.client.redirectUrl}")
    String clientdashboardredirectUrl;

    @Autowired
    UserService userService;

    @GetMapping("/gitlablogin")
    public RedirectView gitlablogin(){
        System.out.println("inside the gitlab oauth controller");
        String url = baseUrl + "?client_id=" + this.clientId + "&redirect_uri=" + this.redirectUrl
                + "&response_type=code&scope=read_user+api";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping("/redirect/gitlablogin")
    public RedirectView getUserDetails(@RequestParam("code") String code , HttpServletResponse httpServletResponse) throws IOException, UserNotFoundException {
        System.out.println("inside redirect gitlab login");
        String accessToken = userService.getGitlabAccessToken(code);
        User user = null;
        RedirectView redirectView = new RedirectView();
        user = userService.getGitlabUserProfile(accessToken);
        String jwtToken = userService.generateToken(httpServletResponse,user);
        Cookie cookie = CookieUtil.create(httpServletResponse, "JWT_TOKEN" , jwtToken, false, -1, serverLocation);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        httpServletResponse.addCookie(cookie);
        redirectView.setUrl(clientdashboardredirectUrl);
        return redirectView;
    }
}
