# ADR-0001: RepairOps-LLM ⊣ Repair Shop Governor architecture

## Status

Accepted. `cloud-itonami-isic-9523` promoted from `:blueprint` to
`:implemented` in the `kotoba-lang/industry` registry. This build also
completes the entire `cloud-itonami/cloud-itonami-isic-*` blueprint
fleet -- zero `:blueprint`-tier candidates remain in that org/prefix
scope after this promotion.

## Context

`cloud-itonami-isic-9523` publishes an OSS business blueprint for
repair of footwear and leather goods. Like every prior actor in this
fleet, the blueprint alone is not an implementation: this ADR records
the governed-actor architecture that promotes it to real, tested code,
following the same langgraph StateGraph + independent Governor +
Phase 0→3 rollout pattern established by `cloud-itonami-isic-6511`
(life insurance) and applied across eighty-three prior siblings, most
recently `cloud-itonami-isic-9529` (repair of other personal and
household goods).

This blueprint's own `:itonami.blueprint/governor` keyword,
`:repair-shop-governor`, is IDENTICAL to `repairshop`/9521's (consumer
electronics), `commrepair`/9512's (communication equipment),
`applianceshop`/9522's (household appliances), `furniture`/9524's
(furniture and home furnishings) and `specialtyrepair`/9529's
(watches, jewelry, bicycles). Per the fleet-wide governor-name-reuse
precedent `commrepair`/9512's own ADR-0001 established -- confirmed
seven times since across three other governor-name families --
sharing a governor name is acceptable when the underlying business
archetype is genuinely the same, provided the reuse is documented and
the new build brings its own genuinely differentiated, well-grounded
check. This build is the NINTH confirmation overall, and the THIRD
time the precedent applies within the ORIGINAL `:repair-shop-governor`
family -- now for a FIFTH sibling.

## Decision

### Decision 1: governor-name reuse -- ninth confirmation, fifth sibling in the original family

`repairshop`/9521, `commrepair`/9512, `applianceshop`/9522,
`furniture`/9524, `specialtyrepair`/9529 and `leathergoods`/9523 all
share the IDENTICAL `:repair-shop-governor` keyword -- a deliberate,
honest reuse of the same repair-shop business archetype (diagnose,
quote/assess, repair, return) applied to a different repair-item
category each time (consumer electronics, communication equipment,
household appliances, furniture and home furnishings, watches/
jewelry/bicycles, and now footwear/leather goods). This is the same
reasoning `commrepair`/9512's, `applianceshop`/9522's, `furniture`/
9524's and `specialtyrepair`/9529's own ADR-0001s established, now
confirmed a FIFTH time within this specific family.

### Decision 2: dual-actuation shape

This blueprint's own README, business-model.md and operator-guide.md
consistently name two real-world acts: "performing a repair or
returning an item to the customer." Matching `repairshop`/9521's,
`commrepair`/9512's, `applianceshop`/9522's, `furniture`/9524's and
`specialtyrepair`/9529's own dual-actuation shape, `high-stakes` here
is a two-member set, `#{:actuation/complete-repair :actuation/return-item}`.

### Decision 3: `parts-cost-matches-claim?` and `safety-test-not-passed?` -- honest, literal reuses

`leathergoods.registry/parts-cost-matches-claim?` and `leathergoods.
governor/safety-test-not-passed-violations` are HONEST, LITERAL reuses
of `repairshop.registry`'s/`commrepair.registry`'s/`applianceshop.
registry`'s/`furniture.registry`'s/`specialtyrepair.registry`'s own
EXACT-MATCH independent-recompute checks -- NOT claimed as new. A
footwear/leather-goods-repair ticket's own claimed parts cost (soles,
heels, leather patches, hardware, zippers) against quantity-times-
unit-price, and a post-repair safety test (e.g. a repaired shoe sole/
heel must not detach under normal wear), are the SAME real-world
concerns as `specialtyrepair`/9529's own checks, reapplied to a
different repair-item category.

### Decision 4: entity and op shape

The primary entity is a `ticket`. Six ops: `:ticket/intake` (directory
upsert, no capital risk), `:jurisdiction/assess` (per-jurisdiction
consumer-product-safety/brand-authenticity evidence checklist, never
auto), `:safety/screen` (post-repair safety screening, honest reuse,
never auto), `:brand/screen` (brand-authenticity screening, GENUINELY
NEW, never auto), `:repair/complete` (POSITIVE, high-stakes), and
`:item/return` (POSITIVE, high-stakes).

