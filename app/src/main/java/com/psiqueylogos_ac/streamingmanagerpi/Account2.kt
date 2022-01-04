package com.psiqueylogos_ac.streamingmanagerpi

import androidx.room.PrimaryKey
import com.psiqueylogos_ac.ajedrex.Entity
import com.psiqueylogos_ac.ajedrex.Record

data class Account2  (
    var id : Int = 0,
    var name : String="",
    var identifier : String="",
    var key: String="",
    var pin : String="",
    var startDate : String="",
    var expirationDate : String="",
    var phone: String="",
    var email : String="",
    var platform : String=""

) : Record