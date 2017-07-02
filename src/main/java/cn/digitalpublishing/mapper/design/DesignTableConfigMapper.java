package cn.digitalpublishing.mapper.design;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.design.DesignTableConfig;

public interface DesignTableConfigMapper extends BaseMapper<DesignTableConfig,DesignTableConfig> {
	
	public DesignTableConfig getConfigByTableId(String tableId);
}