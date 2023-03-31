package homework.HW1;

import homework.HW1.Model.AirCondition;
import homework.HW1.Model.AirConditionResolver;
import homework.HW1.connection.HttpClientAPI;
import org.apache.http.ParseException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.util.Optional;


@SpringBootApplication
public class Hw1Application {

	static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {
		try {
			AirConditionResolver resolver = new AirConditionResolver(new HttpClientAPI());

			Optional<AirCondition> result = resolver.currentAirPollution(80.633116, -40.658784);
			log.info("Result: ".concat(result.get().toString()));

		} catch (URISyntaxException | IOException | ParseException | org.json.simple.parser.ParseException ex) {
			log.error(String.valueOf(ex));
		}
	}
}
