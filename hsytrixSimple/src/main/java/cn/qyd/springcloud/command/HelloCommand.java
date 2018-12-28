package cn.qyd.springcloud.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @Author qyd
 * @Date 18-12-27 下午3:29
 **/
public class HelloCommand extends HystrixCommand<String> {
    private String url;
    CloseableHttpClient httpClient;
    public HelloCommand(String url) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.url = url;
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    protected String run() throws Exception {
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    protected String getFallback() {
        System.out.println("执行 HelloCommand 的回退方法");
        return "error";
    }
}
