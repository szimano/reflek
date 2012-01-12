package org.szimano

import au.com.bytecode.opencsv.CSVReader
import org.squeryl.adapters.H2Adapter

import org.squeryl.annotations.Column

import org.squeryl.PrimitiveTypeMode._
import java.lang.String
import java.io.{InputStreamReader, InputStream, FileReader}
import org.squeryl.{Query, Session, SessionFactory, Schema}
import collection.mutable.ListBuffer

/**
 * @author ${user.name}
 */

object BazaLekow {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  //*********************************** Init code ***************************
  // initialize the DB
  Class.forName("org.h2.Driver")

  SessionFactory.concreteFactory = Some(() => Session.create(
    java.sql.DriverManager.getConnection("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1"),
    new H2Adapter
  ))

  transaction {
    Library.create
  }

  val leki = this.getClass.getResourceAsStream("/leki.csv");

  val reader = new CSVReader(new InputStreamReader(leki))

  var line = reader.readNext()

  println("wczytuje leki")

  while (line != null) {

    transaction {
      Library.leki.insert(new Lek(augmentString(line(0)).toLong, line(1), line(2), line(3), line(7), line(11), line(12)))
    }
    line = reader.readNext()
  }

  println("wczytalem leki")
  //*********************************** Init code ***************************

  def readAll() = {
    var leki: Query[Lek] = null

    val lekiLista: ListBuffer[Lek] = ListBuffer()

    transaction {
      leki = from(Library.leki)(l => select (l))

      for (l <- leki)
        lekiLista += l
    }

    lekiLista
  }


  def main(args: Array[String]) {

    transaction {
      def leki = from(Library.leki)(l => where(l.substancja like "Viga%") select (l))

      println("Znalazlem " + leki.size + " lekow")
      for (l <- leki)
        println(l)
    }

  }

}


class Lek(val id: Long,
          @Column(length = 500)
          var substancja: String,
          @Column(length = 500)
          var nazwa: String,
          @Column(length = 500)
          var opakowanie: String,
          @Column(length = 500)
          var grupa: String,
          @Column(length = 500)
          var wskazania: String,
          @Column(length = 10)
          var refundacja: String) {
  def this() = this (0, "", "", "", "", "", "")

  override def toString() = "Lek[id=" + id + ";substancja=" + substancja + ";nazwa=" + nazwa + ";opakowanie=" + opakowanie + ";grupa=" +
    grupa + ";wskazania=" + wskazania + ";refundacja=" + refundacja + "]"
}

object Library extends Schema {
  val leki = table[Lek]("LEK")
}