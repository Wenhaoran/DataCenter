package cn.digitalpublishing.service.imp.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.excel.XlsColumnMapper;
import cn.digitalpublishing.mapper.excel.XlsObjectMapper;
import cn.digitalpublishing.mapper.imp.ImportRTMappedMapper;
import cn.digitalpublishing.mapper.imp.ImportRoleDetailMapper;
import cn.digitalpublishing.mapper.imp.ImportRoleDetailMatchMapper;
import cn.digitalpublishing.mapper.imp.ImportRoleMapper;
import cn.digitalpublishing.mapper.txt.TxtColumnMapper;
import cn.digitalpublishing.mapper.txt.TxtObjectMapper;
import cn.digitalpublishing.po.excel.XlsColumn;
import cn.digitalpublishing.po.imp.ImportRTMapped;
import cn.digitalpublishing.po.imp.ImportRoleDetail;
import cn.digitalpublishing.po.imp.ImportRoleDetailMatch;
import cn.digitalpublishing.po.txt.TxtColumn;
import cn.digitalpublishing.service.imp.ImportRoleDetailService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("importRoleDetailService")
public class ImportRoleDetailServiceImpl implements ImportRoleDetailService {
	
	@Resource(name="importRoleDetailMapper")
    private ImportRoleDetailMapper importRoleDetailMapper;
	
	@Resource(name="importRoleMapper")
    private ImportRoleMapper importRoleMapper;
	
	@Resource(name="importRTMappedMapper")
    private ImportRTMappedMapper importRTMappedMapper;
	
	@Resource(name="xlsObjectMapper")
    private XlsObjectMapper xlsObjectMapper;
	
	@Resource(name="txtObjectMapper")
    private TxtObjectMapper txtObjectMapper;
	
	@Resource(name="xlsColumnMapper")
    private XlsColumnMapper xlsColumnMapper;
	
	@Resource(name="txtColumnMapper")
    private TxtColumnMapper txtColumnMapper;

	@Resource(name="importRoleDetailMatchMapper")
    private ImportRoleDetailMatchMapper importRoleDetailMatchMapper;
	
	@Override
	public void findDataGrid(PageInfo pageInfo) {
		
		List<ImportRoleDetail> detailList = importRoleDetailMapper.findPageCondition(pageInfo);
		for(ImportRoleDetail detail : detailList){
			if(detail.getMappedType().equals("0")){
				if(detail.getType().equals("xls")){
					XlsColumn column = xlsColumnMapper.findById(detail.getColumnId());
					detail.setColumnName(column.getCode());
				}else if(detail.getType().equals("txt")){
					TxtColumn column = txtColumnMapper.findById(detail.getColumnId());
					detail.setColumnName(column.getCode());
				}
				detail.setObjName("当前对象");
			}else if(detail.getMappedType().equals("1")){
				ImportRTMapped rt = importRTMappedMapper.findById(detail.getObjId());
				detail.setObjName("表："+rt.getDbTable());
				detail.setColumnName(detail.getColumnId());
			}else if(detail.getMappedType().equals("2")){
				ImportRoleDetailMatch match = importRoleDetailMatchMapper.findByDetailId(detail.getId());
				detail.setObjName("表："+match.getTable());
				detail.setColumnName(match.getCode());
			}
			
			ImportRTMapped rt = importRTMappedMapper.findById(detail.getImportRTId());
			detail.setTableName(rt.getDbTable());
		}
		pageInfo.setRows(detailList);
	}

	@Override
	public List<ImportRoleDetail> getImportRoleDetailList(Map<String, Object> condition)throws Exception {
		List<ImportRoleDetail> detailList = importRoleDetailMapper.findListByCondition(condition);
		for(ImportRoleDetail detail : detailList){
			if(detail.getMappedType().equals("0")){
				if(detail.getType().equals("xls")){
					XlsColumn column = xlsColumnMapper.findById(detail.getColumnId());
					detail.setColumnName(column.getCode());
				}else if(detail.getType().equals("txt")){
					TxtColumn column = txtColumnMapper.findById(detail.getColumnId());
					detail.setColumnName(column.getCode());
				}
				detail.setObjName("当前对象");
			}else if(detail.getMappedType().equals("1")){
				ImportRTMapped rt = importRTMappedMapper.findById(detail.getObjId());
				detail.setObjName("表："+rt.getDbTable());
				detail.setColumnName(detail.getColumnId());
			}else if(detail.getMappedType().equals("2")){
				ImportRoleDetailMatch match = importRoleDetailMatchMapper.findByDetailId(detail.getId());
				detail.setObjName("表："+match.getTable());
				detail.setColumnName(match.getCode());
			}
			
			ImportRTMapped rt = importRTMappedMapper.findById(detail.getImportRTId());
			detail.setTableName(rt.getDbTable());
		}
		return detailList;
	}

	@Override
	public ImportRoleDetail getImportRoleDetailById(String id) throws Exception {
		return importRoleDetailMapper.findById(id);
	}

	@Override
	public void updateImportRoleDetail(ImportRoleDetail obj) throws Exception {
		importRoleDetailMapper.updateById(obj);
	}

	@Override
	public void insertImportRoleDetail(ImportRoleDetail obj) throws Exception {
		importRoleDetailMapper.insert(obj);
	}

	@Override
	public void deleteImportRoleDetailById(String id) throws Exception {
		
	}

	@Override
	public void deleteByIds(String[] ids) {
		for(String id:ids){
			importRoleDetailMapper.deleteById(id);
		}
	}

}
