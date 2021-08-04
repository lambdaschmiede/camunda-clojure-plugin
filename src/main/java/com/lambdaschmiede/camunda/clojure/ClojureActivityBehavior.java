package com.lambdaschmiede.camunda.clojure;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.camunda.bpm.engine.impl.bpmn.behavior.TaskActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;

public class ClojureActivityBehavior extends TaskActivityBehavior {

    private final String expression;
    private final String resultVariable;

    ClojureActivityBehavior(String expression, String resultVariable) {
        this.expression = expression;
        this.resultVariable = resultVariable;
    }

    // Activity Behavior
    @Override
    public void execute(final ActivityExecution execution) throws Exception {

        this.executeWithErrorPropagation(execution, () -> {
            String[] result = expression.split("/");
            String ns = result[0];
            String var = result[1];
            IFn fn = Clojure.var(ns, var);
            Object value = fn.invoke(execution);
            if (resultVariable != null) {
                execution.setVariable(resultVariable, value);
            }
            leave(execution);
            return null;
        });
    }
}
