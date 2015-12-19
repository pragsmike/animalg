(set-env!
 :source-paths    #{"src/cljs"}
 :resource-paths  #{"resources"}
 :dependencies '[[org.clojure/clojurescript "1.7.189"]
                 [adzerk/boot-cljs      "1.7.170-3" :scope "test"]
                 [adzerk/boot-cljs-repl     "0.3.0"      :scope "test"]
                 [adzerk/boot-reload        "0.4.2"      :scope "test"]
                 [com.cemerick/piggieback "0.2.1" :scope "test"]
                 [weasel "0.7.0" :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                 [pandeiro/boot-http "0.7.0"      :scope "test"]
                 [org.clojure/core.async "0.2.374"]
                 [reagent "0.5.1"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]]
 '[cljs.build.api])


(deftask build []
  (comp (cljs)))

(deftask run []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (build)))

(deftask production []
  (task-options! cljs {:optimizations :advanced})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none}
                 serve {:dir "target"}
                 reload {:on-jsload 'animalg.app/init})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))
