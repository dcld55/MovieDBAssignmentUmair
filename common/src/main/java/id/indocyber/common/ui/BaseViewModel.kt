package id.indocyber.common.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import com.example.common.ext.SingleLiveEvent

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val navigationEvent = SingleLiveEvent<NavDirections>()
    val popBackStackEvent = SingleLiveEvent<Any>()
    var parent: BaseViewModel? = null
    fun navigate(nav: NavDirections) {
        navigationEvent.postValue(nav)
    }


    fun popBackStack() {
        popBackStackEvent.postValue(Any())
    }

}