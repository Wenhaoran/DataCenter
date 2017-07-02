package authen.cn.digitalpublishing.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PermissionFilter implements Filter {
	/**
	 * 登录链接
	 */
	private String webgateURL="http://127.0.0.1:8080/webgate";
	/**
	 * 获取账户信息
	 */
	private String accountInfoURL="/pages/rest/getAccountInfo";
	/**
	 * 获取资源权限信息
	 */
	private String resourceURL="/pages/rest/getResources";
	/**
	 * 获取功能权限信息
	 */
	private String functionURL="/pages/rest/getFunctions";
	/**
	 * 获取角色信息
	 */
	private String roleInfoURL="/pages/rest/getRoles.xml";
	/**
	 * 提示链接
	 */
	private String promptURL="/common/prompt";
	
	private String functionMapName="functionMap";
	
	private final static String NO_LOGIN="Global.NoLogin";
	
	private final static String LOGIN_FAIL="Global.LoginFail";
	
	private final static String DENY_ACCESS="Global.DenyAccess";
	/**
	 * Session中账户变量名
	 */
	private String pt_accountInfo = "pt_accountInfo";
	/**
	 * Session中票据变量名
	 */
	private String pt_ticket = "pt_ticket";
	/**
	 * URL连接票据名
	 */
	private String pt_ticket_param = "ticket";
	/**
	 * Session中账户信息中的角色变量名
	 */
	private String pt_role_id = "roleId";
	/**
	 * 提示消息变量名
	 */
	private String pt_msg = "pt_msg";
	/**
	 * 提示消息编号名
	 */
	private String pt_msg_code = "pt_msg_code";
	
	private String noFilterSuffix = "xml,json,js,jpg,png,bmp,ico,jsp,xls,xlsx,doc";
	/**
	 * 资源过滤参数
	 */
	private String resourceFilterParam = "";
	/**
	 * 资源过滤类型
	 */
	private String resourceFilterType = "";

	public void destroy() {

	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletrequest,ServletResponse servletresponse, FilterChain filterchain)throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletrequest;
		HttpSession session = request.getSession();
		/**1.请求URL 和 组件名 以便系统可以获取某一个组件下的请求的权限 —— Start **/
		String resource = request.getRequestURI();
		String component = request.getContextPath();
		resource = resource.replace(component,"");
		component = component.replace("/","");
		/**1.请求URL 和 组件名 以便系统可以获取某一个组件下的请求的权限 —— End **/
		if(resource.lastIndexOf(".")>-1){
			String suffix = resource.substring(resource.lastIndexOf(".")+1);
			if(SysConfig.getInstance().get("noFilterSuffix").indexOf(suffix)>=0){//noFilterSuffix
				filterchain.doFilter(servletrequest, servletresponse);
				return;
			}
		}
		
		HttpServletResponse response = (HttpServletResponse)servletresponse;
		
		if(resource.indexOf("_")>-1){//400错误
			response.sendRedirect(SysConfig.getInstance().get("promptURL")+"?"+SysConfig.getInstance().get("pt_msg")+"="+NO_LOGIN+"&"+SysConfig.getInstance().get("pt_msg_code")+"=403");
  			return;
		}
		//框架的ticket
		String ticket = request.getParameter(SysConfig.getInstance().get("pt_ticket_param"))!=null?request.getParameter(SysConfig.getInstance().get("pt_ticket_param")).toString():session.getAttribute(SysConfig.getInstance().get("pt_ticket"))!=null?session.getAttribute(SysConfig.getInstance().get("pt_ticket")).toString():null;
		
		if(null==ticket){//本地没有ticket，没有webgate登录—>跳转 抛出403错误
  			response.sendRedirect(SysConfig.getInstance().get("promptURL")+"?"+SysConfig.getInstance().get("pt_msg")+"="+NO_LOGIN+"&"+SysConfig.getInstance().get("pt_msg_code")+"=403");
  			return;
		}else{
			Map<String,String> accountInfoMap = null;
			try{
				//每次都要去webgate检查用户是否过期、检查是否切换了角色
				accountInfoMap = PermissionTool.accountInfoXmlToMap(ticket);
				//如果webgate没有登录信息则返回登录页面 抛出403错误
				if(accountInfoMap==null||accountInfoMap.isEmpty()||!accountInfoMap.containsKey("success")||accountInfoMap.get("success").equalsIgnoreCase("false")){
		  			response.sendRedirect(SysConfig.getInstance().get("promptURL")+"?"+SysConfig.getInstance().get("pt_msg")+"="+LOGIN_FAIL+"&"+SysConfig.getInstance().get("pt_msg_code")+"=403");
		  			return;
				}
				//获取语言信息
				session.setAttribute("lang", accountInfoMap.get("lang"));
				//如果ticket没有进入Session或者ticket不等于Session中的ticket，那么写入ticket到Session
				if(session.getAttribute(SysConfig.getInstance().get("pt_ticket"))==null||"".equals(session.getAttribute(SysConfig.getInstance().get("pt_ticket")).toString().trim())||!ticket.equals(session.getAttribute(SysConfig.getInstance().get("pt_ticket")).toString().trim())){
					session.setAttribute(SysConfig.getInstance().get("pt_ticket"), ticket);
					session.setAttribute(SysConfig.getInstance().get("pt_accountInfo"), accountInfoMap);
					session.setAttribute("accountId", accountInfoMap.get("accountId"));
					session.removeAttribute(SysConfig.getInstance().get("functionMapName"));
					session.removeAttribute("functionMapGroup");
				}else{
					Map<String,String> accountInfo = session.getAttribute(SysConfig.getInstance().get("pt_accountInfo"))==null?null:(Map<String,String>)session.getAttribute(SysConfig.getInstance().get("pt_accountInfo"));
					if(accountInfo==null||!accountInfo.containsKey(SysConfig.getInstance().get("pt_role_id"))||!accountInfo.get(SysConfig.getInstance().get("pt_role_id")).equals(accountInfoMap.get(SysConfig.getInstance().get("pt_role_id")))){
						session.setAttribute(SysConfig.getInstance().get("pt_accountInfo"), accountInfoMap);
						session.removeAttribute(SysConfig.getInstance().get("functionMapName"));
						session.removeAttribute("functionMapGroup");
					}
				}
				if(isPermitted(component,resource,accountInfoMap)){//如果被允许访问
					//获取该请求下所有的function权限信息，并放入Session中
					if(SysConfig.getInstance().get("resourceFilterParam")==null||"".equals(SysConfig.getInstance().get("resourceFilterParam"))||request.getParameter(SysConfig.getInstance().get("resourceFilterParam"))==null||SysConfig.getInstance().get("resourceFilterType")==null||"".equals(SysConfig.getInstance().get("resourceFilterType"))||!SysConfig.getInstance().get("resourceFilterType").equals(request.getParameter(SysConfig.getInstance().get("resourceFilterParam").toString()))){
						filterchain.doFilter(servletrequest, servletresponse);
						return;
					}
					Map<String,Map<String,String>> functionPermissionsGroup = session.getAttribute("functionMapGroup")==null?(new HashMap<String,Map<String,String>>()):(Map<String,Map<String,String>>)session.getAttribute("functionMapGroup");
					Map<String,String> functionPermissions = session.getAttribute(SysConfig.getInstance().get("functionMapName"))==null?(new HashMap<String,String>()):(Map<String,String>)session.getAttribute(SysConfig.getInstance().get("functionMapName"));
					functionPermissions = PermissionTool.getFunctionPermission(component, resource, accountInfoMap.get(SysConfig.getInstance().get("pt_role_id")),functionPermissions,functionPermissionsGroup);
					session.setAttribute(SysConfig.getInstance().get("functionMapName"), functionPermissions);
					session.setAttribute("functionMapGroup",functionPermissionsGroup);
				}else{//如果被拒绝则返回405错误
					if(SysConfig.getInstance().get("resourceFilterParam")==null||"".equals(SysConfig.getInstance().get("resourceFilterParam"))||request.getParameter(SysConfig.getInstance().get("resourceFilterParam"))==null||SysConfig.getInstance().get("resourceFilterType")==null||"".equals(SysConfig.getInstance().get("resourceFilterType"))||!SysConfig.getInstance().get("resourceFilterType").equals(request.getParameter(SysConfig.getInstance().get("resourceFilterParam").toString()))){
						filterchain.doFilter(servletrequest, servletresponse);
						return;
					}
		  			response.sendRedirect(SysConfig.getInstance().get("promptURL")+"?"+SysConfig.getInstance().get("pt_msg")+"="+DENY_ACCESS+"&"+SysConfig.getInstance().get("pt_msg_code")+"=405");
		  			return;
				}
				filterchain.doFilter(servletrequest, servletresponse);
			}catch(Exception e){//如果出现异常则返回500错误
				e.printStackTrace();
				response.sendRedirect(SysConfig.getInstance().get("promptURL")+"?"+SysConfig.getInstance().get("pt_msg")+"="+e.getMessage()+"&"+SysConfig.getInstance().get("pt_msg_code")+"=500");
	  			return;
			}
		}

		return;
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		/***初始化访问URL***/
		if(filterconfig.getInitParameter ("webgateURL")!=null&&filterconfig.getInitParameter ("webgateURL").trim().length()>0)
			this.webgateURL = filterconfig.getInitParameter ("webgateURL");
		if(filterconfig.getInitParameter ("accountInfoURL")!=null&&filterconfig.getInitParameter ("accountInfoURL").trim().length()>0)
			this.accountInfoURL = filterconfig.getInitParameter ("accountInfoURL");
		if(filterconfig.getInitParameter ("promptURL")!=null&&filterconfig.getInitParameter ("promptURL").trim().length()>0)
			this.promptURL = filterconfig.getInitParameter ("promptURL");
		if(filterconfig.getInitParameter ("resourceURL")!=null&&filterconfig.getInitParameter ("resourceURL").trim().length()>0)
			this.resourceURL = filterconfig.getInitParameter ("resourceURL");
		if(filterconfig.getInitParameter ("functionURL")!=null&&filterconfig.getInitParameter ("functionURL").trim().length()>0)
			this.functionURL = filterconfig.getInitParameter ("functionURL");
		if(filterconfig.getInitParameter ("roleInfoURL")!=null&&filterconfig.getInitParameter ("roleInfoURL").trim().length()>0)
			this.roleInfoURL = filterconfig.getInitParameter ("roleInfoURL");
		
		/***初始化参数***/
		if(filterconfig.getInitParameter ("pt_accountInfo")!=null&&filterconfig.getInitParameter ("pt_accountInfo").trim().length()>0)
			this.pt_accountInfo = filterconfig.getInitParameter ("pt_accountInfo");
		
		if(filterconfig.getInitParameter ("pt_ticket")!=null&&filterconfig.getInitParameter ("pt_ticket").trim().length()>0)
			this.pt_ticket = filterconfig.getInitParameter ("pt_ticket");
		
		if(filterconfig.getInitParameter ("pt_ticket_param")!=null&&filterconfig.getInitParameter ("pt_ticket_param").trim().length()>0)
			this.pt_ticket_param = filterconfig.getInitParameter ("pt_ticket_param");
		
		if(filterconfig.getInitParameter ("pt_role_id")!=null&&filterconfig.getInitParameter ("pt_role_id").trim().length()>0)
			this.pt_role_id = filterconfig.getInitParameter ("pt_role_id");
		
		if(filterconfig.getInitParameter ("pt_msg")!=null&&filterconfig.getInitParameter ("pt_msg").trim().length()>0)
			this.pt_msg = filterconfig.getInitParameter ("pt_msg");
		
		if(filterconfig.getInitParameter ("pt_msg_code")!=null&&filterconfig.getInitParameter ("pt_msg_code").trim().length()>0)
			this.pt_msg_code = filterconfig.getInitParameter ("pt_msg_code");
		
		if(filterconfig.getInitParameter ("functionMapName")!=null&&filterconfig.getInitParameter ("functionMapName").trim().length()>0)
			this.functionMapName = filterconfig.getInitParameter ("functionMapName");
		
		if(filterconfig.getInitParameter ("noFilterSuffix")!=null&&filterconfig.getInitParameter ("noFilterSuffix").trim().length()>0)
			this.noFilterSuffix = filterconfig.getInitParameter ("noFilterSuffix");
		
		if(filterconfig.getInitParameter ("resourceFilterParam")!=null&&filterconfig.getInitParameter ("resourceFilterParam").trim().length()>0)
			this.resourceFilterParam = filterconfig.getInitParameter ("resourceFilterParam");
		
		if(filterconfig.getInitParameter ("resourceFilterType")!=null&&filterconfig.getInitParameter ("resourceFilterType").trim().length()>0)
			this.resourceFilterType = filterconfig.getInitParameter ("resourceFilterType");
		
		SysConfig.getInstance().put("webgateURL", this.webgateURL);
		SysConfig.getInstance().put("accountInfoURL", this.webgateURL+this.accountInfoURL);
		SysConfig.getInstance().put("resourceURL", this.webgateURL+this.resourceURL);
		SysConfig.getInstance().put("functionURL", this.webgateURL+this.functionURL);
		SysConfig.getInstance().put("promptURL", this.webgateURL+this.promptURL);
		SysConfig.getInstance().put("roleInfoURL", this.webgateURL+this.roleInfoURL);
		
		SysConfig.getInstance().put("pt_accountInfo", this.pt_accountInfo);
		SysConfig.getInstance().put("pt_ticket", this.pt_ticket);
		SysConfig.getInstance().put("pt_ticket_param", this.pt_ticket_param);
		SysConfig.getInstance().put("pt_role_id", this.pt_role_id);
		SysConfig.getInstance().put("pt_msg", this.pt_msg);
		SysConfig.getInstance().put("pt_msg_code", this.pt_msg_code);
		SysConfig.getInstance().put("functionMapName", this.functionMapName);
		SysConfig.getInstance().put("noFilterSuffix", this.noFilterSuffix);
		SysConfig.getInstance().put("resourceFilterParam", this.resourceFilterParam);
		SysConfig.getInstance().put("resourceFilterType", this.resourceFilterType);

	}
	
	private Boolean isPermitted(String component,String resource,Map<String,String> accountInfoMap)throws Exception{
		Boolean isPermitted = true;
		try{
			isPermitted=PermissionTool.getResourcePermission(component,resource,accountInfoMap.get(SysConfig.getInstance().get("pt_role_id")));
		}catch(Exception e){
			throw e;
		}
		return isPermitted;
	}

}
