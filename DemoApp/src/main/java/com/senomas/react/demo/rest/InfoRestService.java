package com.senomas.react.demo.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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
		Map<String, Object> result = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		try {
			in = new BufferedReader(
					new InputStreamReader(Application.class.getResourceAsStream("/META-INF/changelog.txt"), "UTF-8"));
			String ln;
			for (int state=0; (ln = in.readLine()) != null; ) {
				switch (state) {
				case 0:
					if (ln.length() > 0) {
						int ix = ln.lastIndexOf("git");
						ln = ln.substring(0, ix).trim();
						String[] sx = ln.split("\\s+");
						String ver = sx[sx.length-1];
						if (ver.startsWith("v")) ver = ver.substring(1);
						result.put("version", ver);
						state = 1;
					}
					break;
				case 1:
					if (ln.length() > 0) {
						String[] sx = ln.split("\\s+");
						result.put("build", sx[3]+"  "+sx[0]+" "+sx[1]+" "+sx[2]);
						result.put("buildTime", sx[0]+"T"+sx[1]+sx[2]);
						sb.append(ln);
						state = 2;
					}
					break;
				default:
					sb.append('\n').append(ln);
				}
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		result.put("buildLog", sb.toString());
		return result;
	}
}
