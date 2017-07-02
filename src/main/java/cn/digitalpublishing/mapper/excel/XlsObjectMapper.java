package cn.digitalpublishing.mapper.excel;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.excel.XlsObject;

public interface XlsObjectMapper extends BaseMapper<XlsObject,XlsObject> {

	List<XlsObject> getColumnByXlsId(String id);
	
}