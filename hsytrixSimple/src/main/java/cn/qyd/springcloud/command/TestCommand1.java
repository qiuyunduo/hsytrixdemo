package cn.qyd.springcloud.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.concurrent.TimeUnit;

/**
 * @Author qyd
 * @Date 18-12-27 下午5:09
 **/
public class TestCommand1 extends HystrixCommand<String> {
    private String key;
    public TestCommand1(String key) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("TestCommandKey")));

        this.key = key;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("执行方法，当前key："+key);
        return "";
    }

    protected String getCacheKey() {
        return this.key;
    }
}
