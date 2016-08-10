(ns lights-out-server.handler
  (:require
    [compojure.core :refer :all]
    [ring.middleware.cors :as cors-middleware]
    [ring.middleware.json :as json-middleware]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [lights-out-server.lights :as lights]))

(defroutes app-routes
  (POST "/reset-lights" [m n]
    (let [m (Integer/parseInt m)
          n (Integer/parseInt n)]
      (println m n)
      (lights/reset-lights m n)
      {:status 200 :body {:lights @lights/lights}}))

  (POST "/flip-light" [x y]
    (let [x (Integer/parseInt x)
          y (Integer/parseInt y)]
      (lights/flip-lights [x y])
      {:status 200 :body {:lights @lights/lights}})))

(def app
  (-> app-routes
      (wrap-defaults api-defaults)
      (json-middleware/wrap-json-response {:keywords? true :bigdecimals? true})
      (cors-middleware/wrap-cors
        :access-control-allow-origin #"http://0.0.0.0:3449"
        :access-control-allow-methods [:post])))
