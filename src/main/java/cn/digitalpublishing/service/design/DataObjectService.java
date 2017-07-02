package cn.digitalpublishing.service.design;

import java.util.HashMap;
import java.util.List;

import cn.digitalpublishing.po.base.Tree;
import cn.digitalpublishing.po.design.DataObject;


public interface DataObjectService {

	List<Tree> findTreeByDBId(String dbconnId,Boolean islist);

	List<DataObject> findListByCondition(HashMap<String, Object> condition);

	void insert(DataObject object);

	DataObject findByObjectId(String objectId);

	List<DataObject> findListByObjectId(String id);

	String createSql(List<DataObject> objList) throws Exception;

	String createSql(DataObject obj) throws Exception;

	void update(DataObject obj);

	void deleteById(String id);
}
