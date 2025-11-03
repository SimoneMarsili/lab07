package it.unibo.inner.impl;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    private final List<T> elements;
    private Predicate<T> filter; 

    public IterableWithPolicyImpl(final T[] array) {
        this(array, new Predicate<T>() {
            public boolean test(T elem) {
                return true;
            }

        });
    }

    public IterableWithPolicyImpl(final T[] array, final Predicate<T> filter) {
        this.filter = filter;
        this.elements = List.of(array);
    }
    
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    public class ArrayIterator implements Iterator<T> {
        
        private int current;

        public ArrayIterator() {
            this.current = 0;
        }
        
        public T next() {
            if (hasNext()) {
                return elements.get(current++);
            }
            else{
                throw new NoSuchElementException("No more elements that satisfy filter");
            }
        }

        public boolean hasNext() {
            while (this.current < elements.size()) {
                if (filter.test(elements.get(this.current))) {
                    return true;
                }
                this.current++;
            }
            return false;
        }
    }
}
