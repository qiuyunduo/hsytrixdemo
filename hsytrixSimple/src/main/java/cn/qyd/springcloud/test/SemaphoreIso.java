package cn.qyd.springcloud.test;

import cn.qyd.springcloud.command.TestCommand;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author qyd
 * @Date 18-12-27 下午5:19
 **/
public class SemaphoreIso {
    public static void main(String[] args) throws InterruptedException {
        //配置使用信号量的策略进行隔离
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.strategy", HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE);
        //设置最大并发数，默认值为10,此次设置为2
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests",4);
        //设置执行回退方法的最大并发，默认值10,此次设为20
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests",20);

        for (int i = 0; i < 6; i++) {
            final int index = i;
            Thread thread = new Thread(() -> {
                TestCommand command = new TestCommand(index);
                command.execute();
            });
            thread.start();
        }

        TimeUnit.SECONDS.sleep(3);
    }

}
