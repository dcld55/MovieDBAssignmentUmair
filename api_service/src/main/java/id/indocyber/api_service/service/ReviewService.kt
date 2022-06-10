package id.indocyber.api_service.service

import id.indocyber.common.Constant.API_KEY
import id.indocyber.common.entity.review.ReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ) : Response<ReviewResponse>
}