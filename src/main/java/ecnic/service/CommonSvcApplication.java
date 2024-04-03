package ecnic.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CommonSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonSvcApplication.class, args);
	}

}
