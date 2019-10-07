package com.example.app1tabbed.TabFragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.app1tabbed.utils.MainAdapter
import com.example.app1tabbed.utils.Message
import com.example.app1tabbed.R
import com.example.app1tabbed.utils.Websockets

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class PublicationsFragment : Fragment() {

    private val messages: MutableList<Message> = mutableListOf()
    lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_publications, container, false)



        val recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        val sendMessageBtn = rootView.findViewById(R.id.send_message_btn) as ImageButton


        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter(messages)

        recyclerView.adapter = adapter

        val prefs = activity!!.getSharedPreferences("storage", Context.MODE_PRIVATE)
        val username = prefs.getString("username", null)


        val webSocket = Websockets(activity,username, messages, recyclerView)

        val io = webSocket.createSocket()


        sendMessageBtn.setOnClickListener {

            val newMessage = rootView.findViewById(R.id.new_message) as EditText
            val message = newMessage.text.toString()

            if(message.isEmpty()){
                Toast.makeText(activity,"El mensaje está vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            /*messages.add(
                Message(
                    "outgoing",
                    username,
                    message,
                    Date().toString(),
                    ""
                )
            )*/


            webSocket.attemptSend(io, username ,message)

            newMessage.text.clear()
            adapter.notifyDataSetChanged()
        }


        io.on("message", webSocket.onNewMessage())


        return rootView
    }

}
