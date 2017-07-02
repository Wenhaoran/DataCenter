package cn.digitalpublishing.mapper.imp;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.imp.ImportRoleDetailMatch;

public interface ImportRoleDetailMatchMapper extends BaseMapper<ImportRoleDetailMatch,ImportRoleDetailMatch> {

	ImportRoleDetailMatch findByDetailId(String detailId);
	
}