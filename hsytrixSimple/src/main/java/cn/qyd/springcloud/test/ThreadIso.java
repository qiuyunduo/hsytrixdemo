package cn.qyd.springcloud.test;

import cn.qyd.springcloud.command.TestCommand;
import com.netflix.config.ConfigurationManager;

import java.util.concurrent.TimeUnit;

/**
 * @Author qyd
 * @Date 18-12-27 下午5:12
 **/
public class ThreadIso {
    public static void main(String[] args) throws InterruptedException {
        ConfigurationManager.getConfigInstance().setProperty("hystrix.threadpool.default.coreSize",3);

        for (int i = 0; i < 6; i++) {
            TestCommand command = new TestCommand(i);
            command.queue();
        }

        TimeUnit.SECONDS.sleep(5);
    }
}
