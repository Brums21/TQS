package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStack {

    private TqsStack<String> emptystack = new TqsStack<>();
    private TqsStack<String> fullStack = new TqsStack<>();

    //data initialization:
    @BeforeEach
    public void startStacking(){
        for (int i=0; i<10; i++){
            fullStack.push(""+i);
        }
    }

    //a
    @Test
    public void testEmptyOnConstruction(){
        assertTrue(emptystack.isEmpty());
    }

    //b
    @Test 
    public void testSizeOnConstruction(){
        assertTrue(emptystack.size()==0);
    }

    //c
    @Test
    public void testNPushes(){
        int n = 10;
        for (int i=0; i<n; i++){
            emptystack.push("randomStacks");
        }

        assertEquals(n, emptystack.size());
        assertTrue(!emptystack.isEmpty()); //make sure its not empty
    }

    //d
    @Test
    public void testPushPop(){
        String x = "This is x";
        fullStack.push(x);
        String value = fullStack.pop();

        assertEquals(value, x);
    }

    //e
    @Test
    public void testPushPeek(){
        String x = "This is x1";
        fullStack.push(x);
        Integer size = fullStack.size();
        String peeked = fullStack.peek();

        assertEquals(peeked, x);
        assertEquals(size, fullStack.size());
    }

    //f
    @Test
    public void testSizePop(){
        //If the size is n, then after n pops, the stack is empty and has a size 0
        Integer size = fullStack.size();
        for (int i = 0; i< size; i++){
            fullStack.pop();
        }
        assertTrue(fullStack.isEmpty());
        assertTrue(fullStack.size() == 0);
    }


    //g
    @Test 
    public void testPopEmpty(){
        assertTrue(emptystack.size() == 0);
        assertThrows(NoSuchElementException.class, () -> {
            emptystack.pop();
        });
    }

    //h
    @Test
    public void testPeekEmpty(){
        assertTrue(emptystack.size() == 0);
        assertThrows(NoSuchElementException.class, () -> {
            emptystack.peek();
        });
    }
}
