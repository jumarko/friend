(ns cemerick.friend.util
  (:require [ring.util.request :as req]))

(defn gets
  "Returns the first value mapped to key found in the provided maps."
  [key & maps]
  (some->> (map #(find % key) maps)
        (remove nil?)
        first
        val))

; this was never _really_ part of the API, was implemented (badly) before req/request-url was available
(def ^:deprecated original-url req/request-url)


(defn relative-url
  "Similar to `util/original-url` (and thus `ring.util.request/request-url`)
  except that it only uses `:uri` and `:query-string` parts of the request
  to build the URL and ignores `:scheme` and the 'host' header.
  This is to avoid issues with redirects using absolute URLs when the app is running behind an SSL proxy."
  [request]
  (str
   (:uri request)
   (when-let [query (:query-string request)]
     (str "?" query))))
