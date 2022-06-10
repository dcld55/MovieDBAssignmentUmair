package com.example.common.ext

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean


fun <T> singleLiveEventOf() = SingleLiveEvent<T>()
fun <T> mutableLiveDataOf() = MutableLiveData<T>()

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer<T> { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    override fun postValue(value: T) {
        mPending.set(true)
        super.postValue(value)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }


}
    //toggle

    fun <T> ArrayList<T>.toggle(t:T){
        if(this.contains(t)){
            this.remove(t)
        } else {
            this.add(t)
        }
    }

    //ext untuk MutableLiveData


    fun <T> MutableLiveData<List<T>>.toggle(t: T) {
        val data = this.value.orEmpty()
        val ar = arrayListOf<T>().apply { this.addAll(data) }
        ar.toggle(t)
        value = ar
    }


    fun <T> MutableLiveData<List<T>>.size(): Int {
        val data = this.value.orEmpty()
        return data.size
    }

    fun <T> MutableLiveData<List<T>>.isEmpty(): Boolean {
        val data = this.value.orEmpty()
        return data.isEmpty()
    }

    fun <T> MutableLiveData<List<T>>.clear() {
        this.value = arrayListOf()
    }

