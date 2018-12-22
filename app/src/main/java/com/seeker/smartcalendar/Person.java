package com.seeker.smartcalendar;

/**
 * @author Seeker
 * @date 2018/12/22/022  10:27
 * @describe TODO
 */
public class Person {

    private String name;

    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "My name is "+name+",I'm "+age+" year old.";
    }
}
