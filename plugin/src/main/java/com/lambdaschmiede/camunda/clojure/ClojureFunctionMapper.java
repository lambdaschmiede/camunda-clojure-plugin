package com.lambdaschmiede.camunda.clojure;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.el.CommandContextFunctionMapper;
import org.camunda.bpm.engine.impl.javax.el.FunctionMapper;
import org.camunda.bpm.engine.impl.util.ReflectUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * A Function mapper which provides the 'clj(arg)' function to Camundas UEL
 * context.
 *
 * The argument is a string, referencing a Clojure function with its qualified
 * name. The targeted function has to accept exactly one parameter. This
 * parameter will be passed by the engine as an instance of DelegateExecution.
 */
public class ClojureFunctionMapper extends FunctionMapper {

    public static Map<String, Method> FUNCTION_MAP = new HashMap<>();

    /**
     * Camunda will query this method on a list of registered FunctionMapper
     * instances with a UEL expression until the first one returns a value. This
     * function checks if the localName of the UEL expression is called 'clj'. If
     * so, it returns a reference to the static method 'eval' in this same class.
     *
     * If the localName does not match, it returns null and Camunda will query the
     * next FunctionMapper instance in its list
     */
    @Override
    public Method resolveFunction(String prefix, String localName) {
        if (!"clj".equals(prefix)) {
            return null;
        }
        initializeFunctionMapIfEmpty();
        return FUNCTION_MAP.get(localName);
    }

    private void initializeFunctionMapIfEmpty() {
        if (FUNCTION_MAP.isEmpty()) {
            synchronized (CommandContextFunctionMapper.class) {
                if (FUNCTION_MAP.isEmpty()) {
                    FUNCTION_MAP.put("delegate",
                            ReflectUtil.getMethod(ClojureFunctionMapper.class, "delegate", String.class));
                    FUNCTION_MAP.put("function",
                            ReflectUtil.getMethod(ClojureFunctionMapper.class, "function", String.class, Object[].class));
                }
            }
        }
    }

    /**
     * Receives the expression which is passed as a parameter in the 'clj' function.
     * E.g. if the expression is `clj('namespace/function')`, the parameter will
     * contain the value 'namespace/function'. This method creates a new instance of
     * JavaDelegate on the spot, with a body that invokes the referenced Clojure
     * function with its DelegateExecution parameter passed as an argument.
     */
    public static JavaDelegate delegate(String expression) {
        return execution -> Clojure.var(expression).invoke(execution);
    }

    public static Object function(String expression, Object... params) {
        int paramSize = params.length;
        IFn fun = Clojure.var(expression);
        switch (paramSize) {
            case 0:
                return fun.invoke();
            case 1:
                return fun.invoke(params[0]);
            case 2:
                return fun.invoke(params[0], params[1]);
            case 3:
                return fun.invoke(params[0], params[1], params[2]);
            // TODO: Some more params would be nice, too
            default:
                throw new IllegalArgumentException(
                        String.format("Parameter size of %s is not allowed for a Clojure expression", paramSize));
        }

    }
}
