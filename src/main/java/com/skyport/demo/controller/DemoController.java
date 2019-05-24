package com.skyport.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/demo/info")
	public Map<String, String> getInfoMap(HttpServletRequest req) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		String remoteAddr = req.getRemoteHost();
		String currTs = fmt.format(new Date());
		
		map.put("uri", req.getRequestURI());
		map.put("timestamp", currTs);
		map.put("remote_addr", remoteAddr);
		
		return map;
	}
	
}
