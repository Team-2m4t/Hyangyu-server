package hyangyu.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"hyangyu.server.aws"})
public class ServerApplication {
	  public static final String APPLICATION_LOCATIONS = "spring.config.location="
	            + "classpath:application.yml,"
	            + "classpath:aws.yml";
	public static void main(String[] args) {
		 new SpringApplicationBuilder(ServerApplication.class)
         .properties(APPLICATION_LOCATIONS)
         .run(args);
	}

}
