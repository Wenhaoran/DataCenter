package cn.digitalpublishing.util.mybatis.page;



import cn.digitalpublishing.util.Reflections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

/**
 * @description：分页实体类 (结合jqery easyui)
 * @author：Wangzhixuan
 * @date：2015年4月23日 上午1:41:46
 */
public class PageInfo {

    private final static int PAGESIZE = 20; //默认显示的记录数 

    private int total; // 总记录 
    private List<?> rows; //显示的记录  
    private String columns;
    @JsonIgnore
    private int from;
    @JsonIgnore
    private int size;
    @JsonIgnore
    private int nowpage; // 当前页 
    @JsonIgnore
    private int pagesize; // 每页显示的记录数 
    @JsonIgnore
    private Map<String, Object> condition; //查询条件

    @JsonIgnore
    private String sort = "";// 排序字段
    @JsonIgnore
    private String order = "desc";// asc，desc mybatis Order 关键字

    @JsonIgnore
    private String selfDefinedCountSql = "";// 自定义count sql    
    
    
    public PageInfo() {
    }

    //构造方法
    public PageInfo(int nowpage, int pagesize) {
        //计算当前页  
        if (nowpage < 0) {
            this.nowpage = 1;
        } else {
            //当前页
            this.nowpage = nowpage;
        }
        //记录每页显示的记录数  
        if (pagesize < 0) {
            this.pagesize = PAGESIZE;
        } else {
            this.pagesize = pagesize;
        }
        //计算开始的记录和结束的记录  
        this.from = (this.nowpage - 1) * this.pagesize;
        this.size = this.pagesize;
    }

    // 构造方法
    public PageInfo(int nowpage, int pagesize, String sort, String order) {
    	
    	// 计算当前页  
        if (nowpage < 0) {
            this.nowpage = 1;
        } else {
            // 当前页
            this.nowpage = nowpage;
        }
        // 记录每页显示的记录数  
        if (pagesize < 0) {
            this.pagesize = PAGESIZE;
        } else {
            this.pagesize = pagesize;
        }
        // 计算开始的记录和结束的记录  
        this.from = (this.nowpage - 1) * this.pagesize;
        this.size = this.pagesize;
        //将bean中属性翻译为字段值
    	String _sort = Reflections.properties2field(sort);
    	if(_sort.lastIndexOf("_str")!=-1){
    		_sort = _sort.substring(0,_sort.lastIndexOf("_str"));
    	}
        // 排序字段，正序还是反序
        this.sort = _sort;
        this.order = order;
    }
    
 // 构造方法
    public PageInfo(int nowpage, int pagesize, String sort, String order,String selfDefinedCountSql) {
    	
    	// 计算当前页  
        if (nowpage < 0) {
            this.nowpage = 1;
        } else {
            // 当前页
            this.nowpage = nowpage;
        }
        // 记录每页显示的记录数  
        if (pagesize < 0) {
            this.pagesize = PAGESIZE;
        } else {
            this.pagesize = pagesize;
        }
        // 计算开始的记录和结束的记录  
        this.from = (this.nowpage - 1) * this.pagesize;
        this.size = this.pagesize;
        //将bean中属性翻译为字段值
    	String _sort = Reflections.properties2field(sort);
    	if(_sort.lastIndexOf("_str")!=-1){
    		_sort = _sort.substring(0,_sort.lastIndexOf("_str"));
    	}
        // 排序字段，正序还是反序
        this.sort = _sort;
        this.order = order;
        this.selfDefinedCountSql = selfDefinedCountSql;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNowpage() {
        return nowpage;
    }

    public void setNowpage(int nowpage) {
        this.nowpage = nowpage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getSelfDefinedCountSql() {
		return selfDefinedCountSql;
	}

	public void setSelfDefinedCountSql(String selfDefinedCountSql) {
		this.selfDefinedCountSql = selfDefinedCountSql;
	}
}
