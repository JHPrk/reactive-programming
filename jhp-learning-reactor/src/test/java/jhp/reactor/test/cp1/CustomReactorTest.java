package jhp.reactor.test.cp1;


import java.time.Duration;
import jhp.reactor.custom.publisher.PublisherImpl;
import jhp.reactor.custom.publisher.SubscriptionImpl;
import jhp.reactor.custom.subscriber.SubscriberImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomReactorTest {

  private SubscriberImpl subscriber;

  @BeforeEach
  void initialize(){
    PublisherImpl publisher = new PublisherImpl();
    subscriber = new SubscriberImpl();
    publisher.subscribe(subscriber);
  }
  @Test
  void demo1(){
    Assertions.assertEquals(subscriber.getSubscription().getClass(), SubscriptionImpl.class);
  }

  @Test
  void demo2() throws InterruptedException {
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
  }

  @Test
  void demo3() throws InterruptedException {
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
    subscriber.getSubscription().cancel();
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
  }
  @Test
  void demo4() throws InterruptedException {
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
    subscriber.getSubscription().request(11);
    Thread.sleep(Duration.ofSeconds(2));
    subscriber.getSubscription().request(3);
    Thread.sleep(Duration.ofSeconds(2));
  }

}
