package com.zw.demo.twelve

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.{JButton, JFrame}


object Note1 {

    implicit def makeAction(action: (ActionEvent) => Unit) = new ActionListener {
        override def actionPerformed(event: ActionEvent) {
            action(event)
        }
    }

    def main(args: Array[String]) {

        var data = 0
        val frame = new JFrame("SAM Testing");
        val jButton = new JButton("Counter")

//        jButton.addActionListener(new ActionListener {
//            override def actionPerformed(event: ActionEvent) {
//                data += 1
//                println(data)
//            }
//        })

        jButton.addActionListener((event: ActionEvent) => {
            data += 1; println(data)
        })

        frame.setContentPane(jButton);
        frame.pack();
        frame.setVisible(true);
    }
}
