package cn.qyd.springcloud.command;

import com.netflix.config.ConfigurationManager;

/**
 * @Author qyd
 * @Date 18-12-27 下午3:35
 **/
public class HelloMain {
    public static void main(String[] args) {
        String normalUrl = "http://localhost:8081/normalHello";
//        String normalUrl = "http://localhost:8081/errorHello";
        HelloCommand helloCommand = new HelloCommand(normalUrl);
        String result = helloCommand.execute();
        System.out.println("请求正常的服务，结果： "+result);
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen","true");
        HelloCommand helloCommand1 = new HelloCommand(normalUrl);
        String result1 = helloCommand1.execute();
        System.out.println("第二次请求正常的服务，结果： "+result1);
    }
}
