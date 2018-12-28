package cn.qyd.springcloud.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.TimeUnit;

/**
 * @Author qyd
 * @Date 18-12-27 下午3:56
 **/
public class RunTest {
    public static void main(String[] args) throws InterruptedException {
        RunCommand c1 = new RunCommand("使用execute方法执行命令");
        c1.execute();

        RunCommand c2 = new RunCommand("使用queue方法执行命令");
        c2.queue();

        RunCommand c3 = new RunCommand("使用Observer方法执行命令");
        Observable<String> observe = c3.observe();
        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("    命令执行完成");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("    命令执行结果："+s);
            }
        });
        TimeUnit.SECONDS.sleep(1);


        RunCommand c4 = new RunCommand("使用toObserver方法执行命令");
        Observable<String> observerable = c4.toObservable();

        observerable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("    命令执行完成");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("    命令执行结果："+s);
            }
        });
        TimeUnit.SECONDS.sleep(1);


    }

    static class RunCommand extends HystrixCommand<String> {
        String msg;

        public RunCommand(String msg) {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
            this.msg = msg;
        }

        @Override
        protected String run() {
            System.out.println(msg);
            return "Success";
        }
    }
}
