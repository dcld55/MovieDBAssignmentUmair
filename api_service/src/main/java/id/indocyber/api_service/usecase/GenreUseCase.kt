package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.GenreService
import id.indocyber.common.entity.genre.Genre
import id.indocyber.common.ui.AppResponse
import kotlinx.coroutines.flow.flow

class GenreUseCase(val genreService: GenreService) {
    operator fun invoke() = flow<AppResponse<List<Genre>>> {
        try {
            emit(AppResponse.loading())
            val result = genreService.getGenre()
            if (result.isSuccessful) {
                result.body()?.let {
                    emit(AppResponse.success(it.genres))
                }
            }
        } catch (e: Exception) {
            emit(AppResponse.failure(e))
        }
    }
}