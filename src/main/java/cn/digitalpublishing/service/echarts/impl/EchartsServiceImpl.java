package cn.digitalpublishing.service.echarts.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.digitalpublishing.mapper.design.DeDBConnectMapper;
import cn.digitalpublishing.mapper.echarts.EchartsMapper;
import cn.digitalpublishing.mapper.export.DataExportMapper;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.echarts.EchartJsonMap;
import cn.digitalpublishing.po.echarts.Echarts;
import cn.digitalpublishing.po.export.DataExport;
import cn.digitalpublishing.service.echarts.EchartsService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;

@Service("echartsService")
public class EchartsServiceImpl implements EchartsService {
	
	@Resource(name="echartsMapper")
    private EchartsMapper echartsMapper;
	
	@Resource(name="dataExportMapper")
    private DataExportMapper dataExportMapper;
	
	@Resource(name="deDBConnectMapper")
    private DeDBConnectMapper deDBConnectMapper;

	@Override
	public List<String> getJsonString(String jsonType,String template, List<EchartJsonMap> jsonMapList) throws Exception{
		DataExport export = dataExportMapper.findById(jsonMapList.get(0).getExpId());
		DeDBConnect db = deDBConnectMapper.findById(export.getDbId());
		if("oneArray".equals(jsonType)){
			List<String> searchList = DBUtil.searchOneArray(db,export.getSql(),jsonMapList.get(0).getColumn());
			//String mustJson = JSONArray.toJSONString(searchList);
			return searchList;
		}
		/*List<Map<String,String>> searchMapList = DBUtil.searchMapList(db,export.getSql());
		if("manyArray".equals(jsonType)){

		}*/
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getjsonArray(String jsonType, String template, List<EchartJsonMap> jsonMapList)throws Exception {
		
		DataExport export = dataExportMapper.findById(jsonMapList.get(0).getExpId());
		DeDBConnect db = deDBConnectMapper.findById(export.getDbId());
		List<Map<String,String>> searchMapList = DBUtil.searchMapList(db,export.getSql());
		if("jsonArray".equals(jsonType)){
			List<Map<String,Object>> jsonArrayList = new ArrayList<Map<String,Object>>();
			for(Map<String,String> map :searchMapList){
				String onejson = "";
				for(int i = 0;i<jsonMapList.size();i++){
					String value = map.get(jsonMapList.get(i).getColumn());
					if(i==0){
						onejson = template.replaceAll("数据"+(i+1), "\""+value+"\"");
					}else{
						onejson = onejson.replaceAll("数据"+(i+1), "\""+value+"\"");
					}
				}
				Map<String,Object> json2map = JSON.parseObject(onejson,  new TypeReference<Map<String,Object>>(){});
				jsonArrayList.add(json2map);
			}
			//String mustJson = JSONArray.toJSONString(jsonArrayList);
			return jsonArrayList;
			
		}
		return null;
	}
	
	@Override
	public List<Echarts> findListByCondition(Map<String, Object> condition) {
		return echartsMapper.findListByCondition(condition);
	}

	@Override
	public void insert(Echarts object) {
		echartsMapper.insert(object);
	}

	@Override
	public Echarts findByObjectId(String objectId) {
		return echartsMapper.findById(objectId);
	}

	@Override
	public List<Echarts> findListByObjectId(String id) {
		return null;
	}

	@Override
	public void update(Echarts obj) {
		echartsMapper.updateById(obj);
	}

	@Override
	public void deleteById(String id) {
		echartsMapper.deleteById(id);
	}

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(echartsMapper.findPageCondition(pageInfo));
	}

}
