package id.indocyber.api_service.usecase

import id.indocyber.api_service.paging.MoviePager
import id.indocyber.api_service.service.MovieService
import id.indocyber.common.Constant

class MovieUseCase(
    val movieService: MovieService
) {
    operator fun invoke(genre: String) =
        MoviePager.createPager(Constant.BASE_PAGE_SIZE, movieService, genre).flow
}