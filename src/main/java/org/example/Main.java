package org.example;

import com.sun.jmx.snmp.ThreadContext;
import java.sql.DriverManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.reflect.Reflection;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello world!");

    // This will look for 'applicationContext.xml' in the root of the classpath
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    // Retrieve the MessageService bean from the context
    MessageService messageService = (MessageService) context.getBean("messageService", MessageService.class);

    // Print the message from the MessageService bean
    System.out.println("MessageService message: " + messageService.getMessage());

    ((ClassPathXmlApplicationContext) context).close();

    // Print Java jvm version
    String javaVersion = System.getProperty("java.version");
    System.out.println("Java version: " + javaVersion);

    Object properties = System.getProperties();

    Object a = ClassLoader.getSystemClassLoader();
    Object classLoader = ApplicationContext.class.getClassLoader();

    System.out.println("System properties: " + properties);
  }
}