### Decision 5: `brand-authenticity-unconfirmed?` -- the 69th unconditional-evaluation grounding, a genuinely new PROVENANCE/ANTI-COUNTERFEITING concept, the SIXTH conditional variant

Before writing this check, every prior sibling's governor namespace
across the entire fleet was grepped for any check function named
`authenticity`, `counterfeit` or `brand-authentic` -- zero hits,
confirming this is a genuinely new concept.
`brand-authenticity-unconfirmed-violations` reuses the unconditional-
evaluation-screening DISCIPLINE (`casualty.governor/sanctions-
violations`'s original fix) for the 69th distinct application overall
(most recently `specialtyrepair.governor/hallmark-integrity-
unconfirmed-violations` at 68th). This is the SIXTH conditional
variant (after `socialresearch`/7220's, `bizassoc`/9411's, `training`/
8549's, `furniture`/9524's and `specialtyrepair`/9529's own, at 63rd,
64th, 66th, 67th and 68th) -- CONDITIONAL on the ticket's own
`:involves-branded-item? true` ground truth: a generic leather belt or
boot repair has no brand-authenticity concern at all.

Unlike every prior check in this discipline -- which verifies either a
subject-fact (a study, a position, a ticket), an assessor-credential
fact (`training`/8549's own instructor-license check), or a material-
composition-integrity fact (`specialtyrepair`/9529's own hallmark-
integrity check) -- this check verifies whether the ITEM's own
provenance/authenticity has been confirmed before repairing or
returning a branded good, a FOURTH structurally distinct category:
provenance/anti-counterfeiting verification. Grounded in real
trademark law: the US Lanham Act (15 U.S.C. §1114/§1125) and Trademark
Counterfeiting Act (18 U.S.C. §2320, trafficking in counterfeit
goods), the UK's Trade Marks Act 1994 Section 92 (criminal unauthorized
use of a mark in the course of business), Germany's Markengesetz §143
(criminal trademark-infringement provisions), and Japan's own 商標法
(Trademark Act) Article 78. Unlike every prior repair-shop-cluster
sibling's own honest single-jurisdiction gap, ALL FOUR seeded
jurisdictions actually have a real trademark/anti-counterfeiting
enforcement regime here, reported honestly -- the same honesty
discipline that forbids fabricating coverage also forbids under-
reporting it.

### Decision 6: dedicated double-actuation-guard booleans

`:repair-completed?`/`:item-returned?` are dedicated booleans on the
`ticket` record, never a single `:status` value -- an honest, literal
reuse of `specialtyrepair.governor`'s own guards, informed by `cloud-
itonami-isic-6492`'s real status-lifecycle bug (ADR-2607071320).

### Decision 7: Store protocol, MemStore + DatomicStore parity

`leathergoods.store/Store` is implemented by both `MemStore` (atom-
backed, default for dev/tests/demo) and `DatomicStore` (`langchain.
db`-backed), proven to satisfy the same contract in
`test/leathergoods/store_contract_test.clj` -- the same seam every
sibling actor uses so swapping the SSoT backend is a configuration
change, not a rewrite.

### Decision 8: Phase 0→3 rollout

Phase 3's `:auto` set has exactly one member, `:ticket/intake` (no
capital risk). `:jurisdiction/assess`, `:safety/screen` and `:brand/
screen` are never auto-eligible at any phase (matching every sibling's
screening-op posture), and `:repair/complete`/`:item/return` are
permanently excluded from every phase's `:auto` set -- a structural
fact, not a rollout milestone, enforced by BOTH `leathergoods.phase`
and `leathergoods.governor`'s `high-stakes` set independently.

### Decision 9: no bespoke domain capability lib, and no `blueprint.edn` field-sync fixes needed

This blueprint's own `:itonami.blueprint/required-technologies` names
no domain-specific capability beyond the generic robotics/identity/
forms/dmn/bpmn/audit-ledger stack -- there was no capability-lib
decision to make at all. This repo's `blueprint.edn` already had the
correct `isic-` prefixed `:id` and correctly populated `:required-
technologies`/`:optional-technologies` matching the `kotoba-lang/
industry` registry's own entry for `"9523"` exactly -- only the
`:maturity` field itself needed adding.

### Decision 10: mock + LLM advisor pair

`leathergoods.repairopsllm` provides `mock-advisor` (deterministic,
default everywhere -- the actor graph and governor contract run
offline) and `llm-advisor` (backed by `langchain.model/ChatModel`,
with a defensive EDN-proposal parser so a malformed LLM response
degrades to a safe low-confidence noop rather than ever auto-
completing a repair or auto-returning an item).

## Alternatives considered

- **An unconditional brand-authenticity check** (applying to every
  ticket regardless of whether the repair actually touches a branded/
  trademarked item). Rejected: a generic leather belt/boot repair has
  no brand-authenticity concern at all -- forcing the check onto every
  ticket would fabricate a requirement.
- **Fabricating a jurisdiction gap** to match the pattern of prior
  siblings' own single-jurisdiction honesty gap. Rejected: the same
  honesty discipline that forbids fabricating coverage also forbids
  under-reporting it -- all four seeded jurisdictions genuinely have a
  real trademark/anti-counterfeiting regime, and reporting anything
  less would itself be dishonest.
- **A CITES/endangered-species-leather compliance check** (for exotic
  leathers like crocodile/snake skin). Considered and rejected in
  favor of brand-authenticity: `cloud-itonami-isic-9103`'s own
  `conservation.facts` already cites CITES as a spec-basis for a
  different business context (live-specimen transport/welfare
  permits), so reusing the same underlying international treaty here
  risked reading as a rehash rather than a genuinely distinct concept;
  brand-authenticity/anti-counterfeiting was grep-verified as
  completely unclaimed fleet-wide.
- **Framing the new check as another material-composition-integrity
  check**, reusing `specialtyrepair`/9529's own structural category.
  Rejected: the brand-authenticity concern is about the item's
  provenance/legal origin (is it a genuine trademarked good), not its
  physical material composition or purity marking -- a genuinely
  distinct fourth structural category.
- **Declining the build and treating the repair-shop cluster as
  exhausted at four siblings.** Rejected: `commrepair`/9512's,
  `applianceshop`/9522's, `furniture`/9524's and `specialtyrepair`/
  9529's own ADR-0001s already established the precedent generalizes;
  extending it to a fifth sibling confirms this rather than treating
  four instances as a ceiling.

## Consequences

- Eighty-fifth actor in this fleet (84 implemented before this
  build).
- Confirms the fleet-wide governor-name-reuse precedent a ninth
  time, and confirms it generalizes to a FIFTH sibling within the
  original `:repair-shop-governor` family -- reinforcing it as a
  durable, scalable fleet-wide pattern.
- Establishes a genuinely NEW conditional unconditional-evaluation-
  screening concept (brand-authenticity-unconfirmed?), the first
  provenance/anti-counterfeiting check in the discipline (distinct
  from subject-fact, assessor-credential and material-composition-
  integrity checks), grep-verified absent from every prior sibling
  before the claim was finalized.
- `MemStore` ‖ `DatomicStore` parity is proven by
  `test/leathergoods/store_contract_test.clj`, the same `:db-api`-
  driven swap pattern every sibling actor uses.
- 40 tests / 192 assertions pass; lint is clean; the demo
  (`clojure -M:dev:run`) walks one clean dual-actuation lifecycle plus
  five HARD-hold scenarios end-to-end.
- `blueprint.edn` required no field-sync fixes this time (already
  correct) -- only the `:maturity` flip itself.
- **This completes the entire `cloud-itonami/cloud-itonami-isic-*`
  blueprint fleet**: zero `:blueprint`-tier candidates remain in that
  specific org/prefix scope after this promotion (13 unrelated
  `:blueprint`-tier entries remain under the separate `gftdcojp/
  cloud-itonami-*` "Community" fleet, out of this scope).

## References

- `cloud-itonami-isic-9521/docs/adr/0001-architecture.md` (origin of
  the dual-actuation Repair Shop Governor shape)
- `cloud-itonami-isic-9512/docs/adr/0001-architecture.md` (origin of
  the governor-name-reuse precedent)
- `cloud-itonami-isic-9529/docs/adr/0001-architecture.md` (most recent
  prior sibling in this family)
- Lanham Act, 15 U.S.C. §1114/§1125; Trademark Counterfeiting Act,
  18 U.S.C. §2320 (US)
- Trade Marks Act 1994, Section 92 (UK)
- Markengesetz (MarkenG) §143 (Germany)
- 商標法 (Trademark Act) Article 78; 関税法 (Customs Act) Article 69-11 (Japan)
