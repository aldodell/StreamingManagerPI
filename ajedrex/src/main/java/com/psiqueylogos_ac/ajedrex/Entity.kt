package com.psiqueylogos_ac.ajedrex

import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance


open class Entity<T:  Record> : MutableCollection<T>{

    private val records = ArrayList<T>()
    private var recordType : KClass<T>

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

    fun load(path: String, clear : Boolean = true) {
        val f = File(path)
        val s = f.readText()
        if(clear) records.clear()
        fill(JSONArray(s))
    }

    override val size: Int
        get() = records.size

    override fun contains(element: T): Boolean = records.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean =records.containsAll(elements)
    override fun isEmpty(): Boolean = records.isEmpty()
    override fun add(element: T): Boolean =  records.add(element)
    override fun addAll(elements: Collection<T>): Boolean = records.addAll(elements)
    override fun clear() {
        records.clear()
    }
    override fun iterator(): MutableIterator<T> = records.iterator()
    override fun remove(element: T): Boolean = records.remove(element)
    override fun removeAll(elements: Collection<T>): Boolean = records.removeAll(elements)
    override fun retainAll(elements: Collection<T>): Boolean = records.retainAll(elements)

}


