package cn.digitalpublishing.service.excel.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.excel.XlsColumnMapper;
import cn.digitalpublishing.po.excel.XlsColumn;
import cn.digitalpublishing.service.excel.XlsColumnService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("xlsColumnService")
public class XlsColumnServiceImpl implements XlsColumnService {
	
	@Resource(name="xlsColumnMapper")
    private XlsColumnMapper xlsColumnMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(xlsColumnMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<XlsColumn> getXlsColumnList(Map<String, Object> condition)throws Exception {
		return xlsColumnMapper.findListByCondition(condition);
	}

	@Override
	public XlsColumn getXlsColumnById(String id) throws Exception {
		return xlsColumnMapper.findById(id);
	}

	@Override
	public void updateXlsColumn(XlsColumn obj) throws Exception {
		xlsColumnMapper.updateById(obj);
	}

	@Override
	public void insertXlsColumn(XlsColumn obj) throws Exception {
		xlsColumnMapper.insert(obj);
	}

	@Override
	public void deleteXlsColumnById(String id) throws Exception {
		xlsColumnMapper.deleteById(id);
	}

	@Override
	public void batchDelByXlsId(String xlsId, String[] ids) {
		for(String id:ids){
			xlsColumnMapper.deleteById(id);
		}
	}

	@Override
	public List<XlsColumn> getXlsObjectListByXlxId(String id) {
		return xlsColumnMapper.getColumnByXlsId(id);
	}

}
