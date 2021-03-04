package com.murray.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Map;
 /**线程类中不能使用@Autowired注解注入Bean
  * 解决办法:添加此类使用    ChatUserService chatUserService= ManageSpringBeans.getBean(ChatUserService.class);
  *
  * */
@Service
public class ManageSpringBeans implements ApplicationContextAware {
	private static ApplicationContext context;
 
	public static <T> T getBean(final Class<T> requiredType) {
		return context.getBean(requiredType);
	}
 
	public static <T> T getBean(final String beanName) {
		@SuppressWarnings("unchecked")
		final T bean = (T) context.getBean(beanName);
		return bean;
	}
 
	public static <T> Map<String, T> getBeans(final Class<T> requiredType) {
		return context.getBeansOfType(requiredType);
	}
 
	public static Map<String, Object> getBeansWithAnnotation(final Class<? extends Annotation> annotationType) {
		return context.getBeansWithAnnotation(annotationType);
	}
 
	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		context = applicationContext;
	}
}