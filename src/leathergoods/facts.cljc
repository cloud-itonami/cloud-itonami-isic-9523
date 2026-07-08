(ns leathergoods.facts
  "Per-jurisdiction consumer-product-safety AND trademark/anti-
  counterfeiting regulatory catalog -- the G2-style spec-basis table
  the Repair Shop Governor checks every `:jurisdiction/assess` proposal
  against ('did the advisor cite an OFFICIAL public source for this
  jurisdiction's requirements, or did it invent one?'), closely modeled
  on `cloud-itonami-isic-9529`'s `specialtyrepair.facts`.

  This blueprint's own named activities (shoes, bags, belts and
  similar leather goods) routinely include repairing branded/
  trademarked items (designer handbags, branded footwear) -- a real,
  distinct regulatory concern beyond `repairshop`/9521's, `commrepair`/
  9512's, `applianceshop`/9522's and `furniture`/9524's own catalogs,
  and structurally different from `specialtyrepair`/9529's own
  precious-metal-hallmark-integrity concern: repairing or altering a
  branded good that turns out to be counterfeit can expose a repair
  shop to trademark-infringement/counterfeit-trafficking liability
  (the US Lanham Act's trademark-counterfeiting provisions, the UK's
  Trade Marks Act 1994 criminal offenses for unauthorized use of a
  mark in the course of business, Germany's Markengesetz criminal
  provisions, and Japan's own 商標法 (Trademark Act) criminal
  penalties are all real, well-documented regimes). Each jurisdiction
  entry below therefore cites BOTH the consumer-product-safety law
  this fleet's repair-shop catalogs already model AND a SEPARATE
  brand-authenticity/anti-counterfeiting law.

  Unlike every prior repair-shop-cluster sibling's own honesty-gap
  discipline (which always found Japan honestly missing a sub-
  citation), ALL FOUR seeded jurisdictions actually have a real,
  well-documented trademark/anti-counterfeiting enforcement regime --
  reported honestly here too: coverage is not artificially forced
  narrower than it actually is, the same honesty discipline this
  fleet applies in both directions.

  Coverage is reported HONESTLY (see `coverage`), the same discipline
  every sibling actor's `facts` namespace uses: a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  diagnostic-report/parts-used-documentation/post-repair-safety-test-
  record evidence set (PLUS a brand-authenticity record for every
  seeded jurisdiction, since all four actually have a trademark/anti-
  counterfeiting enforcement regime); `:legal-basis` / `:owner-
  authority` / `:provenance` are the G2 citation the governor requires
  before any `:jurisdiction/assess` proposal can commit.
  `:brand-owner-authority` / `:brand-legal-basis` / `:brand-
  provenance` are the SEPARATE brand-authenticity/anti-counterfeiting
  citation the governor's `brand-authenticity-unconfirmed?` check is
  grounded in."
  {"JPN" {:name "Japan"
          :owner-authority "経済産業省 (Ministry of Economy, Trade and Industry, METI)"
          :legal-basis "消費生活用製品安全法 (Consumer Product Safety Act)"
          :national-spec "身の回り品修理に関する一般消費生活用製品安全基準"
          :provenance "https://www.meti.go.jp/product_safety/"
          :required-evidence ["故障診断書 (diagnostic report)"
                              "使用部品記録 (parts-used documentation)"
                              "修理後安全試験記録 (post-repair safety-test record)"
                              "ブランド真贋確認記録 (brand-authenticity record)"]
          :brand-owner-authority "特許庁 (Japan Patent Office, JPO) / 税関 (Japan Customs)"
          :brand-legal-basis "商標法 (Trademark Act) Article 78 (counterfeit-goods criminal penalties); 関税法 (Customs Act) Article 69-11 (import prohibition on counterfeit goods)"
          :brand-provenance "https://www.jpo.go.jp/e/system/laws/rule/trademark/index.html"}
   "USA" {:name "United States"
          :owner-authority "U.S. Consumer Product Safety Commission (CPSC)"
          :legal-basis "Consumer Product Safety Act (15 U.S.C. §§2051 et seq.)"
          :national-spec "CPSC product-safety standards for personal/household goods"
          :provenance "https://www.cpsc.gov/Regulations-Laws--Standards/Statutes"
          :required-evidence ["Diagnostic report"
                              "Parts-used documentation"
                              "Post-repair safety-test record"
                              "Brand-authenticity record"]
          :brand-owner-authority "U.S. Patent and Trademark Office (USPTO) / Homeland Security Investigations (HSI) National IPR Coordination Center"
          :brand-legal-basis "Lanham Act (15 U.S.C. §1114, §1125); Trademark Counterfeiting Act (18 U.S.C. §2320, trafficking in counterfeit goods)"
          :brand-provenance "https://www.uspto.gov/trademarks/protect"}
   "GBR" {:name "United Kingdom"
          :owner-authority "Office for Product Safety and Standards (OPSS)"
          :legal-basis "General Product Safety Regulations 2005"
          :national-spec "OPSS product-safety enforcement standards for personal/household goods"
          :provenance "https://www.gov.uk/government/organisations/office-for-product-safety-and-standards"
          :required-evidence ["Diagnostic report"
                              "Parts-used documentation"
                              "Post-repair safety-test record"
                              "Brand-authenticity record"]
          :brand-owner-authority "UK Intellectual Property Office (IPO) / National Trading Standards"
          :brand-legal-basis "Trade Marks Act 1994, Section 92 (unauthorized use of a trademark in the course of business)"
          :brand-provenance "https://www.gov.uk/guidance/counterfeiting-and-piracy"}
   "DEU" {:name "Germany"
          :owner-authority "Marktüberwachungsbehörden der Länder"
          :legal-basis "Produktsicherheitsgesetz (ProdSG)"
          :national-spec "ProdSG Marktüberwachungsanforderungen für Gebrauchsgegenstände"
          :provenance "https://www.baua.de/DE/Themen/Anwendungssichere-Chemikalien-und-Produkte/Produktsicherheit/Produktsicherheit_node.html"
          :required-evidence ["Diagnosebericht (diagnostic report)"
                              "Ersatzteilnachweis (parts-used documentation)"
                              "Sicherheitsprüfungsprotokoll nach Reparatur (post-repair safety-test record)"
                              "Markenauthentizitätsnachweis (brand-authenticity record)"]
          :brand-owner-authority "Deutsches Patent- und Markenamt (DPMA) / Zollverwaltung (German Customs)"
          :brand-legal-basis "Markengesetz (MarkenG) §143 (Straftaten, criminal trademark-infringement provisions); EU Regulation 608/2013 (customs enforcement of IP rights)"
          :brand-provenance "https://www.dpma.de/marken/index.html"}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to complete a
  repair or return an item on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-isic-9523 R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog, not a survey of all ~194 "
                 "jurisdictions -- extend `leathergoods.facts/catalog`, "
                 "never fabricate a jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn brand-spec-basis
  "The jurisdiction's brand-authenticity/anti-counterfeiting
  requirement map, or nil -- nil means this jurisdiction has NO formal
  statutory trademark/anti-counterfeiting enforcement regime this
  catalog is aware of. In this R0 catalog all four seeded
  jurisdictions actually have one (unlike prior siblings' own honest
  single-jurisdiction gaps), reported honestly."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:brand-owner-authority sb)
      (select-keys sb [:brand-owner-authority :brand-legal-basis :brand-provenance]))))
