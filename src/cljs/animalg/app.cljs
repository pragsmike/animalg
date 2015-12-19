(ns animalg.app
  (:require   [cljs.core.async :as a :refer (chan timeout ) ]
              [reagent.core :as reagent :refer [atom]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(defonce god (atom 0))
(def model (atom {:s1 0 :s2 0 :vals (vec (repeatedly 40 #(rand-int 100)))}))

(defn swap [v i1 i2]
  (if (< (nth v i2) (nth v i1))
    (assoc v i2 (nth v i1) i1 (nth v i2))
    v))

(defn advance% [s1 s2]
  (let [width 39
        s1 (mod (inc s1) width)
        s2 (if (= 0 s1) (inc s2) s2)]
    [s1 s2 (> s2 width)])
  )

(defn advance%% [m]
  (let [[s1 s2 done] (advance% (:s1 m) (:s2 m))]
    (-> m
        (assoc :s1 s1)
        (assoc :s2 s2)
        (assoc :done done))))
(defn advance [m]
  (if (:done m)
    m
    (-> m
        (update :vals #(swap % (:s1 m) (inc (:s1 m))))
        (advance%%)
        )
))

(def dt (/ 1 60))

(defn bar [x h]
  [:rect { :x (+ 4 (* 5 x)) :y (- 200 h)
          :width 4 :height h
          :stroke "#ff0033"
          :stroke-width 1
          :fill :orange}])


(defn render [m]
  (map-indexed bar (:vals m)))

(defn octo []
  (apply vector (concat
                 [:svg {:width 200 :height 200}]
                 (render @model))))

(defn calling-component []
  [:div
   [:div "Sorting: " (str (:s1 @model) " " (:s2 @model))]
   [octo]])

(defn init []
  (swap! god inc)
  (go (loop  [me @god]
        (swap! model advance)
        (<! (timeout 16))
        (if (= me @god)
          (recur me))))
  (reagent/render-component [calling-component]
                            (.getElementById js/document "container")))
