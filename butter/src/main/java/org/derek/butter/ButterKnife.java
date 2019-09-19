package org.derek.butter;

import android.app.Activity;

import org.derek.butter.adapter.InjectAdapter;
import org.derek.butter.adapter.NullAdapter;

import java.util.HashMap;
import java.util.Map;

public class ButterKnife  {

    static Map<Class<?> ,InjectAdapter<?>> sInjectCache = new HashMap<>();

    public static void inject(Activity activity){
        System.out.println(" inject activity ");
        InjectAdapter<Activity> adapter = getViewAdapter(activity.getClass());
        adapter.injects(activity);
    }

    private static <T> InjectAdapter<T> getViewAdapter(Class<?> clazz){
        InjectAdapter<?> adapter = sInjectCache.get(clazz);
        if (adapter == null){
            String adapterClassName = clazz.getName() + "$InjectAdapter";

            try {
                Class<?> adapterClass = Class.forName(adapterClassName);
                adapter = (InjectAdapter<?>) adapterClass.newInstance();
                sInjectCache.put(adapterClass,adapter);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return adapter == null ? new NullAdapter() : adapter;
    }
}
