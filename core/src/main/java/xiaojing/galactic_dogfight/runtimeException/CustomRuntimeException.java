package xiaojing.galactic_dogfight.runtimeException;

/**
 * @author 尽
 * @apiNote
 */
public class CustomRuntimeException extends RuntimeException {
    public CustomRuntimeException(String message) {
        super(message);
    }

    public CustomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
