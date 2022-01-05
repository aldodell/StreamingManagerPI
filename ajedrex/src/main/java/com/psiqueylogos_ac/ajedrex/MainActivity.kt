package com.psiqueylogos_ac.ajedrex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data class Person(var name : String ="" ) : Record ()
        data class Car(var brand:String="", var color: String="") : Record()


     var filename = this.getDatabasePath("andreina.json").path
        val person = Entity(Person::class)

        person.add(Person("Andreina"))
        person.save(filename)

person.filter { it.name == "aldo" }



    }
}