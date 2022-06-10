package id.indocyber.api_service.service

import id.indocyber.common.Constant.API_KEY
import id.indocyber.common.entity.detail_movie.MovieDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailService {
    @GET("movie/{movie_id}")
    suspend fun getDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = API_KEY
    ): Response<MovieDetailResponse>
}