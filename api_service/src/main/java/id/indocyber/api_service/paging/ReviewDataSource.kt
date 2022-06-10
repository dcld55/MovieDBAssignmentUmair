package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.ReviewService
import id.indocyber.common.entity.review.Result


class ReviewDataSource(
    private val reviewService: ReviewService,
    private val movieId: Int
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val result = reviewService.getMovieReviews(
                movieId = movieId,
                page = params.key ?: 1
            )
            result.body()?.let {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (it.page == 1) null else it.page - 1,
                    nextKey = if (it.results.isEmpty()) null else it.page + 1
                )
            } ?: LoadResult.Error(java.lang.Exception("invalid"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object ReviewPager {
    fun createPager(
        pageSize: Int,
        reviewService: ReviewService,
        movieId: Int
    ): Pager<Int, Result> = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = { ReviewDataSource(reviewService, movieId) }
    )
}