package com.psiqueylogos_ac.streamingmanagerpi

import androidx.room.*

@Entity
data class Account (
    @PrimaryKey val id : Int?,
    val name : String,
    val identifier : String,
    val key: String,
    val pin : String,
    val startDate : String,
    val expirationDate : String,
    val phone: String,
    val email : String,
    val platform : String
)

@Dao
interface AccountDao {
    @Insert
    fun insert(vararg  accounts: Account)

    @Delete
    fun delete(account: Account)

    @Query("select * from account where id = :id")
    fun getById(id: Int) : Account

    @Update(entity = Account::class)
    fun update(account: Account)

    @Query("select * from account order by name")
    fun getAllByName() : List<Account>


    @Query ("select distinct platform from account order by platform")
    fun getPlatforms() : List<String>

    @Query ("select distinct email from account order by platform")
    fun getEmails() : List<String>

}

@Database(entities = arrayOf(Account::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao() : AccountDao
}
