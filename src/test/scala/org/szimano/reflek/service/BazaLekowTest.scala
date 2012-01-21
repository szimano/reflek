package org.szimano.reflek.service

import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.testng.TestNGSuite
import org.szimano.service.BazaLekow

/**
 * Integration test for BazaLekow
 *
 * User: szimano
 */

@RunWith(classOf[JUnitRunner])
class BazaLekowTest extends FeatureSpec with GivenWhenThen {
  feature ("User can search for drugs") {
    info("As a user")
    info("I want to be able to search for refunded drugs")
    info("So I can easily find what I am looking for")

    scenario("All drugs are queried") {
      given("non-empty db")
      val bazaLekow = createNonEmpty("db-all-noempty");

      when("all drugs are queried")
      val all = bazaLekow.readAll()

      then("all drugs should be returned")
      assert(all.size === 3)
    }
    scenario("All drugs are queried on an empty DB"){
      given("empty db")
      val bazaLekow = createEmpty("db-all-empty");

      when("all drugs are queried")
      val all = bazaLekow.readAll()

      then("no drugs should be returned")
      assert(all.isEmpty)
    }
    scenario("Drugs with one search token are queried"){
      given("non-empty db")
      val bazaLekow = createNonEmpty("drugs-one-nonempty")

      when("drugs are queried with one search token")
      val leki = bazaLekow.searchLeki(List("cukrzyca"))

      then("all drugs with such token should be returned")
      assert(leki.size === 2)
      assert(leki(0).substancja == "Acarbosum")
      assert(leki(1).substancja == "Clindamycinum")
    }
    scenario("Drugs with two search tokens are queried"){
      given("non-empty db")
      val bazaLekow = createNonEmpty("drugs-two-nonempty")

      when("drugs are queried with two search tokens")
      val leki = bazaLekow.searchLeki(List("cukrzyca", "antybiotyki"))

      then("all drugs that have all the tokens should be returned")
      assert(leki.size === 1)
      assert(leki(0).substancja == "Clindamycinum")
    }
    scenario("Drugs with one search token are queried on an empty DB"){
      given("empty db")
      val bazaLekow = createEmpty("drugs-one-empty")

      when("drugs are queried with one search token")
      val leki = bazaLekow.searchLeki(List("cukrzyca"))

      then("no drugs should be returned")
      assert(leki.isEmpty)
    }
    scenario("Drugs with two search tokens are queried on an empty DB"){
      given("empty db")
      val bazaLekow = createEmpty("drugs-two-empty")

      when("drugs are queried with two search tokens")
      val leki = bazaLekow.searchLeki(List("cukrzyca", "antybiotyki"))

      then("no drugs should be returned")
      assert(leki.isEmpty)
    }
    scenario("Drugs with no search tokens should return same as readAll()"){
      given("non-empty db")
      val bazaLekow = createNonEmpty("drugs-null-search")

      when("seraching without search tokens")
      val leki = bazaLekow.searchLeki(Nil)

      then("result should be same as readAll")
      assert(leki.corresponds(bazaLekow.readAll()){_.id == _.id})
    }
    scenario("When searching with null search tokens IllegalArgumentException should be thrown"){
      given("non-empty db")
      val bazaLekow = createNonEmpty("drugs-no-search")

      when("seraching with null search tokens")
      then("exception should be thrown")

      intercept[IllegalArgumentException] {
        val leki = bazaLekow.searchLeki(null)
      }
    }
  }
  
  def createNonEmpty(dbName: String) = {
    val bazaLekow = new BazaLekow("/leki-nonempty.csv", dbName)

    bazaLekow
  }

  def createEmpty(dbName: String) = {
    val bazaLekow = new BazaLekow("/leki-empty.csv", dbName)

    bazaLekow
  }
}