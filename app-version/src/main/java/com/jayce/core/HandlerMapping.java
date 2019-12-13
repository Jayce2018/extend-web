package com.jayce.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static Map<Class,Map<String,Method>> handlerMapping=new HashMap<>();

    public static Map<Class, Map<String, Method>> getHandlerMapping() {
        return handlerMapping;
    }

    public static void setHandlerMapping(Map<Class, Map<String, Method>> handlerMapping) {
        HandlerMapping.handlerMapping = handlerMapping;
    }
}
