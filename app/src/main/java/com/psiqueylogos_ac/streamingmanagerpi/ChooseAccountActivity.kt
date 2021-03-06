package com.psiqueylogos_ac.streamingmanagerpi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class ChooseAccountActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var sortByNameButton : Button
    lateinit var sortByExpirationDate : Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_account)

        // Initialize Firebase Auth
        auth = Firebase.auth
        auth.signInAnonymously()


        recyclerView = findViewById(R.id.recyclerView)
        sortByNameButton = findViewById(R.id.choose_account_by_name)
        sortByExpirationDate = findViewById(R.id.choose_account_by_expiration_date)
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        floatingActionButton = findViewById(R.id.floatingActionButton)


        sortByNameButton.setOnClickListener {
            orderByName()
        }

        sortByExpirationDate.setOnClickListener {
            orderByExpirationDate()
        }



        floatingActionButton.setOnClickListener {

            //Abrir el editor
            var clientIntent = Intent(this.baseContext, ClientEditorActivity::class.java)
            startActivity(clientIntent)
        }

    }

    override fun onResume() {
        super.onResume()
        orderByName()

    }

    fun orderByName() {
        val db = Room
            .databaseBuilder(this.baseContext, AppDatabase::class.java, "Account")
            .allowMainThreadQueries()
            .build()
        val accounts = db.accountDao().getAllByName()
        recyclerView.adapter = AccountAdapter(accounts)
    }

    fun orderByExpirationDate() {
        val sfd = SimpleDateFormat("dd/MM/yyyy")
        val db = Room
            .databaseBuilder(this.baseContext, AppDatabase::class.java, "Account")
            .allowMainThreadQueries()
            .build()
        val accounts = db.accountDao().getAllByName()

        val sorted =  accounts.sortedBy { sfd.parse(it.expirationDate).time }

        recyclerView.adapter = AccountAdapter(sorted)
    }
}