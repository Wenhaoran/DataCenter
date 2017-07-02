package cn.digitalpublishing.service.imp.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.imp.ImportLogMapper;
import cn.digitalpublishing.po.imp.ImportLog;
import cn.digitalpublishing.service.imp.ImportLogService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("importLogService")
public class ImportLogServiceImpl implements ImportLogService {
	
	@Resource(name="importLogMapper")
    private ImportLogMapper importLogMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		List<ImportLog> logList = importLogMapper.findPageCondition(pageInfo);
		pageInfo.setRows(logList);
	}

	@Override
	public List<ImportLog> getImportLogList(Map<String, Object> condition)throws Exception {
		return importLogMapper.findListByCondition(condition);
	}

	@Override
	public ImportLog getImportLogById(String id) throws Exception {
		return importLogMapper.findById(id);
	}

	@Override
	public void updateImportLog(ImportLog obj) throws Exception {
		importLogMapper.updateById(obj);
	}

	@Override
	public void insertImportLog(ImportLog obj) throws Exception {
		importLogMapper.insert(obj);
	}

	@Override
	public void deleteImportLogById(String id) throws Exception {
		importLogMapper.deleteById(id);
	}

}
