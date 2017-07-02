package cn.digitalpublishing.util.mybatis.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;


/**
*
* 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。
* 利用拦截器实现Mybatis分页的原理：
* 要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句
* 是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。在Mybatis中Statement语句是通过RoutingStatementHandler对象的
* prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用
* StatementHandler对象的prepare方法，即调用invocation.proceed()。
* 对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少，这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设
* 置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。
*
*/
@Intercepts( {
@Signature(method = "query", type = Executor.class, args = {
       MappedStatement.class, Object.class, RowBounds.class,
       ResultHandler.class })
 })
public class PageInterceptor implements Interceptor{

	private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
	private String databaseType;//数据库类型，不同的数据库有不同的分页方法
	   
    /**
     * 拦截后要执行的方法
     */
    public Object intercept(Invocation invocation) throws Throwable {
    	
       MappedStatement mappedStatement=(MappedStatement)invocation.getArgs()[0];         
       Object parameter = invocation.getArgs()[1];   
       BoundSql boundSql = mappedStatement.getBoundSql(parameter); 
       //拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
       Object obj = boundSql.getParameterObject();
       //这里我们简单的通过传入的是Page对象就认定它是需要进行分页操作的。
       if (obj instanceof PageInfo) {
           PageInfo page = (PageInfo) obj;
           Connection connection=mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
           //获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
           String sql = boundSql.getSql();
           //组装授权sql语句
//           String mapperName = mappedStatement.getId();
//           if(AuthSQL.isAuthSQL(mapperName)){        	   
//        	   sql = AuthSQL.generateAuthSQL(mapperName, sql);
//           }
           //给当前的page参数对象设置总记录数
           this.setTotalRecord(page,mappedStatement, sql,connection);           
          
           //获取分页Sql语句
           String pageSql = this.getPageSql(page, sql);     
           
           invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);     
           BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pageSql,boundSql.getParameterMappings(),boundSql.getParameterObject());     
           MappedStatement newMs = copyFromMappedStatement(mappedStatement,new BoundSqlSqlSource(newBoundSql));    
            
