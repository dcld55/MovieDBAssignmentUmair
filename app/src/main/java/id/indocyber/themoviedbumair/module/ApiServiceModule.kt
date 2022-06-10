package id.indocyber.themoviedbumair.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.indocyber.api_service.service.*
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {
    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context) = RetrofitClient.getClient(context)

    @Provides
    @Singleton
    fun provideGenreService(retrofit: Retrofit) = retrofit.create(GenreService::class.java)

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideDetailService(retrofit: Retrofit) = retrofit.create(DetailService::class.java)

    @Provides
    @Singleton
    fun provideVideoService(retrofit: Retrofit) = retrofit.create(VideoService::class.java)

    @Provides
    @Singleton
    fun provideReviewService(retrofit: Retrofit) = retrofit.create(ReviewService::class.java)
}