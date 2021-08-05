package com.lambdaschmiede.camunda.clojure;

import clojure.java.api.Clojure;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.javax.el.FunctionMapper;
import org.camunda.bpm.engine.impl.util.ReflectUtil;

import java.lang.reflect.Method;

public class ClojureFunctionMapper extends FunctionMapper {

    @Override
    public Method resolveFunction(String prefix, String localName) {
        if ("clj".equalsIgnoreCase(localName)) {
            return ReflectUtil.getMethod(ClojureFunctionMapper.class, "eval", String.class);
        } else {
            return null;
        }
    }

    public static JavaDelegate eval(String expression) {
        return execution -> Clojure.var(expression).invoke(execution);
    }
}
