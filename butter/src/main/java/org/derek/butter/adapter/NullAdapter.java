package org.derek.butter.adapter;

public class NullAdapter<T> implements InjectAdapter<T> {
    @Override
    public void injects(T target) {

    }
}
