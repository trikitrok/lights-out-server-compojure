(ns lights-out-server.lights)

(def lights (atom nil))

(def ^:private light-on 1)
(def ^:private light-off 0)

(defn- neighbors? [[i0 j0] [i j]]
  (or (and (= j0 j) (= 1 (Math/abs (- i0 i))))
      (and (= i0 i) (= 1 (Math/abs (- j0 j))))))

(defn- neighbors [m n pos]
  (for [i (range m)
        j (range n)
        :when (neighbors? pos [i j])]
    [i j]))

(defn light-off? [light]
  (= light light-off))

(defn- flip-light [light]
  (if (light-off? light)
    light-on
    light-off))

(defn- flip [lights pos]
  (update-in lights pos flip-light))

(defn- flip-neighbors [m n pos lights]
  (->> pos
       (neighbors m n)
       (cons pos)
       (reduce flip lights)))

(defn- all-lights-on [m n]
  (vec (repeat m (vec (repeat n light-on)))))

(defn- num-rows []
  (count @lights))

(defn- num-colums []
  (count (first @lights)))

(defn reset-lights! [m n]
  (reset! lights (all-lights-on m n)))

(defn flip-light! [pos]
  (swap! lights (partial flip-neighbors (num-rows) (num-colums) pos)))