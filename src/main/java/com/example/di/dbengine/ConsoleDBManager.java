package com.example.di.dbengine;

import java.util.ArrayList;
import java.util.List;

import com.example.di.annotation.Component;

@Component
public class ConsoleDBManager {
    public <T> List<T> query(Class<T> clazz) {
        try {
            return List.of(
              clazz.newInstance(),
              clazz.newInstance(),
              clazz.newInstance(),
              clazz.newInstance()
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
