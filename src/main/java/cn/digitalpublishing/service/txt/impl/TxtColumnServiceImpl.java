package cn.digitalpublishing.service.txt.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.txt.TxtColumnMapper;
import cn.digitalpublishing.po.txt.TxtColumn;
import cn.digitalpublishing.service.txt.TxtColumnService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("txtColumnService")
public class TxtColumnServiceImpl implements TxtColumnService {
	
	@Resource(name="txtColumnMapper")
    private TxtColumnMapper txtColumnMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(txtColumnMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<TxtColumn> getTxtColumnList(Map<String, Object> condition)throws Exception {
		return txtColumnMapper.findListByCondition(condition);
	}

	@Override
	public TxtColumn getTxtColumnById(String id) throws Exception {
		return txtColumnMapper.findById(id);
	}

	@Override
	public void updateTxtColumn(TxtColumn obj) throws Exception {
		txtColumnMapper.updateById(obj);
	}

	@Override
	public void insertTxtColumn(TxtColumn obj) throws Exception {
		txtColumnMapper.insert(obj);
	}

	@Override
	public void deleteTxtColumnById(String id) throws Exception {
		txtColumnMapper.deleteById(id);
	}

	@Override
	public void batchDelByTxtId(String txtId, String[] ids) {
		for(String id:ids){
			txtColumnMapper.deleteById(id);
		}
	}

	@Override
	public List<TxtColumn> getTxtColumnListByTxtId(String id) {
		return txtColumnMapper.getColumnByTxtId(id);
	}

}
