package quartz.cn.digitalpublishing.service;

import java.util.List;

import quartz.cn.digitalpublishing.model.ScheduleJob;
import quartz.cn.digitalpublishing.vo.ScheduleJobVo;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务服务
 * version : 1.0
 */
public interface ScheduleJobService {

    /**
     * 初始化定时任务
     */
    public void initScheduleJob() throws Exception;

    /**
     * 新增
     * 
     * @param scheduleJobVo
     * @return
     */
    public void insert(ScheduleJob scheduleJob) throws Exception;

    public void update(ScheduleJob scheduleJob) throws Exception;

    public void delUpdate(ScheduleJob scheduleJob) throws Exception;

     //
    public void delete(String scheduleJobId) throws Exception;

    public void runOnce(String scheduleJobId) throws Exception;
    public void pauseJob(String scheduleJobId) throws Exception;

    public void resumeJob(String scheduleJobId) throws Exception;

    
    public ScheduleJob getScheduleJobByRoleId(String roleId)throws Exception;
     /*public ScheduleJobVo get(Long scheduleJobId);

    *//**
     * 查询任务列表
     * 
     * @param scheduleJobVo
     * @return
     *//*
    public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo);

    *//**
     * 获取运行中的任务列表
     *
     * @return
     */
    public List<ScheduleJobVo> queryExecutingJobList();

}
