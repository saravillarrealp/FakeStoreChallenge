package com.svillarreal.fakestorechallenge.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.svillarreal.fakestorechallenge.BuildConfig
import com.svillarreal.fakestorechallenge.data.local.db.AppDatabase
import com.svillarreal.fakestorechallenge.data.local.dao.FavoriteDao
import com.svillarreal.fakestorechallenge.data.local.dao.ProductDao
import com.svillarreal.fakestorechallenge.data.remote.api.ProductApi
import com.svillarreal.fakestorechallenge.data.repository.FavoriteRepositoryImpl
import com.svillarreal.fakestorechallenge.data.repository.ProductRepositoryImpl
import com.svillarreal.fakestorechallenge.data.repository.connectivity.ConnectivityRepositoryImpl
import com.svillarreal.fakestorechallenge.domain.repository.connectivity.ConnectivityRepository
import com.svillarreal.fakestorechallenge.domain.repository.FavoriteRepository
import com.svillarreal.fakestorechallenge.domain.repository.ProductRepository
import com.svillarreal.fakestorechallenge.domain.usecase.GetProductDetailUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.GetProductsUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveIsFavoriteUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveProductsUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.SetFavoriteUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ToggleFavoriteUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    @Named("unsafe")
    fun provideUnsafeRetrofit(moshi: Moshi, @Named("unsafe") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi, dao: ProductDao): ProductRepository =
        ProductRepositoryImpl(api, dao)

    @Provides
    @Singleton
    fun provideProductFavoriteRepository(dao: FavoriteDao): FavoriteRepository =
        FavoriteRepositoryImpl(dao)

    @Module
    @InstallIn(SingletonComponent::class)
    object ConnectivityModule {

        @Provides
        @Singleton
        fun provideConnectivityManager(
            @ApplicationContext context: Context
        ): ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        @Provides
        @Singleton
        fun provideConnectivityRepository(
            connectivityManager: ConnectivityManager
        ): ConnectivityRepository =
            ConnectivityRepositoryImpl(connectivityManager)
    }


    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao = database.productsDao()

    @Provides
    fun provideGetProductsUseCase(
        repository: ProductRepository
    ): GetProductsUseCase = GetProductsUseCase(repository)

    @Provides
    fun provideGetProductDetailUseCase(
        repository: ProductRepository
    ): GetProductDetailUseCase = GetProductDetailUseCase(repository)

    @Provides
    fun provideObserveIsFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ): ObserveIsFavoriteUseCase = ObserveIsFavoriteUseCase(favoriteRepository)

    @Provides
    fun provideToggleFavoriteUseCase(
        useCase: SetFavoriteUseCase
    ): ToggleFavoriteUseCase = ToggleFavoriteUseCase(useCase)

    @Provides
    fun provideSetFavoriteUseCase(
        repository: FavoriteRepository
    ): SetFavoriteUseCase = SetFavoriteUseCase(repository)

    @Provides
    fun provideObserveProductsUseCase(
        repository: ProductRepository
    ): ObserveProductsUseCase = ObserveProductsUseCase(repository)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DBConnectionModule {

        @Module
        @InstallIn(SingletonComponent::class)
        object DatabaseModule {

            @Provides
            @Singleton
            fun provideDb(@ApplicationContext context: Context): AppDatabase =
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "fake_store.db"
                ).fallbackToDestructiveMigration().build()

            @Provides
            fun provideFavoritesDao(db: AppDatabase): FavoriteDao = db.favoritesDao()
        }
    }
}