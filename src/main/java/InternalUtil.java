import java.util.concurrent.TimeUnit;

public class InternalUtil {

    public static void sleep(int sleepTime) {
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (Exception interruptError) {
            System.out.println(interruptError.getMessage());
        }
    }
}
