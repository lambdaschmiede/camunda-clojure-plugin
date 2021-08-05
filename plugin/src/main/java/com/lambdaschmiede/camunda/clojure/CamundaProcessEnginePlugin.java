package com.lambdaschmiede.camunda.clojure;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;


public class CamundaProcessEnginePlugin implements ProcessEnginePlugin {

    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        // Nothing to do here
    }

    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        //TBD für später
        processEngineConfiguration.getExpressionManager().addFunctionMapper(new ClojureFunctionMapper());
    }

    public void postProcessEngineBuild(ProcessEngine processEngine) {
        // Nothing to do here
    }
}
