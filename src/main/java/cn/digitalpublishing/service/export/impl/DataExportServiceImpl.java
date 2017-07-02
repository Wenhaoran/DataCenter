package cn.digitalpublishing.service.export.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.DeDBConnectMapper;
import cn.digitalpublishing.mapper.export.DataExportMapper;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.export.DataExport;
import cn.digitalpublishing.service.export.DataExportService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("dataExportService")
public class DataExportServiceImpl implements DataExportService {
	
	@Resource(name="dataExportMapper")
    private DataExportMapper dataExportMapper;
	
	@Resource(name="deDBConnectMapper")
    private DeDBConnectMapper deDBConnectMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		List<DataExport> exportList = dataExportMapper.findPageCondition(pageInfo);
		for(DataExport export: exportList){
			DeDBConnect conn = deDBConnectMapper.findById(export.getDbId());
			export.setConn(conn);
			export.setConnName(conn.getName());
		}
		pageInfo.setRows(exportList);
	}

	@Override
	public List<DataExport> getDataExportList(Map<String, Object> condition)throws Exception {
		return dataExportMapper.findListByCondition(condition);
	}

	@Override
	public DataExport getDataExportById(String id) throws Exception {
		return dataExportMapper.findById(id);
	}

	@Override
	public void updateDataExport(DataExport obj) throws Exception {
		dataExportMapper.updateById(obj);
	}

	@Override
	public void insertDataExport(DataExport obj) throws Exception {
		dataExportMapper.insert(obj);
	}

	@Override
	public void deleteDataExportById(String id) throws Exception {
		dataExportMapper.deleteById(id);
	}

}
