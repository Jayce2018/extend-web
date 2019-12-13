package com.jayce.common.config;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jayce.common.enums.VersionEnum;
import com.jayce.common.util.SpringContextUtil;
import com.jayce.core.HandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class VersionDispatcherServlet extends DispatcherServlet {
    private static final long serialVersionUID = 1L;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("--requestInfo: " + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + " " + request.getMethod());
        //filter
        if (request.getRequestURI().equals("/error")) {
            super.doDispatch(request, response);
            return;
        }
        //Target method
        Method methodBar;
        //find
        String requestContentType = request.getContentType();

        //get Version,Trade
        Map<String, String> tradeMap = getVersionAndTrade(request.getRequestURI());

        //get class
        Class<?> clazz = Class.forName(getClassName(tradeMap.get("version")));
        Object instance = SpringContextUtil.getBean(clazz);
        //Object instance = clazz.newInstance();
        //field Auto wired
        //fieldAutoLoad(instance);

        //get target method
        Map<Class, Map<String, Method>> handlerMapping = HandlerMapping.getHandlerMapping();
        Map<String, Method> methodHandler = handlerMapping.get(clazz);
        if (null == methodHandler) {
            methodHandler = new HashMap<>();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                methodHandler.put(method.getName(), method);
            }
            handlerMapping.put(clazz, methodHandler);
            methodBar = methodHandler.get(tradeMap.get("trade"));
        } else {
            methodBar = handlerMapping.get(clazz).get(tradeMap.get("trade"));
        }

        //method invoking
        JSONObject object;
        //parameter adopter
        try {
            Object obj = invokeAdopter(instance,methodBar,request);
            if ("".equals(obj) || null == obj) {
                response.getWriter().print(obj);
                return;
            }
            object = (JSONObject) obj;

        } catch (JSONException exception) {
            throw new Exception("Wrong parameter type/参数类型错误，解析失败");
        }

        log.info("--resultInfo" + object + "\n");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(object);
    }

    /**
     * field Auto wired
     */
    private void fieldAutoLoad(Object instance) throws IllegalAccessException, InstantiationException {

        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getDeclaredAnnotation(Autowired.class);
            if(null!=autowired){
                field.setAccessible(true);
                field.set(instance,SpringContextUtil.getBean(field.getType()));
            }
            Resource resource = field.getDeclaredAnnotation(Resource.class);
            if(null!=resource) {
                field.setAccessible(true);
                field.set(instance, SpringContextUtil.getBean(field.getName()));
            }
        }
    }

    /**
     * pram adopter
     */
    private Object invokeAdopter(Object instance,Method methodBar,HttpServletRequest request) throws Exception {
        HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
        if (httpMethod.equals(HttpMethod.POST)) {
            String string = getBodyString(request);
            log.info("--requestCS:" + string);
            //invoke
            return methodBar.invoke(instance, JSONObject.parseObject((String) string, methodBar.getParameterTypes()[0]));
        } else if (httpMethod.equals(HttpMethod.GET)) {
            //代码参数map<pram注解，参数类型>
            Map<RequestParam, Class> methodMap = new LinkedHashMap<>();
            Parameter[] parameters = methodBar.getParameters();
            for (Parameter param : parameters) {
                if (null == param.getAnnotation(RequestParam.class)) {
                    throw new Exception("Pram参数型请求，请加RequestParam注解");
                }
                methodMap.put(param.getAnnotation(RequestParam.class), param.getType());
            }
            //请求参数map<参数名，参数值>
            Map<String, String[]> requestParameterMap = request.getParameterMap();


            Map<String, Object> map = new HashMap<String, Object>();
            map.put("instance", instance);
            String params = "";
            Set<RequestParam> keySet = methodMap.keySet();

            //组装调用代码参数
            int i = 0;
            for (RequestParam pram : keySet) {
                String[] strings = requestParameterMap.get(pram.value());
                Class typeClass = methodMap.get(pram);
                String pramName = "pram" + i;
                String[] requestValues = requestParameterMap.get(pram.value());
                if (null == requestValues || requestValues.length == 0) {
                    throw new Exception("参数名不匹配");
                }
                if (typeClass.equals(Integer.class)) {
                    map.put(pramName, Integer.valueOf(requestValues[0]));
                } else {
                    map.put(pramName, typeClass.cast(requestValues[0]));
                }
                params += pramName + ",";
                i++;
            }
            log.info("--requestCS:" + map);
            String name = methodBar.getName();
            String expression = "instance." + name + "(" + params.substring(0, params.length() - 1) + ");";
            return convertToCode(expression, map);
        }
        return null;
    }

    /**
     * string convert To Coding
     */
    private static Object convertToCode(String jexlExp, Map<String, Object> map) {
        JexlEngine jexl = new JexlEngine();
        log.info("--jexlExp:" + jexlExp);
        Expression e = jexl.createExpression(jexlExp);
        JexlContext jc = new MapContext();
        for (String key : map.keySet()) {
            jc.set(key, map.get(key));
        }
        if (null == e.evaluate(jc)) {
            return "";
        }
        return e.evaluate(jc);
    }


    /**
     * get className
     */
    private String getClassName(String version) {
        String packagePre = "com.jayce.business.Business";
        VersionEnum enumObject = VersionEnum.getEnumValue(Double.parseDouble(version));
        assert enumObject != null;
        return packagePre + enumObject.getCode();
    }

    /**
     * get version and trade
     */
    private Map<String, String> getVersionAndTrade(String uri) {
        Map<String, String> map = new HashMap<>(2);
        String[] strings = uri.trim().split("/");
        int safeSize = 2;
        if (strings.length < safeSize) {
            return new HashMap<>(0);
        }
        map.put("version", strings[1]);
        map.put("trade", strings[2]);
        return map;
    }


    /**
     * Resolving parameters for special request types
     */
    private String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
