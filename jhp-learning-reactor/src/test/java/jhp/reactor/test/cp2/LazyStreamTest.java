package jhp.reactor.test.cp2;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyStreamTest {

  private static final Logger log = LoggerFactory.getLogger(LazyStreamTest.class);

  @Test
  void test(){
    Stream.of(1)
        .peek(i-> log.info("received : {}",i))
        .toList();
  }

}
