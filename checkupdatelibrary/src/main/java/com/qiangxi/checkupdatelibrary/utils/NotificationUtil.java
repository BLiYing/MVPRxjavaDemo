package com.qiangxi.checkupdatelibrary.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.NumberFormat;

import static android.app.PendingIntent.getActivity;

/**
 * 作者 任强强 on 2016/10/18 11:36.
 */

public class NotificationUtil {
    private static final int notificationId = 10;

    private NotificationUtil() {
    }

    /**
     * 展示下载成功通知
     *
     * @param context               上下文
     * @param file                  下载的apk文件
     * @param notificationIconResId 通知图标资源id
     * @param notificationTitle     通知标题
     * @param isCanClear            通知是否可被清除
     */
    public static void showDownloadSuccessNotification(Context context, File file, int notificationIconResId, String notificationTitle, String notificationContent, boolean isCanClear) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent installIntent = new Intent();
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        //当前设备系统版本在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName(), file);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(false).setShowWhen(true).setSmallIcon(notificationIconResId).setContentTitle(notificationTitle).setContentText(notificationContent);
        PendingIntent pendingIntent = getActivity(context, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(notificationId, notification);// 显示通知
    }

    /**
     * 展示下载成功通知
     *
     * @param context               上下文
     * @param file                  下载的apk文件
     * @param notificationIconResId 通知图标资源id
     * @param notificationTitle     通知标题
     * @param isCanClear            通知是否可被清除
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void showDownloadSuccessNotificationAndroidO(Context context, File file, int notificationIconResId, String notificationTitle, String notificationContent, boolean isCanClear) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent installIntent = new Intent();
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        //当前设备系统版本在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName(), file);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        NotificationChannel channel = new NotificationChannel("1",
                "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(false); //是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.GREEN); //小红点颜色
        channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
        manager.createNotificationChannel(channel);
        Notification.Builder builder = new Notification.Builder(context,"1");
        builder.setAutoCancel(false).setShowWhen(true).setSmallIcon(notificationIconResId).setContentTitle(notificationTitle).setContentText(notificationContent);
        PendingIntent pendingIntent = getActivity(context, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(notificationId, notification);// 显示通知
    }

    /**
     * 展示实时下载进度通知
     *
     * @param context               上下文
     * @param currentProgress       当前进度
     * @param totalProgress         总进度
     * @param notificationIconResId 通知图标资源id
     * @param notificationTitle     通知标题
     * @param isCanClear            通知是否可被清除
     */
    private static NotificationManager manager;
    private static NotificationCompat.Builder builder;
    private static Notification.Builder builderAndroidO;
    private static Notification notification;
    public static void showDownloadingNotification(Context context, int currentProgress, int totalProgress, int notificationIconResId, String notificationTitle, boolean isCanClear) {
        if (manager == null)
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1",
                    "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(false); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            manager.createNotificationChannel(channel);
            showDownloadingNotificationAndroidO(context,currentProgress,totalProgress,notificationIconResId,notificationTitle,isCanClear);
        }else {

            if (builder == null) {
                builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(false).setShowWhen(false).setSmallIcon(notificationIconResId).setContentTitle(notificationTitle)
                        .setProgress(totalProgress, currentProgress, false);
            }
            if (notification == null) {
                notification = builder.build();// 获取一个Notification
                notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;// 设置为默认的声音
                notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
                manager.notify(notificationId, notification);
            } else {
                String percentProgress = caculate(currentProgress, totalProgress);
                builder.setContentText(percentProgress);
                builder.setProgress(totalProgress, currentProgress, false);
                manager.notify(notificationId, builder.build());// 显示通知
            }
        }
    }

    /**
     * 适配android O 8.0
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void showDownloadingNotificationAndroidO(Context context, int currentProgress, int totalProgress, int notificationIconResId, String notificationTitle, boolean isCanClear) {
        if (builderAndroidO == null) {
            builderAndroidO = new Notification.Builder(context,"1");
            builderAndroidO.setAutoCancel(false).setShowWhen(false).setSmallIcon(notificationIconResId).setContentTitle(notificationTitle)
                    .setProgress(totalProgress, currentProgress, false);
        }
        if (notification == null) {
            notification = builderAndroidO.build();// 获取一个Notification
            notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;// 设置为默认的声音
            notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
            manager.notify(notificationId, notification);
        } else {
            String percentProgress = caculate(currentProgress, totalProgress);
            builderAndroidO.setContentText(percentProgress);
            builderAndroidO.setProgress(totalProgress, currentProgress, false);
            manager.notify(notificationId, builderAndroidO.build());// 显示通知
        }
    }

    /**
     * 获取百分数
     * @param currentProgress
     * @param totalProgress
     * @return 百分比
     */
    private static String caculate(int currentProgress, int totalProgress){
//        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        double percentProgressD = (float)currentProgress/totalProgress;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(1);
        String percentProgressS = nf.format(percentProgressD);
        return percentProgressS;
    }

    /**
     * 展示下载失败通知
     *
     * @param context               上下文
     * @param notificationContent   通知内容,比如:下载失败,点击重新下载
     * @param intent                该intent用来重新下载应用
     * @param notificationIconResId 通知图标资源id
     * @param notificationTitle     通知标题
     * @param isCanClear            通知是否可被清除
     */
    public static void showDownloadFailureNotification(Context context, Intent intent, int notificationIconResId, String notificationTitle, String notificationContent, boolean isCanClear) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(false).setShowWhen(true).setSmallIcon(notificationIconResId)
                .setContentTitle(notificationTitle).setContentText(notificationContent);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(notificationId, notification);// 显示通知
    }

    /**
     * 展示下载失败通知
     *
     * @param context               上下文
     * @param notificationContent   通知内容,比如:下载失败,点击重新下载
     * @param intent                该intent用来重新下载应用
     * @param notificationIconResId 通知图标资源id
     * @param notificationTitle     通知标题
     * @param isCanClear            通知是否可被清除
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void showDownloadFailureNotificationAndroidO(Context context, Intent intent, int notificationIconResId, String notificationTitle, String notificationContent, boolean isCanClear) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context,"1");
        builder.setAutoCancel(false).setShowWhen(true).setSmallIcon(notificationIconResId)
                .setContentTitle(notificationTitle).setContentText(notificationContent);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(notificationId, notification);// 显示通知
    }
}
