package RPCClient;

import com.dongao.IUser;
import com.dongao.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        IUser user = RPCClient.getObject(IUser.class,"127.0.0.1",5555);
        String name = user.getUserName("小能");
        System.out.println(name);
        User user1 = user.getUser(19L);
        System.out.println(user1.getId()+"---"+user1.getName());
    }
}
