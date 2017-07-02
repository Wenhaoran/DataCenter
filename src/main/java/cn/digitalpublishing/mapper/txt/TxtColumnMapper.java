package cn.digitalpublishing.mapper.txt;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.txt.TxtColumn;

public interface TxtColumnMapper extends BaseMapper<TxtColumn,TxtColumn> {

	List<TxtColumn> getColumnByTxtId(String id);
	
}