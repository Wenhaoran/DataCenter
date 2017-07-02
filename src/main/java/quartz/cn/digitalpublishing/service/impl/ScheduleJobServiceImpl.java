package quartz.cn.digitalpublishing.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quartz.cn.digitalpublishing.mapper.ScheduleJobMapper;
import quartz.cn.digitalpublishing.model.ScheduleJob;
import quartz.cn.digitalpublishing.service.ScheduleJobService;
import quartz.cn.digitalpublishing.utils.ScheduleUtils;
import quartz.cn.digitalpublishing.vo.ScheduleJobVo;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务服务实现
 * version : 1.0
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

	@Resource(name="scheduleJobMapper")
    private ScheduleJobMapper scheduleJobMapper;
	
    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;
    
    @Override
    public void initScheduleJob() throws Exception{
        List<ScheduleJob> scheduleJobList = scheduleJobMapper.findListByCondition(null);//jdbcDao.queryList(Criteria.select(ScheduleJob.class))
        
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ScheduleJob scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public void insert(ScheduleJob scheduleJob) throws Exception{
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        scheduleJobMapper.insert(scheduleJob);
    }

    @Override
    public void update( ScheduleJob scheduleJob) throws Exception{
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        scheduleJobMapper.updateById(scheduleJob);
    }

    @Override
    public void delUpdate(ScheduleJob scheduleJob) throws Exception{
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        //数据库直接更新即可
        scheduleJobMapper.updateById(scheduleJob);
    }

    @Override
    public void delete(String aliasName) throws Exception{// aliasName 任务别名，保存 规则的ID 
        ScheduleJob scheduleJob = scheduleJobMapper.findByRoleId(aliasName);
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        scheduleJobMapper.deleteById(scheduleJob.getScheduleJobId());
    }

    @Override
    public void runOnce(String aliasName) throws Exception{// aliasName 任务别名，保存 规则的ID 
        ScheduleJob scheduleJob = scheduleJobMapper.findByRoleId(aliasName);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    @Override
    public void pauseJob(String aliasName) throws Exception{// aliasName 任务别名，保存 规则的ID 
        ScheduleJob scheduleJob = scheduleJobMapper.findByRoleId(aliasName);
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //演示数据库就不更新了
    }

    @Override
    public void resumeJob(String aliasName) throws Exception{// aliasName 任务别名，保存 规则的ID 
    	scheduler.start();
        ScheduleJob scheduleJob = scheduleJobMapper.findByRoleId(aliasName);
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //演示数据库就不更新了
    }

	@Override
	public ScheduleJob getScheduleJobByRoleId(String roleId) throws Exception {
		ScheduleJob scheduleJob = scheduleJobMapper.findByRoleId(roleId);
		return scheduleJob;
	}

    /*public ScheduleJobVo get(Long scheduleJobId) {
        ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
        return scheduleJob.getTargetObject(ScheduleJobVo.class);
    }
    
    public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo) {

        List<ScheduleJob> scheduleJobs = jdbcDao.queryList(scheduleJobVo.getTargetObject(ScheduleJob.class));

        List<ScheduleJobVo> scheduleJobVoList = BeanConverter.convert(ScheduleJobVo.class, scheduleJobs);
        try {
            for (ScheduleJobVo vo : scheduleJobVoList) {

                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                vo.setJobTrigger(trigger.getKey().getName());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                vo.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
            }
        } catch (SchedulerException e) {
            //演示用，就不处理了
        }
        return scheduleJobVoList;
    }

    *//**
     * 获取运行中的job列表
     * @return
     */
	@Override
    public List<ScheduleJobVo> queryExecutingJobList() {
        try {
            // 存放结果集
            List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>();

            // 获取scheduler中的JobGroupName
            for (String group:scheduler.getJobGroupNames()){
                // 获取JobKey 循环遍历JobKey
                for(JobKey jobKey:scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))){
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    ScheduleJob scheduleJob = (ScheduleJob)jobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);
                    ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
                    convert(scheduleJobVo,scheduleJob);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    Trigger trigger = triggers.iterator().next();
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    scheduleJobVo.setJobTrigger(trigger.getKey().getName());
                    scheduleJobVo.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        scheduleJobVo.setCronExpression(cronExpression);
                    }
                    // 获取正常运行的任务列表
                    if(triggerState.name().equals("NORMAL")){
                        jobList.add(scheduleJobVo);
                    }
                }
            }

            return jobList;
        } catch (Exception e) {
            return null;
        }

    }

	private void convert(ScheduleJobVo scheduleJobVo, ScheduleJob scheduleJob) {
		scheduleJobVo.setJobName(scheduleJob.getJobName());
	}
    
}
