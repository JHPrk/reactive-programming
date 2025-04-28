package jhp.reactor.test.cp2;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import jhp.reactor.custom.common.Util;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoCreationTest {
  private static final Logger log = LoggerFactory.getLogger(MonoCreationTest.class);

  @Test
  void monoFromSupplier(){

    var list = List.of(1,2,3);
    // just 메소드는 Memory에 이미 적재된 값을 Mono로 만드는 것이다.
    Mono.just(sum(list))
        .subscribe(Util.subscriber());

    // 따라서 just 내부 메소드의 실행을 지연하기 위해서 fromSupplier를 제공함
    // sum(list)가 subscribe 될때만 실행되도록 지연 실행 가능
    Mono.fromSupplier(() -> sum(list))
        .subscribe(Util.subscriber());
  }

  @Test
  void monoFromCallable(){

    var list = List.of(1,2,3);

    // fromSupplier 와 동작이 동일하고 지연실행이됨
    // 그렇다면 다른점은??? - 기존 자바 버전과의 호환을 위해
    // Callable -> JAVA의 쓰레드 처리를 위해 나온 메소드
    // Supplier -> Java 8부터 나온 함수형 인터페이스의 메소드
    // 차이점은 Supplier는 Exception 을 함수 내부에서 던지면 꼭 try-catch로 다뤄야하지만 다루지 않지만 Callable은 그렇지 않아도 됨.
    Mono.fromCallable(() -> sum(list))
        .subscribe(Util.subscriber());
  }

  int sum(List<Integer> list){
    log.info("finding the sum of {}", list);
    return list.stream().mapToInt(a -> a).sum();
  }

  @Test
  void monoFromRunnable(){
    // Mono.empty()와 같이 빈 값을 전송해야하지만 내부적으로는 처리해야할 데이터가 있을때 사용
    // Runnable은 기본적으로 void타입이라 리턴값이 없이 그냥 처리만 필요한, 즉 Consumer의 역할을 수행
    getProductName(3)
        .subscribe(Util.subscriber());
  }

  Mono<String> getProductName(int productId){
    if(productId == 1){
      return Mono.fromSupplier(() -> Util.faker().commerce().productName());
    }
    return Mono.fromRunnable(() -> doSomeWorkForEmptyResponse(productId));
  }

  void doSomeWorkForEmptyResponse(int productId){
    log.info("notifying business on unavailable product id: {}", productId);
  }

  @Test
  void monoFromFuture() {
    // 주의사항 : CompletableFuture 는 Lazy 하게 동작하지 않음!
    // 생성시 바로 실행되게 됨.
    Mono.fromFuture(getName())
        .subscribe(Util.subscriber());


    // Reactive 프로그래밍의 핵심을 바로 모든 동작을 lazy하게 처리하는 것이 핵심
    // 따라서 다음과 같이 supplier 를 넣어주면 됨.
    Mono.fromFuture(this::getName)
        .subscribe(Util.subscriber());


    // CompletableFuture는 기본적으로 forkJoinPool을 사용해서 데몬 쓰레드임.
    // mainThread가 먼저 종료되지 않도록 sleep을 걸어줌
    Util.sleepSeconds(1);
  }

  CompletableFuture<String> getName(){
    return CompletableFuture.supplyAsync(() -> {
          log.info("generating name");
          return Util.faker().name().firstName();
        }
    );
  }
}
