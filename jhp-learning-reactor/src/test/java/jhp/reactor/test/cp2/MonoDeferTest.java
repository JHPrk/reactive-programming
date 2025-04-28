package jhp.reactor.test.cp2;

import java.util.List;
import jhp.reactor.custom.common.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoDeferTest {
  private static final Logger log = LoggerFactory.getLogger(MonoDeferTest.class);

  @Test
  @DisplayName("Publisher 의 생성을 lazy 하게 동작하고 싶을 사용")
  void monoDeferTest(){
    // Publisher 의 생성을 Lazy 하게
    Mono<Integer> defer = Mono.defer(this::createPublisher);

    defer.subscribe(Util.subscriber());
  }

  // 시간이 많이 걸리는 Publisher 생성 로직
  Mono<Integer> createPublisher(){
    log.info("create publisher");
    var list = List.of(1,2,3);
    Util.sleepSeconds(1);
    return Mono.fromSupplier(() -> sum(list));
  }

  // 시간이 많이 걸리는 비지니스 로직
  int sum(List<Integer> list){
    log.info("finding the sum of {}", list);
    Util.sleepSeconds(3);
    return list.stream().mapToInt(a -> a).sum();
  }


}
