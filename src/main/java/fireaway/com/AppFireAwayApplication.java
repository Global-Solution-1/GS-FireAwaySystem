package fireaway.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AppFireAwayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppFireAwayApplication.class, args);
    }
}
