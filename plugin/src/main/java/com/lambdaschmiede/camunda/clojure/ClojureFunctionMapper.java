package com.lambdaschmiede.camunda.clojure;

import clojure.java.api.Clojure;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.javax.el.FunctionMapper;
import org.camunda.bpm.engine.impl.util.ReflectUtil;

import java.lang.reflect.Method;

/**
 * A Function mapper which provides the 'clj(arg)' function to Camundas UEL context.
 *
 * The argument is a string, referencing a Clojure function with its qualified name.
 * The targeted function has to accept exactly one parameter. This parameter will
 * be passed by the engine as an instance of DelegateExecution.
 */
public class ClojureFunctionMapper extends FunctionMapper {

    /**
     * Camunda will query this method on a list of registered FunctionMapper instances
     * with a UEL expression until the first one returns a value. This function checks
     * if the localName of the UEL expression is called 'clj'. If so, it returns a reference
     * to the static method 'eval' in this same class.
     *
     * If the localName does not match, it returns null and Camunda will query the next
     * FunctionMapper instance in its list
     */
    @Override
    public Method resolveFunction(String prefix, String localName) {
        if ("clj".equalsIgnoreCase(localName)) {
            return ReflectUtil.getMethod(ClojureFunctionMapper.class, "eval", String.class);
        } else {
            return null;
        }
    }

    /**
     * Receives the expression which is passed as a parameter in the 'clj' function.
     * E.g. if the expression is `clj('namespace/function')`, the parameter will
     * contain the value 'namespace/function'. This method creates a new instance
     * of JavaDelegate on the spot, with a body that invokes the referenced Clojure
     * function with its DelegateExecution parameter passed as an argument.
     */
    public static JavaDelegate eval(String expression) {
        return execution -> Clojure.var(expression).invoke(execution);
    }
}
