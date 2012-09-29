package org.szimano.reflek.entity

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
          @Column(length = 1000)
          var refundacja: String,
          @Column(length = 10)
          var cena: String) {
  def this() = this (0, "", "", "", "", "", "", "")

  override def toString() = "Lek[id=" + id + ";substancja=" + substancja + ";nazwa=" + nazwa + ";opakowanie=" + opakowanie + ";grupa=" +
    grupa + ";wskazania=" + wskazania + ";refundacja=" + refundacja + ";cena=" + cena + "]"
}




