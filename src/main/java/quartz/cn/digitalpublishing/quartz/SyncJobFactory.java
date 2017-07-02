package quartz.cn.digitalpublishing.quartz;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import quartz.cn.digitalpublishing.model.ScheduleJob;
import quartz.cn.digitalpublishing.vo.ScheduleJobVo;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 同步任务工厂
 * version : 1.0
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends QuartzJobBean {

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(SyncJobFactory.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("SyncJobFactory execute");
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        ScheduleJob scheduleJob = (ScheduleJob) mergedJobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);
        System.out.println("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
        String url = scheduleJob.getUrl();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response;
        try {
            response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
