package co.zip.candidate.userapi;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@EnableCaching
@SpringBootApplication
@ComponentScan("co.zip.candidate") //to scan packages mentioned
@EnableJpaRepositories("co.zip.candidate")
public class UserApiApplication {

	@Bean
    public TaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // minimise server resource usage
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        
        // queue up to 1000 reqs
        executor.setQueueCapacity(1000);
        executor.setThreadGroupName("asyncExecutor");
        executor.initialize();
        return executor;
    }

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}

}