           invocation.getArgs()[0] = newMs;    
       }
       return invocation.proceed();
    }
 
    /**
     * 内部静态类
     * @author Administrator
     *
     */
    public static class BoundSqlSqlSource implements SqlSource {    
        BoundSql boundSql;    

        public BoundSqlSqlSource(BoundSql boundSql) {    
            this.boundSql = boundSql;    
        }    

        public BoundSql getBoundSql(Object parameterObject) {    
            return boundSql;    
        }    
    }   
    
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {     
           Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(), newSqlSource, ms.getSqlCommandType());     
           builder.resource(ms.getResource());     
           builder.fetchSize(ms.getFetchSize());     
           builder.statementType(ms.getStatementType());     
           builder.keyGenerator(ms.getKeyGenerator());     
           //builder.keyProperty(ms.getKeyProperties()[0]);     
           builder.timeout(ms.getTimeout());     
           builder.parameterMap(ms.getParameterMap());     
           builder.resultMaps(ms.getResultMaps());     
           builder.cache(ms.getCache());   
           builder.useCache(ms.isUseCache());
           MappedStatement newMs = builder.build();     
           return newMs;     
    }     
    /**
     * 拦截器对应的封装原始对象的方法
     */
    public Object plugin(Object target) {
       return Plugin.wrap(target, this);
    }
 
    /**
     * 设置注册拦截器时设定的属性
     */
    public void setProperties(Properties properties) {
       this.databaseType = properties.getProperty("dialect");
    }
   
    /**
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle
     * 其它的数据库都 没有进行分页
     *
     * @param page 分页对象
     * @param sql 原sql语句
     * @return
     */
    private String getPageSql(PageInfo page, String sql) {
       StringBuffer sqlBuffer = new StringBuffer(sql);
       if ("mysql".equalsIgnoreCase(databaseType)) {
           return getMysqlPageSql(page, sqlBuffer);
       } else if ("oracle".equalsIgnoreCase(databaseType)) {
           return getOraclePageSql(page, sqlBuffer);
       }
       return sqlBuffer.toString();
    }
   
    /**
     * 获取Mysql数据库的分页查询语句
     * @param page 分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    private String getMysqlPageSql(PageInfo page, StringBuffer sqlBuffer) {
       //计算第一条记录的位置，Mysql中记录的位置是从0开始的。
       int offset = (page.getNowpage() - 1) * page.getPagesize();
       if(!Strings.isNullOrEmpty(page.getSort())){
    	   sqlBuffer.append(" order by ").append(page.getSort()).append(" ").append(page.getOrder());
       }
       sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPagesize());
       return sqlBuffer.toString();
    }
   
    /**
     * 获取Oracle数据库的分页查询语句
     * @param page 分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Oracle数据库的分页查询语句
     */
    private String getOraclePageSql(PageInfo page, StringBuffer sqlBuffer) {
       //计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
       int offset = (page.getNowpage() - 1) * page.getPagesize() + 1;
       StringBuffer sql = new StringBuffer();
       //支持多字段排序
       if(!Strings.isNullOrEmpty(page.getSort())){
    	   //sql.append(" order by ").append(page.getSort()).append(" ").append(page.getOrder());
    	   sql.append(" order by ");
    	   String sort = page.getSort();   
    	   String order = page.getOrder();
    	   if(sort.contains(",")){
    		   String[] sorts = sort.split(",");
    		   String[] orders = order.split(",");
    		   for(int i=0;i<sorts.length;i++){
    			   String _order = orders[i];
    			   String _sorts = sorts[i];
    			   if(_order==null){
    				   _order = " asc ";
    			   }
    			   if(i==sorts.length-1){
    				   sql.append(" ").append(_sorts).append(" ").append(_order);
    			   }else{
    				   sql.append(" ").append(_sorts).append(" ").append(_order).append(" , ");
    			   }
    		   }
    	   }else{
    		 sql.append("  ").append(page.getSort()).append(" ").append(page.getOrder());
    	   }
       }
       sqlBuffer.insert(0, "select u.*, rownum r from (").append(sql.toString()).append(") u where rownum < ").append(offset + page.getPagesize());
       sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
       //上面的Sql语句拼接之后大概是这个样子：
       //select * from (select u.*, rownum r from (select * from t_user) u where rownum < 31) where r >= 16
       return sqlBuffer.toString();
    }
   
    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection 当前的数据库连接
     */
    private void setTotalRecord(PageInfo page,
           MappedStatement mappedStatement, String newSQL,Connection connection) {
       System.out.println("==>  ------------------------------------------------------------------------------------------------ <==");
       System.out.println("==>  Execute count(0)  ");
       long starttime = System.currentTimeMillis();
       //获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
       //delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
       BoundSql boundSql = mappedStatement.getBoundSql(page);
       //获取到我们自己写在Mapper映射语句中对应的Sql语句
       //String sql = boundSql.getSql();  
       String sql = newSQL;
       
       String countSql = null;
       String definedCountSql = page.getSelfDefinedCountSql();
       if(StringUtils.isNotEmpty(definedCountSql)){
    	   countSql = definedCountSql;
    	   PreparedStatement pstmt = null;
           ResultSet rs = null;
           try {
               pstmt = connection.prepareStatement(countSql);
               //通过parameterHandler给PreparedStatement对象设置参数
               //parameterHandler.setParameters(pstmt);
               //之后就是执行获取总记录数的Sql语句和获取结果了。
               rs = pstmt.executeQuery();
               if (rs.next()) {
                  int totalRecord = rs.getInt(1);
                  //给当前的参数page对象设置总记录数
                  page.setTotal(totalRecord);
               }
               System.out.println("==>  Total:"+page.getTotal());
               //EntityCache.putCacheObject(countSql, page.getTotal());
           } catch (SQLException e) {
        	   logger.error("SQLException",e);
           } finally {
               try {
                  if (rs != null){
                      rs.close();
                  }
                  if (pstmt != null){
                      pstmt.close();
                  }
                  if(connection!=null){
                	  connection.close();
                  }
               } catch (SQLException e) {
            	   logger.error("SQLException",e);
               }
           }
    	   
       }else{
    	 //通过查询Sql语句获取到对应的计算总记录数的sql语句
           countSql = this.getCountSql(sql);
         //通过BoundSql获取对应的参数映射
           List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
           //利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
           BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
           //通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
           ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
           //通过connection建立一个countSql对应的PreparedStatement对象。
           PreparedStatement pstmt = null;
           ResultSet rs = null;
           try {
               pstmt = connection.prepareStatement(countSql);
               //通过parameterHandler给PreparedStatement对象设置参数
               parameterHandler.setParameters(pstmt);
               //之后就是执行获取总记录数的Sql语句和获取结果了。
               rs = pstmt.executeQuery();
               if (rs.next()) {
                  int totalRecord = rs.getInt(1);
                  //给当前的参数page对象设置总记录数
                  page.setTotal(totalRecord);
               }
               System.out.println("==>  Total:"+page.getTotal());
               //EntityCache.putCacheObject(countSql, page.getTotal());
           } catch (SQLException e) {
        	   logger.error("SQLException",e);
           } finally {
               try {
                  if (rs != null)
                      rs.close();
                   if (pstmt != null)
                      pstmt.close();
               } catch (SQLException e) {
            	   logger.error("SQLException",e);
               }
           }
           
       }
       
       System.out.println("==>  Preparing-->"+countSql);
//       Object hitCache = (Object) EntityCache.getCacheDataObject(countSql);
//       if(hitCache != null){
//    	   System.out.println("==>  Cache Hit Ratio -->"+hitCache);
//    	   page.setTotal(Integer.parseInt(hitCache.toString()));
//    	   return;
//       }
       
       long endtime = System.currentTimeMillis();
       System.out.println("==>  it take time is "+(endtime-starttime)+".ms");
       System.out.println("==>  ------------------------------------------------------------------------------------------------ <==");
    }
   
    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
    	//全部转为小写 解决FROM的问题    
       //return "select count(0) from (" + sql.toString() + ")";
    	int index = sql.toLowerCase().lastIndexOf("from");
		return  "select count(0) " + sql.substring(index);
    }
   
    
}