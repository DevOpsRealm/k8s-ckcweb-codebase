package ph.edu.ckc.k8sckcbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;


@SpringBootApplication
public class K8sCkcBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(K8sCkcBackendApplication.class, args);

        System.out.println(UUID.randomUUID());
    }
}
