(ns leathergoods.render-html
  "Build-time HTML renderer for `docs/samples/operator-console.html`.

  Closes flagship checklist item 2 (com-junkawasaki/root ADR-2607189300):
  this repo previously had NO demo page and no generator at all. This
  namespace drives the REAL actor stack (`leathergoods.operation` ->
  `leathergoods.governor` -> `leathergoods.store`, via langgraph
  `g/run*`) through a scenario built on this repo's own seeded ticket
  directory (`leathergoods.store/demo-data`, ids `ticket-1`..`ticket-5`)
  and renders the result deterministically -- no invented entities, no
  timestamps in the page content, byte-identical across reruns against
  the same seed.

  EVERY entity, id, number, statute and rule name on the rendered page
  is read back out of the store/ledger this run actually produced, or
  out of `leathergoods.facts/catalog`. Nothing is hand-typed HTML data.
  Two honesty notes the page itself carries:

    - `:item` / `:item-type` (`:boots` `:belt` `:handbag` `:shoes`) are
      FREE-TEXT / declared fields on each seeded ticket record. This
      repo has NO item, leather-species or material taxonomy to look
      them up in, so the page traces them to the ticket's own declared
      seed value and says so, rather than implying a registry that does
      not exist.
    - approver attribution is DERIVED at render time by walking each
      committed register and checking whether an approver key actually
      survived the store write (see `approver-attribution-rows`), so
      the page self-corrects if the store's retention changes.

  The scenario deliberately exercises ALL SEVEN of the governor's HARD
  (un-overridable) rules -- `-main` refuses to write a page that
  produced zero governor holds, so the HARD-hold requirement is a
  build-time invariant rather than a convention. Verified in BOTH
  directions: with the hold steps removed, `-main` throws and writes no
  file; unmodified, it writes and exits 0.

  Measured shape of one run (re-derive rather than cite -- these are
  outputs, not settings): 14 graph threads; 40 facts on the run-level
  audit channel (14 advisor proposals, 7 commits, 6 approval-requested,
  6 approval-granted, 7 governor-holds) against 14 facts in the durable
  store ledger (7 commits + 7 holds). The 12 approval facts are exactly
  what the ledger does not keep.

  Usage: `clojure -M:dev:render-html [out-file]`
  (default `docs/samples/operator-console.html`)."
  (:require [jp-go-dds.skin]
            [kotoba.lang.text :as str]
            [leathergoods.facts :as facts]
            [leathergoods.registry :as registry]
            [leathergoods.store :as store]
            [leathergoods.operation :as op]
            [langgraph.graph :as g]))

(def ^:private operator
  {:actor-id "op-1" :actor-role :repair-technician :phase 3})

