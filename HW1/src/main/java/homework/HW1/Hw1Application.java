package homework.HW1;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.lang.invoke.MethodHandles;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Hw1Application {

	static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {
		SpringApplication.run(Hw1Application.class, args);
	}
}
