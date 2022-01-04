package com.psiqueylogos_ac.streamingmanagerpi

import java.text.SimpleDateFormat
import java.util.*


/**
 * Add days to this Date object
 */
fun Date.plus(days: Int) : Date {
    this.time += (1000L * 60L * 60L * 24L * days.toLong())
    return this
}


/**
        Create a Date from a simple string
 */
fun Date.fromString(string : String) {
    val sfd = SimpleDateFormat("dd/MM/yyyy")
    val d = sfd.parse(string)
    this.time = d.time
}


fun String.toDate() : Date {
    val sfd = SimpleDateFormat("dd/MM/yyyy")
    return sfd.parse(this)
}