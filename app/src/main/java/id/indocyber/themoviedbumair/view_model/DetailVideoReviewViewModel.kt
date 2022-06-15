package id.indocyber.themoviedbumair.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.DetailUseCase
import id.indocyber.api_service.usecase.ReviewUseCase
import id.indocyber.api_service.usecase.VideoUseCase
import id.indocyber.common.entity.detail_movie.MovieDetailResponse
import id.indocyber.common.entity.review.Result
import id.indocyber.common.entity.video.MovieVideoResponse
import id.indocyber.common.ui.AppResponse
import id.indocyber.common.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailVideoReviewViewModel @Inject constructor(
    application: Application,
    val detailUsecase: DetailUseCase,
    val videoUsecase: VideoUseCase,
    val reviewUsecase: ReviewUseCase
) : BaseViewModel(application) {
    val getReviewData = MutableLiveData<PagingData<Result>>()
    val getDetail = MutableLiveData<AppResponse<MovieDetailResponse>>()
    val getVideo = MutableLiveData<AppResponse<MovieVideoResponse>>()



    fun getAllDetail(movieId: Int) {
        viewModelScope.launch {
            detailUsecase(movieId).collect {
                getDetail.postValue(it)
            }
            videoUsecase(movieId).collect {
                getVideo.postValue(it)
            }
            reviewUsecase(movieId).cachedIn(viewModelScope).collect {
                getReviewData.postValue(it)
            }
        }
    }





}