package cn.digitalpublishing.service.design;

import java.util.HashMap;
import java.util.List;

import cn.digitalpublishing.po.design.DataField;
import cn.digitalpublishing.util.mybatis.page.PageInfo;


public interface DataFieldService {

	void findDataGrid(PageInfo pageInfo);

	void insert(DataField field);

	List<DataField> findListByCondition(HashMap<String, Object> condition);
	
	void deleteById(String id);

	void deleteListByIds(String[] ids);

	DataField findById(String id);

	void update(DataField field);
}
