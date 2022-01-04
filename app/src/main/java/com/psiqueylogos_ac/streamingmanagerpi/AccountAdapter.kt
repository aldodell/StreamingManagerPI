package com.psiqueylogos_ac.streamingmanagerpi

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class AccountAdapter(var accounts: List<Account>) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name : Button = itemView.findViewById(R.id.client_row_name)
        val identifier : TextView = itemView.findViewById(R.id.client_row_identifier)
        val expirationDate : TextView = itemView.findViewById(R.id.client_row_expiration_date)
        val platform : TextView = itemView.findViewById(R.id.client_row_platform)
        /*

        val name : EditText = itemView.findViewById(R.id.client_name)
        val key : EditText = itemView.findViewById(R.id.client_key)
        val pin : EditText = itemView.findViewById(R.id.client_pin)
        val startDate : EditText = itemView.findViewById(R.id.client_start_date)
        val expirationDate : EditText = itemView.findViewById(R.id.client_expiration_date)
        val phone : EditText = itemView.findViewById(R.id.client_phone)
        val email : EditText = itemView.findViewById(R.id.client_account_email)
        val platform : EditText = itemView.findViewById(R.id.client_platform)

         */
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.client_row,parent, false)
        val vh = ViewHolder(view)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accounts[position]
        holder.name.setText(account.name)
        holder.identifier.setText(account.identifier)
        holder.platform.setText(account.platform)
        holder.expirationDate.setText(account.expirationDate)

        if (account.expirationDate.toDate().time < Date().time) {
            holder.expirationDate.setTextColor(Color.RED)
        }


        holder.name.setOnClickListener {
            val myIntent = Intent(it.context, ClientEditorActivity::class.java)
            myIntent.putExtra("id", account.id)
            myIntent.flags += FLAG_ACTIVITY_NEW_TASK
            it.context.startActivity(myIntent)
        }

        /*

        holder.name.setText(account.name)
        holder.key.setText(account.key)
        holder.startDate.setText(account.startDate)

       holder.phone.setText(account.phone)
        holder.email.setText(account.email)

        holder.pin.setText(account.pin)

         */
    }

    override fun getItemCount(): Int {
        return accounts.size
    }
}

// \scale set reyAjedrex