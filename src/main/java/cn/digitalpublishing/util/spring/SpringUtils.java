package cn.digitalpublishing.util.spring;

import org.springframework.context.ApplicationContext;

public class SpringUtils {

	private ApplicationContext context;
	private static SpringUtils instance;

	private SpringUtils() {
	}

	private static synchronized SpringUtils get() {
		if (instance == null) {
			instance = new SpringUtils();
		}
		return instance;
	}

	@SuppressWarnings("static-access")
	public static <T> T getBean(Class<T> requiredType) {
		return get().getContext().getBean(requiredType);
	}

	@SuppressWarnings("static-access")
	public static Object getBean(String name) {
		System.out.println(get().getContext());
		return get().getContext().getBean(name);
	}

	public static ApplicationContext getContext() {
		return get().context;
	}

	public static void setContext(ApplicationContext context) {
		get().context = context;
	}
}