package cn.digitalpublishing.util.sql;  

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import cn.digitalpublishing.po.design.DataObject;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.po.imp.ImportRoleDetail;
  
  
  
public class DBUtil {  
	private static DBUtil db;

	private DBUtil() {
	}

	private static synchronized DBUtil get() {
		if (db == null) {
			db = new DBUtil();
		}
		return db;
	}
	
	public static Connection getConnect(DeDBConnect dbconn){
		try {
			String type = dbconn.getType();
			String driver ="",url="";
			Connection conn = null;
			if(type.equals("mysql")){
				driver = "com.mysql.jdbc.Driver";
				url ="jdbc:mysql://"+dbconn.getAddress()+":"+dbconn.getPort()+"/"+dbconn.getDbname()+"?characterEncoding=utf8";
				conn = getConnection(driver,url,dbconn.getUsername(),dbconn.getPassword());
			}else if(type.equals("oracle")){
				driver = "oracle.jdbc.driver.OracleDriver";//jdbc:oracle:thin:@127.0.0.1:1521:orcl
				url ="jdbc:oracle:thin:@"+dbconn.getAddress()+":"+dbconn.getPort()+":"+dbconn.getDbname();//+"?characterEncoding=utf8"
				conn = getConnection(driver,url,dbconn.getUsername(),dbconn.getPassword());
			}
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	public static Connection getConnection(String driver,String url ,String name ,String pwd){  
	       try{  
	            Class.forName(driver);
	            Connection conn=DriverManager.getConnection(url,name,pwd);//获取连接对象  
	            return conn;  
	        }catch(ClassNotFoundException e){  
	            e.printStackTrace();  
	            return null;  
	        }catch(SQLException e){  
	            e.printStackTrace();  
	            return null;  
	        }  
	    } 
	
	//批量执行sql 
	public static void executeSqls(String[] sqls,DeDBConnect dbconn)throws Exception{
		Connection conn = getConnect(dbconn);
		Statement stmt = (Statement) conn.createStatement();
		for (int i = 0; i < sqls.length; i++) {
			stmt.execute(sqls[i]+";");
		}
		if (!conn.isClosed()) {
			conn.close();
		}
	}
	
	public static void executeSql(DeDBConnect dbconn,String sql)throws Exception{
		Connection conn = getConnect(dbconn);
		Statement stmt = (Statement) conn.createStatement();
		stmt.execute(sql);
		if (!conn.isClosed()) {
			conn.close();
		}
	}
	
	public static Map<String, String> executeSqlgetValueReturn(DeDBConnect dbconn, String tableName,String id,List<ImportRoleDetail> keyDetail)throws Exception{
		Connection conn = getConnect(dbconn);
		Map<String, String> returnKey = new HashMap<String, String>();
		
		String sql = "select * from "+tableName + " where id ='"+id+"';";
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			for(ImportRoleDetail detail:keyDetail){
				returnKey.put(detail.getId(), rs.getString(detail.getColumnId()));
			}
		}
		return returnKey;
	}
	
	public static void executeSql(DeDBConnect dbconn,String sql,String[] params)throws Exception{
		Connection conn = getConnect(dbconn);				//,Statement.RETURN_GENERATED_KEYS
		PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		// 如果使用静态的SQL，则不需要动态插入参数 
		for(int i =0;i<params.length;i++){
			pstmt.setString(i+1, params[i]); 
		}
		pstmt.executeUpdate(); 
		
		if (!conn.isClosed()) {
			conn.close();
		}
	}
	
	public static List<Map<String, Object>> execute(DeDBConnect db,String sql)throws Exception {
		Connection conn = getConnect(db);
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
	    int columnCount = md.getColumnCount();
	    while (rs.next()) {
	      Map<String, Object> rowData = new HashMap<String, Object>();
	      for (int i = 1; i <= columnCount; i++) {
	        rowData.put(md.getColumnName(i), rs.getObject(i));
	      }
	      list.add(rowData);
	    }
	    return list;
	}
	
	public static List<String> executeSqlReturnColumnList(DeDBConnect db,String sql)throws Exception {
		
		Connection conn = getConnect(db);
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		List<String> columnList = new ArrayList<String>();
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int count = rsmd.getColumnCount();
		for(int i=1;i<count+1;i++){
			String name = rsmd.getColumnName(i);
			columnList.add(name);
		}
		return columnList;
	}

	public static List<DesignTable> getTableList(DeDBConnect db) throws Exception{
		List<DesignTable> designList = new ArrayList<DesignTable>();
		Connection conn = getConnect(db);
		String sql = "";
		if(db.getType().equals("mysql")){
			sql = "SELECT t.table_name AS name,t.TABLE_COMMENT AS comments FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = (select database()) ORDER BY t.TABLE_NAME";
		}else if(db.getType().equals("oracle")){
			sql = "select TBCOMM.table_name as name ,TBCOMM.comments as comments from user_tables tb ,user_tab_comments tbcomm  where TB.table_name = TBCOMM.table_name";
		}
		
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			DesignTable table = new DesignTable();
			table.setTableName(rs.getString("name")); 
			table.setComments(rs.getString("comments")); 
			designList.add(table);
		}
		return designList;
	}

