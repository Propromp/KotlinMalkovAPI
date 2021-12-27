package net.propromp.malkov.util

import net.propromp.malkov.MalkovNode


fun <K:Any?> getChanceMap(map: Map<K,Int>) : MutableMap<K, Double> {
    var total = 0.0
    map.values.forEach {
        total+=it
    }
    val res = mutableMapOf<K,Double>()
    map.forEach { (key,value) ->
        res[key] = value/total
    }
    return res
}
fun <K:Any?> getRandom(map: Map<K,Double>) : K? {
    var chance = 0.0
    val random = Math.random()
    map.forEach { (key,value) ->
        chance+=value
        if(chance<=random) {
            return key
        }
    }
    return null
}