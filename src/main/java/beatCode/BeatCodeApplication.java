package beatCode;
import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BeatCodeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BeatCodeApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }

}
