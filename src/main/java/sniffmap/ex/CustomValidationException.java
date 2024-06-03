package sniffmap.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException {

    //객체를 구문할 때 사용, JVM에게 중요. 자세하게 알 필요는 없다.
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}