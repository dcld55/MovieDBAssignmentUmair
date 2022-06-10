package id.indocyber.api_service.service

import id.indocyber.common.Constant.API_KEY
import id.indocyber.common.entity.genre.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {
    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<GenreResponse>
}