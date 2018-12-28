package cn.qyd.springcloud.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * @Author qyd
 * @Date 18-12-27 下午5:09
 **/
public class TestCommand extends HystrixCommand<String> {
    private int index;
    public TestCommand(int index) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")));

        this.index = index;
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("执行方法，当前索引："+index);

        return "";
    }

    protected String getFallback() {
        System.out.println("执行 TestCommand 的回退方法,当前索引："+index);
        return "error";
    }
}
