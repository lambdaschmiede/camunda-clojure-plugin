(ns camunda-clojure-plugin.delegate)

(defn test-delegate [execution]
    (.setVariable execution "my-var" 2))
