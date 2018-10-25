package com.oa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置、启用异步
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
//开启异步
@EnableAsync(proxyTargetClass = true)
@Configuration
public class AsycTaskExecutorConfig {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		//核心线程数
		taskExecutor.setCorePoolSize(50);
		//最大线程数
		taskExecutor.setMaxPoolSize(100);
		return taskExecutor;
	}
}
