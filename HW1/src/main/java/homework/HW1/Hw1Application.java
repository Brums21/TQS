package homework.HW1;

import homework.HW1.Model.AirCondition;
import homework.HW1.Model.AirConditionResolver;
import homework.HW1.connection.HttpClientAPI;
import org.apache.http.ParseException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;


@SpringBootApplication
public class Hw1Application {

	static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {
		try {
			AirConditionResolver resolver = new AirConditionResolver(new HttpClientAPI());
			LocalDateTime start = LocalDateTime.of(2023, 4, 3, 8, 2, 43);
			LocalDateTime end = LocalDateTime.now();
			List<AirCondition> result = resolver.airPollutionDates(80.633116, -40.658784, start, end); //mudar isto
			log.info("Result: ".concat(result.toString()));

		} catch (URISyntaxException | IOException | ParseException | org.json.simple.parser.ParseException ex) {
			log.error(String.valueOf(ex));

		}
	}
}
