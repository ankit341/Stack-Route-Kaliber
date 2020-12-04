package com.kaliber.apigateway.kaliberapigateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GatewayController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirect() {
        // System.out.println("in apigateway controller");
        return "forward:/kaliber-webapplication/";
    }
}
