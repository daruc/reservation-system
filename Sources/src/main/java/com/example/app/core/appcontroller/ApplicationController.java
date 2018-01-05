package com.example.app.core.appcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class ApplicationController {
	private Map<String, AccessLevel> minAccessLevelMap = new HashMap<>();
	
	public void setMinAccessLevel(String path, AccessLevel level) {
		minAccessLevelMap.put(path, level);
	}
	
	boolean hasPermissions(HttpServletRequest request) {
		String path = request.getServletPath();
		if (!minAccessLevelMap.containsKey(path)) {
			return false;
		}
		
		AccessLevel userAccessLevel = getAccessLevel(request.getSession());
		
		return minAccessLevelMap.get(path).toInt() <= userAccessLevel.toInt();
	}
	
	private AccessLevel getAccessLevel(HttpSession httpSession) {
		String login = (String) httpSession.getAttribute("login");
		if (login != null) {
			int accessLevel = (int) httpSession.getAttribute("access_level");
			if (accessLevel == 2) {
				return AccessLevel.ADMIN;
			}
			return AccessLevel.LOGGED_IN;
		}
		return AccessLevel.EVERYONE;
	}
}