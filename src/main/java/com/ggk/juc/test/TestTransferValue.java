package com.ggk.juc.test;

import com.ggk.juc.entity.Person;

public class TestTransferValue {
    public void changeValue1(int age){
        age =30;
    }
    public void changeValue2(Person person){
        person.setPersonName("xxx");
    }
    public void changeValue3(String str){
        str = "xxx";
    }

    public static void main(String[] args) {
        TestTransferValue testTransferValue = new TestTransferValue();
        int age = 20;
        testTransferValue.changeValue1(age);
        System.out.println("age--------"+age);

        Person person = new Person("abc");
        testTransferValue.changeValue2(person);
        System.out.println("personName--------"+person.getPersonName());

        String str = "abc";
        testTransferValue.changeValue3(str);
        System.out.println("String--------"+str);
    }
}
