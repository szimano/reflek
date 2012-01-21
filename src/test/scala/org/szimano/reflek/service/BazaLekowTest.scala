package org.szimano.reflek.service

import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.testng.TestNGSuite

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
      when("all drugs are queried")
      then("all drugs should be returned")
      pending
    }
    scenario("All drugs are queried on an empty DB"){
      given("empty db")
      when("all drugs are queried")
      then("no drugs should be returned")
      pending
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
}