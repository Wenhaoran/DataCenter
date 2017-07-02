package quartz.cn.digitalpublishing.mapper;

import cn.digitalpublishing.mapper.BaseMapper;
import quartz.cn.digitalpublishing.model.ScheduleJob;

public interface ScheduleJobMapper extends BaseMapper<ScheduleJob,ScheduleJob> {

	ScheduleJob findByRoleId(String aliasName);
	
}