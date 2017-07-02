package cn.digitalpublishing.servlet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.digitalpublishing.util.param.Param;

@SuppressWarnings("serial")
public class ParamConfig extends HttpServlet {
	
	public void init() throws ServletException {
		try {
			InputStream io = getServletContext().getResourceAsStream(getInitParameter("Path"));
			if (io != null) {
				Properties p = new Properties();
				p.load(io);
				Param.setParam(p);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("RealPath", getServletContext().getRealPath(""));
				Param.setMap("ContextPath", map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}