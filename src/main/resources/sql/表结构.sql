/*
design 代码表结构设计  v0.1


数据库类型		      : MYSQL

Date: 2017-03-14 13:45:52
*/

-- design_db	数据库连接
-- ----------------------------
-- Table structure for design_db
-- cn.digitalpublishing.po.design.DeDBConnect
-- ----------------------------
DROP TABLE IF EXISTS `design_db`;
CREATE TABLE `design_db` (
  `db_id` varchar(32) NOT NULL COMMENT '数据库连接ID',
  
  `db_code` varchar(32) DEFAULT NULL COMMENT '连接编号',
  `db_name` varchar(64) DEFAULT NULL COMMENT '连接别名',
  `db_type` varchar(64) DEFAULT NULL COMMENT '类型',
  `db_address` varchar(64) DEFAULT NULL COMMENT '地址',
  `db_port` varchar(64) DEFAULT NULL COMMENT '端口',
  `db_dbname` varchar(64) DEFAULT NULL COMMENT '数据库名称',
  `db_username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `db_password` varchar(64) DEFAULT NULL COMMENT '密码',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`db_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





-- design_obj	对象设计 树
-- ----------------------------
-- Table structure for design_obj
-- cn.digitalpublishing.po.design.DataObject
-- ----------------------------
DROP TABLE IF EXISTS `design_obj`;
CREATE TABLE `design_obj` (
  `obj_id` varchar(32) NOT NULL COMMENT '对象ID',
  
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





-- design_field	对象 字段 设计 
-- ----------------------------
-- Table structure for design_field
-- cn.digitalpublishing.po.design.DataObject
-- ----------------------------
DROP TABLE IF EXISTS `design_field`;
CREATE TABLE `design_field` (
  `field_id` varchar(32) NOT NULL COMMENT '字段ID',
  
  `field_code` varchar(32) DEFAULT NULL COMMENT '字段编号',
  `field_name` varchar(64) DEFAULT NULL COMMENT '字段名称',
  `field_type` varchar(64) DEFAULT NULL COMMENT '字段类型',
  `field_size` varchar(64) DEFAULT NULL COMMENT '字段长度',
  `field_must` decimal(10,0) DEFAULT NULL COMMENT '是否必需：1-是 2-否 ', -- 只有叶子节点，算是数据库表
  `field_def` varchar(64) DEFAULT NULL COMMENT '字段默认值',
  `field_description` varchar(256) DEFAULT NULL COMMENT '字段描述',
  
  `field_reftable` varchar(64) DEFAULT NULL COMMENT '字段 关联表名',
  `field_reffield` varchar(64) DEFAULT NULL COMMENT '关联表字段名称',
  
  `field_tableid` varchar(64) DEFAULT NULL COMMENT '当前字段所属 对象ID',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`field_id`),
  KEY `field_tableid` (`field_tableid`) USING BTREE,
  CONSTRAINT `design_field_ibfk_3` FOREIGN KEY (`field_tableid`) REFERENCES `design_obj` (`obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- design_table	业务方案表
-- ----------------------------
-- Table structure for design_table
-- cn.digitalpublishing.po.design.DesignTable
-- ----------------------------
DROP TABLE IF EXISTS `design_table`;
CREATE TABLE `design_table` (
  `tab_id` varchar(32) NOT NULL COMMENT '对象ID',
  `tab_tablename` varchar(64) DEFAULT NULL COMMENT '表名称',
  `tab_name` varchar(64) DEFAULT NULL COMMENT '方案名称',
  `tab_comments` varchar(64) DEFAULT NULL COMMENT '描述',
  `tab_className` varchar(64) DEFAULT NULL COMMENT '实体类名称',
  `tab_type` varchar(64) DEFAULT NULL COMMENT '类型',
  
  `tab_dbid` varchar(64) DEFAULT NULL COMMENT '当前业务表所属数据库',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`tab_id`),
  KEY `tab_dbid` (`tab_dbid`) USING BTREE,
  CONSTRAINT `design_tab_ibfk_3` FOREIGN KEY (`tab_dbid`) REFERENCES `design_db` (`db_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- design_tableColumn	业务表  字段 表
-- ----------------------------
-- Table structure for design_tableColumn
-- cn.digitalpublishing.po.design.DesignTableColumn
-- ----------------------------
DROP TABLE IF EXISTS `design_tableColumn`;
CREATE TABLE `design_tableColumn` (
  `col_id` varchar(32) NOT NULL COMMENT '字段ID',
  
  `col_tableId` varchar(64) DEFAULT NULL COMMENT '所属表ID',
  `col_name` varchar(64) DEFAULT NULL COMMENT '列名',
  `col_type` varchar(64) DEFAULT NULL COMMENT '类型',
  `col_comments` varchar(256) DEFAULT NULL COMMENT '描述',
  `col_javaType` varchar(64) DEFAULT NULL COMMENT 'java类型',
  `col_javaField` varchar(64) DEFAULT NULL COMMENT 'JAVA字段名',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`col_id`),
  KEY `col_tableId` (`col_tableId`) USING BTREE,
  CONSTRAINT `design_col_ibfk_3` FOREIGN KEY (`col_tableId`) REFERENCES `design_table` (`tab_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- design_tableConfig	业务表  设计 表
-- ----------------------------
-- Table structure for design_tableConfig
-- cn.digitalpublishing.po.design.DesignTableConfig
-- ----------------------------
DROP TABLE IF EXISTS `design_tableConfig`;
CREATE TABLE `design_tableConfig` (
  `conf_id` varchar(32) NOT NULL COMMENT '字段ID',
  
  `conf_hostName` varchar(64) DEFAULT NULL COMMENT 'ftp服务器地址',
  `conf_port` varchar(64) DEFAULT NULL COMMENT 'ftp服务器端口',
  `conf_username` varchar(64) DEFAULT NULL COMMENT 'ftp登录账户',
  `conf_password` varchar(64) DEFAULT NULL COMMENT 'ftp登录密码',
  `conf_pathname` varchar(64) DEFAULT NULL COMMENT 'ftp 保存路径',
  `conf_originfilename` varchar(64) DEFAULT NULL COMMENT '待上传文件的名称（绝对地址）',
  
  `conf_packagePath` varchar(64) DEFAULT NULL COMMENT '生成包路径',
  `conf_projectName` varchar(64) DEFAULT NULL COMMENT '项目名',
  `conf_moduleName` varchar(64) DEFAULT NULL COMMENT '模块名',
  
  `tab_id` varchar(64) DEFAULT NULL COMMENT '所属表ID',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`conf_id`),
  KEY `tab_id` (`tab_id`) USING BTREE,
  CONSTRAINT `design_conf_ibfk_3` FOREIGN KEY (`tab_id`) REFERENCES `design_table` (`tab_id`)
) comment='业务设计 配置 表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;





-- design_ftp	FTP连接
-- ----------------------------
-- Table structure for design_ftp
-- cn.digitalpublishing.po.design.FTPConnect
-- ----------------------------
DROP TABLE IF EXISTS `design_ftp`;
CREATE TABLE `design_ftp` (
  `ftp_id` varchar(32) NOT NULL COMMENT '数据库连接ID',
  
  `ftp_code` varchar(32) DEFAULT NULL COMMENT '连接编号',
  `ftp_name` varchar(64) DEFAULT NULL COMMENT '连接别名',
  
  `ftp_type` varchar(64) DEFAULT NULL COMMENT '类型',
  
  `ftp_address` varchar(64) DEFAULT NULL COMMENT '地址',
  `ftp_port` varchar(64) DEFAULT NULL COMMENT '端口',
  `ftp_username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `ftp_password` varchar(64) DEFAULT NULL COMMENT '密码',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`ftp_id`)
)  COMMENT 'ftp 链接信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- design_excel	excel对象
-- ----------------------------
-- Table structure for design_excel
-- cn.digitalpublishing.po.design.XlsObject
-- ----------------------------
DROP TABLE IF EXISTS `design_excel`;
CREATE TABLE `design_excel` (
  `xls_id` varchar(32) NOT NULL COMMENT 'excel对象ID',
  
  `xls_name` varchar(64) DEFAULT NULL COMMENT '对象名称',
  `xls_code` varchar(32) DEFAULT NULL COMMENT '对象code',
  
  `xls_pathtype` varchar(64) DEFAULT NULL COMMENT '目录类型', -- 目录类型 类型：0.本地路径  1.FTP 路径 
  `ftp_id` varchar(64) DEFAULT NULL COMMENT 'FTPID', -- FTP Id 	
  `xls_path` varchar(64) DEFAULT NULL COMMENT 'excel文件 路径',
  `xls_pathsuccess` varchar(64) DEFAULT NULL COMMENT 'excel文件 成功 路径',
  `xls_patherror` varchar(64) DEFAULT NULL COMMENT 'excel文件 失败 路径',
  
  `xls_type` varchar(64) DEFAULT NULL COMMENT '对象 类型', -- 对象类型  0.未关联 db  1.已关联db 
  `db_id` varchar(64) DEFAULT NULL COMMENT '关联表的 dbId', -- FTP Id
  `db_table` varchar(64) DEFAULT NULL COMMENT '关联表的 table', -- FTP Id

  `xls_mapped` varchar(64) DEFAULT NULL COMMENT '是否已经字段映射完成', -- 对象类型  0.字段映射未完成    1.字段已全部映射。
  
  `task_id` varchar(64) DEFAULT NULL COMMENT '任务 ID', -- FTP Id
  `xls_ifrole` varchar(64) DEFAULT NULL COMMENT '是否设置 导入规则 ', -- 0.未设置  1.已设置
  `role_id` varchar(64) DEFAULT NULL COMMENT '导入规则 ID', 
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`xls_id`)
) COMMENT 'excel对象' ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- design_xls_column	excel对象 字段表 
-- ----------------------------
-- Table structure for design_xls_column
-- cn.digitalpublishing.po.design.XlsColumn
-- ----------------------------
DROP TABLE IF EXISTS `design_xls_column`;
CREATE TABLE `design_xls_column` (
  `col_id` varchar(32) NOT NULL COMMENT 'ID',
  
  `col_code` varchar(32) DEFAULT NULL COMMENT '字段名',
  `col_name` varchar(64) DEFAULT NULL COMMENT '字段 描述',
  
  `col_type` varchar(64) DEFAULT NULL COMMENT '类型', -- 是否已经 映射 0.未映射  1.已映射
  
  `xls_id` varchar(64) DEFAULT NULL COMMENT 'excel Id ', -- 对应excel 的ID 
  
  `tab_column` varchar(64) DEFAULT NULL COMMENT '数据库字段名',
  `tab_columntype` varchar(64) DEFAULT NULL COMMENT '数据库字段类型',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`col_id`)
) COMMENT 'excel字段表' ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- design_import_task	定时任务
-- ----------------------------
-- Table structure for design_import_task
-- cn.digitalpublishing.po.design.ImportTimeTask
-- ----------------------------
DROP TABLE IF EXISTS `design_import_task`;
CREATE TABLE `design_import_task` (
  `task_id` varchar(32) NOT NULL COMMENT 'ID',
  
  `task_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `task_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `task_type` varchar(64) DEFAULT NULL COMMENT '类型', -- 是否已经 映射 0.未映射  1.已映射
  
  `task_minute` varchar(64) DEFAULT NULL COMMENT '分',
  `task_hour` varchar(64) DEFAULT NULL COMMENT '时',
  `task_day` varchar(64) DEFAULT NULL COMMENT '日',
  `task_month` varchar(64) DEFAULT NULL COMMENT '月',
  `task_week` varchar(64) DEFAULT NULL COMMENT '周',
  
  `task_cron` varchar(64) DEFAULT NULL COMMENT '任务配置',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`task_id`)
) COMMENT '定时任务' ENGINE=InnoDB DEFAULT CHARSET=utf8;






