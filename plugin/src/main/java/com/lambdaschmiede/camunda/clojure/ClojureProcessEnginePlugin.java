package com.lambdaschmiede.camunda.clojure;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;

/**
 * A ProcessEnginePlugin which adds a UEL function to use plain Clojure functions as Java Delegates via Delegate Expressions
 *
 */
public class ClojureProcessEnginePlugin implements ProcessEnginePlugin {

    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        // Nothing to do here
    }

    /**
     * Registers the ClojureFunctionMapper as an additional expression mapper in the Process Engine after it is initialized
     */
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.getExpressionManager().addFunctionMapper(new ClojureFunctionMapper());
    }

    public void postProcessEngineBuild(ProcessEngine processEngine) {
    }
}
