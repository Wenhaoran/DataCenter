package cn.digitalpublishing.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextAware implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringUtils.setContext(arg0);
	}
}
