package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.MovieService
import id.indocyber.common.entity.movie.Result

class MovieDataSource(
    private val movieService: MovieService,
    private val genres: String
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val result = movieService.getMovieByGenre(
                genres, page = params.key ?: 1
            )
            if (result.isSuccessful) {
                result.body()?.let {

                    LoadResult.Page(
                        data = it.results,
                        prevKey = if (it.results.isEmpty()) null else it.page - 1,
                        nextKey = it.page + 1
                    )
                } ?: LoadResult.Error(Exception("Error Backend: ${result.code()}"))

            } else {
                LoadResult.Error(Exception("Error Backend: ${result.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object MoviePager {
    fun createPager(
        pageSize: Int,
        movieService: MovieService,
        genres: String
    ): Pager<Int, Result> = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = { MovieDataSource(movieService, genres) }
    )
}