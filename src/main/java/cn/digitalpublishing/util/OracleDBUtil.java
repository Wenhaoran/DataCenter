package cn.digitalpublishing.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleDBUtil {

	public static Logger logger = LoggerFactory.getLogger(OracleDBUtil.class);
	
	public static Connection getConnection() {
	
		String fileName ="";
		try {
			String path = OracleDBUtil.class.getResource("/").getPath();
			File file = new File(path);
			path = file.getParent();
			//logger.info("配置文件地址："+path);
			fileName = path+File.separator + "classes"+File.separator+"config"+File.separator+"jdbc.properties";
		} catch (Exception e) {
			logger.error("读取jdbc.properties出错！", e);
		}
		String driverName = null;
		String userName = null;
		String pwd = null;
		String url = null;		
		
		//logger.info("====================================================");
		driverName = "oracle.jdbc.driver.OracleDriver";		
		userName = readProperties(fileName, "master_username");
		pwd = readProperties(fileName, "master_password");
		url = readProperties(fileName, "master_driverUrl");
		logger.info("==>"+url+"--"+userName);
		Connection con = null;
		try {
			Class.forName(driverName);
			con = DriverManager.getConnection(url, userName, pwd);
			con.setAutoCommit(false);
			logger.debug("获取数据库连接成功...");
		} catch (Exception e) {
			logger.error("连接数据库错误.....", e);
		}
		return con;

	}
	
	public static List<String> getSequence(String seqName,int maxnum) {
		seqName = seqName.toUpperCase();
		List<String> list = new ArrayList<String>();
		Statement str = null;
		String sequence = "0";
		try {
			Connection con = OracleDBUtil.getConnection();
			str = con.createStatement();
			int count = 0;
			ResultSet rs =  null;
			while(count < maxnum){
				String sql = "SELECT "+seqName+".NEXTVAL FROM USER_SEQUENCES WHERE SEQUENCE_NAME='"+seqName+"'";
				rs = str.executeQuery(sql);
				if (rs.next()) {
					sequence = rs.getString(1);
					list.add(sequence);
				}
				count++;
			}
			close(rs, str, con);
			logger.info("---"+seqName);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			rebuildSequence(seqName,maxnum);
			list = getSequence(seqName,maxnum);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	public static void rebuildSequence(String seqName,int maxnum) {
		
		Statement stmt = null;	
		Connection con = null;
		try {
			con = OracleDBUtil.getConnection();
			stmt = con.createStatement();		
			
			String dropsql = "DROP SEQUENCE "+seqName;
			try{
				int result = stmt.executeUpdate(dropsql);
				logger.info("删除序列["+seqName+"]成功....");
			}catch(Exception e){
				logger.error("删除序列["+seqName+"]失败");
			}
			
			String createSEQ = "CREATE SEQUENCE "+seqName+" START WITH 1 INCREMENT BY 1 NOCYCLE NOMAXVALUE";  	
			int result = stmt.executeUpdate(createSEQ);			
			logger.info("重新创建序列["+seqName+"]成功....");
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void dropSequence(String seqName) {
		
		Statement stmt = null;	
		Connection con = null;
		try {
			con = OracleDBUtil.getConnection();
			stmt = con.createStatement();		
			
			String dropsql = "DROP SEQUENCE "+seqName;
			int result = stmt.executeUpdate(dropsql);
			logger.info("删除序列["+seqName+"]成功....");			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
		}
		
	}
	public static void close(ResultSet rs, Statement str, Connection con) {

		try {
			rs.close();
			str.close();
			con.close();
			logger.info("==>释放数据库资源......");
		} catch (SQLException e) {
			logger.error("数据库资源关闭异常....", e);
		} finally {
			try {
				rs.close();
				str.close();
				con.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}
	}

	/*
	 * 读取init.properties
	 */

	private static FileInputStream fis;
	private static Properties p = new Properties();
	public static Properties readFile(String fileName){
		Properties pt = new Properties();
		try {
			fis = new FileInputStream(fileName);
			pt.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return pt;
	}
	public static String getProValue(Properties pt,String key) {
		if (pt.containsKey(key)) {
			String value = pt.getProperty(key);// 得到某一属性的值
			return value;
		}
		return "";
	}
	
	// 读取url的properties配置文件
	public static String readProperties(String fileName, String key) {

		try {
			fis = new FileInputStream(fileName);
			p.load(fis);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String value = getValue(key);
		return value;
	}

	private static String getValue(String key) {
		if (p.containsKey(key)) {
			String value = p.getProperty(key);// 得到某一属性的值
			return value;
		}
		return "";
	}
	

}
