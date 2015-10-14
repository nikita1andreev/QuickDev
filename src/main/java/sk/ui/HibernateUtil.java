/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;

import a1.entity.Events;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
//import org.hibernate.ejb.Ejb3Configuration;

public class HibernateUtil {
private static SessionFactory sessionFactory;
//private static final Ejb3Configuration ejb3Configuration;

  static {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
      //ejb3Configuration = new Ejb3Configuration().configure("/hibernate.cfg.xml");
    } catch (Throwable ex) {
    	Main.generateAndSendEmail(ex);
    	try {
    	sessionFactory = getSessionFactory("jdbc:mysql://localhost:3306/psk2?zeroDateTimeBehavior=convertToNull","root","");
    	}
    	catch (Throwable ex1) {
    		Main.generateAndSendEmail(ex);
    		throw new ExceptionInInitializerError(ex1);
    		}
    
     // logger.error("Initial SessionFactory creation failed." + ex);
      //throw new ExceptionInInitializerError(ex);
    }
  }
    public static SessionFactory getSessionFactory(String url,String l, String p){
      AnnotationConfiguration af = new AnnotationConfiguration();
      af.setProperty("hibernate.connection.url", url);
      af.setProperty("hibernate.connection.username", l);
      af.setProperty("hibernate.connection.password", p);
      af.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
      af.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
      af.setProperty("hibernate.show_sql", "true");
      af.setProperty("hibernate.hbm2ddl.auto", "update");
      af.setProperty("current_session_context_class", "thread");
      //af.setProperty( "hibernate.jdbc.factory_class", "org.hibernate.jdbc.BatcherFactory" );
      af.addAnnotatedClass(a1.entity.Events.class);
      af.addAnnotatedClass(a1.entity.Contacts.class);
      af.addAnnotatedClass(a1.entity.Contact_Status.class);
      af.addAnnotatedClass(a1.entity.Event_types.class);
      af.addAnnotatedClass(a1.entity.Responsible.class);
      sessionFactory = af.buildSessionFactory();
      return sessionFactory;
  }    
    
  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

//  public static Ejb3Configuration getEjb3Configuration() {
//    return ejb3Configuration;
//  }


  
  
}