package cn.digitalpublishing.service.imp.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.DeDBConnectMapper;
import cn.digitalpublishing.mapper.imp.ImportRTMappedMapper;
import cn.digitalpublishing.mapper.imp.ImportRoleDetailMapper;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.imp.ImportRTMapped;
import cn.digitalpublishing.po.imp.ImportRoleDetail;
import cn.digitalpublishing.service.imp.ImportRTMappedService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("importRTMappedService")
public class ImportRTMappedServiceImpl implements ImportRTMappedService {
	
	@Resource(name="importRTMappedMapper")
    private ImportRTMappedMapper importRTMappedMapper;
	
	@Resource(name="deDBConnectMapper")
    private DeDBConnectMapper deDBConnectMapper;
	
	@Resource(name="importRoleDetailMapper")
    private ImportRoleDetailMapper importRoleDetailMapper;
	
	@Override
	public void createSql(String roleId,String createId) throws Exception {
		List<ImportRTMapped> rtList = importRTMappedMapper.findListByRoleId(roleId);
		for(ImportRTMapped rt :rtList){
			List<ImportRoleDetail> detailList = findDetailList(roleId, rt.getId());
			createSql(rt,detailList,createId);
			importRTMappedMapper.updateById(rt);
		}
		Set<String> rt1 = new HashSet<String>(); 
		List<ImportRTMapped> rtOne = new ArrayList<ImportRTMapped>();
		for(ImportRTMapped rt :rtList){
			if(rt.getInsertSort().equals("0")){
				rt1.add(rt.getId());
				rtOne.add(rt);
			}
		}
		rtList.removeAll(rtOne);
		int sort = 1;
		otherSort(sort,rt1,rtList);
	}
	
	
	private void otherSort(int sort,Set<String> rt1, List<ImportRTMapped> rtList) {
		Set<String> rtOther = new HashSet<String>();
		
		List<ImportRTMapped> rtOtherList = new ArrayList<ImportRTMapped>();
		for(ImportRTMapped rt :rtList){
			Set<String> rtKey = getKey(rt);
			Iterator<String> ite2 = rtKey.iterator();
			boolean isFullEqual = true;
			
			while (ite2.hasNext()) {
				if (!rt1.contains(ite2.next())) {
					isFullEqual = false;
					break;
				}
			}
			if(isFullEqual){
				rtOtherList.add(rt);
				rt.setInsertSort(sort+"");
				rtOther.add(rt.getId());
				importRTMappedMapper.updateById(rt);
			}
		}
		rt1.addAll(rtOther);
		rtList.removeAll(rtOtherList);
		sort = sort+1;
		if(rtList.size()>0){
			otherSort(sort,rt1,rtList);
		}
	}
	
	private Set<String> getKey(ImportRTMapped rt) {
		List<ImportRoleDetail> detailList = importRoleDetailMapper.findListByRTId(rt.getId());
		Set<String> key = new HashSet<String>();
		for(ImportRoleDetail detail:detailList){
			if(detail.getMappedType().equals("1")){
				key.add(detail.getObjId());
			}
		}
		return key;
	}
/**
 * INSERT INTO `design`.`excel_one` (
	`id`,
	`name`,
	`type`,
	`code`,
	`create_id`,
	`create_date`,
	`update_id`,
	`update_date`,
	`status`
	
			'1d3d89783b7411e78db10a0027000000',
			'name',
			'txt',
			'0',
			NULL,
			NULL,
			NULL,
			NULL,
			NULL
		);
 */	
	public void createSql(ImportRTMapped rt,List<ImportRoleDetail> detailList,String createId){
		DeDBConnect db = deDBConnectMapper.findById(rt.getDbId());
		String dbType = db.getType();
		//遍历规则明细， 分别生成，  文件映射list   和   	外键映射list
		List<ImportRoleDetail> filemappedList = new ArrayList<>();
		List<ImportRoleDetail> keymappedList = new ArrayList<>();
		for(ImportRoleDetail detail: detailList){
			if(detail.getMappedType().equals("0")){
				filemappedList.add(detail);
			}else{
				keymappedList.add(detail);
			}
		}
		StringBuffer sb = sqlSb(dbType,rt,detailList,createId);
		
		StringBuffer paramIds = new StringBuffer();
		paramIds.append("");
		for(ImportRoleDetail detail:detailList){
			paramIds.append(detail.getId()+",");
		}
		rt.setSql(sb.toString());
		rt.setParamIds(paramIds.toString());
		rt.setInsertSort(keymappedList.size()+"");
		rt.setUpdateId(createId);
		rt.setUpdateDate(new Date());
	}
	
	@SuppressWarnings("unused")
	public StringBuffer sqlSb(String type,ImportRTMapped rt,List<ImportRoleDetail> detailList,String createId ){
		StringBuffer sb = new StringBuffer();
		sb.append("");
		sb.append("INSERT INTO `"+rt.getDbTable()+"` ( ");//`design`.
		sb.append("`id`,");
		for(ImportRoleDetail detail:detailList){
			sb.append("`"+detail.getTableColumn()+"`,");
		}
		sb.append("`create_id`,");
		sb.append("`create_date`,");
		sb.append("`status`");
		sb.append(")VALUES(");
		sb.append("?,");
		for(ImportRoleDetail detail:detailList){
			sb.append("?,");
		}
		sb.append(""+createId+",");
		if("mysql".equals(type)){
			sb.append(" NOW(),");
		}else if("oracle".equals(type)){
			sb.append("(select sysdate from dual),");
		}
		
		sb.append("0");
		sb.append(");");
		return sb;
	}
	
	public List<ImportRoleDetail> findDetailList(String roleId,String rtId){
		Map<String,Object> condition = new HashMap<>();
		condition.put("roleId", roleId);
		condition.put("rtId", rtId);
		List<ImportRoleDetail> detailList = importRoleDetailMapper.findListByCondition(condition);
		return detailList;
	}

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		
		List<ImportRTMapped> rtList = importRTMappedMapper.findPageCondition(pageInfo);
		for(ImportRTMapped rt :rtList){
			DeDBConnect conn = deDBConnectMapper.findById(rt.getDbId());
			rt.setDbName(conn.getName());
		}
		
		pageInfo.setRows(rtList);
	}

	@Override
	public List<ImportRTMapped> getImportRTMappedList(Map<String, Object> condition)throws Exception {
		List<ImportRTMapped> rtList = importRTMappedMapper.findListByCondition(condition);
		for(ImportRTMapped rt :rtList){
			DeDBConnect conn = deDBConnectMapper.findById(rt.getDbId());
			rt.setDbName(conn.getName());
		}
		return rtList;
	}

	@Override
	public ImportRTMapped getImportRTMappedById(String id) throws Exception {
		return importRTMappedMapper.findById(id);
	}

	@Override
	public void updateImportRTMapped(ImportRTMapped obj) throws Exception {
		importRTMappedMapper.updateById(obj);
	}

	@Override
	public void insertImportRTMapped(ImportRTMapped obj) throws Exception {
		importRTMappedMapper.insert(obj);
	}

	@Override
	public void deleteImportRTMappedById(String id) throws Exception {
		importRTMappedMapper.deleteById(id);
	}

	@Override
	public void deleteListByIds(String[] ids) {
		for(String id:ids){
			importRTMappedMapper.deleteById(id);
		}
	}

	@Override
	public List<ImportRTMapped> getImportRTMappedByRoleId(String id) {
		return importRTMappedMapper.findListByRoleId(id);
	}

}
