package priv.ymqm.housing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HousingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousingApplication.class, args);
    }

}
