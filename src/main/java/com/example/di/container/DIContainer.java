package com.example.di.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.example.di.annotation.Component;
import com.example.di.annotation.Inject;

public class DIContainer {

    private final Map<Class<?>, Object> components = new HashMap<>();

    public DIContainer() {
        List<Object> highLevelComponents = new ArrayList<>();
        // Add All components first
        for (Class<?> clazz : getAllClassesByAnnotation(Component.class)) {
            Object component = createComponent(clazz);
            components.put(clazz, component);
            if (isHighLevelComponent(clazz)) {
                highLevelComponents.add(component);
            }
        }

        System.out.println(highLevelComponents);
        // Add dependency to high level components
        for (Object component: highLevelComponents) {
            injectDependencies(component);
        }
    }

    private Set<Class> getAllClassesByAnnotation(Class<? extends Annotation> clazz) {
        Reflections reflections = new Reflections("com.example.di");
        return new HashSet<>(reflections.getTypesAnnotatedWith(clazz));
    }

    private Object createComponent(Class<?> clazz) {
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            System.out.println("Cannot initiate class: " + clazz.getName());
        }
        return instance;
    }

    private boolean isHighLevelComponent(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Inject.class))
                return true;
        }
        return false;
    }

    private void injectDependencies(Object component) {
        Field[] fields = component.getClass().getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> type = field.getType();
                Object dependency = getInstance(type);
                field.setAccessible(true);
                try {
                    field.set(component, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error injecting dependency of type " + type.getName() + " into field " + field.getName() + " of component " + component.getClass().getName(), e);
                }
            }
        }

    }

    public <T> T getInstance(Class<T> type) {
        if (!components.containsKey(type)) {
            throw new RuntimeException("No instance found for that type " + type.getName());
        }
        return type.cast(components.get(type));
    }



}

