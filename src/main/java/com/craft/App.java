package com.craft;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");

        Student student = context.getBean("student", Student.class);
        System.out.println(student);
        student.getDetails();

        // Bean scope check
        Student student1 = context.getBean("student", Student.class);
        Student student2 = context.getBean("student", Student.class);
        System.out.println("Same instance? " + (student1 == student2));

        student1.setName("Changed Name");
        System.out.println("student2 name: " + student2.getName());
    }
}