package cn.catguild.anime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author xiyan
 */
@EnableAsync
@SpringBootApplication
public class AnimeTimeApp {

    public static void main(String[] args) {
        SpringApplication.run(AnimeTimeApp.class, args);
    }

}
