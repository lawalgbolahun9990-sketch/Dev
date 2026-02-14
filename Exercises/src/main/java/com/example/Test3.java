package com.example;

public class Test3 {
    public static void main(String[] args) {
        GenericStack<Character> stack = new GenericStack<>();
        stack.push('c');
        stack.push('a');
        stack.push('t');

        System.out.println(stack.poll());
        System.out.println(stack.poll());
        System.out.println(stack.peek());
    }
}
