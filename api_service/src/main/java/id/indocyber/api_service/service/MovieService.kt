package id.indocyber.api_service.service

import id.indocyber.common.Constant.API_KEY
import id.indocyber.common.entity.movie.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("with_genres") genres: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ) : Response<MovieResponse>
}