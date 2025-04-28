package jhp.reactor.test.cp2;

import jhp.reactor.custom.common.Util;
import jhp.reactor.custom.subscriber.SubscriberImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoTest {
  private static final Logger log = LoggerFactory.getLogger(MonoTest.class);

  @DisplayName("just() -> 가장 간단하게 Publisher 를 만드는 방법")
  @Test
  void monoJust(){
    var mono = Mono.just("park");
    var subscriber = new SubscriberImpl();
    mono.subscribe(subscriber);

    subscriber.getSubscription().request(10);
    subscriber.getSubscription().request(10);
    subscriber.getSubscription().cancel();
  }

  @DisplayName("subscriber 객체를 생략하고 함수형 프로그래밍을 통해 구독 가능")
  @Test
  void monoSubscriber(){
    var mono = Mono.just(1)
        .map(i -> i + "a");

    // onNext, onError, onComplete, onSubscribe
    // onSubscribe 의 default 동작 -> request(Long.MAX_VALUE)
    mono.subscribe(
        i -> log.info("received: {}", i),
        err -> log.error("error", err),
        () -> log.info("completed!"),
        subscription -> subscription.request(1)
        );
  }


  @DisplayName("getUserName 함수를 통해서 error, empty Mono 테스팅")
  @Test
  void monoEmptyError() {
    // 정상 Input
    getUserName(1)
        .subscribe(Util.subscriber());

    // 찾지 못하였을때 null -> empty
    getUserName(2)
        .subscribe(Util.subscriber());

    // 에러 (Invalid)
    getUserName(3)
        .subscribe(Util.subscriber());
  }

  Mono<String> getUserName(int userId){
    return switch (userId){
      case 1 -> Mono.just("sam");
      case 2 -> Mono.empty();
      default -> Mono.error(new RuntimeException("invalid input"));
    };
  }

}
