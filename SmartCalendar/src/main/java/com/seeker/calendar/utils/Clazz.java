package com.seeker.calendar.utils;

import java.lang.reflect.Constructor;

/**
 * @author Seeker
 * @date 2018/12/18/018  15:30
 * @describe TODO
 */
public class Clazz {

    public static <T> T getConstructor(String clazz){
        Object o = null;
        try {
            Class c = Class.forName(clazz);
            if (c != null){
                Constructor constructor = c.getDeclaredConstructor();
                if (constructor != null){
                    o = constructor.newInstance();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return (T) o;
    }

}