	public static List<DesignTableColumn> getColumnList(DesignTable table) throws Exception{
		
		List<DesignTableColumn> columnList = new ArrayList<DesignTableColumn>();
		Connection conn = getConnect(table.getDbconn());
		String sql = "";
		if("mysql".equals(table.getDbconn().getType())){
			sql = createSqlByMysql(table.getTableName());
		}else if("oracle".equals(table.getDbconn().getType())){
			sql = createSqlByOracle(table.getTableName());
		}
		
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			DesignTableColumn column = new DesignTableColumn();
			column.setId(rs.getString("name"));
			column.setName(rs.getString("name"));
			column.setComments(rs.getString("comments")); 
			column.setType(rs.getString("type"));
			column.setJavaField(underline2Camel(rs.getString("name"),true));
			column.setJavaType(javaType(rs.getString("type")));
			columnList.add(column);
		}
		return columnList;
	}

	public static Map<String, DesignTableColumn> getColumnMap(DesignTable table)throws Exception {
		Map<String, DesignTableColumn> columnMap = new HashMap<String, DesignTableColumn>();
		Connection conn = getConnect(table.getDbconn());
		String sql = "";
		if("mysql".equals(table.getDbconn().getType())){
			sql = createSqlByMysql(table.getTableName());
		}else if("oracle".equals(table.getDbconn().getType())){
			sql = createSqlByOracle(table.getTableName());
		}
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			DesignTableColumn column = new DesignTableColumn();
			column.setName(rs.getString("name"));
			column.setComments(rs.getString("comments")); 
			column.setType(rs.getString("type"));
			column.setJavaField(underline2Camel(rs.getString("name"),true));
			column.setJavaType(javaType(rs.getString("type")));
			columnMap.put(rs.getString("name"), column);
		}
		return columnMap;
	}
	
	public static boolean tableIsexist(DataObject object)throws Exception{
		boolean isExist = false;
		String sqlType = object.getDbconn().getType();
		Connection conn = getConnect(object.getDbconn());
		String sql = "";
		
		if(sqlType.equals("mysql")){
			sql = "SELECT t.table_name AS name,t.TABLE_COMMENT AS comments FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = (select database()) and t.table_name='"+object.getTableName()+"' ORDER BY t.TABLE_NAME;";
		}else if(sqlType.equals("oracle")){
			sql = "select table_name as NAME from user_tables where table_name = '"+object.getTableName()+"' ORDER BY table_name";
		}
		
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			if(object.getTableName().equals(rs.getString("name"))){
				isExist = true;
			} 
		}
		return isExist;
	}
	
	public static String getValueByMatch(DeDBConnect dbConn, String table, String name, String code, String matchValue)throws Exception {
		String value = "";
		List<String> valueList = new ArrayList<String>();
		Connection conn = getConnect(dbConn);
		String sql = "select " + code + " from " + table + " where "+ name +" = '"+matchValue+"'";
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			value = rs.getString(code);
			valueList.add(value);
		}
		return value;
	}
	
	public static List<String> searchOneArray(DeDBConnect db, String sql, String column)throws Exception {
		Connection conn = getConnect(db);
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		List<String> valueList = new ArrayList<String>();
		while (rs.next()) {
			String value = rs.getString(column);
			valueList.add(value);
		}
		return valueList;
	}
	
	public static List<Map<String, String>> searchMapList(DeDBConnect db, String sql)throws Exception {
		Connection conn = getConnect(db);
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		List<String> columnList = new ArrayList<String>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for(int i=1;i<count+1;i++){
			String name = rsmd.getColumnName(i);
			columnList.add(name);
		}
		List<Map<String, String>> searchMapList = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			Map<String,String> map = new HashMap<String,String>();
			for(String column :columnList){
				String value = rs.getString(column);
				map.put(column, value);
			}
			searchMapList.add(map);
		}
		
		return searchMapList;
	}
	
	private static String createSqlByMysql(String tableName){
		String sql = "SELECT "
				+ "t.COLUMN_NAME AS NAME, "
				+ "(CASE WHEN t.IS_NULLABLE = 'YES' THEN '1' ELSE '0' END ) AS isNull,"
				+"(t.ORDINAL_POSITION * 10) AS sort,"
				+"t.COLUMN_COMMENT AS comments,"
				+"t.DATA_TYPE AS type  "
				+ "FROM information_schema.`COLUMNS` t "
				+ "WHERE "
				+" t.TABLE_SCHEMA = (SELECT DATABASE()) AND t.TABLE_NAME = '"+ tableName +"' ORDER BY t.ORDINAL_POSITION";
		return sql;
	}
	
	private static String createSqlByOracle(String tableName){
		String sql = "select "
				+ "com.column_name as name,"
				+ "COM.comments as comments , "
				+ "col.data_type as type "
				+ "from "
				+ "user_col_comments com,"
				+ "user_tab_columns col "
				+ "where "
				+ "COL.column_name = COM.column_name "
				+ "and  "
				+ "COL.table_name = COM.table_name "
				+ "and  "
				+ "COM.table_name= '"+ tableName +"'";
		return sql;
	}
	
	// 带下划线的字符串,转 驼峰命名法  eg: db_id ==> dbId
	public static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
	
	public static String javaType(String dbtype){
		String javatype="String";
		if(dbtype.equals("varchar")){
			javatype = "String";
		}
		if(dbtype.equals("text")){
			javatype = "String";
		}
		if(dbtype.equals("datetime")){
			javatype = "Date";
		}
		if(dbtype.equals("decimal")){
			javatype = "Integer";
		}
		if(dbtype.equals("int")){
			javatype = "Integer";
		}
		if(dbtype.equals("integer")){
			javatype = "Integer";
		}
		return javatype;
	}
}  


/* String driver="com.mysql.jdbc.Driver";   //获取mysql数据库的驱动类  
String url="jdbc:mysql://localhost:3306/test"; //连接数据库（kucun是数据库名）  
String name="root";//连接mysql的用户名  
String pwd="123456";//连接mysql的密码  */ 



/*ResultSetMetaData rsmd = rs.getMetaData();
int count = rsmd.getColumnCount();
String name = rsmd.getColumnName(count);*/