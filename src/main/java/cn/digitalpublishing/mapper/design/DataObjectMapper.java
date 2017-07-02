package cn.digitalpublishing.mapper.design;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.design.DataObject;

public interface DataObjectMapper extends BaseMapper<DataObject,DataObject> {

	List<DataObject> findResourceByDBIdAndPidNull(String dbconnId);

	List<DataObject> findListByObjectId(String id);
	
}