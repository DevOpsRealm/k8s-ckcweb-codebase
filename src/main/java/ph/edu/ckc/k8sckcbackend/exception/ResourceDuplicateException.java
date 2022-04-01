package ph.edu.ckc.k8sckcbackend.exception;

public class ResourceDuplicateException extends RuntimeException {
    public ResourceDuplicateException(String s) {
        super(s);
    }
}
