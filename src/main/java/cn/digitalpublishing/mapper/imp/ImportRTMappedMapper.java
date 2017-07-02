package cn.digitalpublishing.mapper.imp;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.imp.ImportRTMapped;

public interface ImportRTMappedMapper extends BaseMapper<ImportRTMapped,ImportRTMapped> {

	List<ImportRTMapped> findListByRoleId(String id);
	
}