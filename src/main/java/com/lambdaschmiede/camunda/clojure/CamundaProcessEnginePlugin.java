package com.lambdaschmiede.camunda.clojure;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;

import java.util.ArrayList;
import java.util.List;

public class CamundaProcessEnginePlugin implements ProcessEnginePlugin {

    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

        List<BpmnParseListener> postParseListeners = processEngineConfiguration.getCustomPostBPMNParseListeners();
        if (null == postParseListeners) {
            postParseListeners = new ArrayList<>();
        }
        postParseListeners.add(new ClojureBpmnParseListener());
        processEngineConfiguration.setCustomPostBPMNParseListeners(postParseListeners);
    }

    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
