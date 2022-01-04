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
import java.text.SimpleDateFormat


class ClientEditorActivity : AppCompatActivity() {
    lateinit var saveButton : Button
    lateinit var identifier : EditText
    lateinit var key : EditText
    lateinit var pin : EditText
    lateinit var email : AutoCompleteTextView
    lateinit var name : EditText
    lateinit var platform : AutoCompleteTextView
    lateinit var startDate : EditText
    lateinit var expirationDate : EditText
    lateinit var phone : EditText
    lateinit var days30 : Button

    val sfd = SimpleDateFormat("dd/MM/yyyy")
    var theId : Int  = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        theId = intent.extras?.getInt("id",-1) ?: -1
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

        //Database reference
        val db = Room
            .databaseBuilder(this.baseContext, AppDatabase::class.java, "Account")
            .allowMainThreadQueries()
            .build()

        if(theId > -1 ) {
            val account = db.accountDao().getById(theId)
            setUi(account)
        }

        saveButton.setOnClickListener {

            val account = getUi()

            if(theId > -1 ) {
                db.accountDao().update(account)
            } else {
                db.accountDao().insert(account)
            }
            finish()
        }


        days30.setOnClickListener {
            var d0 = sfd.parse(expirationDate.text.toString())
            expirationDate.setText(sfd.format(d0.plus(30)))
        }

        startDate.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {
                var d0 = sfd.parse(startDate.text.toString())
                d0.plus(30)
                expirationDate.setText(sfd.format(d0))
            }
        }


        var platformArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, db.accountDao().getPlatforms())
        platform.setAdapter(platformArrayAdapter)

        var emailArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, db.accountDao().getEmails())
        email.setAdapter(emailArrayAdapter)

    }


    fun getUi(mId: Int? = null) : Account{
        var myId : Int? = null
        if(theId >= 0 ) myId = theId

        return Account(
            myId,
            name.text.toString(),
            identifier.text.toString(),
            key.text.toString(),
            pin.text.toString(),
            startDate.text.toString(),
            expirationDate.text.toString(),
            phone.text.toString(),
            email.text.toString(),
            platform.text.toString()

        )
    }

    fun setUi(account: Account) {
        theId = account.id ?: -1
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