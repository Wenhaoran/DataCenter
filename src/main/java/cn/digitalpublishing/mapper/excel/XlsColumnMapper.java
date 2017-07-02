package cn.digitalpublishing.mapper.excel;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.excel.XlsColumn;

public interface XlsColumnMapper extends BaseMapper<XlsColumn,XlsColumn> {

	List<XlsColumn> getColumnByXlsId(String id);
	
}