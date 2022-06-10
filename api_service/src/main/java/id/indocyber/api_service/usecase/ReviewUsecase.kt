package id.indocyber.api_service.usecase

import id.indocyber.api_service.paging.ReviewPager
import id.indocyber.api_service.service.ReviewService
import id.indocyber.common.Constant

class ReviewUsecase(
    val reviewService: ReviewService
) {
    operator fun invoke(movieId: Int) =
        ReviewPager.createPager(Constant.BASE_PAGE_SIZE, reviewService, movieId).flow
}