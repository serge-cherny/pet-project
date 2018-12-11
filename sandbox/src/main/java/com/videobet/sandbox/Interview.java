package com.videobet.sandbox;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Interview {
    public static void main(String[] args) throws Exception {
//        reflection();
//        fizzBuzz();
//        binaryLiterals();
//        testIntegers();
//        testRequests();
        testEnum();
    }

    private static void reflection() throws Exception {
        //        Person p = new Person();
        Class cl = Class.forName("com.videobet.sandbox.Person");
        Person p = (Person)cl.getConstructor(new Class[] {String.class, String.class})
                .newInstance("Smith", "John");
        System.out.print(cl.getMethod("getFullName").invoke(p));
    }

    private static void fizzBuzz() {
        FizzBuzz fizBuzz = new FizzBuzz();
        fizBuzz.print(5);
    }

    private static void binaryLiterals() {
        System.out.println(Integer.toBinaryString(5000>>>31 ^ 1));
        System.out.println(Integer.toBinaryString(-5000>>>31 ^ 1));
//        System.out.println(Integer.toBinaryString(-5000));
    }

    private static void testIntegers() {
        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 200;
        Integer i4 = 200;
        System.out.println(i1 == i2);
        System.out.println(i3 == i4);

        byte b1 = -127;
        byte b2 = -127;
        System.out.println((byte)(b1 + b2));
    }

    private static void testRequests() {
        ActionSequence actionSequence = new ActionSequence();
        Random rnd = new Random();
        for (int i = 1; i  < 10000; i++) {
            Thread t = new Thread(actionSequence::start);
            if (rnd.nextInt(10) % 9 == 0) {
                t = new Thread(actionSequence::clear);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(rnd.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.start();
        }
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("final clear()");
        actionSequence.clear();
    }

    private static void testEnum() {
        TestEnum t = null;
        System.out.println(t);
//        String белиберда = "белиберда";
//        System.out.println(белиберда);
    }

    enum TestEnum {
        test1;
    }


}