(defn- record-run!
  "Keeps the FINAL `:audit` channel of each graph thread, last write per
  thread-id winning. Necessary because the `:audit` channel's reducer is
  `into`: a resumed run's state already contains the pre-interrupt
  facts, so naively concatenating every run would double-count them."
  [runs tid result]
  (let [a (vec (get-in result [:state :audit]))]
    (swap! runs (fn [v]
                  (if-let [i (first (keep-indexed #(when (= tid (first %2)) %1) v))]
                    (assoc v i [tid a])
                    (conj v [tid a])))))
  result)

(defn run-demo!
  "Runs a fresh seeded store through a scenario that reaches every
  disposition this actor can produce, and every one of the Repair Shop
  Governor's seven HARD rules.

  ticket-1 (Sakura Tanaka, JPN, leather boots, NOT a branded item)
  clears a full lifecycle: intake (auto-commits clean at phase 3 -- the
  only op in any phase's `:auto` set), a jurisdiction assessment
  (phase-gated, approved), a post-repair safety screening (approved), a
  brand-authenticity screening (approved -- `:not-applicable`, since the
  ticket's own `:involves-branded-item?` is false), a repair completion
  and an item return (both ALWAYS escalate: `:actuation/complete-repair`
  and `:actuation/return-item` are permanently high-stakes and are
  absent from every phase's `:auto` set -- approved by a human).

  Then seven HARD holds, none of which ever reaches a human:
    :no-spec-basis                  ticket-2, assessed against the
                                    deliberately unregistered ATL
                                    jurisdiction.
    :parts-cost-mismatch            ticket-3, whose claimed parts cost
                                    (60.0) does not equal the
                                    independently recomputed
                                    parts-quantity x parts-unit-price
                                    (1 x 40 = 40.0).
    :safety-test-not-passed         ticket-4, whose own safety screening
                                    detects its failed post-repair test.
    :brand-authenticity-unconfirmed ticket-5, a branded designer handbag
                                    whose authenticity is unconfirmed.
    :evidence-incomplete            ticket-4 again, this time a repair
                                    completion attempted with no
                                    jurisdiction assessment on file at
                                    all.
    :already-completed              ticket-1's repair, completed twice.
    :already-returned               ticket-1's item, returned twice.

  Returns `{:db store :audit [run-level audit facts]}`. The two are NOT
  the same trail, and the page shows both: the store ledger is the
  durable SSoT record (`:committed` / `:governor-hold` only), while the
  graph's `:audit` channel additionally carries the advisor traces and
  the `:approval-requested` / `:approval-granted` facts that the commit
  path never appends to the ledger."
  []
  (let [db (store/seed-db)
        actor (op/build db)
        runs (atom [])
        exec! (fn [tid request]
                (record-run! runs tid
                             (g/run* actor {:request request :context operator}
                                     {:thread-id tid})))
        approve! (fn [tid]
                   (record-run! runs tid
                                (g/run* actor {:approval {:status :approved :by "op-1"}}
                                        {:thread-id tid :resume? true})))]
    ;; ---- ticket-1: the one clean end-to-end lifecycle ----
    (exec! "t1-intake" {:op :ticket/intake :subject "ticket-1"
                        :patch {:id "ticket-1" :customer "Sakura Tanaka"}})

    (exec! "t1-assess" {:op :jurisdiction/assess :subject "ticket-1"})
    (approve! "t1-assess")

    (exec! "t1-safety" {:op :safety/screen :subject "ticket-1"})
    (approve! "t1-safety")

    (exec! "t1-brand" {:op :brand/screen :subject "ticket-1"})
    (approve! "t1-brand")

    (exec! "t1-complete" {:op :repair/complete :subject "ticket-1"})
    (approve! "t1-complete")

    (exec! "t1-return" {:op :item/return :subject "ticket-1"})
    (approve! "t1-return")

    ;; ---- the seven HARD holds ----
    (exec! "t2-assess" {:op :jurisdiction/assess :subject "ticket-2" :no-spec? true})

    (exec! "t3-assess" {:op :jurisdiction/assess :subject "ticket-3"})
    (approve! "t3-assess")
    (exec! "t3-complete" {:op :repair/complete :subject "ticket-3"})

    (exec! "t4-safety" {:op :safety/screen :subject "ticket-4"})
    (exec! "t4-complete" {:op :repair/complete :subject "ticket-4"})

    (exec! "t5-brand" {:op :brand/screen :subject "ticket-5"})

    (exec! "t1-complete-again" {:op :repair/complete :subject "ticket-1"})
    (exec! "t1-return-again" {:op :item/return :subject "ticket-1"})
    {:db db :audit (vec (mapcat second @runs))}))

;; ----------------------------- rendering -----------------------------

(defn- esc [v]
  (-> (str v)
      (str/replace "&" "&amp;")
      (str/replace "<" "&lt;")
      (str/replace ">" "&gt;")))

(defn- nm
  "Fully-qualified rendering for keywords, identity for everything else
  -- ledger `:basis` vectors legitimately hold both keywords and
  citation strings. Deliberately NOT `clojure.core/name`: that drops the
  namespace, collapsing `:jurisdiction/assess` and `:safety/screen` into
  bare `assess`/`screen` and making distinct ops look identical on the
  page."
  [v]
  (if (keyword? v) (subs (str v) 1) (str v)))

(defn- last-fact-for [ledger ticket-id]
  (last (filter #(= (:subject %) ticket-id) ledger)))

(defn- status-cell [ledger ticket-id]
  (let [f (last-fact-for ledger ticket-id)]
    (cond
      (nil? f) "<span class=\"muted\">no activity</span>"
      (= :committed (:t f)) "<span class=\"ok\">committed</span>"
      (= :approval-granted (:t f)) "<span class=\"ok\">approved &amp; committed</span>"
      (= :governor-hold (:t f))
      (str "<span class=\"critical\">HARD hold &middot; "
           (esc (nm (or (-> f :violations first :rule) :unknown))) "</span>")
      (= :approval-requested (:t f)) "<span class=\"warn\">awaiting approval</span>"
      :else "<span class=\"muted\">in progress</span>")))

(defn- lifecycle-cell [{:keys [repair-completed? item-returned?]}]
  (cond
    item-returned? "<span class=\"ok\">repaired &amp; returned</span>"
    repair-completed? "<span class=\"warn\">repaired, not yet returned</span>"
    :else "<span class=\"muted\">in repair</span>"))

(defn- branded-cell [{:keys [involves-branded-item? brand-authenticity-confirmed?]}]
  (cond
    (not involves-branded-item?) "<span class=\"muted\">not a branded item</span>"
    brand-authenticity-confirmed? "<span class=\"ok\">branded &middot; authenticity confirmed</span>"
    :else "<span class=\"critical\">branded &middot; authenticity UNCONFIRMED</span>"))

(defn- ticket-row [ledger {:keys [id customer item item-type jurisdiction] :as t}]
  (format "        <tr><td><code>%s</code></td><td>%s</td><td>%s</td><td><code>%s</code></td><td><code>%s</code></td><td>%s</td><td>%s</td><td>%s</td></tr>"
          (esc id) (esc customer) (esc item) (esc (nm item-type)) (esc jurisdiction)
          (branded-cell t)
          (lifecycle-cell t)
          (status-cell ledger id)))

(defn- money-row
  "Each ticket's own parts arithmetic, recomputed here through the SAME
  pure functions the governor uses -- this table is the check, not a
  transcription of it."
  [{:keys [id claimed-parts-cost parts-quantity parts-unit-price] :as t}]
  (let [recomputed (registry/compute-parts-cost t)
        ok? (registry/parts-cost-matches-claim? t)]
    (format "        <tr><td><code>%s</code></td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>"
            (esc id) (esc parts-quantity) (esc parts-unit-price)
            (esc recomputed) (esc claimed-parts-cost)
            (if ok?
              "<span class=\"ok\">matches</span>"
              "<span class=\"critical\">MISMATCH &middot; HARD hold</span>"))))

(defn- hard-hold-rows
  "One row per governor violation actually recorded in this run's
  ledger -- rule name, op, ticket and the governor's own detail
  string."
  [ledger]
  (for [f ledger
        :when (= :governor-hold (:t f))
        v (:violations f)]
    (format "        <tr><td><code>%s</code></td><td><code>%s</code></td><td><code>%s</code></td><td>%s</td><td>%s</td></tr>"
            (esc (nm (:rule v))) (esc (nm (:op f))) (esc (:subject f))
            (esc (:detail v)) (esc (:confidence f)))))

(defn- jurisdiction-row [[iso3 {:keys [name owner-authority legal-basis provenance
                                       required-evidence brand-owner-authority
                                       brand-legal-basis brand-provenance]}]]
  (format (str "        <tr><td><code>%s</code></td><td>%s</td>"
               "<td>%s<br><span class=\"muted\">%s</span><br><span class=\"muted\">%s</span></td>"
               "<td>%s<br><span class=\"muted\">%s</span><br><span class=\"muted\">%s</span></td>"
               "<td>%s</td></tr>")
          (esc iso3) (esc name)
          (esc legal-basis) (esc owner-authority) (esc provenance)
          (esc brand-legal-basis) (esc brand-owner-authority) (esc brand-provenance)
          (count required-evidence)))

(defn- registry-row [kind r]
  (format "        <tr><td>%s</td><td><code>%s</code></td><td><code>%s</code></td><td><code>%s</code></td><td>%s</td></tr>"
          (esc kind)
          (esc (get r "record_id")) (esc (get r "ticket_id"))
          (esc (get r "jurisdiction"))
          (if (get r "immutable")
            "<span class=\"ok\">immutable</span>"
            "<span class=\"warn\">mutable</span>")))

;; --- approver attribution, DERIVED (never asserted) ---

(def ^:private register-readers
  "op -> [register-label, (fn [db subject] -> the committed entry)].
  Used to walk back into the store and see, per approval, whether the
  approver key actually survived the write."
  {:jurisdiction/assess ["assessments"         (fn [db s] (store/assessment-of db s))]
   :safety/screen       ["safety-screenings"   (fn [db s] (store/safety-screening-of db s))]
   :brand/screen        ["brand-screenings"    (fn [db s] (store/brand-screening-of db s))]
   :repair/complete     ["completion-history"  (fn [db s] (first (filter #(= s (get % "ticket_id"))
                                                                        (store/completion-history db))))]
   :item/return         ["return-history"      (fn [db s] (first (filter #(= s (get % "ticket_id"))
                                                                        (store/return-history db))))]})

(defn- retained-approver
  "Whatever approver identity is ACTUALLY present in a committed
  register entry, or nil. Checks both the EDN keyword and the
  registry drafts' string-keyed shape, so a store that starts
  retaining attribution is picked up without editing this file."
  [entry]
  (when (map? entry)
    (or (:approved-by entry) (get entry "approved_by") (get entry "approved-by"))))

(defn- approval-facts
  "The `:approval-granted` facts from the graph's run-level audit
  channel. MEASURED, not assumed: this actor's commit path appends only
  `:committed` to the store ledger, so the approver's identity exists
  ONLY here and never reaches the SSoT."
  [audit]
  (filter #(= :approval-granted (:t %)) audit))

(defn- approver-attribution-rows
  "For every approval this run granted, read back the register that op
  actually wrote to and report whether the approver survived the write.
  Derived at render time on purpose: if `store/commit-record!` later
  retains `:payload` for the actuation effects, these rows flip to
  `retained in record` with no change here."
  [db audit]
  (for [f (approval-facts audit)
        :let [[label reader] (register-readers (:op f))
              entry (when reader (reader db (:subject f)))
              retained (retained-approver entry)]]
    (format "        <tr><td><code>%s</code></td><td><code>%s</code></td><td>%s</td><td><code>%s</code></td><td>%s</td></tr>"
            (esc (nm (:op f))) (esc (:subject f)) (esc (:by f))
            (esc (or label "—"))
            (if retained
              (str "<span class=\"ok\">retained in record &middot; " (esc retained) "</span>")
              "<span class=\"warn\">audit only — not retained in record</span>"))))

(defn- run-audit-row [{:keys [t op subject summary reason by confidence]}]
  (format "        <tr><td>%s</td><td><code>%s</code></td><td><code>%s</code></td><td>%s</td><td>%s</td></tr>"
          (esc (nm t)) (esc (nm (or op :n-a))) (esc subject)
          (esc (or summary (some-> reason nm) (some->> by (str "by ")) ""))
          (esc (or confidence ""))))

(defn- ledger-row [{:keys [t op subject disposition basis]}]
  (format "        <tr><td>%s</td><td><code>%s</code></td><td><code>%s</code></td><td>%s</td></tr>"
          (esc (nm t)) (esc (nm (or op :n-a))) (esc subject)
          (esc (or (some->> basis (map nm) (str/join ", ")) (some-> disposition nm) ""))))

(def ^:private action-gate-rows
  ;; Static description of this actor's own closed op contract
  ;; (`leathergoods.phase/phases`, `leathergoods.governor/high-stakes`)
  ;; -- documentation of fixed behaviour, not runtime telemetry.
  ["        <tr><td><code>:ticket/intake</code></td><td><span class=\"ok\">phase-3 auto-commit when governor-clean &middot; no capital risk</span></td></tr>"
   "        <tr><td><code>:jurisdiction/assess</code></td><td><span class=\"warn\">phase-3: human approval (not auto-eligible)</span></td></tr>"
   "        <tr><td><code>:safety/screen</code></td><td><span class=\"warn\">phase-3: human approval &middot; never auto-eligible at any phase</span></td></tr>"
   "        <tr><td><code>:brand/screen</code></td><td><span class=\"warn\">phase-3: human approval &middot; never auto-eligible at any phase</span></td></tr>"
   "        <tr><td><code>:repair/complete</code></td><td><span class=\"warn\">ALWAYS human approval &middot; never auto at any phase &middot; parts cost independently recomputed</span></td></tr>"
   "        <tr><td><code>:item/return</code></td><td><span class=\"warn\">ALWAYS human approval &middot; never auto at any phase &middot; safety test &amp; brand authenticity re-checked</span></td></tr>"])

(defn render
  "Renders the full operator-console.html document from a store `db`
  that has already run `run-demo!` (or any other real scenario)."
  [{:keys [db audit]}]
  (let [ledger (vec (store/ledger db))
        tickets (store/all-tickets db)
        holds (filter #(= :governor-hold (:t %)) ledger)
        distinct-rules (->> holds (mapcat :violations) (map :rule) distinct sort)
        cov (facts/coverage (distinct (map :jurisdiction tickets)))]
    (str
     "<html lang=\"en\"><head><meta charset=\"utf-8\">"
     "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
     "<title>cloud-itonami-isic-9523 &middot; footwear and leather goods repair</title><style>"
     (jp-go-dds.skin/dds+skin)
     "</style></head><body>\n"
     "<header class=\"bar\">\n"
     "  <h1>Repair of footwear and leather goods (ISIC 9523) — Operator Console</h1>\n"
     "  <span class=\"badge\">read-only sample · governor-gated · repair completion / item return always human-approved</span>\n"
     "</header>\n"
     "<main>\n"

     ;; ---- 1. tickets ----
     "  <section class=\"card\">\n"
     "    <h2>Repair tickets</h2>\n"
     "    <p class=\"muted\">Demo snapshot — build-time-generated from <code>leathergoods.store/demo-data</code> by driving the real actor graph (<code>leathergoods.operation</code> → <code>leathergoods.governor</code> → <code>leathergoods.store</code>) via <code>clojure -M:dev:render-html</code>.</p>\n"
     "    <p class=\"muted\"><strong>Provenance note:</strong> <code>Item</code> and <code>Item type</code> are free-text / declared fields on each seeded ticket record. This repo has <em>no</em> item, leather-species or material taxonomy, so these trace to the ticket's own declared seed value and <em>not</em> to any lookup table. <code>Jurisdiction</code>, by contrast, is looked up in <code>leathergoods.facts/catalog</code> — a jurisdiction absent from that table has no spec-basis at all (see below).</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Ticket</th><th>Customer</th><th>Item</th><th>Item type</th><th>Jurisdiction</th><th>Brand authenticity</th><th>Repair/return status</th><th>Last op status</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" (map (partial ticket-row ledger) tickets)) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"

     ;; ---- 2. action gate ----
     "  <section class=\"card\">\n"
     "    <h2>Action gate (Repair Shop Governor)</h2>\n"
     "    <p class=\"muted\">Seven HARD rules, none of which a human approver can override. Two independent layers — <code>leathergoods.governor</code>'s high-stakes set and <code>leathergoods.phase</code>'s per-phase <code>:auto</code> set — separately guarantee that completing a repair and returning an item are always a human call.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Op</th><th>Gate</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" action-gate-rows) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"

     ;; ---- 3. HARD holds actually produced ----
     "  <section class=\"card\">\n"
     "    <h2>HARD governor holds (this run)</h2>\n"
     "    <p class=\"muted\">"
     (count holds) " hold" (when (not= 1 (count holds)) "s") " covering "
     (count distinct-rules) " distinct HARD rules: <code>"
     (esc (str/join "</code>, <code>" (map nm distinct-rules)))
     "</code>. Every row below is read out of this run's append-only ledger — rule name, op, ticket and detail string are the governor's own output. None of these reached a human: a HARD violation short-circuits the approval node entirely.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Rule</th><th>Op</th><th>Ticket</th><th>Governor detail</th><th>Advisor confidence</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" (hard-hold-rows ledger)) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"

     ;; ---- 4. parts-cost independent recompute ----
     "  <section class=\"card\">\n"
     "    <h2>Independent parts-cost recompute</h2>\n"
     "    <p class=\"muted\">The claimed parts cost is never trusted from the proposal. Each row below is computed here by calling the same <code>leathergoods.registry/compute-parts-cost</code> and <code>parts-cost-matches-claim?</code> the governor calls — compared at money precision (1/10000 of a unit), not on raw doubles.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Ticket</th><th>Parts qty</th><th>Unit price</th><th>Recomputed</th><th>Claimed</th><th>Verdict</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" (map money-row tickets)) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"

     ;; ---- 5. jurisdiction catalog ----
     "  <section class=\"card\">\n"
     "    <h2>Jurisdiction spec-basis catalog</h2>\n"
     "    <p class=\"muted\">Every entry cites <strong>two separate</strong> statutory regimes: consumer product safety, and trademark / anti-counterfeiting (this domain routinely repairs branded goods — designer handbags, branded footwear). Rendered directly from <code>leathergoods.facts/catalog</code>.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>ISO3</th><th>Jurisdiction</th><th>Product-safety basis</th><th>Brand-authenticity basis</th><th>Required evidence items</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" (map jurisdiction-row (sort-by key facts/catalog))) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "    <p class=\"muted\"><strong>Honest coverage over the jurisdictions actually present in this ticket set:</strong> "
     (:requested cov) " requested, " (:covered cov) " covered ("
     (esc (str/join ", " (:covered-jurisdictions cov))) ")"
     (when (seq (:missing-jurisdictions cov))
       (str ", " (count (:missing-jurisdictions cov)) " with <strong>no spec-basis at all</strong> ("
            (esc (str/join ", " (:missing-jurisdictions cov)))
            "). A jurisdiction missing from the catalog is held, never guessed."))
     " " (esc (:note cov)) "</p>\n"
     "  </section>\n"

     ;; ---- 6. registry drafts ----
     "  <section class=\"card\">\n"
     "    <h2>Registry drafts produced</h2>\n"
     "    <p class=\"muted\">Append-only, jurisdiction-scoped draft records. There is no international check-digit standard for a repair-completion or item-return reference number, so this actor does not invent one — it assigns a jurisdiction-scoped sequence. Every certificate it produces is <strong>unsigned</strong>: signing is the repair shop's act, not this actor's.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Kind</th><th>Record id</th><th>Ticket</th><th>Jurisdiction</th><th>Immutability</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" (concat (map (partial registry-row "repair-completion") (store/completion-history db))
                            (map (partial registry-row "item-return") (store/return-history db)))) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"

     ;; ---- 7. approver attribution (derived) ----
     "  <section class=\"card\">\n"
     "    <h2>Approver attribution</h2>\n"
     "    <p class=\"muted\">Derived at render time, not asserted: for every approval this run granted, the register that op wrote to is read back and checked for an approver key. <em>Retained in record</em> means the approver survived the store write and is queryable from the SSoT. <em>Audit only</em> means the approval is provable from the run's audit trail but the register itself did not keep it — labelled explicitly, because silently omitting it would make “nobody approved” indistinguishable from “the store didn't keep it”. These rows self-correct if store retention changes.</p>\n"
     "    <p class=\"muted\"><strong>Measured on this run:</strong> the approver's identity reaches the <em>graph audit channel</em> only. <code>leathergoods.operation</code>'s <code>:commit</code> node appends just a <code>:committed</code> fact to the store ledger, so <code>op-1</code> appears nowhere in the durable ledger below — the "
     (count (approval-facts audit)) " approvals in this run are recoverable only from the run-level trail.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Op</th><th>Ticket</th><th>Approved by (audit fact)</th><th>Register written</th><th>Attribution in register</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" (approver-attribution-rows db audit)) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"

     ;; ---- 7b. run-level audit channel ----
     "  <section class=\"card\">\n"
     "    <h2>Graph audit channel (this run)</h2>\n"
     "    <p class=\"muted\">The actor's own <code>:audit</code> channel — strictly richer than the durable ledger. It carries every advisor proposal trace, every <code>:approval-requested</code> pause and every <code>:approval-granted</code> resume. " (count audit) " facts, versus " (count ledger) " in the store ledger.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Fact</th><th>Op</th><th>Ticket</th><th>Summary / reason</th><th>Confidence</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" (map run-audit-row audit)) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"

     ;; ---- 8. ledger ----
     "  <section class=\"card\">\n"
     "    <h2>Audit ledger (this run)</h2>\n"
     "    <p class=\"muted\">Append-only decision-fact log — every commit and hold this scenario produced, in order. " (count ledger) " facts.</p>\n"
     "    <table>\n"
     "      <thead><tr><th>Fact</th><th>Op</th><th>Ticket</th><th>Basis</th></tr></thead>\n"
     "      <tbody>\n"
     (str/join "\n" (map ledger-row ledger)) "\n"
     "      </tbody>\n"
     "    </table>\n"
     "  </section>\n"
     "</main>\n"
     "</body></html>\n")))

(defn -main [& args]
  (let [out (or (first args) "docs/samples/operator-console.html")
        {:keys [db] :as result} (run-demo!)
        ledger (vec (store/ledger db))
        holds (filter #(= :governor-hold (:t %)) ledger)
        rules (->> holds (mapcat :violations) (map :rule) distinct sort)]
    ;; Build-time invariant, not a convention: a console that shows no
    ;; HARD hold has not demonstrated that the governor can refuse the
    ;; advisor, which is the entire point of this page. Fail the build.
    (when (zero? (count holds))
      (throw (ex-info "render-html: scenario produced ZERO :governor-hold records -- refusing to write a console that does not demonstrate a HARD hold"
                      {:ledger-facts (count ledger)
                       :out out})))
    (let [html (render result)]
      (.mkdirs (java.io.File. (or (.getParent (java.io.File. ^String out)) ".")))
      (spit out html)
      (println "wrote" out "-" (count ledger) "ledger facts,"
               (count holds) "HARD holds over" (count rules) "distinct rules"
               (pr-str (vec rules)) ","
               (count (store/completion-history db)) "repair completions,"
               (count (store/return-history db)) "item returns"))))
