package net.propromp.malkov

import net.propromp.malkov.util.getChanceMap
import net.propromp.malkov.util.getRandom

/**
 * Malkov node
 *
 * @param T t
 * @property label label of the node
 * @constructor Create empty Malkov node
 */
class MalkovNode<T>(val label:T) {
    private val children = mutableMapOf<MalkovNode<T>?,Int>()

    /**
     * Learn
     *
     * @param node next node
     */
    fun learn(node:MalkovNode<T>?) {
        children[node]=(children[node]?:0)+1
    }

    /**
     * Get next node
     *
     * @return
     */
    fun getNextNode():MalkovNode<T>? {
        return getRandom(getChanceMap(children))
    }
}