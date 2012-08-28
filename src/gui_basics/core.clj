(ns gui-basics.core
  (:use [seesaw core font]))

;native look for ui
(native!)

(def f (frame :title "Get to know Seesaw"))
(-> f pack! show!)
(config! f :title "Herp derp")
(config! f :content "This is some content")

(def lbl (label "I'm another label"))
(config! f :content lbl)

(defn display [content]
  (config! f :content content)
  content)

(display lbl)

(config! lbl :font "ARIAL-BOLD-21")

(config! lbl :font (font :name :monospaced
                         :style #{:bold :italic}
                         :size 18))

(def b (button :text "Click Me"))
(alert "I'm an alert")
(display b)
(def b-listen
  (listen b :action (fn [e] (alert e "Thanks!"))))
(b-listen) ;removes the listener

(def b-listen-2
  (listen b :mouse-entered #(config! % :foreground :blue)
          :mouse-exited #(config! % :foreground :red)))
(b-listen-2)

(def lb (listbox :model (-> 'seesaw.core ns-publics keys sort)))
(display lb)
(display (scrollable lb))

(selection lb) ;get selection

(selection lb {:multi? true})
(selection! lb 'all-frames)

(def lb-listener
  (listen lb :selection (fn [e] (println "Selection is " (selection e)))))
(lb-listener)

(def field (display (text "This is a text field.")))
(text field) ;get text
(text! field "A new value")
(config! field :font "MONOSPACED-PLAIN-12" :background "#f88")

(def area (text :multi-line? true :font "MONOSPACED-PLAIN-14"
                :text "This
is
multi
line
text"))
(display area)
(text! area (java.net.URL. "http://clojure.com"))
(display (scrollable area))
(scroll! area :to :top)
(scroll! area :to :bottom)
(scroll! area :to [:line 50])

(def split (left-right-split (scrollable lb) (scrollable area) :divider-location 1/3))
(display split)

(defn doc-str [s] (-> (symbol "seesaw.core" (name s)) resolve meta :doc))
(def lb-listener-2
  (listen lb :selection
          (fn [e]
            (when-let [s (selection e)]
              (-> area
                  (text! (doc-str s))
                  (scroll! :to :top))))))
(lb-listener-2)

(def rbs (for [i [:source :doc]]
           (radio :id i :class :type :text (name i))))

(display (border-panel
          :north (horizontal-panel :items rbs)
          :center split
          :vgap 5 :hgap 5 :border 5))
(select f [:JRadioButton])
(select f [:.type]) ; class selector
(select f [:#source]) ; id selector
(def group (button-group))
(config! (select f [:.type]) :group group)
(selection group)

(def group-listener
  (listen group :selection
          (fn [e]
            (when-let [s (selection group)]
              (println "Selection is " (id-of s))))))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
