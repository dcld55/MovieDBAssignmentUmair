package id.indocyber.themoviedbumair.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.indocyber.api_service.service.*
import id.indocyber.api_service.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
class UsecaseModule {
    @Provides
    fun provideGenreUsecase(genreService: GenreService) = GenreUsecase(genreService)

    @Provides
    fun provideMovieUsecase(movieService: MovieService) = MovieUseCase(movieService)

    @Provides
    fun provideDetailUsecase(detailService: DetailService) = DetailUsecase(detailService)

    @Provides
    fun provideVideoUsecase(videoService: VideoService) = VideoUsecase(videoService)

    @Provides
    fun provideReviewUsecase(reviewService: ReviewService) = ReviewUsecase(reviewService)


}