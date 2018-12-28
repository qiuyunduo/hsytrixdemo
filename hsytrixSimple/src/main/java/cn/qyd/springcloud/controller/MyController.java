package cn.qyd.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Author qyd
 * @Date 18-12-27 下午3:21
 **/
@RestController
public class MyController {

    @GetMapping("/normalHello")
    public String normalHello(HttpServletRequest request){
        return "Hello World";
    }

    @GetMapping("/errorHello")
    public String errorHello(HttpServletRequest request) throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        return "Error Hello World";
    }
}
