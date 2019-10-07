package com.example.app1tabbed.utils

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings.Global.getString
import android.service.notification.StatusBarNotification
import android.support.v4.app.FragmentActivity
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.app1tabbed.MainActivity
import com.example.app1tabbed.R
import com.example.app1tabbed.TabFragments.PublicationsFragment
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*
import kotlin.coroutines.coroutineContext

class Websockets(activity: FragmentActivity?, private val user: String, elementos: MutableList<Message>, recyclerView: RecyclerView){

    lateinit var mMessage: Message

    fun createSocket(): Socket {
        var mSocket: Socket? = null

        val mUrl = "https://neki-platform.herokuapp.com"

        try {
            val opts = IO.Options()
            opts.forceNew = true
            opts.reconnection = true
            opts.secure = true
            //opts.query = "token=" + token
            mSocket = IO.socket(mUrl,opts)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        return mSocket!!.connect()
    }

    /**Send Message*/
    fun attemptSend( socket: Socket, name: String, message: String ) {

        val obj = JSONObject()
        //val name = user

        try {

            obj.put("name", name)
            obj.put("message", message)

        } catch (e: JSONException) {

            e.printStackTrace()

        }

        socket.emit("message", obj)

    }

    /**Receive JSONObject messages*/
    private val incomingMessage = Emitter.Listener { args ->

         activity!!.runOnUiThread{

             createNotificationChannel(activity)

             val data = args[0] as JSONObject
             val mName: String
             var mType = "incoming"
             val mMessage: String
             val mImage: String

             mName = data.getString("name")
             mMessage = data.getString("message")
             //mImage = data.getString("imageUrl")

             if( mName == user)
                 mType = "outgoing"
             else
               notificationHandler(activity, mName ,mMessage)

             elementos.add(Message(mType ,mName,mMessage, Date().toString(), "https://www.asthmamd.org/images/icon_user_1.png"))

            (recyclerView.adapter as MainAdapter).notifyDataSetChanged()

        }

    }

    fun onNewMessage(): Emitter.Listener {
        return incomingMessage
    }

    fun notificationHandler(activity: FragmentActivity , name: String,message: String){

        val intent = Intent(activity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent
            .getActivity(activity, 0 /* Request code */, intent, 0)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(activity, "0")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(name + " Te envió un mensaje"))
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setChannelId("0")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setWhen(System.currentTimeMillis())
            .setDefaults(
               NotificationCompat.DEFAULT_SOUND
            or NotificationCompat.DEFAULT_VIBRATE
            or NotificationCompat.DEFAULT_LIGHTS
            )



        with(NotificationManagerCompat.from(activity)){
            notify(0, builder.build())
        }

        //val notificationManager = activity.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
        //notificationManager.createNotificationChannel(NotificationChannel("0", "name", NotificationManager.IMPORTANCE_DEFAULT).apply{ description  = "text"})
        //notificationManager.notify(0 /* ID of notification */, builder.build())

        //notificationManager.ac

        /*AlertDialog.Builder(activity)
            .setTitle("Notification")
            .setMessage(message)

            .setPositiveButton("ok", { dialog, which -> }).show()*/
    }

    private fun createNotificationChannel(activity: FragmentActivity) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = activity.getString(R.string.channel_name)
            val descriptionText = activity.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("0", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                activity.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}