package jhp.reactor.custom.common;

import com.github.javafaker.Faker;
import java.time.Duration;
import org.reactivestreams.Subscriber;

public class Util {
  private static final Faker faker = Faker.instance();

  public static <T> Subscriber<T> subscriber(){
    return new DefaultSubscriber<>("");
  }

  public static <T> Subscriber<T> subscriber(String name){
    return new DefaultSubscriber<>(name);
  }

  public static Faker faker(){
    return faker;
  }

  public static void sleepSeconds(int seconds){
    try {
      Thread.sleep(Duration.ofSeconds(seconds));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
