package pe.tcloud.shikotsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("pe.tcloud.shikotsu.config")
public class ShikotsuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShikotsuApplication.class, args);
    }

}
