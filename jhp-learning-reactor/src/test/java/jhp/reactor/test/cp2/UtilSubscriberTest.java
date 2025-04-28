package jhp.reactor.test.cp2;

import jhp.reactor.custom.common.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class UtilSubscriberTest {

  @Test
  @DisplayName("jhp.reactor.custom.common.Util 에 있는 subscriber 테스트")
  void namedSubscriberTest(){
    var mono = Mono.just(1);

    mono.subscribe(Util.subscriber());
    mono.subscribe(Util.subscriber("sub1"));
    mono.subscribe(Util.subscriber("sub2"));
  }

}
