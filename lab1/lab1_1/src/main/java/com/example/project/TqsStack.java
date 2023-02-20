package com.example.project;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    // Implementacao de um stack em java, onde T corresponde ao objeto no stack,
    // neste caso iremos usar ints para testar
    private LinkedList<T> stack = new LinkedList<T>();

    public T pop() throws NoSuchElementException{
        T t = stack.pop();
        if (t == null) {
            throw new NoSuchElementException();
        }
        return t;
    }

    public int size(){
        return stack.size();
    }

    public T peek(){
        T t = stack.getFirst();
        if (t == null) {
            throw new NoSuchElementException();
        }
        return stack.getFirst();
    }

    public void push(T t){
        stack.addFirst(t);
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

}
