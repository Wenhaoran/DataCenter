package cn.digitalpublishing.util.param;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class Param {
	private static HashMap<String, HashMap<String, String>> param = new HashMap<String, HashMap<String, String>>();

	public static HashMap<String, String> getParam(String key) {
		HashMap<String, String> hashmap = param.containsKey(key) ?  param.get(key): param == null ? null : null;
		return hashmap;
	}

	public static void setParam(Properties p) {
		if ((!p.isEmpty()) && (p.size() > 0)) {
			String value = "";
			String key = "";
			for (Enumeration e = p.keys(); e.hasMoreElements();) {
				key = e.nextElement().toString();
				value = p.getProperty(key);
				if ((value != null) && (!value.trim().equals("")))
					param.put(key, getValue(value));
			}
		}
	}

	public static void setMap(String key, HashMap<String, String> map) {
		if (!param.containsKey(key))
			param.put(key, map);
	}

	private static HashMap<String, String> getValue(String val) {
		HashMap<String, String> value = new HashMap<String, String>();
		String[] val1 = val.split(";");
		for (int i = 0; i < val1.length; i++) {
			String[] val2 = val1[i].split(":");
			if (val2.length == 2) {
				value.put(val2[0], val2[1]);
			}
		}
		return value;
	}
}