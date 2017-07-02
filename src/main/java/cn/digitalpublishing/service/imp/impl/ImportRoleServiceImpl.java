package cn.digitalpublishing.service.imp.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.imp.ImportRoleMapper;
import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.service.imp.ImportRoleService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("importRoleService")
public class ImportRoleServiceImpl implements ImportRoleService {
	
	@Resource(name="importRoleMapper")
    private ImportRoleMapper importRoleMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(importRoleMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<ImportRole> getImportRoleList(Map<String, Object> condition)throws Exception {
		return importRoleMapper.findListByCondition(condition);
	}

	@Override
	public ImportRole getImportRoleById(String id) throws Exception {
		return importRoleMapper.findById(id);
	}

	@Override
	public void updateImportRole(ImportRole obj) throws Exception {
		importRoleMapper.updateById(obj);
	}

	@Override
	public void insertImportRole(ImportRole obj) throws Exception {
		importRoleMapper.insert(obj);
	}

	@Override
	public void deleteImportRoleById(String id) throws Exception {
		importRoleMapper.deleteById(id);
	}

}
