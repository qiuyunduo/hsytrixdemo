package cn.qyd.springcloud.collapse;

import cn.qyd.springcloud.entity.Person;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author qyd
 * @Date 18-12-27 下午6:06
 **/
public class CollapseMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //收集1秒内发生的请求，合并为一个命令执行
        ConfigurationManager.getConfigInstance().setProperty("hystrix.collapser.default.timerDelayInMilliseconds",10000);

        //请求上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        //创建请求合并处理器
        MyHystrixCollapse c1 = new MyHystrixCollapse("Tom");
        MyHystrixCollapse c2 = new MyHystrixCollapse("Tone");
        MyHystrixCollapse c3 = new MyHystrixCollapse("Jim");
        MyHystrixCollapse c4 = new MyHystrixCollapse("Decor");
        MyHystrixCollapse c5 = new MyHystrixCollapse("Alice");

        //异步执行
        Future<Person> queue = c1.queue();
        Future<Person> queue1 = c2.queue();
        Future<Person> queue2 = c3.queue();
        Future<Person> queue3 = c4.queue();
        Future<Person> queue4 = c5.queue();

        System.out.println(queue.get());
        System.out.println(queue1.get());
        System.out.println(queue2.get());
        System.out.println(queue3.get());
        System.out.println(queue4.get());

        context.shutdown();
    }
}
