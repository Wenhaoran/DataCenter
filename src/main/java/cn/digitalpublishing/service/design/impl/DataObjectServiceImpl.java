package cn.digitalpublishing.service.design.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.DataFieldMapper;
import cn.digitalpublishing.mapper.design.DataObjectMapper;
import cn.digitalpublishing.mapper.design.DeDBConnectMapper;
import cn.digitalpublishing.po.base.Tree;
import cn.digitalpublishing.po.design.DataField;
import cn.digitalpublishing.po.design.DataObject;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.service.design.DataObjectService;

@Service("dataObjectService")
public class DataObjectServiceImpl implements DataObjectService {
	
	@Resource(name="dataObjectMapper")
    private DataObjectMapper dataObjectMapper;
	
	@Resource(name="dataFieldMapper")
    private DataFieldMapper dataFieldMapper;
	
	@Resource(name="deDBConnectMapper")
    private DeDBConnectMapper deDBConnectMapper;
	
	@Override
	public void update(DataObject obj) {
		dataObjectMapper.updateById(obj);
	}
	
	@Override
	public String createSql(List<DataObject> objList)  throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("");
		for(DataObject obj : objList){
			sql.append(createSql(obj));
		}
		sql.append("");
		return sql.toString();
	}

	@Override
	public String createSql(DataObject obj) throws Exception{
		StringBuffer sql = new StringBuffer();
		DeDBConnect db = deDBConnectMapper.findById(obj.getDbId());
		String dbType = db.getType();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("tableId", obj.getId());
		//查询当前表 所有的字段
		List<DataField> fieldAllList = dataFieldMapper.findListByCondition(condition);
		List<DataField> refList = new ArrayList<DataField>();
		if("mysql".equals(dbType)){
			sql=createSqlByMysql(sql,fieldAllList,refList,obj);
		}else if("oracle".equals(dbType)){
			sql=createSqlByOracle(sql,fieldAllList,refList,obj);
		}else if("sqlserver".equals(dbType)){
			sql=createSqlByOracle(sql,fieldAllList,refList,obj);
		}
		
		return sql.toString();
	}
	
	public StringBuffer createSqlByOracle(StringBuffer sql,List<DataField> fieldAllList,List<DataField> refList,DataObject obj)throws Exception{
		//************************** 开始拼接sql **********************************
		sql.append("");
		sql.append("DROP TABLE IF EXISTS `"+obj.getTableName()+"`;"); // 换行：windows 用 \r\n 
		sql.append("CREATE TABLE `"+obj.getTableName()+"` (");
		sql.append("`id` varchar(32) NOT NULL COMMENT '对象ID',");
		sql.append("");
		//取出所有 字段
		for(DataField field : fieldAllList){
			sql.append("");
			//根据类型，来拼接sql  ，decimal or 引用 or other
			if(field.getType().equals("decimal")){
				sql.append("`"+field.getCode()+"` decimal("+field.getSize()+",0) DEFAULT "+field.getDef()+" COMMENT '"+field.getName()+"',");
			}else if(field.getType().equals("ref")){
				sql.append("`"+field.getCode()+"` varchar(64) DEFAULT "+field.getDef()+" COMMENT '"+field.getName()+"',");
				refList.add(field);
			}else{
				sql.append("`"+field.getCode()+"` "+field.getType()+"("+field.getSize()+") DEFAULT "+field.getDef()+" COMMENT '"+field.getName()+"',");
			}
			sql.append("");
		}
		sql.append("");
		sql.append("`create_id` varchar(64) DEFAULT NULL,");
		sql.append("`create_date` datetime(6) DEFAULT NULL,");
		sql.append("`update_id` varchar(64) DEFAULT NULL,");
		sql.append("`update_date` datetime(6) DEFAULT NULL,");
		sql.append("`status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',");
		sql.append("");
		int i = 1;
		for(DataField field : refList){
			sql.append("");
			sql.append("KEY `"+field.getCode()+"` (`"+field.getCode()+"`) USING BTREE,");
			//sql.append("KEY `obj_dbid` (`obj_dbid`) USING BTREE,");
			sql.append("CONSTRAINT `"+obj.getTableName()+"_ibfk_"+i+"` FOREIGN KEY (`"+field.getCode()+"`) REFERENCES `"+field.getRefTable()+"` (`"+field.getRefField()+"`),");
			//sql.append("CONSTRAINT `design_obj_ibfk_3` FOREIGN KEY (`obj_dbid`) REFERENCES `design_db` (`db_id`)");
			sql.append("");
			i++;
		}
		sql.append("");
		sql.append("PRIMARY KEY (`id`)");
		sql.append(") comment='"+obj.getDescription()+"' ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		sql.append("");
		//************************** sql拼接 end **********************************
		return sql;
	}
	
	public StringBuffer createSqlByMysql(StringBuffer sql,List<DataField> fieldAllList,List<DataField> refList,DataObject obj)throws Exception{
		//************************** 开始拼接sql **********************************
		sql.append("");
		sql.append("DROP TABLE IF EXISTS `"+obj.getTableName()+"`;"); // 换行：windows 用 \r\n 
		sql.append("CREATE TABLE `"+obj.getTableName()+"` (");
		sql.append("`id` varchar(32) NOT NULL COMMENT '对象ID',");
		sql.append("");
		//取出所有 字段
		for(DataField field : fieldAllList){
			sql.append("");
			//根据类型，来拼接sql  ，decimal or 引用 or other
			if(field.getType().equals("decimal")){
				sql.append("`"+field.getCode()+"` decimal("+field.getSize()+",0) DEFAULT "+field.getDef()+" COMMENT '"+field.getName()+"',");
			}else if(field.getType().equals("ref")){
				sql.append("`"+field.getCode()+"` varchar(64) DEFAULT "+field.getDef()+" COMMENT '"+field.getName()+"',");
				refList.add(field);
			}else{
				sql.append("`"+field.getCode()+"` "+field.getType()+"("+field.getSize()+") DEFAULT "+field.getDef()+" COMMENT '"+field.getName()+"',");
			}
			sql.append("");
		}
		sql.append("");
		sql.append("`create_id` varchar(64) DEFAULT NULL,");
		sql.append("`create_date` datetime(6) DEFAULT NULL,");
		sql.append("`update_id` varchar(64) DEFAULT NULL,");
		sql.append("`update_date` datetime(6) DEFAULT NULL,");
		sql.append("`status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',");
		sql.append("");
		int i = 1;
		for(DataField field : refList){
			sql.append("");
			sql.append("KEY `"+field.getCode()+"` (`"+field.getCode()+"`) USING BTREE,");
			//sql.append("KEY `obj_dbid` (`obj_dbid`) USING BTREE,");
			sql.append("CONSTRAINT `"+obj.getTableName()+"_ibfk_"+i+"` FOREIGN KEY (`"+field.getCode()+"`) REFERENCES `"+field.getRefTable()+"` (`"+field.getRefField()+"`),");
			//sql.append("CONSTRAINT `design_obj_ibfk_3` FOREIGN KEY (`obj_dbid`) REFERENCES `design_db` (`db_id`)");
			sql.append("");
			i++;
		}
		sql.append("");
		sql.append("PRIMARY KEY (`id`)");
		sql.append(") comment='"+obj.getDescription()+"' ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		sql.append("");
		//************************** sql拼接 end **********************************
		return sql;
	}
	
	@Override
	public List<Tree> findTreeByDBId(String dbId,Boolean islist) {
		List<Tree> trees = new ArrayList<Tree>();
        List<DataObject> objectList = dataObjectMapper.findResourceByDBIdAndPidNull(dbId);
        if (objectList != null) {
            for (DataObject object : objectList) {
            	HashMap<String,Object> condition = new HashMap<String,Object>();
                Tree treeOne = new Tree();
                treeOne.setId(object.getId());
                treeOne.setText(object.getName());
                condition.put("parentId", object.getId());
                if(islist){
                	condition.put("type",1);
                }
                treeOne.setChildren(findChildOrgByPid(condition,islist));
                treeOne.setType(String.valueOf(object.getType()));
                trees.add(treeOne);
            }
        }
        System.out.println();
        return trees;
	}
	
	private List<Tree> findChildOrgByPid(HashMap<String,Object> condition,Boolean islist) {
    	List<Tree> trees = new ArrayList<Tree>();  
        List<DataObject> objectList = dataObjectMapper.findListByCondition(condition);
        if(objectList != null){
        	for(DataObject object : objectList){
        		if(condition.get("orgId")!= null && 
            			((String)condition.get("orgId")).equals(object.getId())){
            		continue;
            	}
            	Tree tree = new Tree();
                tree.setId(object.getId());
                tree.setText(object.getName());
                condition.put("parentId", object.getId());
                if(islist){
                	condition.put("type",1);
                }
                tree.setChildren(findChildOrgByPid(condition,islist));
                trees.add(tree);
            }
        }
        return trees;
    }

	@Override
	public List<DataObject> findListByCondition(HashMap<String, Object> condition) {
		return dataObjectMapper.findListByCondition(condition);
	}

	@Override
	public void insert(DataObject object) {
		dataObjectMapper.insert(object);
	}

	@Override
	public DataObject findByObjectId(String objectId) {
		return dataObjectMapper.findById(objectId);
	}

	@Override
	public List<DataObject> findListByObjectId(String id) {
		List<DataObject> objList = new ArrayList<DataObject>();
		//根据objId，查询 当前 obj 的所有子集。
		DataObject object = dataObjectMapper.findById(id);
		if(object.getType()==1){
			List<DataObject> list = dataObjectMapper.findListByObjectId(id);
			for(DataObject obj : list){
				if(obj.getType()==1){
					List<DataObject> cObjList = findListByObjectId(obj.getId());
					for(DataObject cObj : cObjList){
						objList.add(cObj);
					}
				}else{
					objList.add(obj);
				}
			}
		}else{
			objList.add(object);
		}
		return objList;
	}

	@Override
	public void deleteById(String id) {
		dataObjectMapper.deleteById(id);
	}

}
/**
DROP TABLE IF EXISTS `design_obj`;
CREATE TABLE `design_obj` (
  `id` varchar(32) NOT NULL COMMENT '对象ID',
  
  `obj_code` varchar(32) DEFAULT NULL COMMENT '对象编号',
  `obj_name` varchar(64) DEFAULT NULL COMMENT '对象名称',
  `obj_description` varchar(64) DEFAULT NULL COMMENT '对象描述',
  `obj_leaf` decimal(10,0) DEFAULT NULL COMMENT '叶子节点：1-是 2-否 ', -- 只有叶子节点，算是数据库表
  `obj_type` decimal(10,0) DEFAULT NULL COMMENT '类型：1-目录 2-对象 ', -- 只有叶子节点，算是数据库表
  `obj_parentId` varchar(64) DEFAULT NULL COMMENT '父节点ID',
  `obj_tablename` varchar(64) DEFAULT NULL COMMENT '表名',-- 如果属于叶子节点，此节点，应该有对应数据 表
  
  `obj_dbid` varchar(64) DEFAULT NULL COMMENT '当前对象所属数据库',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`obj_id`),
  KEY `obj_dbid` (`obj_dbid`) USING BTREE,
  CONSTRAINT `design_obj_ibfk_3` FOREIGN KEY (`obj_dbid`) REFERENCES `design_db` (`db_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */