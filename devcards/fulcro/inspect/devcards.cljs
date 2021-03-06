(ns fulcro.inspect.devcards
  (:require [devcards.core]
            [fulcro.client.logging :as log]
            [fulcro.inspect.core]
            [fulcro.inspect.ui.demos-cards]
            [fulcro.inspect.ui.data-history-cards]
            [fulcro.inspect.ui.data-viewer-cards]
            [fulcro.inspect.ui.data-watcher-cards]
            [fulcro.inspect.ui.element-cards]
            [fulcro.inspect.ui.network-cards]
            [fulcro.inspect.ui.transactions-cards]
            [fulcro.inspect.ui.i18n-cards]
            [fulcro-css.css :as css]))

(log/set-level :none)
(devcards.core/start-devcard-ui!)

(css/upsert-css "fulcro.inspector" fulcro.inspect.core/GlobalRoot)
