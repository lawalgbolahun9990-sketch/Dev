package com.example;

public class PowerUsingRecusion {
    public static void main(String[] args) {
        System.out.println(findPower(2,200));
    }
    public static long findPower(int n, int power){
        if (power == 0) {
            return 1;
        } else {
            return n * findPower(n, power -1);
        }
    }
}
