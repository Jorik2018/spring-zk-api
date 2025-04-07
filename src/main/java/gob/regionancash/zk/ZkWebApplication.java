package gob.regionancash.zk;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZkWebApplication {

    public static void main(String[] args) {
        (new SpringApplicationBuilder(new Class[] { gob.regionancash.zk.ZkWebApplication.class
        })).properties(new String[] { "spring.config.name:zk-web" }).build().run(args);
    }

}