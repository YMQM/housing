package priv.ymqm.housing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author chenhonnian
 * @since 2020/03/16
 */
@RestController
@RequestMapping("/test")
public class TestHelloWorld {

    @GetMapping("hello")
    public String helloWorld() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String msg = "hello, world " + localDateTime.toString();
        return msg;
    }
}
