package RPCClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 1, 获取到执行的接口实例
 * 2，通过动态代理来执行接口方法
 * Created by lcc on 2017/6/24.
 */
public class RPCClient {

    /**
     *
     * @param clazz 调用接口的class对象
     * @param <T>
     * @return
     */
    public static <T> T getObject(Class<T> clazz,String host,int port){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ObjectInvocation(host,port,clazz.getName()));
    }
}
