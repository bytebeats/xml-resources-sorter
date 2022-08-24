package me.bytebeats.ipp.xmlres.util

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

private const val GROUP = "XmlResourcesSorter"
private const val ERROR_TITLE = "XmlError"
private const val WARNING_TITLE = "XmlWarning"
private const val INFO_TITLE = "XmlInfo"
fun notifyError(error: String) {
    Notifications.Bus.notify(Notification(GROUP, ERROR_TITLE, error, NotificationType.ERROR))
}

fun notifyWarning(warning: String) {
    Notifications.Bus.notify(Notification(GROUP, WARNING_TITLE, warning, NotificationType.WARNING))
}

fun notifyInfo(information: String) {
    Notifications.Bus.notify(Notification(GROUP, INFO_TITLE, information, NotificationType.INFORMATION))
}