-- design_import_log	导入日志
-- ----------------------------
-- Table structure for design_import_log
-- cn.digitalpublishing.po.design.ImportLog
-- ----------------------------
DROP TABLE IF EXISTS `design_import_log`;
CREATE TABLE `design_import_log` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  
  `log_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `log_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `log_type` varchar(64) DEFAULT NULL COMMENT '类型', -- 成功 失败
  
  `log_fileName` varchar(64) DEFAULT NULL COMMENT '导入文件名',
  `log_fileType` varchar(64) DEFAULT NULL COMMENT '导入类型', -- xls txt
  `log_roleId` varchar(64) DEFAULT NULL COMMENT '导入规则ID',
  `log_objName` varchar(64) DEFAULT NULL COMMENT '导入对象名称',
  `log_objId` varchar(64) DEFAULT NULL COMMENT '导入对象Id',
  `log_objPath` varchar(64) DEFAULT NULL COMMENT '导入路径',
  
  `db_id` varchar(64) DEFAULT NULL COMMENT '导入源Id',
  `db_table` varchar(64) DEFAULT NULL COMMENT '映射表',
  
  `log_errorinfo` varchar(256) DEFAULT NULL COMMENT '错误信息',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`id`)
) COMMENT '导入日志' ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- design_import_role	导入规则
-- ----------------------------
-- Table structure for design_import_role
-- cn.digitalpublishing.po.design.ImportRole
-- ----------------------------
DROP TABLE IF EXISTS `design_import_role`;
CREATE TABLE `design_import_role` (
  `role_id` varchar(32) NOT NULL COMMENT 'ID',
  
  `role_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `role_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `role_type` varchar(64) DEFAULT NULL COMMENT '规则类型  xls/txt',
  
  `role_objid` varchar(64) DEFAULT NULL COMMENT '对象ID',
  `role_objname` varchar(64) DEFAULT NULL COMMENT '对象名称',
  `role_importType` varchar(64) DEFAULT NULL COMMENT '导入状态',
  
  `role_taskId` varchar(64) DEFAULT NULL COMMENT '任务ID',
  `role_cronTask` varchar(64) DEFAULT NULL COMMENT '任务',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`role_id`)
) COMMENT '导入规则' ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- design_import_rt_mapped	规则 对应数据源表
-- ----------------------------
-- Table  for design_import_rt_mapped
-- cn.digitalpublishing.po.design.ImportRTMapped
-- ----------------------------
DROP TABLE IF EXISTS `design_import_rt_mapped`;
CREATE TABLE `design_import_rt_mapped` (
  `rt_id` varchar(32) NOT NULL COMMENT 'ID',
  
  `rt_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `rt_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `rt_type` varchar(64) DEFAULT NULL COMMENT '规则类型  xls/txt',
  
  `role_id` varchar(64) DEFAULT NULL COMMENT '导入规则ID',
  
  `rt_dbid` varchar(64) DEFAULT NULL COMMENT '数据源ID',
  `rt_dbtable` varchar(64) DEFAULT NULL COMMENT '源表',
  
  `rt_sql` varchar(1024) DEFAULT NULL COMMENT '当前表对应插入语句',
  `rt_params` varchar(64) DEFAULT NULL COMMENT 'sql数组参数',
  
  `rt_insertSort` varchar(64) DEFAULT NULL COMMENT '执行顺序',
  
  `rt_mstype` varchar(64) DEFAULT NULL COMMENT '主从关系',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`rt_id`)
) COMMENT '规则 对应数据源表' ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- design_import_role_detail	导入规则明细
-- ----------------------------
-- Table structure for design_import_role_detail
-- cn.digitalpublishing.po.design.ImportRoleDetail
-- ----------------------------
DROP TABLE IF EXISTS `design_import_role_detail`;
CREATE TABLE `design_import_role_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT 'ID',
  
  `detail_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `detail_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `detail_type` varchar(64) DEFAULT NULL COMMENT '规则类型  xls/txt',
  
  `detail_roleid` varchar(64) DEFAULT NULL COMMENT '规则ID',
  `detail_mappedtype` varchar(64) DEFAULT NULL COMMENT '映射类型',
  
  `detail_objid` varchar(64) DEFAULT NULL COMMENT '文件对象ID',
  `detail_columnid` varchar(64) DEFAULT NULL COMMENT '对象字段ID',
  
  `detail_importRTId` varchar(64) DEFAULT NULL COMMENT '映射到 表id',
  `detail_tableColumn` varchar(64) DEFAULT NULL COMMENT '映射字段',
  `detail_columnType` varchar(64) DEFAULT NULL COMMENT '字段类型',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`detail_id`)
) COMMENT '导入规则明细' ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- design_export_data	导出规则
-- ----------------------------
-- Table structure for design_export_data
-- cn.digitalpublishing.po.design.DataExport
-- ----------------------------
DROP TABLE IF EXISTS `design_export_data`;
CREATE TABLE `design_export_data` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  
  `exp_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `exp_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `exp_type` varchar(64) DEFAULT NULL COMMENT '规则类型',
  
  `exp_dbid` varchar(64) DEFAULT NULL COMMENT '数据连接',
  `exp_sql` varchar(2048) DEFAULT NULL COMMENT 'sql语句',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`id`)
) COMMENT '导出规则' ENGINE=InnoDB DEFAULT CHARSET=utf8;





-- design_text	text对象
-- ----------------------------
-- Table structure for design_text
-- cn.digitalpublishing.po.design.XlsObject
-- ----------------------------
DROP TABLE IF EXISTS `design_text`;
CREATE TABLE `design_text` (
  `txt_id` varchar(32) NOT NULL COMMENT 'text对象ID',
  
  `txt_name` varchar(64) DEFAULT NULL COMMENT '对象名称',
  `txt_code` varchar(32) DEFAULT NULL COMMENT '对象code',
  `txt_type` varchar(64) DEFAULT NULL COMMENT '对象 类型', -- 分割类型    ;/| 
  
  `txt_pathtype` varchar(64) DEFAULT NULL COMMENT '目录类型', -- 目录类型 类型：0.本地路径  1.FTP 路径 
  `ftp_id` varchar(64) DEFAULT NULL COMMENT 'FTPID', -- FTP Id 	
  `txt_path` varchar(64) DEFAULT NULL COMMENT 'text文件 路径',
  `txt_pathsuccess` varchar(64) DEFAULT NULL COMMENT 'text文件 成功 路径',
  `txt_patherror` varchar(64) DEFAULT NULL COMMENT 'text文件 失败 路径',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`txt_id`)
) COMMENT 'text对象' ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- design_txt_column	txt对象 字段表 
-- ----------------------------
-- Table structure for design_txt_column
-- cn.digitalpublishing.po.design.XlsColumn
-- ----------------------------
DROP TABLE IF EXISTS `design_txt_column`;
CREATE TABLE `design_txt_column` (
  `col_id` varchar(32) NOT NULL COMMENT 'ID',
  
  `col_code` varchar(32) DEFAULT NULL COMMENT '字段名',
  `col_name` varchar(64) DEFAULT NULL COMMENT '字段 描述',
  
  `col_type` varchar(64) DEFAULT NULL COMMENT '类型', -- 是否已经 映射 0.未映射  1.已映射
  
  `txt_id` varchar(64) DEFAULT NULL COMMENT 'excel Id ', -- 对应excel 的ID 
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`col_id`)
) COMMENT 'text字段表' ENGINE=InnoDB DEFAULT CHARSET=utf8;





-- design_import_role_detail_match	导入规则明细-match
-- ----------------------------
-- Table structure for design_import_role_detail_match
-- cn.digitalpublishing.po.design.ImportRoleDetailMatch
-- ----------------------------
DROP TABLE IF EXISTS `design_import_role_detail_match`;
CREATE TABLE `design_import_role_detail_match` (
  `match_id` varchar(32) NOT NULL COMMENT 'ID',
  
  `match_type` varchar(64) DEFAULT NULL COMMENT '备用字段',
  
  `match_dbid` varchar(64) DEFAULT NULL COMMENT '匹配源',
  `match_table` varchar(64) DEFAULT NULL COMMENT '匹配表',
  `match_name` varchar(64) DEFAULT NULL COMMENT '匹配字段',
  `match_code` varchar(32) DEFAULT NULL COMMENT '查询字段',
  
  `detail_id` varchar(64) DEFAULT NULL COMMENT '所属规则明细Id',

  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`match_id`)
) COMMENT '导入规则明细-match' ENGINE=InnoDB DEFAULT CHARSET=utf8;












-- design_echarts	echarts对象
-- ----------------------------
-- Table structure for design_echarts
-- cn.digitalpublishing.po.design.echarts.Echarts
-- ----------------------------
DROP TABLE IF EXISTS `design_echarts`;
CREATE TABLE `design_echarts` (
  `echart_id` varchar(32) NOT NULL COMMENT '图表对象ID',
  
  `echart_name` varchar(64) DEFAULT NULL COMMENT '图表名称',
  `echart_code` varchar(32) DEFAULT NULL COMMENT '图表code', -- 图表code ，对应一个 图表的jsp 页面，页面名称，需要与图表code一致。再根据 报表新建图表的时候，需要填写 当前code生成的 必填字段页面
  `echart_type` varchar(64) DEFAULT NULL COMMENT '图表类型',  
  
  `echart_json` varchar(64) DEFAULT NULL COMMENT '图表对应json', -- 目录类型 类型：0.本地路径  1.FTP 路径 
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`echart_id`)
) COMMENT '图表对象' ENGINE=InnoDB DEFAULT CHARSET=utf8;













-- design_echart_column	echarts 字段表
-- ----------------------------
-- Table structure for design_echart_column
-- cn.digitalpublishing.po.design.echarts.EchartColumn
-- ----------------------------
DROP TABLE IF EXISTS `design_echart_column`;
CREATE TABLE `design_echart_column` (
  `col_id` varchar(32) NOT NULL COMMENT '图表字段ID',
  
  `col_name` varchar(64) DEFAULT NULL COMMENT '图表字段名称',
  `col_code` varchar(32) DEFAULT NULL COMMENT '字段code', 
  `col_type` varchar(64) DEFAULT NULL COMMENT '字段类型',  
  
  `echart_id` varchar(64) DEFAULT NULL COMMENT '图表Id',   
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`col_id`)
) COMMENT '图表字段' ENGINE=InnoDB DEFAULT CHARSET=utf8;










-- design_echart_column_value	echarts 字段存值表
-- ----------------------------
-- Table structure for design_echart_column_value
-- cn.digitalpublishing.po.design.echarts.EchartColumnValue
-- ----------------------------
DROP TABLE IF EXISTS `design_echart_column_value`;
CREATE TABLE `design_echart_column_value` (
  `colv_id` varchar(32) NOT NULL COMMENT '图表字段ID',
  
  `colv_name` varchar(64) DEFAULT NULL COMMENT '图表名称',
  `colv_code` varchar(32) DEFAULT NULL COMMENT '图表code', 
  `colv_type` varchar(64) DEFAULT NULL COMMENT '图表类型',  
  
  `echart_id` varchar(64) DEFAULT NULL COMMENT '图表Id',
  `exp_id` varchar(64) DEFAULT NULL COMMENT '报表Id',
  `col_id` varchar(64) DEFAULT NULL COMMENT '字段Id',
  `col_val` varchar(64) DEFAULT NULL COMMENT '字段值',
  
  `rs_id` varchar(64) DEFAULT NULL COMMENT '关联表ID',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`colv_id`)
) COMMENT '图表字段值' ENGINE=InnoDB DEFAULT CHARSET=utf8;












-- design_echart_json	echarts json对象表
-- ----------------------------
-- Table structure for design_echart_json
-- cn.digitalpublishing.po.design.echarts.EchartJson
-- ----------------------------
DROP TABLE IF EXISTS `design_echart_json`;
CREATE TABLE `design_echart_json` (
  `json_id` varchar(32) NOT NULL COMMENT 'jsonID',
  
  `json_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `json_code` varchar(32) DEFAULT NULL COMMENT 'code', 
  `json_type` varchar(64) DEFAULT NULL COMMENT '',
  
  `echart_id` varchar(64) DEFAULT NULL COMMENT '图表Id',
  `json_source` varchar(2048) DEFAULT NULL COMMENT 'json数据',
  `json_temp` varchar(64) DEFAULT NULL COMMENT '生成模板',
  `json_mapnum` varchar(64) DEFAULT NULL COMMENT '所需数据个数',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`json_id`)
) COMMENT '图表字段值' ENGINE=InnoDB DEFAULT CHARSET=utf8;







