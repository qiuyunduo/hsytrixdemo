package cn.qyd.springcloud.cache;

import cn.qyd.springcloud.command.TestCommand1;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * @Author qyd
 * @Date 18-12-27 下午6:23
 **/
public class CacheMain {
    public static void main(String[] args) {
        //初始化应用上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        //请求正常服务
        String key = "cache-key";
        TestCommand1 command1 = new TestCommand1(key);
        TestCommand1 command2 = new TestCommand1(key);
        TestCommand1 command3 = new TestCommand1(key);

        //输出结果
        System.out.println(command1.execute()+"command1 是否读取缓存： "+command1.isResponseFromCache());
        System.out.println(command2.execute()+"command2 是否读取缓存： "+command2.isResponseFromCache());
        System.out.println(command3.execute()+"command3 是否读取缓存： "+command3.isResponseFromCache());

        HystrixRequestCache hystrixRequestCache = HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("TestCommandKey"), HystrixConcurrencyStrategyDefault.getInstance());
        hystrixRequestCache.clear(key);


        TestCommand1 command4 = new TestCommand1(key);
        TestCommand1 command5 = new TestCommand1(key);
        System.out.println(command4.execute()+"command4 是否读取缓存： "+command4.isResponseFromCache());
        System.out.println(command5.execute()+"command5 是否读取缓存： "+command5.isResponseFromCache());

        context.shutdown();
    }
}
