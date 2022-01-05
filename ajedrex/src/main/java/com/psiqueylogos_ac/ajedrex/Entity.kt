package com.psiqueylogos_ac.ajedrex

import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation


open class Entity<T : Record> : MutableCollection<T> {

    private val records = ArrayList<T>()
    private var recordType: KClass<T>
    private var lastIndex = 0

    constructor(record: KClass<T>) {
        recordType = record
    }

    /**
     * Return a JSONArray object with all fields of this record
     */
    private fun retrieve(): JSONArray {
        var jsonArray = JSONArray()
        for (record in records) {
            jsonArray.put(record.get())
        }
        return jsonArray
    }

    private fun fill(jsonArray: JSONArray) {
        for (i in 0 until jsonArray.length()) {
            val p = jsonArray[i] as JSONObject
            var q = recordType.createInstance()
            q.set(p)
            records.add(q)
        }
    }

    fun save(path: String) {
        val f = File(path)
        f.setWritable(true)
        f.writeText(retrieve().toString())
    }

    fun load(path: String, clear: Boolean = true) {
        val f = File(path)
        val s = f.readText()
        if (clear) records.clear()
        fill(JSONArray(s))
    }


    fun calculateIndex(): Int {
        var i = 0
        records.forEach { if (it._ID > i) i = it._ID }
        return i
    }

    fun update(record: T, predicate: (T) -> Boolean) {
        //records.first { record. }
        // var k = record::class.declaredMemberProperties.find { it.hasAnnotation<PrimaryKey>() }
        val i = records.indexOfFirst(predicate)
        records[i] = record
    }


    fun update(record: T) {
        val i = records.indexOfFirst { it._ID == record._ID }
        records.set(i, record)
    }

    override val size: Int
        get() = records.size

    override fun contains(element: T): Boolean = records.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = records.containsAll(elements)
    override fun isEmpty(): Boolean = records.isEmpty()
    override fun add(element: T): Boolean {
        element._ID = lastIndex
        records.add(element)
        lastIndex++
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elements.forEach { add(it) }
        return true
    }

    override fun clear() {
        records.clear()
        lastIndex = 0
    }

    override fun iterator(): MutableIterator<T> = records.iterator()
    override fun remove(element: T): Boolean = records.remove(element)
    override fun removeAll(elements: Collection<T>): Boolean = records.removeAll(elements)
    override fun retainAll(elements: Collection<T>): Boolean = records.retainAll(elements)

}


