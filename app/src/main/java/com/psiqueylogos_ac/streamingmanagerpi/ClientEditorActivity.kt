package com.psiqueylogos_ac.streamingmanagerpi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.psiqueylogos_ac.ajedrex.Entity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ClientEditorActivity : AppCompatActivity() {
    lateinit var saveButton: Button
    lateinit var identifier: EditText
    lateinit var key: EditText
    lateinit var pin: EditText
    lateinit var email: AutoCompleteTextView
    lateinit var name: EditText
    lateinit var platform: AutoCompleteTextView
    lateinit var startDate: EditText
    lateinit var expirationDate: EditText
    lateinit var phone: EditText
    lateinit var days30: Button

    val sfd = SimpleDateFormat("dd/MM/yyyy")
    var db = Entity(Account2::class)
    var theId = -1
    var currentAccount = Account2()

    fun emails(): List<String> {
        var l = ArrayList<String>()
        db.forEach { if (!l.contains(it.email)) l.add(it.email) }

        return l.toList()
    }

    fun platforms(): List<String> {
        var l = ArrayList<String>()
        db.forEach { if (!l.contains(it.platform)) l.add(it.platform) }
        return l.toList()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        theId = intent.extras?.getInt("id", -1) ?: -1
        saveButton = findViewById(R.id.client_save_button)
        identifier = findViewById(R.id.client_identifier)
        key = findViewById(R.id.client_key)
        pin = findViewById(R.id.client_pin)
        email = findViewById(R.id.client_account_email)
        name = findViewById(R.id.client_name)
        platform = findViewById(R.id.client_platform)
        startDate = findViewById(R.id.client_start_date)
        expirationDate = findViewById(R.id.client_expiration_date)
        phone = findViewById(R.id.client_phone)
        days30 = findViewById(R.id.client_30_days)


        db.load(databaFilename)

        if (theId > 0) {
            currentAccount = db.first { it._ID == theId }
        } else {
            db.add(currentAccount)
            currentAccount = db.last()
        }
        setUi(currentAccount)

        saveButton.setOnClickListener {
            getUi()
            db.update(currentAccount)
            db.save(databaFilename)
            finish()
        }


        days30.setOnClickListener {
            var d0 = sfd.parse(expirationDate.text.toString())
            expirationDate.setText(sfd.format(d0.plus(30)))
        }

        startDate.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                var d0 = sfd.parse(startDate.text.toString())
                d0.plus(30)
                expirationDate.setText(sfd.format(d0))
            }
        }


        var platformArrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, platforms())
        platform.setAdapter(platformArrayAdapter)

        var emailArrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, emails())
        email.setAdapter(emailArrayAdapter)

    }


    fun getUi() {
        currentAccount.name = name.text.toString()
        currentAccount.identifier = identifier.text.toString()
        currentAccount.key = key.text.toString()
        currentAccount.pin = pin.text.toString()
        currentAccount.startDate = startDate.text.toString()
        currentAccount.expirationDate = expirationDate.text.toString()
        currentAccount.phone = phone.text.toString()
        currentAccount.email = email.text.toString()
        currentAccount.platform = platform.text.toString()

    }

    fun setUi(account: Account2) {


        if (account.startDate.isEmpty()) {
            account.startDate = sfd.format(Date())
        }

        name.setText(account.name)
        identifier.setText(account.identifier)
        key.setText(account.key)
        pin.setText(account.pin)
        startDate.setText(account.startDate)
        expirationDate.setText(account.expirationDate)
        phone.setText(account.phone)
        email.setText(account.email)
        platform.setText(account.platform)
    }


}