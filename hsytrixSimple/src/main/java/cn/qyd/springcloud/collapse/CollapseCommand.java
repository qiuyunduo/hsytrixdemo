package cn.qyd.springcloud.collapse;

import cn.qyd.springcloud.entity.Person;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.*;

/**
 * @Author qyd
 * @Date 18-12-27 下午5:44
 **/
public class CollapseCommand extends HystrixCommand<Map<String, Person>> {

    Collection<HystrixCollapser.CollapsedRequest<Person,String>> requests;

    public CollapseCommand(Collection<HystrixCollapser.CollapsedRequest<Person, String>> requests) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")));
        this.requests = requests;
    }

    @Override
    protected Map<String, Person> run() throws Exception {
        System.out.println("收集参数后执行命令，参数数量： "+requests.size());
        //处理参数
        List<String> personNames = new ArrayList<String>();

        for (HystrixCollapser.CollapsedRequest<Person,String> request : requests) {
            personNames.add(request.getArgument());
        }

        //调用服务（模拟外部服务），根据名称获取Person
        Map<String,Person> result = callService(personNames);
        return result;
    }

    private Map<String,Person> callService(List<String> names) {
        Map<String,Person> result = new HashMap<String,Person>();

        for (String name : names) {
            Person p = new Person();
            p.setId(UUID.randomUUID().toString());
            p.setName(name);
            p.setAge(new Random().nextInt());
            result.put(name,p);
        }
        return result;
    }
}
