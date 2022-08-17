package me.bytebeats.ipp.xmlres.util

import javax.swing.JComboBox
import javax.swing.JLabel

fun JComboBox<*>.alignItemCenter() {
    (renderer as JLabel).horizontalAlignment = JLabel.CENTER
}