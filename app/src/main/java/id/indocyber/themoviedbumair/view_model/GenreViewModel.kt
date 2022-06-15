package id.indocyber.themoviedbumair.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.SelectionTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.GenreUseCase
import id.indocyber.common.entity.genre.Genre
import id.indocyber.common.ui.AppResponse
import id.indocyber.common.ui.BaseViewModel
import id.indocyber.themoviedbumair.fragment.genre.GenreFragmentDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    application: Application,
    private val genreUsecase: GenreUseCase
) :
    BaseViewModel(application) {

    val genreLiveData = MutableLiveData<AppResponse<List<Genre>>>()
    var listGenre = arrayListOf<Genre>()
    var selectionTrack: SelectionTracker<Long>? = null //selection tracker

    init {
        getAllGenre()
    }

    fun getAllGenre() {
        viewModelScope.launch {
            genreUsecase.invoke().collect {
                genreLiveData.postValue(it)
            }
        }
    }

    fun toMovie() {
        val genre = selectionTrack?.selection?.toMutableList().orEmpty().joinToString(separator = ",")
        navigate(
            GenreFragmentDirections.actionGenreFragmentToMovieFragment(
                genre,
                listGenre.toTypedArray()
            )
        )
    }
}