-- design_echart_json_map	echarts json 对象映射 表
-- ----------------------------
-- Table structure for design_echart_json_map
-- cn.digitalpublishing.po.design.echarts.EchartJsonMap
-- ----------------------------
DROP TABLE IF EXISTS `design_echart_json_map`;
CREATE TABLE `design_echart_json_map` (
  `jmap_id` varchar(32) NOT NULL COMMENT 'json映射ID',
  
  `jmap_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `jmap_code` varchar(32) DEFAULT NULL COMMENT 'code', 
  `jmap_type` varchar(64) DEFAULT NULL COMMENT '类型',
  
  `echart_id` varchar(64) DEFAULT NULL COMMENT '图表Id',
  
  `exp_id` varchar(64) DEFAULT NULL COMMENT '报表Id',
  `exp_column` varchar(64) DEFAULT NULL COMMENT '映射报表的哪个字段',
  
  `json_id` varchar(64) DEFAULT NULL COMMENT 'jsonId',
  `jmap_order` varchar(64) DEFAULT NULL COMMENT '当前映射，属于这个json 的第几个字段',
  
  `rs_id` varchar(64) DEFAULT NULL COMMENT 'jsonId',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`jmap_id`)
) COMMENT 'json映射字段值' ENGINE=InnoDB DEFAULT CHARSET=utf8;







-- design_echart_exp_rs	echarts-export 关系表
-- ----------------------------
-- Table structure for design_echart_exp_rs
-- cn.digitalpublishing.po.design.echarts.EchartExportRS
-- ----------------------------
DROP TABLE IF EXISTS `design_echart_exp_rs`;
CREATE TABLE `design_echart_exp_rs` (
  `rs_id` varchar(32) NOT NULL COMMENT 'rsID',
  
  `rs_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `rs_code` varchar(64) DEFAULT NULL COMMENT '值',
  `rs_type` varchar(64) DEFAULT NULL COMMENT '类型',
  
  `echart_id` varchar(64) DEFAULT NULL COMMENT '图表Id',
  `exp_id` varchar(64) DEFAULT NULL COMMENT '报表Id',
  
  `create_id` varchar(64) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `update_id` varchar(64) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `status` decimal(10,0) DEFAULT NULL COMMENT '状态：1-在用 2-停用',
  PRIMARY KEY (`rs_id`)
) COMMENT 'echarts-export 关系表' ENGINE=InnoDB DEFAULT CHARSET=utf8;






