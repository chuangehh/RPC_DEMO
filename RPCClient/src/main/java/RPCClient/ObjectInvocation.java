package RPCClient;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by lcc on 2017/6/24.
 */
public class ObjectInvocation implements InvocationHandler {
    private String host;
    private int port;
    private String interfaceName;

    public ObjectInvocation(String host, int port, String interfaceName) {
        this.host = host;
        this.port = port;
        this.interfaceName = interfaceName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeUTF(interfaceName);
        objectOutputStream.writeUTF(method.getName());
        objectOutputStream.writeObject(method.getParameterTypes());
        objectOutputStream.writeObject(args);
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object resultObject = objectInputStream.readObject();
        return resultObject;
    }
}
