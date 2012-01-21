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
      assert(!all.isEmpty)
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
      when("drugs are queried with one search token")
      then("all drugs with such token should be returned")
      pending
    }
    scenario("Drugs with two search tokens are queried"){
      given("non-empty db")
      when("drugs are queried with two search tokens")
      then("all drugs that have all the tokens should be returned")
      pending
    }
    scenario("Drugs with one search token are queried on an empty DB"){
      given("empty db")
      when("drugs are queried with one search token")
      then("no drugs should be returned")
      pending
    }
    scenario("Drugs with two search tokens are queried on an empty DB"){
      given("empty db")
      when("drugs are queried with two search tokens")
      then("no drugs should be returned")
      pending
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