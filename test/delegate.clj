(ns camunda-clojure-plugin.delegate)

(defn test-delegate [execution]
  (let [{:strs [myvar]} (.getVariables execution)]
    (.setVariable execution "myvar" (- myvar 10))))
