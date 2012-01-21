package org.szimano.reflek.entity

import org.squeryl.Schema

/**
 * @author ${user.name}
 */

object ReflekEntities extends Schema {
  val leki = table[Lek]("LEK")
}




