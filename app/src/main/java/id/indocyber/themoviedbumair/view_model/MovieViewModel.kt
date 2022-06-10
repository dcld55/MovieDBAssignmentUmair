package id.indocyber.themoviedbumair.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.MovieUseCase
import id.indocyber.common.entity.movie.Result
import id.indocyber.common.ui.BaseViewModel
import id.indocyber.themoviedbumair.fragment.movie.MovieFragmentDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    application: Application,
    private val movieUseCase: MovieUseCase
) : BaseViewModel(application) {

    val moviePagingLiveData = MutableLiveData<PagingData<Result>>()

    fun getMovie(genreIds: String) {
        if (moviePagingLiveData.value == null) {

            viewModelScope.launch {
                movieUseCase.invoke(genreIds).cachedIn(viewModelScope).collect {
                    moviePagingLiveData.postValue(it)
                }
            }
        } else {
            moviePagingLiveData.postValue(moviePagingLiveData.value)
        }
    }

    fun toDetail(movieId: Int) {
        navigate(
            MovieFragmentDirections.toDetail(
                movieId
            )
        )
    }
}