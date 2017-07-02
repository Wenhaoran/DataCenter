package cn.digitalpublishing.mapper.imp;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.imp.ImportRoleDetail;

public interface ImportRoleDetailMapper extends BaseMapper<ImportRoleDetail,ImportRoleDetail> {

	List<ImportRoleDetail> findListByRoleId(String id);

	//查询 被插入 
	List<ImportRoleDetail> findListByRTId(String id);

	//查询，外键
	List<ImportRoleDetail> findListByKeyRTId(String id);
	
}