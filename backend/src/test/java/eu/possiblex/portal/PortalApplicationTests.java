package eu.possiblex.portal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { "version.no = thisistheversion", "version.date = 21.03.2022" })
class PortalApplicationTests {

    @Test
    void contextLoads() {
        // basic context loading test
    }

}
