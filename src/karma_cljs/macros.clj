(ns karma-cljs.macros
  (:require
   [cljs.analyzer.api :as api]))

(defmacro tests-count
  [& namespaces]
  `(+ ~@(map (fn [[_ ns]]
               (count
                (filter #(true? (get-in % [1 :test]))
                        (cljs.analyzer.api/ns-publics ns))))
             namespaces)))

(defmacro run-tests
  "Runs all tests in the given namespaces."
  [karma & namespaces]
  `(if (nil? ~karma)
     (cljs.test/run-tests ~@namespaces)
     (do (karma-cljs.core/start ~karma (tests-count ~@namespaces))
         (cljs.test/run-tests (cljs.test/empty-env :karma) ~@namespaces))))

(defmacro run-all-tests
  "Runs all tests in all namespaces.
  Optional argument is a regular expression; only namespaces with
  names matching the regular expression (with re-matches) will be
  tested."
  ([karma]
   `(karma-cljs.macros/run-all-tests ~karma #".*"))
  ([karma re]
   `(karma-cljs.macros/run-tests
      ~karma
      ~@(into '()
              (comp (filter #(re-matches re (name %)))
                    (map (fn [ns] `(quote ~ns))))
              (api/all-ns)))))
