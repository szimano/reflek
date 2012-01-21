package org.szimano.reflek.service

import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen

/**
 * Integration test for BazaLekow
 *
 * User: szimano
 */

class BazaLekowTest extends FeatureSpec {
  feature ("User can search for drugs") {
    scenario("All drugs are queried")(pending)
    scenario("All drugs are queried on an empty DB")(pending)
    scenario("Drugs with one search token are queried")(pending)
    scenario("Drugs with two search tokens are queried")(pending)
    scenario("Drugs with one search token are queried on an empty DB")(pending)
    scenario("Drugs with two search tokens are queried on an empty DB")(pending)
  }
}