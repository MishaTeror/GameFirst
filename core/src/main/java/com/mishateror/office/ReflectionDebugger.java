package com.mishateror.office;

import java.lang.reflect.Field;

/**
 * Dynamically inspects an object at runtime, reading even its private fields.
 */
public class ReflectionDebugger {

    /**
     * Print object state.
     *
     * @param obj the obj
     */
    public static void printObjectState(Object obj) {
        System.out.println("=== [REFLECTION] DEBUGGING " + obj.getClass().getSimpleName() + " ===");

        Class<?> clazz = obj.getClass();

        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    System.out.println("Field: " + field.getName() + " = " + field.get(obj));
                } catch (IllegalAccessException e) {
                    System.out.println("Could not access field: " + field.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }
        System.out.println("=========================================");
    }
}
