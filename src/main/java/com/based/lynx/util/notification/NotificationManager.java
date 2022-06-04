package com.based.lynx.util.notification;

import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager {
    //https://github.com/superblaubeere27/NotificationSystem/blob/master/me/superblaubeere27/client/notifications/NotificationManager.java
    //credit ^^^

    private static LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<>();
    private static Notification currentNotification = null;

    public static void show(Notification notification) {
        pendingNotifications.add(notification);
    }

    public static void update() {
        if (currentNotification != null && !currentNotification.isShown()) {
            currentNotification = null;
        }

        if (currentNotification == null && !pendingNotifications.isEmpty()) {
            currentNotification = pendingNotifications.poll();
            currentNotification.show();
        }

    }

    public static void render() {
        update();

        if (currentNotification != null)
            currentNotification.render();
    }
}
