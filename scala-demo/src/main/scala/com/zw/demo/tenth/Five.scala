package com.zw.demo.tenth

import java.awt.Point
import java.beans.{PropertyChangeSupport, PropertyChangeEvent, PropertyChangeListener}

/**
  * Created by zhangws on 16/10/28.
  */
object Five {

  def main(args: Array[String]) {
    val p = new Point() with PropertyChange {
      val propertyChangeSupport = new PropertyChangeSupport(this)
      propertyChangeSupport.addPropertyChangeListener(new PropertyChangeListener {
        override def propertyChange(evt: PropertyChangeEvent): Unit = {
          println(evt.getPropertyName
            + ": oldValue = " + evt.getOldValue
            + " newValue = " + evt.getNewValue)
        }
      })
    }
    val newX : Int = 20
    p.propertyChangeSupport.firePropertyChange("x", p.getX, newX)
    p.move(newX, 30)
  }
}