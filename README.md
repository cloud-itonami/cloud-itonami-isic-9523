# cloud-itonami-isic-9523

Open Business Blueprint for **ISIC Rev.5 9523**: Repair of footwear
and leather goods.

This repository publishes a footwear-and-leather-goods-repair actor --
item intake, per-jurisdiction consumer-product-safety/brand-
authenticity regulatory assessment, post-repair safety screening,
brand-authenticity screening, repair completion and item return -- as
an OSS business that any qualified operator can fork, deploy, run,
improve and sell, so a community or independent provider never
surrenders customer data and ledgers to a closed SaaS.

Built on this workspace's
[`langgraph`](https://github.com/kotoba-lang/langgraph)
StateGraph runtime (portable `.cljc`, supervised superstep loop,
interrupts, Datomic/in-mem checkpoints) -- the same actor pattern as
every prior actor in this fleet
([`cloud-itonami-isic-6511`](https://github.com/cloud-itonami/cloud-itonami-isic-6511),
[`6512`](https://github.com/cloud-itonami/cloud-itonami-isic-6512),
[`6621`](https://github.com/cloud-itonami/cloud-itonami-isic-6621),
[`6622`](https://github.com/cloud-itonami/cloud-itonami-isic-6622),
[`6629`](https://github.com/cloud-itonami/cloud-itonami-isic-6629),
[`6520`](https://github.com/cloud-itonami/cloud-itonami-isic-6520),
[`6530`](https://github.com/cloud-itonami/cloud-itonami-isic-6530),
[`6820`](https://github.com/cloud-itonami/cloud-itonami-isic-6820),
[`6612`](https://github.com/cloud-itonami/cloud-itonami-isic-6612),
[`6492`](https://github.com/cloud-itonami/cloud-itonami-isic-6492),
[`6920`](https://github.com/cloud-itonami/cloud-itonami-isic-6920),
[`6611`](https://github.com/cloud-itonami/cloud-itonami-isic-6611),
[`7120`](https://github.com/cloud-itonami/cloud-itonami-isic-7120),
[`8620`](https://github.com/cloud-itonami/cloud-itonami-isic-8620),
[`8530`](https://github.com/cloud-itonami/cloud-itonami-isic-8530),
[`9200`](https://github.com/cloud-itonami/cloud-itonami-isic-9200),
[`7500`](https://github.com/cloud-itonami/cloud-itonami-isic-7500),
[`9603`](https://github.com/cloud-itonami/cloud-itonami-isic-9603),
[`9521`](https://github.com/cloud-itonami/cloud-itonami-isic-9521),
[`9321`](https://github.com/cloud-itonami/cloud-itonami-isic-9321),
[`8730`](https://github.com/cloud-itonami/cloud-itonami-isic-8730),
[`9102`](https://github.com/cloud-itonami/cloud-itonami-isic-9102),
[`9103`](https://github.com/cloud-itonami/cloud-itonami-isic-9103),
[`9602`](https://github.com/cloud-itonami/cloud-itonami-isic-9602),
[`9000`](https://github.com/cloud-itonami/cloud-itonami-isic-9000),
[`8890`](https://github.com/cloud-itonami/cloud-itonami-isic-8890),
[`8610`](https://github.com/cloud-itonami/cloud-itonami-isic-8610),
[`9311`](https://github.com/cloud-itonami/cloud-itonami-isic-9311),
[`8510`](https://github.com/cloud-itonami/cloud-itonami-isic-8510),
[`9412`](https://github.com/cloud-itonami/cloud-itonami-isic-9412),
[`6491`](https://github.com/cloud-itonami/cloud-itonami-isic-6491),
[`8720`](https://github.com/cloud-itonami/cloud-itonami-isic-8720),
[`8521`](https://github.com/cloud-itonami/cloud-itonami-isic-8521),
[`6619`](https://github.com/cloud-itonami/cloud-itonami-isic-6619),
[`3600`](https://github.com/cloud-itonami/cloud-itonami-isic-3600),
[`6190`](https://github.com/cloud-itonami/cloud-itonami-isic-6190),
[`3030`](https://github.com/cloud-itonami/cloud-itonami-isic-3030),
[`3830`](https://github.com/cloud-itonami/cloud-itonami-isic-3830),
[`7020`](https://github.com/cloud-itonami/cloud-itonami-isic-7020),
[`9420`](https://github.com/cloud-itonami/cloud-itonami-isic-9420),
[`9491`](https://github.com/cloud-itonami/cloud-itonami-isic-9491),
[`2610`](https://github.com/cloud-itonami/cloud-itonami-isic-2610),
[`3512`](https://github.com/cloud-itonami/cloud-itonami-isic-3512),
[`8810`](https://github.com/cloud-itonami/cloud-itonami-isic-8810),
[`8691`](https://github.com/cloud-itonami/cloud-itonami-isic-8691),
[`8569`](https://github.com/cloud-itonami/cloud-itonami-isic-8569),
[`6419`](https://github.com/cloud-itonami/cloud-itonami-isic-6419),
[`7310`](https://github.com/cloud-itonami/cloud-itonami-isic-7310),
[`7320`](https://github.com/cloud-itonami/cloud-itonami-isic-7320),
[`7210`](https://github.com/cloud-itonami/cloud-itonami-isic-7210),
[`7410`](https://github.com/cloud-itonami/cloud-itonami-isic-7410),
[`8710`](https://github.com/cloud-itonami/cloud-itonami-isic-8710),
[`8541`](https://github.com/cloud-itonami/cloud-itonami-isic-8541),
[`8690`](https://github.com/cloud-itonami/cloud-itonami-isic-8690),
[`9601`](https://github.com/cloud-itonami/cloud-itonami-isic-9601),
[`6420`](https://github.com/cloud-itonami/cloud-itonami-isic-6420),
[`7420`](https://github.com/cloud-itonami/cloud-itonami-isic-7420),
[`9609`](https://github.com/cloud-itonami/cloud-itonami-isic-9609),
[`8550`](https://github.com/cloud-itonami/cloud-itonami-isic-8550),
[`7010`](https://github.com/cloud-itonami/cloud-itonami-isic-7010),
[`8790`](https://github.com/cloud-itonami/cloud-itonami-isic-8790),
[`8542`](https://github.com/cloud-itonami/cloud-itonami-isic-8542),
[`6411`](https://github.com/cloud-itonami/cloud-itonami-isic-6411),
[`7490`](https://github.com/cloud-itonami/cloud-itonami-isic-7490),
[`9319`](https://github.com/cloud-itonami/cloud-itonami-isic-9319),
[`9329`](https://github.com/cloud-itonami/cloud-itonami-isic-9329),
[`9312`](https://github.com/cloud-itonami/cloud-itonami-isic-9312),
[`9492`](https://github.com/cloud-itonami/cloud-itonami-isic-9492),
[`9499`](https://github.com/cloud-itonami/cloud-itonami-isic-9499),
[`9512`](https://github.com/cloud-itonami/cloud-itonami-isic-9512),
[`9522`](https://github.com/cloud-itonami/cloud-itonami-isic-9522),
[`7220`](https://github.com/cloud-itonami/cloud-itonami-isic-7220),
[`9411`](https://github.com/cloud-itonami/cloud-itonami-isic-9411),
[`8522`](https://github.com/cloud-itonami/cloud-itonami-isic-8522),
[`8549`](https://github.com/cloud-itonami/cloud-itonami-isic-8549),
[`9524`](https://github.com/cloud-itonami/cloud-itonami-isic-9524),
[`9529`](https://github.com/cloud-itonami/cloud-itonami-isic-9529)) --
here it is **RepairOps-LLM ⊣ Repair Shop Governor** -- the SAME
governor name `repairshop`/9521 (consumer electronics), `commrepair`/
9512 (communication equipment), `applianceshop`/9522 (household
appliances), `furniture`/9524 (furniture and home furnishings) and
`specialtyrepair`/9529 (watches, jewelry, bicycles and similar
personal/household goods) already use, a deliberate, honest reuse of
the same repair-shop business archetype for a different repair-item
category (see `docs/adr/0001-architecture.md` Decision 1 for why this
is not a naming error, and for why this is the NINTH confirmation of
the fleet-wide governor-name-reuse precedent, and the THIRD time it
applies within the ORIGINAL `:repair-shop-governor` family for a FIFTH
sibling). **This build also completes the entire `cloud-itonami/
cloud-itonami-isic-*` blueprint fleet**: after this promotion, ZERO
`:blueprint`-tier candidates remain in that specific org/prefix scope.

> **Why an actor layer at all?** An LLM is great at drafting a
> diagnostic summary, normalizing records, and checking whether a
> claimed parts cost actually equals the ticket's own recorded parts-
> quantity times unit-price -- but it has **no notion of which
> jurisdiction's consumer-product-safety/brand-authenticity law is
> official, no license to complete a real repair or return a real
> item, and no way to know on its own whether a repaired branded
> item's authenticity is actually confirmed**. Letting it complete a
> repair or return an item directly invites fabricated regulatory
> citations, a result being charged on top of a mismatched parts-cost
> claim, an item reaching a customer without a passed safety test, and
> a repaired designer handbag or branded shoe reaching a customer
> without confirming the item isn't a counterfeit -- exposing the shop
> to real trademark-infringement/counterfeit-trafficking liability --
> and liability, for whoever runs it. This project seals the
> RepairOps-LLM into a single node and wraps it with an independent
> **Repair Shop Governor**, a human **approval workflow**, and an
> immutable **audit ledger**.

## Scope: what this actor does and does not do

This actor covers item intake through consumer-product-safety/brand-
authenticity regulatory assessment, post-repair safety screening,
brand-authenticity screening, repair completion and item return. It
does **not**, by itself, hold any license required to operate a
footwear/leather-goods-repair shop in a given jurisdiction, and it
does not claim to. It also does not perform the actual repair/
diagnostic work itself, or judge repair quality --
`leathergoods.registry/parts-cost-matches-claim?` is a pure ground-
truth recompute against the ticket's own recorded fields, not a
repair-quality judgment. Whoever deploys and operates a live instance
(a qualified cobbler/leather-goods-repair technician/shop owner)
supplies any jurisdiction-specific license, the real diagnostic/repair
delivery and the real repair-shop-management-system integrations, and
bears that jurisdiction's liability -- the software supplies the
governed, spec-cited, audited execution scaffold so that operator does
not have to build the compliance layer from scratch.

### Actuation

**Completing a real repair and returning a real item to the customer
are never autonomous, at any phase, by construction.** Two independent
layers enforce this (`leathergoods.governor`'s `:actuation/complete-
repair`/`:actuation/return-item` high-stakes gate and `leathergoods.
phase`'s phase table, which never puts either op in any phase's
`:auto` set) -- see `leathergoods.phase`'s docstring and
`test/leathergoods/phase_test.clj`'s `repair-complete-never-auto-at-
any-phase`/`item-return-never-auto-at-any-phase`. The actor may draft,
check and recommend; a human repair technician is always the one who
actually completes a repair or returns an item. Grounded directly in
this blueprint's own README text ("No automated proposal, by itself,
can complete the following without governor approval and audit
evidence: performing a repair or returning an item to the customer")
-- a genuine DUAL-actuation shape (two distinct real-world acts on the
same ticket), structurally identical to `repairshop`/9521's,
`commrepair`/9512's, `applianceshop`/9522's, `furniture`/9524's and
`specialtyrepair`/9529's own `:actuation/complete-repair`/`:actuation/
return-*` shape (the same business archetype, applied to footwear and
leather goods rather than consumer electronics, communication
equipment, household appliances, furniture or watches/jewelry/
bicycles).

## The core contract

```
item intake + jurisdiction facts (leathergoods.facts, spec-cited)
        |
        v
   ┌───────────────────────┐   proposal      ┌───────────────────────┐
   │ RepairOps-LLM         │ ─────────────▶ │ Repair Shop                    │  (independent system)
   │ (sealed)              │  + citations    │ Governor:                    │
   └───────────────────────┘                 │ spec-basis · evidence-       │
          │                 commit ◀┼ incomplete · parts-cost-         │
          │                         │ mismatch (honest reuse) ·             │
    record + ledger        escalate ┼ safety-test-not-passed (honest         │
          │              (ALWAYS for│ reuse) · brand-authenticity-              │
          │       :actuation/complete│ unconfirmed (conditional, NEW) ·          │
          │       -repair/:actuation/│ already-completed · already-returned       │
          │       return-item)       │                                            │
          ▼                          └───────────────────────┘
      human approval
```

**The RepairOps-LLM never completes a repair or returns an item the
Repair Shop Governor would reject, and never does so without a human
sign-off.** Hard violations (fabricated regulatory requirements;
unsupported evidence; a parts-cost mismatch; a failed safety test; an
unconfirmed brand-authenticity status on a branded item; a double
completion/return) force **hold** and *cannot* be approved past; a
clean completion/return proposal still always routes to a human.

## Run

```bash
clojure -M:dev:run     # walk one clean dual-actuation lifecycle + five HARD-hold cases through the actor
clojure -M:dev:test    # governor contract · phase invariants · store parity · registry conformance · facts coverage
clojure -M:lint        # clj-kondo (errors fail; CI mirrors this)
```

## Robotics premise

All cloud-itonami verticals are designed on the premise that a **robot
performs the physical domain work**. Here a workbench robot assists
fine-motor shoe/leather-goods-repair tasks (stitching, sole/heel
replacement, hardware repair), under the actor, gated by the
independent **Repair Shop Governor**. The governor never dispatches
hardware itself; `:high`/`:safety-critical` actions require human
sign-off.

## Open business

This repository is not only source code. It is a public, forkable
business model:

| Layer | What is open |
|---|---|
| OSS core | Actor runtime, Repair Shop Governor, repair-completion/item-return draft records, audit ledger |
| Business blueprint | Customer, offer, pricing, unit economics, sales motion |
| Operator playbook | How to fork, license, deploy and support the service in a jurisdiction |
| Trust controls | Governance, security reporting, actuation invariant, audit requirements |

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md) to start this as an
open business on itonami.cloud, and
[`docs/adr/0001-architecture.md`](docs/adr/0001-architecture.md) for the
full architecture and decision record.

## Capability layer

This blueprint resolves its technology stack via
[`kotoba-lang/industry`](https://github.com/kotoba-lang/industry) (ISIC
`9523`). This vertical's service/member records are practice-specific
rather than a shared cross-operator data contract, so `leathergoods.*`
runs on the generic robotics/identity/forms/dmn/bpmn/audit-ledger
stack only -- no bespoke domain capability lib to reference at all.

## Layout

| File | Role |
|---|---|
| `src/leathergoods/store.cljc` | **Store** protocol -- `MemStore` ‖ `DatomicStore` (`langchain.db`) + append-only audit ledger + repair-completion AND item-return history (dual history, mirroring `repairshop`/9521's, `commrepair`/9512's, `applianceshop`/9522's, `furniture`/9524's and `specialtyrepair`/9529's own shape). The double-actuation guard checks dedicated `:repair-completed?`/`:item-returned?` booleans rather than a `:status` value |
| `src/leathergoods/registry.cljc` | Repair-completion/item-return draft records, plus `parts-cost-matches-claim?` -- an HONEST, literal reuse of `repairshop.registry`'s/`commrepair.registry`'s/`applianceshop.registry`'s/`furniture.registry`'s/`specialtyrepair.registry`'s own EXACT-MATCH independent-recompute check for the SAME real-world concern, not claimed as new |
| `src/leathergoods/facts.cljc` | Per-jurisdiction consumer-product-safety AND brand-authenticity/anti-counterfeiting catalog (a genuine extension beyond `repairshop.facts`'s, `commrepair.facts`'s, `applianceshop.facts`'s, `furniture.facts`'s and `specialtyrepair.facts`'s own catalogs) with an official spec-basis citation per entry, honest coverage reporting -- ALL FOUR seeded jurisdictions have a brand-authenticity sub-citation here, unlike prior siblings' own honest single-jurisdiction gap |
| `src/leathergoods/repairopsllm.cljc` | **RepairOps-LLM** -- `mock-advisor` ‖ `llm-advisor`; intake/jurisdiction-assessment/safety-screening/brand-screening/repair-completion/item-return proposals |
| `src/leathergoods/governor.cljc` | **Repair Shop Governor** -- 7 HARD checks (spec-basis · evidence-incomplete · parts-cost-mismatch, honest reuse · safety-test-not-passed, honest reuse · brand-authenticity-unconfirmed, CONDITIONAL unconditional evaluation, GENUINELY NEW, the 69th grounding of this discipline · already-completed guard · already-returned guard) + 1 soft (confidence/actuation gate) |
| `src/leathergoods/phase.cljc` | **Phase 0→3** -- read-only → assisted intake → assisted assess → supervised (repair completion/item return always human; ticket intake is the ONLY auto-eligible op, no direct capital risk) |
| `src/leathergoods/operation.cljc` | **OperationActor** -- langgraph StateGraph |
| `src/leathergoods/sim.cljc` | demo driver |
| `test/leathergoods/*_test.clj` | governor contract · phase invariants · store parity · registry conformance · facts coverage |

## Business-process coverage (honest)

This actor covers item intake through consumer-product-safety/brand-
authenticity regulatory assessment, post-repair safety screening,
brand-authenticity screening, repair completion and item return -- the
core governed lifecycle this blueprint's own `docs/business-model.md`
names as its Offer:

| Covered | Not covered (out of scope for this R0) |
|---|---|
| Ticket intake + per-jurisdiction evidence checklisting, HARD-gated on an official spec-basis citation (`:ticket/intake`/`:jurisdiction/assess`) | Real repair-shop-management-system integration, real diagnostic/repair work itself (see `leathergoods.facts`'s docstring) |
| Post-repair safety screening + brand-authenticity screening, each evaluated so the screening op itself can HARD-hold on its own finding (`:safety/screen`/`:brand/screen`, the latter CONDITIONAL on the ticket's own branded-item ground truth) | Repair-quality judgment itself -- deliberately outside this actor's competence |
| Repair completion, HARD-gated on full evidence and a matching parts-cost claim, plus a double-completion guard (`:actuation/complete-repair`) | |
| Item return, HARD-gated on full evidence, a passed safety test and a confirmed brand-authenticity status (when applicable), plus a double-return guard (`:actuation/return-item`) | |
| Immutable audit ledger for every intake/assessment/screening/completion/return decision | |

Extending coverage is additive: add the next gate (e.g. a warranty-
coverage-verification check) as its own governed op with its own HARD
checks and tests, following the SAME "an independent governor
re-verifies against the actor's own records before any real-world
act" pattern this repo's flagship ops already establish.

## Jurisdiction coverage (honest)

`leathergoods.facts/coverage` reports how many requested jurisdictions
actually have an official spec-basis in `leathergoods.facts/catalog`
-- currently 4 seeded (JPN, USA, GBR, DEU) out of ~194 jurisdictions
worldwide. This is a starting catalog to prove the governor contract
end-to-end, not a claim of global coverage. Adding a jurisdiction is
additive: one map entry in `leathergoods.facts/catalog`, citing a real
official source -- never fabricate a jurisdiction's requirements to
make coverage look bigger. Note that the brand-authenticity sub-
citation is, for once, FULL coverage rather than a gap: unlike prior
repair-shop-cluster siblings' own honest single-jurisdiction gap
(Japan lacking an equivalent regime), ALL FOUR seeded jurisdictions
(JPN, USA, GBR, DEU) actually have a real, well-documented trademark/
anti-counterfeiting enforcement regime, reported honestly -- the same
honesty discipline that forbids fabricating coverage also forbids
under-reporting it.

## Maturity

`:implemented` -- `RepairOps-LLM` + `Repair Shop Governor` run as
real, tested code (see `Run` above), promoted from the originally-
published `:blueprint`-tier scaffold, modeled closely on `repairshop`/
9521's, `commrepair`/9512's, `applianceshop`/9522's, `furniture`/9524's
and `specialtyrepair`/9529's own architecture and the eighty-three
other prior actors' architecture across this fleet. This build
completes the entire `cloud-itonami/cloud-itonami-isic-*` blueprint
fleet -- zero `:blueprint`-tier candidates remain in that org/prefix
scope after this promotion. See `docs/adr/0001-architecture.md` for
the history and design.

## License

Code and implementation templates are AGPL-3.0-or-later.
