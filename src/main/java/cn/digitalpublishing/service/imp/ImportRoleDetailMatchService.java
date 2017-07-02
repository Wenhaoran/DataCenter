package cn.digitalpublishing.service.imp;

import cn.digitalpublishing.po.imp.ImportRoleDetailMatch;

public interface ImportRoleDetailMatchService {
	
	void deleteById(String id);
	
	ImportRoleDetailMatch findByDetailId(String detailId);
	
	void insert(ImportRoleDetailMatch match);

}
