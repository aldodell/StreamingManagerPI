package com.psiqueylogos_ac.ajedrex

import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.*
import kotlin.reflect.full.*


annotation class Ignore
annotation class PrimaryKey

interface  Record {

    @PrimaryKey
    var _ID : Int

    /**
     * Fill this record object with data from jsonObject
     */
    fun set(jsonObject: JSONObject) {
        this::class.declaredMemberProperties.forEach {
                p->

            if(p.hasAnnotation<Ignore>()) {
                //Ignore this loop
            } else  if(p.returnType.isSubtypeOf(Record::class.createType())) {
                val q = p as KMutableProperty1<Record, Record>
                val r = q.get(this)
                val s = jsonObject[p.name] as JSONObject
                r.set(s)
                q.set(this, r)

            } else if (p.returnType.classifier == IntArray::class) {
                set0<Int>(p, jsonObject[p.name] as JSONArray)
            }   else if (p.returnType.classifier == DoubleArray::class) {
                set0<Double>(p,jsonObject[p.name] as JSONArray)
            }   else if (p.returnType.classifier == FloatArray::class) {
                set0<Float>(p,jsonObject[p.name] as JSONArray)
            }   else if (p.returnType.classifier == LongArray::class) {
                set0<Long>(p,jsonObject[p.name] as JSONArray)

            } else {
                val q = p as KMutableProperty1<Record, Any>
                q.set(this, jsonObject[p.name])
            }

        }
    }


    private inline fun <reified T> set0 (p : KProperty1<out Record, *>, arr: JSONArray) {
        val q = p as KMutableProperty1<Record, Array<T>>
        var al = ArrayList<T>()

        for(i in 0 until arr.length()) {
            al.add(arr[i] as T)
        }
        q.set(this, al.toTypedArray())
    }


    /**
     * Return a JsonObject with data of this record object
     */
    fun get() : JSONObject {

        var obj = JSONObject()

        //Recorremos todas las propiedades declaradas
        this::class.declaredMemberProperties.forEach {
                p->
            if (p.returnType.classifier == String::class) {
                obj.put(p.name, (p as KProperty1<Record, String>).get(this))
            }  else if (p.hasAnnotation<Ignore>()) {
                //Ignore this
            }   else if  (p.returnType.classifier == Int::class) {
                obj.put(p.name,(p as KProperty1<Record, Int>).get(this))
            }  else if (p.returnType.classifier == Long::class) {
                obj.put(p.name,(p as KProperty1<Record, Long>).get(this))
            } else if (p.returnType.classifier == Double::class) {
                obj.put(p.name,(p as KProperty1<Record, Double>).get(this))
            }  else if (p.returnType.classifier == Float::class) {
                obj.put(p.name,(p as KProperty1<Record, Float>).get(this))
            } else if (p.returnType.classifier == Boolean::class) {
                obj.put(p.name,(p as KProperty1<Record, Boolean>).get(this))

            }  else if (p.returnType.isSubtypeOf(Array::class.createType(listOf(KTypeProjection.STAR)))) {
                val o = (p as KProperty1<Record, Array<Any>>).get(this)
                val q = JSONArray(o)
                obj.put(p.name, q)

            }  else if (p.returnType.isSubtypeOf(Record::class.createType())) {
                val o = (p as KProperty1<Record, Record>).get(this)
                obj.put(p.name, o.get())

            } else {
                obj.put(p.name,(p as KProperty1<Record, String>).get(this))

            }
        }
        return obj
    }
}