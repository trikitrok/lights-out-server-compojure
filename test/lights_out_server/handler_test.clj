(ns lights-out-server.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [lights-out-server.handler :refer :all]))

(deftest test-lights-out
  (testing "resetting lights"
    (let [response (app (mock/request :post "/reset-lights" {:m 3 :n 3}))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"lights\":[[1,1,1],[1,1,1],[1,1,1]]}"))))

  (testing "fliping lights"
    (app (mock/request :post "/reset-lights" {:m 3 :n 3}))

    (let [response (app (mock/request :post "/flip-light" {:x 0 :y 0}))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"lights\":[[0,0,1],[0,1,1],[1,1,1]]}"))))

  )