package tag.cn.digitalpublishing.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import tag.cn.digitalpublishing.util.ButtonUtil;

/**
 * 
 * @author Administrator
 *	<ingenta-tag:BtnTag limit="${form.limit}" code="add" iconStyle="icon-plus-sign bigger-125" 
 *		buttonStyle="btn btn-small btn-primary" scopeName="functionMap" 
 *		lang="lang" name="Function.List.Button.New" i18n="true" onClick="WebGate.Function.addObj();" />
 */
@SuppressWarnings("serial")
public class ButtonTag extends TagSupport {
	private String name = "";

	private String code = "";

	private String limit = "false";

	private String iconStyle = "";

	private String onClick = "";

	private String buttonStyle = "";

	private String scope = "session";

	private String scopeName = "";

	private String key = "";
	private Map<String, String> map;
	private String sourceCode = "";

	private String separator = "#";

	private String sourceKey = "";

	private String lang = "";

	private String displayStyle = "value";

	private String debug = "false";

	private String pagePath = "";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int doStartTag() throws JspTagException {
		
		if (("true".equalsIgnoreCase(this.limit))&&(!"".equals(this.scope.trim()))&&(!"".equals(this.scopeName.trim()))) {
			if ("".equals(this.separator.trim())) {
				this.separator = "#";
			}
			
			HttpServletRequest req = (HttpServletRequest) this.pageContext.getRequest();
			String path = req.getRequestURI();
			this.pagePath = path;
			if ((!"".equals(getSourceKey().trim())) && ("".equals(getSourceCode().trim())) && (req.getAttribute(getSourceKey()) != null)) {
				this.sourceCode = req.getAttribute(getSourceKey()).toString();
			}

			if (!"".equals(this.sourceCode.trim()))
				this.key = (path + this.separator + this.sourceCode + this.separator + this.code);
			else {
				this.key = (path + this.separator + this.code);
			}
			Object obj = null;
			if (("session".equalsIgnoreCase(this.scope)) && (req.getSession().getAttribute(this.scopeName) != null)) {
				obj = req.getSession().getAttribute(this.scopeName);
			}

			if (("request".equalsIgnoreCase(this.scope)) && (req.getAttribute(this.scopeName) != null)) {
				obj = req.getAttribute(this.scopeName);
			}

			if ((obj != null) && ((obj instanceof HashMap)))
				setMap((HashMap) obj);
		} else {
			setLimit("false");
		}
		return 0;
	}

	public int doEndTag() throws JspTagException {
		String button = "";
		try {
			JspWriter out = this.pageContext.getOut();

			button = ButtonUtil.initButton(this.name, this.limit,this.iconStyle, this.onClick, this.buttonStyle, this.key,this.map, this.displayStyle);

			if ("true".equalsIgnoreCase(this.debug)) {
				out.println(this.key);
				out.println(this.pagePath);
				out.println(this.code);
			}
			out.print(button);
		} catch (Exception localException) {
		}

		return 0;
	}

	public void release() {
		this.buttonStyle = "";
		this.name = "";
		this.limit = "false";
		this.iconStyle = "";
		this.onClick = "";
		this.scope = "";
		this.code = "";
		this.key = "";
		this.scopeName = "";
		this.displayStyle = "value";
		this.debug = "false";
		super.release();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLimit() {
		return this.limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOnClick() {
		return this.onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getScopeName() {
		return this.scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getSeparator() {
		return this.separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getSourceKey() {
		return this.sourceKey;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIconStyle() {
		return this.iconStyle;
	}

	public void setIconStyle(String iconStyle) {
		this.iconStyle = iconStyle;
	}

	public String getButtonStyle() {
		return this.buttonStyle;
	}

	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getMap() {
		return this.map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getDisplayStyle() {
		return this.displayStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		this.displayStyle = displayStyle;
	}

	public String getDebug() {
		return this.debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	public String getPagePath() {
		return this.pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
}