package cn.digitalpublishing.service.imp.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.imp.ImportTimeTaskMapper;
import cn.digitalpublishing.po.imp.ImportTimeTask;
import cn.digitalpublishing.service.imp.ImportTimeTaskService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("importTimeTaskService")
public class ImportTimeTaskServiceImpl implements ImportTimeTaskService {
	
	@Resource(name="importTimeTaskMapper")
    private ImportTimeTaskMapper importTimeTaskMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(importTimeTaskMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<ImportTimeTask> getImportTimeTaskList(Map<String, Object> condition)throws Exception {
		return importTimeTaskMapper.findListByCondition(condition);
	}

	@Override
	public ImportTimeTask getImportTimeTaskById(String id) throws Exception {
		return importTimeTaskMapper.findById(id);
	}

	@Override
	public void updateImportTimeTask(ImportTimeTask obj) throws Exception {
		importTimeTaskMapper.updateById(obj);
	}

	@Override
	public void insertImportTimeTask(ImportTimeTask obj) throws Exception {
		importTimeTaskMapper.insert(obj);
	}

	@Override
	public void deleteImportTimeTaskById(String id) throws Exception {
		importTimeTaskMapper.deleteById(id);
	}

}
