package ph.edu.ckc.k8sckcbackend.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ExceptionPayload {

    private ZonedDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String path;
}
