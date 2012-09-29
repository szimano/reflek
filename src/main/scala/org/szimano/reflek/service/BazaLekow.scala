package org.szimano.service

import au.com.bytecode.opencsv.CSVReader
import org.squeryl.adapters.H2Adapter


import org.squeryl.PrimitiveTypeMode._
import java.lang.String
import java.io.InputStreamReader
import org.squeryl.{Session, SessionFactory}
import org.szimano.reflek.entity.{ReflekEntities, Lek}
import annotation.tailrec

/**
 * @author ${user.name}
 */

class BazaLekow(val sourceFile: String, val dbName: String) {

  //*********************************** Init code ***************************
  // initialize the DB
  Class.forName("org.h2.Driver")

  SessionFactory.concreteFactory = Some(() => Session.create(
    java.sql.DriverManager.getConnection("jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1"),
    new H2Adapter
  ))

  transaction {
    ReflekEntities.create
  }

  val leki = this.getClass.getResourceAsStream(sourceFile);

  val reader = new CSVReader(new InputStreamReader(leki, "UTF-8"))

  println("wczytuje leki")

  transaction {
    @tailrec
    def readNextLine(line: Array[String], lineNo: Int): Void = {
      if (line == null) {
        null
      }
      else {
        try {
          ReflekEntities.leki.insert(
            new Lek(augmentString(line(0)).toLong, line(1), line(2), line(3), line(7), line(11),
            line(12), line(9)))
        }
        catch {
          case e: Exception => {
            System.err.println("Problem reading source file at line " + lineNo)
            e.printStackTrace()
          }
        }

        readNextLine(reader.readNext(), lineNo+1)
      }
    }

    readNextLine(reader.readNext(), 0)
  }

  println("wczytalem leki")

  //*********************************** Init code ***************************

  def readAll() = {
    transaction {
      val leki = from(ReflekEntities.leki)(l => select(l))

      leki.toArray
    }
  }

  def searchLeki(s: List[String]) = {
    if (s == null) {
      throw new IllegalArgumentException("The list of tokens cannot be null")
    }

    transaction {
      val leki = from(ReflekEntities.leki)(l => where(
        s.map("%" + _ + "%").foldLeft(l.id === l.id or l.id === l.id)((c, str) => (c and
          ((lower(l.substancja) like str) or
            (lower(l.nazwa) like str) or
            (lower(l.grupa) like str) or
            (lower(l.opakowanie) like str) or
            (lower(l.wskazania) like str) or
            (lower(l.refundacja) like str)))
        )) select (l))

      leki.toArray
    }
  }


  def main(args: Array[String]) {

    transaction {
      def leki = from(ReflekEntities.leki)(l => where(l.substancja like "Viga%") select (l))

      println("Znalazlem " + leki.size + " lekow")
      for (l <- leki)
        println(l)
    }

  }

}




