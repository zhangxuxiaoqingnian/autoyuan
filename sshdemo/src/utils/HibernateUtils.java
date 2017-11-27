package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class HibernateUtils {

    private static SessionFactory factory;

    static {
        ApplicationContext ac = new FileSystemXmlApplicationContext("web/WEB-INF/applicationContext.xml");

        HibernateUtils hibernateUtils = (HibernateUtils) ac.getBean("hibernateUtils");
        factory = hibernateUtils.getSessionFactory();
    }



    /*
      *打开Session
    */

    public static Session getSession() {

        return factory.openSession();
    }
    /*
      *关闭Session
    */

    public static void closeSession(Session session) {
        if (session != null) {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public  void setFactory(SessionFactory factory) {
      this.factory = factory;
    }
}
