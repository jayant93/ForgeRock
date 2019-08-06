package com.cloudweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 
 * @author kumar.panchal
 *
 */
@Controller
public class WebController {

	@RequestMapping({ "/", "index" })
	public String inicio() {
		return "index";
	}

	@RequestMapping(value="/webprivate",method = RequestMethod.GET)
	public String privado() {
		
		
		return "private";
	}

	@RequestMapping("/webpublic")
	public String loginpub() {
		return "public";
	}

	@RequestMapping("/webadmin")
	public String admin() {
		return "admin";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
