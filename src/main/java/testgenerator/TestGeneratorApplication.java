package testgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TestGeneratorApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(TestGeneratorApplication.class, args);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

}
