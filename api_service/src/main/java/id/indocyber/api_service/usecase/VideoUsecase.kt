package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.VideoService
import id.indocyber.common.entity.video.MovieVideoResponse
import id.indocyber.common.ui.AppResponse
import kotlinx.coroutines.flow.flow

class VideoUsecase(
    val videoService: VideoService
) {
    operator fun invoke(movieId: Int) = flow<AppResponse<MovieVideoResponse>> {
        try {
            emit(AppResponse.loading())
            val result = videoService.getMovieVideo(movieId)
            if (result.isSuccessful) {
                result.body()?.let {
                    emit(AppResponse.success(it))
                }
            }
        } catch (e: Exception) {
            emit(AppResponse.failure(e))
        }
    }
}