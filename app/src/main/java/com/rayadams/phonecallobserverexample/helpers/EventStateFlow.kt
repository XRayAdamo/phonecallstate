package com.rayadams.phonecallobserverexample.helpers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/***
 * Class used as Event flow.
 * Uses [notifyObservers] functionality to notify observers
 * Uses Unit as data type and do not store cached value so attaching to this flow will not raise it immediately
 */
@OptIn(ExperimentalCoroutinesApi::class)
class EventStateFlow : MutableStateFlow<Unit> {
    override var value: Unit = Unit
        set(value) {
            field = value
            innerFlow.tryEmit(Unit)
        }

    private val innerFlow = MutableSharedFlow<Unit>(replay = 1)

    override fun compareAndSet(expect: Unit, update: Unit): Boolean {
        value = update
        return true
    }

    override suspend fun emit(value: Unit) {
        this.value = Unit
    }

    override fun tryEmit(value: Unit): Boolean {
        this.value = Unit
        return true
    }

    /***
     * Notify subscribers
     */
    fun notifyObservers() {
        innerFlow.tryEmit(Unit)
        resetReplayCache()
    }

    override val subscriptionCount: StateFlow<Int> = innerFlow.subscriptionCount

    override fun resetReplayCache() = innerFlow.resetReplayCache()
    override suspend fun collect(collector: FlowCollector<Unit>): Nothing = innerFlow.collect(collector)
    override val replayCache: List<Unit> = innerFlow.replayCache
}
