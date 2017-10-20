(ns test-lein.core-smoke-test
  (:require
   [clj-http.client :as clj-http-client]
   [clojure.test :as test]
   [com.stuartsierra.component :as component]
   [test-lein.config :as config]
   [test-lein.core :as test-lein]))

(def system nil)

(defn init
  []
  (alter-var-root
   #'system
   (constantly (test-lein/system "test-automation"))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root
   #'system
   (fn [system] (when system (component/stop system)))))

(defn fixture
  [f]
  (init)
  (start)
  (f)
  (stop))

(test/use-fixtures :once fixture)

(test/deftest core-smoke-test
  (let [config (config/get-config-for "test-automation")
        test-port (:port config)
        server-response (clj-http-client/get
                         (config/get-fully-qualified-url config))
        response-body (:body server-response)]
    (test/testing "Can get a hello world from the server"
      (test/is (= "Hello, World!" response-body)))))
