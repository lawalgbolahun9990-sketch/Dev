package com.example;

public class GenericStack<E> {
    private int n = 0;
    private E[] arr = (E[])new Object[8];

    public int getSize(){
        return n;
    }
    public E peek(){
        if(n == 0) return null;
        return arr[n - 1];
    }
    public void push(E e){
        if(n == arr.length){
            E[] temp = (E[])new Object[n * 2];
            for (int i = 0; i < n; i++) {
                arr[i] = temp[i];
            }
            arr = temp;
        }

        arr[n++] = e;
    }
    public E poll(){
        if (n == 0) return null;

        E e = arr[--n];
        arr[n] = null;
        return e;
    }
    public boolean isEmpty(){
        return n == 0;
    }

    @Override
    public String toString(){
        return "Stack: " + arr.toString();
    }
}
