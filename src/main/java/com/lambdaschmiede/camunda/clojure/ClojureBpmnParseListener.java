package com.lambdaschmiede.camunda.clojure;

import org.camunda.bpm.engine.impl.bpmn.behavior.ScriptTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.ServiceTaskExpressionActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

public class ClojureBpmnParseListener extends AbstractBpmnParseListener {

    @Override
    public void parseServiceTask(Element serviceTaskElement, ScopeImpl scope, ActivityImpl activity) {
        ServiceTaskDelegateExpressionActivityBehavior existingBehavior = (ServiceTaskDelegateExpressionActivityBehavior) activity.getActivityBehavior();

        System.out.println("PARRRRRSE");
    }
}
