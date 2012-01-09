package org.szimano

import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader

/**
 * @author ${user.name}
 */
object App {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  
  def main(args : Array[String]) {
    println( "Hello World!" )
    println("concat arguments = " + foo(args))

    val fileName = args(0)

    println(fileName)

    val reader = new CSVReader(new FileReader(fileName))
    
    var line = Array[String]()
    
    while((line = reader.readNext()) != null) {
      println(foo(line))
    }
  }

}
