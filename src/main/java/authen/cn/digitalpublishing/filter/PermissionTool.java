package authen.cn.digitalpublishing.filter;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.web.client.RestTemplate;



public class PermissionTool {
	
	public final static Integer RESULT_SUCCESS = 1; 
	
	public final static Integer RESULT_FAILURE = 2;
	
	public final static String ACCESS_Y = "Y";
	
	public final static String ACCESS_N = "N";
	
	protected RestTemplate template;
	
	public static Map<String,String> roleXmlToMap()throws Exception{
		String roleInfoURL = SysConfig.getInstance().get("roleInfoURL");
		String urlString = roleInfoURL;
		return xmlToMap(urlString);
	}
	
	public static String permissionXmlToMap(String component,String resource,String roleId) throws Exception{

		String isPermit = ACCESS_N;
		SAXBuilder builder = new SAXBuilder();
		String premissionURL = SysConfig.getInstance().get("resourceURL");
		URL url = new URL(premissionURL + "/" + component+"/"+resource.replace("/","_")+"/"+roleId+".xml");
		Document doc = builder.build(url);
		//[element] map
		Element root = doc.getRootElement();
		Element element=root.getChild("type");
		if("1".equals(element.getValue())){
			Element obj=root.getChild("obj");
			if(obj!=null){
				Element result = obj.getChild("string");
				if(result!=null){
					isPermit = result.getText().trim().toUpperCase();
				}
			}
			
		}
		return isPermit;
	}
	
	public static Map<String,String> functionsXmlToMap(String component,String resource,String roleId)throws Exception{
		String functionURL = SysConfig.getInstance().get("functionURL");
		String urlString = functionURL + "/" + component+"/"+resource.replace("/","_")+"/"+roleId+".xml";
		return xmlToMap(urlString);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> xmlToMap(String urlString)throws Exception{
		Map<String,String> map = null;
		SAXBuilder builder = new SAXBuilder();
		URL url = new URL(urlString);
		Document doc = builder.build(url);
		Element root = doc.getRootElement();
		Element element=root.getChild("type");
		if("1".equals(element.getValue())){
			Element obj=root.getChild("obj");
			if(obj!=null){
				List<Element> entry_1 = obj.getChildren("map");
				map = new HashMap<String,String>();
				for(int i = 0 , j =entry_1.size() ; i < j ; i++){
					Element entryTemp = entry_1.get(i);
					List<Element> entry_2 = entryTemp.getChildren("entry");
					if(entry_2!=null&&!entry_2.isEmpty()){
						for(int n = 0 , m =entry_2.size() ; n < m ; n++){
							Element entryTemp1 = entry_2.get(n);
							int size = entryTemp1.getChildren("string").size();
							Element name = (Element)entryTemp1.getChildren("string").get(0);
							Element value = null;
							if(size==2){
								value = (Element)entryTemp1.getChildren("string").get(1);
							}
							map.put(name.getText(), value==null?"":value.getText());
						}
					}
				}
			}
		}
		return map;
	}
	
	public static Map<String,String> accountInfoXmlToMap(String ticket) throws Exception{
		String loginInfoURL = SysConfig.getInstance().get("accountInfoURL");
		String urlString = loginInfoURL + "/" + ticket+".xml";
		return xmlToMap(urlString);
	}
	
	public static Boolean getResourcePermission(String component,String resource,String roleId)throws Exception{
		Boolean isSuccess = true;
		try{
			String result = permissionXmlToMap(component,resource,roleId);
			if(ACCESS_Y.equals(result)){
				isSuccess = true;
			}else{
				isSuccess = false;
			}
		}catch(Exception e){
			throw e;
		}
		return isSuccess;
	}
	
	public static Map<String,String> getFunctionPermission(String component,String resource,String roleId,Map<String,String> functionPermission,Map<String,Map<String,String>> functionPermissionsGroup)throws Exception{
		Map<String,String> permissions = functionsXmlToMap(component,resource,roleId);
		if(functionPermissionsGroup!=null&&!functionPermissionsGroup.isEmpty()&&functionPermissionsGroup.containsKey(component+resource)){
			Map<String,String> group  = functionPermissionsGroup.get(component+resource);
			if(group!=null&&!group.isEmpty()){
				for (Map.Entry<String, String> entry : group.entrySet()) {
					String key = entry.getKey();
					if(functionPermission.containsKey(key)){
						functionPermission.remove(key);
					}
				}
				group.clear();
			}
		}
		functionPermissionsGroup.put(component+resource, permissions);
		for (Map.Entry<String, String> entry : permissions.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			functionPermission.put(key, value);
		}
		return functionPermission;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try{
//			SysConfig.getInstance().put("accountInfoURL","http://127.0.0.1:8080/webgate/pages/rest/getAccountInfo");
//			Map<String,String> map = PermissionTool.accountInfoXmlToMap("1393125021589_0.38624100154746266");
//			System.out.println("account end");
//			SysConfig.getInstance().put("resourceURL","http://127.0.0.1:8080/webgate/pages/rest/getPermissions");
//			Boolean result = getResourcePermission("webgate","/pages/account/form/index",map.get("roleId"));
//			System.out.println(result);
//			SysConfig.getInstance().put("functionURL","http://127.0.0.1:8080/webgate/pages/rest/getFunctions");
//			Map<String,String> map1 = PermissionTool.functionsXmlToMap("webgate","/pages/account/form/index",map.get("roleId"));
//			System.out.println("function end");
//			SysConfig.getInstance().put("roleInfoURL","http://127.0.0.1:8080/webgate/pages/rest/getRoles.xml");
//			Map<String,String> map2 = PermissionTool.roleXmlToMap();
//			System.out.println("Role end");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
}
