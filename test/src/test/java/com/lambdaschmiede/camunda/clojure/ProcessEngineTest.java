package com.lambdaschmiede.camunda.clojure;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;


public class ProcessEngineTest {

    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    @Test
    @Deployment(resources = "test-process.bpmn")
    public void testHappyPath() {
        IFn func = Clojure.var("clojure.core", "load-file");
        func.invoke("delegate.clj");
        ProcessInstance instance = runtimeService().startProcessInstanceByKey("Process_TestProcess");
        assertThat(instance).hasPassed("Activity_Test_01").isEnded();
        assertThat(instance).variables().hasSize(1);
        assertThat(instance).variables().containsEntry("my-var", 2L);
    }
}
