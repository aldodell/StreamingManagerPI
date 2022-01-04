package com.psiqueylogos_ac.streamingmanagerpi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.psiqueylogos_ac.ajedrex.Entity

var databaFilename : String = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaFilename = this.getDatabasePath("data.json").path

        //Abrir el editor
        var clientIntent = Intent(this.baseContext, ChooseAccountActivity::class.java)
        startActivity(clientIntent)
    }
}