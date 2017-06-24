package RPCServer;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            System.out.println(Arrays.toString("神".getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
