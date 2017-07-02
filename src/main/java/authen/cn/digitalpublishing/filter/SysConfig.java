package authen.cn.digitalpublishing.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: to load system static dic config (file's fix with ".properties" , dic's value is separated by ";" , example : "sex=men:1;women:2")
 * @author wanglei
 *
 */
public class SysConfig {
	
	private static SysConfig instance = null;
	
	private static Map<String,String> cfg = new HashMap<String,String>();
	
	private static Object lock=new Object();

	public void put(String key, String value) {
		cfg.put(key, value);
	}
	
	private SysConfig() {
	}

	public String get(String key) {
		return cfg.get(key);
	}

	public static SysConfig getInstance() {
		if (instance == null) {
			synchronized(lock){
				if(instance == null)
					instance = new SysConfig();
			}
		}
		return instance;
	}

}
