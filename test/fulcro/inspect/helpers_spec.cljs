(ns fulcro.inspect.helpers-spec
  (:require
    [fulcro-spec.core :refer [specification behavior component assertions]]
    [fulcro.inspect.helpers :as h]
    [om.next :as om]))

(om/defui ^:once Child
  static om/IQuery
  (query [_] [:child/id])

  static om/Ident
  (ident [_ props] [:child/id (:child/id props)]))

(om/defui ^:once Container
  static om/IQuery
  (query [_] [:container/id
              {:child (om/get-query Child)}])

  static om/Ident
  (ident [_ props] [:container/id (:container/id props)]))

(specification "merge-entity"
  (assertions
    (h/merge-entity {} Container {:container/id "cont"
                                  :child        {:child/id "child"}})
    => {:container/id {"cont" {:container/id "cont"
                               :child        [:child/id "child"]}}
        :child/id     {"child" {:child/id "child"}}}

    (h/merge-entity {:container/id {"cont" {:container/id "cont"
                                            :child        [:child/id "child"]}}
                     :child/id     {"child" {:child/id   "child"
                                             :child/name "Bla"}}} Container
      {:container/id   "cont"
       :container/name "Huf"
       :child          {:child/id "child"}})
    => {:container/id {"cont" {:container/id   "cont"
                               :container/name "Huf"
                               :child          [:child/id "child"]}}
        :child/id     {"child" {:child/id "child"
                                :child/name "Bla"}}}))

(specification "deep-remove"
  (assertions
    (h/deep-remove-ref {} [:id 123])
    => {}

    (h/deep-remove-ref {:id {123 {:id 123}}} [:id 123])
    => {:id {}}

    (h/deep-remove-ref {:user {123 {:id      123
                                    :related [:user 321]}
                               321 {:id 321}}} [:user 123])
    => {:user {}}

    (h/deep-remove-ref {:user {123 {:id      123
                                    :related [[:user 321]
                                              [:user 42]]}
                               321 {:id 321}
                               42  {:id 42}
                               33  {:id 33}}} [:user 123])
    => {:user {33 {:id 33}}}))

(specification "vec-remove-index"
  (assertions
    (h/vec-remove-index 0 [5 6]) => [6]
    (h/vec-remove-index 1 [5 6]) => [5]
    (h/vec-remove-index 1 [:a :b :c]) => [:a :c]))