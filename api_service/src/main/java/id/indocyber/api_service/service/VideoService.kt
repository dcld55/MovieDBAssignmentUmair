package id.indocyber.api_service.service

import id.indocyber.common.Constant.API_KEY
import id.indocyber.common.entity.video.MovieVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoService {
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = API_KEY
    ) : Response<MovieVideoResponse>
}