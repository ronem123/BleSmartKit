package com.ram.mandal.blesmartkit.di.module

import android.content.Context
import androidx.work.WorkManager
import com.ram.mandal.blesmartkit.core.dispatcher.DefaultDispatcherProvider
import com.ram.mandal.blesmartkit.core.dispatcher.DispatcherProvider
import com.ram.mandal.blesmartkit.core.logger.AppLogger
import com.ram.mandal.blesmartkit.core.logger.Logger
import com.ram.mandal.blesmartkit.core.networkhelper.NetworkHelper
import com.ram.mandal.blesmartkit.core.networkhelper.NetworkHelperImpl
import com.ram.mandal.blesmartkit.data.network.ApiService
import com.ram.mandal.blesmartkit.data.network.DNTHeaderInterceptor
import com.ram.mandal.blesmartkit.di.ApiKey
import com.ram.mandal.blesmartkit.di.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @ApiKey
    @Provides
    fun provideApiKey(): String = "IwAR3ZEbtRGyHewHmtWX4pKXii7cn04A2DlQ8RAc5NQutk1hjTRcXKtVyHMGQ"

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://www.nepalitools.com/"


    @Singleton
    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonFactory: GsonConverterFactory,
        apiKeyInterceptor: DNTHeaderInterceptor
    ): ApiService {

        val client = OkHttpClient
            .Builder()
            .addInterceptor(apiKeyInterceptor)

//        if (BuildConfig.DEBUG) {
//            val loggingInterceptor = HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
//            client.addInterceptor(loggingInterceptor)
//        }

        return Retrofit
            .Builder()
            .client(client.build()) //adding client to intercept all responses
            .baseUrl(baseUrl)
            .addConverterFactory(gsonFactory)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger = AppLogger()

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()


    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }

}