# camunda-clojure-plugin
A Camunda Process Engine Plugin to execute Clojure Functions as Delegates

## Why do we need this?
While Camunda is tightly integrated with Java, using it with Clojure could be more elegant. If we want to call Clojure code as a Delegate, we need to create and compile a Java class from Clojure, so that it can be found by Camunda via the classloader. This does not go well with the REPL development flow. This project is an approach to call pure Clojure functions from Camunda process instances, without having to extend BPMN or Camunda itself.

## How does it work?
This Process Engine plugin registers a `org.camunda.bpm.engine.impl.javax.el.FunctionMapper` which can be referred to as a `DelegateExpression` from the BPMN file. It provides the UEL function `clj`, which accepts a string parameter, which can point to any Clojure function accepting a single parameter. This parameter passed will be an instance from Camundas `DelegateExecution` class. As an example, the delegate expression `${clj('example.camunda/test-delegate')}` will call the function `test-delegate` in the namespace `example.camunda`, which might look like this:

```clojure
(defn test-delegate [execution]
  (do
    (println (str "The instance is " (.getProcessInstanceId execution)))
    (.setVariable execution "var-name" 2)))
```

This has the following benefits:
* No customizing of BPMN. The default Camunda modeler and Cockpit just work
* No customizing of Camunda. No fork is needed, we can just make use of any Camunda version we like 
* No Java Interop Code. We can define our delegates as plain clojure functions
* Full REPL integration. We can adjust the Clojure delegate functions in the REPL on the fly and test them instantly with Camunda

## How can it be used?
When bootstrapping the Camunda engine in your Clojure program, you have to add the plugin class to the engine like this: 

``` clojure
(-> engine-config
    (.getProcessEnginePlugins)
    (.add (com.lambdaschmiede.camunda.clojure.ClojureProcessEnginePlugin.)))
```

The library is not yet available via Clojars / Maven Public repo, but we are working on that.


## How stable is this? 
Currently this implementation is only used by lambdaschmiede itself, although are presenting it to customers using Camunda. It works in our projects, and we will keep maintaining it to guarantee a stable library for our own use.

