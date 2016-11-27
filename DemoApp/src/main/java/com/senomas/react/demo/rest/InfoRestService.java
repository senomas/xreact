package com.senomas.react.demo.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.senomas.boot.menu.Menu;
import com.senomas.boot.menu.MenuItem;
import com.senomas.react.demo.Application;

@RestController
@RequestMapping("/${rest.uri}")
@Menu({ @MenuItem(order = 11, id = "Operator/About", path = "about"), })
public class InfoRestService {

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Map<String, Object> getBuildInfo() throws IOException {
		Map<String, Object> result = new LinkedHashMap<>();
		List<Map<String, Object>> logs = new LinkedList<>();
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(Application.class.getResourceAsStream("/META-INF/changelog.txt"), "UTF-8"))) {
			String ln;

			Pattern regx = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+(.*)");
			for (int state = 0; (ln = in.readLine()) != null;) {
				switch (state) {
				case 0:
					if (ln.length() > 0) {
						int ix = ln.lastIndexOf("git");
						ln = ln.substring(0, ix).trim();
						String[] sx = ln.split("\\s+");
						String ver = sx[sx.length - 1];
						if (ver.startsWith("v"))
							ver = ver.substring(1);
						result.put("version", ver);
						state = 1;
					}
					break;
				case 1:
				case 2:
					if (ln.length() > 0) {
						Matcher m = regx.matcher(ln);
						if (m.matches() && m.groupCount() >= 5) {
							if (state == 1) {
								result.put("id", m.group(4));
								result.put("build", m.group(5));
								result.put("buildTime", m.group(1) + "T" + m.group(2) + m.group(3));
							} else {
								state = 2;
							}
							Map<String, Object> log = new LinkedHashMap<>();
							log.put("id", m.group(4));
							log.put("build", m.group(5));
							log.put("buildTime", m.group(1) + "T" + m.group(2) + m.group(3));
							logs.add(log);
						}
					}
					break;
				}
			}
		}
		result.put("logs", logs);
		return result;
	}
}
