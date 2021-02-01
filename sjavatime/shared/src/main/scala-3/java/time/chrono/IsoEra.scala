package java.time.chrono

import java.{lang => jl}
import java.time.DateTimeException

enum IsoEra extends jl.Enum[IsoEra] with Era {

  case BCE extends IsoEra

  case CE extends IsoEra

  def getValue(): Int = ordinal
}

object IsoEra {

  // TODO"values can not be called with parens
  def of(isoEra: Int): IsoEra = values.lift(isoEra).getOrElse {
    throw new DateTimeException(s"Invalid value for isoEra: $isoEra")
  }
}
