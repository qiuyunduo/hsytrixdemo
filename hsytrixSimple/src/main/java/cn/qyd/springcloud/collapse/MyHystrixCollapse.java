package cn.qyd.springcloud.collapse;

import cn.qyd.springcloud.entity.Person;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;

import java.util.Collection;
import java.util.Map;

/**
 * @Author qyd
 * @Date 18-12-27 下午5:56
 * 合并处理器
 **/
public class MyHystrixCollapse extends HystrixCollapser<Map<String, Person>,Person,String> {

    String personName;

    public MyHystrixCollapse(String personName) {
        this.personName = personName;
    }


    @Override
    public String getRequestArgument() {
        return personName;
    }

    @Override
    protected HystrixCommand<Map<String, Person>> createCommand(Collection<CollapsedRequest<Person, String>> collection) {
        return new CollapseCommand(collection);
    }

    @Override
    protected void mapResponseToRequests(Map<String, Person> stringPersonMap, Collection<CollapsedRequest<Person, String>> collection) {
        //让结果与请求关联
        for (CollapsedRequest<Person,String> request : collection) {
            Person singleResult = stringPersonMap.get(request.getArgument());
            request.setResponse(singleResult);
        }
    }
}
