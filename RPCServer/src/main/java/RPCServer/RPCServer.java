package RPCServer;

import com.dongao.UserService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 1，首先要有一个容器
 * 2，将所有的类统一管理
 * 3，要开启一个socket服务
 * 4，接受客户端请求，利用反射来调用方法
 * 5，将客户端请求的相关对象还原
 * 6，从容器中找到对应的对象执行
 * 7，将结果以序列化形式返回
 * Created by lcc on 2017/6/24.
 */
public class RPCServer {

    //key:接口全路径名  value:接口的实现对象
    private static Map<String, Object> objectMap = new HashMap<>();

    public void registerObject(Object object){
        //首先要得到对象的接口名
        Class<?>[] interfaces = object.getClass().getInterfaces();
        if (interfaces ==null) {
            throw new RuntimeException("该对象没有实现接口");
        }
        for (Class<?> anInterface : interfaces) {
            System.out.println("注册接口："+anInterface.getName());
            objectMap.put(anInterface.getName(), object);
        }
    }

    /**
     *
     * @param port 端口
     */
    public void start(int port){
        try {
            //创建一个服务
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                //获取客户端的请求信息
                InputStream inputStream = clientSocket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                //获取你调用的接口
                String interfaceName = objectInputStream.readUTF();
                //获取调用的方法
                String methodName = objectInputStream.readUTF();
                //从容器中拿出对象
                Object invokeObject = objectMap.get(interfaceName);
                if (invokeObject ==null){
                    throw new RuntimeException("未找到对象");
                }
                //获取客户端调用方法的类型
                Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
                Method method = invokeObject.getClass().getMethod(methodName, parameterTypes);
                Object[] args = (Object[]) objectInputStream.readObject();
                Object resultObject = method.invoke(invokeObject, args);
                //将结果返回客户端
                OutputStream outputStream = clientSocket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(resultObject);
                outputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RPCServer rpcServer = new RPCServer();
        rpcServer.registerObject(new UserService());
        rpcServer.start(5555);

    }



}
