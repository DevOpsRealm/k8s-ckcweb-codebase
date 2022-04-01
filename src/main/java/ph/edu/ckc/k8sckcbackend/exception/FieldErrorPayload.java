package ph.edu.ckc.k8sckcbackend.exception;

import lombok.*;

import java.util.Map;
import java.util.Objects;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorPayload extends ExceptionPayload {

    private Map<String, String> validationErrors;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FieldErrorPayload that = (FieldErrorPayload) o;
        return Objects.equals(validationErrors, that.validationErrors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), validationErrors);
    }
}
