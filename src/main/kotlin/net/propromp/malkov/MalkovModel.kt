package net.propromp.malkov

import net.propromp.malkov.util.getChanceMap
import net.propromp.malkov.util.getRandom

/**
 * Malkov model
 *
 * @param T type
 * @constructor Create empty Malkov model
 */
open class MalkovModel<T> {
    private val firstNodes = mutableMapOf<MalkovNode<T>,Int>()
    private val nodes = mutableMapOf<T,MalkovNode<T>>()

    /**
     * Get a chain.
     *
     * @param node first node
     * @param minLength min length of the chain
     * caution: If this model hasn't learned enough, this function will throw StackOverFlowError.
     * @param maxLength max length of the chain
     * @return chain
     */
    fun getChain(node:MalkovNode<T>,minLength:Int=0,maxLength:Int=100):List<MalkovNode<T>> {
        var res = mutableListOf<MalkovNode<T>>()
        var length = 0
        while(true) {
            res.add(res.last().getNextNode()?:break)
            length++
            // check maxLength
            if(maxLength<length) {
                break
            }
        }
        // check minLength
        if(length<minLength) {
            res = getChain(node,minLength, maxLength).toMutableList()
        }
        return res
    }

    /**
     * Get a chain without specifying the first node.
     *
     * @param minLength min length of the chain
     * caution: If this model hasn't learned enough, this function will throw StackOverFlowError.
     * @param maxLength max length of the chain
     * @return chain
     */
    fun getChainFromNothing(minLength:Int=0,maxLength:Int=100):List<MalkovNode<T>> {
        return getChain(getFirstNode(),minLength, maxLength)
    }

    /**
     * Get a chain as T list.
     *
     * @param node first node
     * @param minLength min length of the chain
     * caution: If this model hasn't learned enough, this function will throw StackOverFlowError.
     * @param maxLength max length of the chain
     * @return chain
     */
    fun getChainAsTList(node:MalkovNode<T>,minLength:Int=0,maxLength:Int=100):List<T> {
        return getChain(node, minLength, maxLength).map { it.label }
    }

    /**
     * Get a chain without specifying the first node as T list.
     *
     * @param minLength min length of the chain
     * caution: If this model hasn't learned enough, this function will throw StackOverFlowError.
     * @param maxLength max length of the chain
     * @return chain
     */
    fun getChainAsTListFromNothing(minLength:Int=0,maxLength:Int=100):List<T> {
        return getChainFromNothing(minLength, maxLength).map { it.label }
    }

    /**
     * Get a first node by random.
     *
     * @return
     */
    fun getFirstNode(): MalkovNode<T> {
        return getRandom(getChanceMap(firstNodes))!!
    }

    /**
     * Learn from chain.
     *
     * @param chain
     */
    fun learn(chain:List<T>) {
        learnFirstNode(getOrCreateNode(chain[0]))
        for((i,t) in chain.withIndex()) {
            nodes[t]=getOrCreateNode(t).also{it.learn(getOrCreateNode(chain[i+1]?:return@also))}
        }
        nodes[chain.last()] = getOrCreateNode(chain.last()).also {it.learn(null)}
    }

    /**
     * Learn first node will be used by [getChainFromNothing] or [getChainAsTListFromNothing]
     *
     * @param node
     */
    fun learnFirstNode(node:MalkovNode<T>) {
        firstNodes[node]=(firstNodes[node]?:0)+1
    }

    /**
     * Get or create node if absent
     *
     * @param t
     * @return
     */
    fun getOrCreateNode(t:T):MalkovNode<T> {
        return nodes[t]?:MalkovNode(t).also{nodes[t]=it}
    }
}