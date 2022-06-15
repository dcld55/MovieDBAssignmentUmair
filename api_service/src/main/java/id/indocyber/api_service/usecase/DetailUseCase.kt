package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.DetailService
import id.indocyber.common.entity.detail_movie.MovieDetailResponse
import id.indocyber.common.ui.AppResponse
import kotlinx.coroutines.flow.flow

class DetailUseCase(
    private val detailService: DetailService
) {
    operator fun invoke(movieId: Int) = flow<AppResponse<MovieDetailResponse>> {
        try {
            emit(AppResponse.loading())
            val result = detailService.getDetail(movieId)
            if(result.isSuccessful){
                result.body()?.let {
                    emit(AppResponse.success(it))
                }
            }
        } catch (e:Exception){
            emit(AppResponse.failure(e))
        }
    }
}