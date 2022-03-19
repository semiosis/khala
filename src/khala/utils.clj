(ns khala.utils
  (:require
   [clojure.string :as str]))

(use '[clojure.java.shell :only [sh]])
(use '[clojure.string :only (join split upper-case)])

(defn cmd
  ""
  [& args]
  (clojure.string/join
   " "
   ;; I have to use the jq version so unicode works
   ;; But it's much slower. So I have to rewrite this with clojure
   (map (fn [s] (->
                 (sh "pen-q-jq" :in (str s))
                 :out)) args)))

;; (defn app [req]
;;   {:status  200
;;    :headers {"Content-Type" "text/html"}
;;    :body    (str (t/time-now))})

(defn tv [s]
  (sh "pen-tv" :in (str s))
  s)

(defn args-to-envs [args]
  (join "\n"
        (map (fn [[key value]]
               (str
                (str/replace
                 (upper-case
                  (name key))
                 "-" "_")
                "=" (cmd value)))
             (seq args))))