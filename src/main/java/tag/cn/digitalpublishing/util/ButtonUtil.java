package tag.cn.digitalpublishing.util;

import java.util.Map;

public class ButtonUtil {
	public static String initButton(String name, String limit,String iconStyle, String onClick, String buttonStyle,String keyName, Map<String, String> map, String displayStyle) {
		String btn = "";
		boolean isShow = true;
		if ("false".equalsIgnoreCase(limit)) {
			isShow = true;
		} else if ((map != null) && (map.containsKey(keyName)) && ("true".equalsIgnoreCase((String) map.get(keyName))))
			isShow = true;
		else {
			isShow = false;
		}

		if (isShow) {
			btn = btn + "<button type=\"button\" class=\"" + buttonStyle
					+ "\" ";
			if (!"".equals(onClick.trim())) {
				btn = btn + "onclick=\"" + onClick.trim() + "\"";
			}
			if (("float".equalsIgnoreCase(displayStyle))||("both".equalsIgnoreCase(displayStyle))) {
				btn = btn + " title=\"" + name + "\" ";
			}
			btn = btn + ">";
			if (!"".equals(iconStyle.trim())) {
				btn = btn + "<i class=\"" + iconStyle + "\"></i>";
			}
			if (("value".equalsIgnoreCase(displayStyle))
					|| ("both".equalsIgnoreCase(displayStyle))) {
				btn = btn + name;
			}
			btn = btn + "</button>";
		}
		return btn;
	}
}