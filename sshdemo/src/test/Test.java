package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class Test {
    public static void main(String[] args) throws UnknownHostException {

        System.out.println("uuid1"+ UUID.randomUUID().toString());
        System.out.println("uuid2"+ UUID.randomUUID().toString());
        ApplicationContext ac = new FileSystemXmlApplicationContext("web/WEB-INF/applicationContext.xml");
        TestService ts = (TestService)ac.getBean("testService");
        ts.hello();

        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();
        System.out.println("ipip===" + ip);
    }
}
