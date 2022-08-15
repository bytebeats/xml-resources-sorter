package me.bytebeats.ipg.xmlsorter

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

private const val GROUP = "AndroidResourceSorter"
private const val ERROR_TITLE = "Error"
private const val WARNING_TITLE = "Warning"
private const val INFO_TITLE = "Information"
fun notifyError(error: String) {
    Notifications.Bus.notify(Notification(GROUP, ERROR_TITLE, error, NotificationType.ERROR))
}

fun notifyWarning(warning: String) {
    Notifications.Bus.notify(Notification(GROUP, WARNING_TITLE, warning, NotificationType.WARNING))
}

fun notifyInfo(information: String) {
    Notifications.Bus.notify(Notification(GROUP, INFO_TITLE, information, NotificationType.INFORMATION))
}