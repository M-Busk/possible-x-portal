package eu.possiblex.portal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"version.no = thisistheversion"})
class PortalApplicationTests {

    @Test
    void contextLoads() {

    }

}
