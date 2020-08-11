package com.example.mygw.dagger.module

import dagger.Module


/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
class NetworkModule {

//    /**
//     * Provides the Car service implementation.
//     * @param retrofit the Retrofit object used to instantiate the service
//     * @return the Car service implementation.
//     */
//    @Provides
//    internal fun providePostApi(retrofit: Retrofit): CarApi {
//        return retrofit.create(CarApi::class.java)
//    }
//
//    /**
//     * Provides the Retrofit object.
//     * @return the Retrofit object
//     */
//    @Provides
//    internal fun provideRetrofitInterface(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//            .build()
//    }
//
//    @Provides
//    internal fun provideCarDao(context : Context): CarDao {
//        return Room.databaseBuilder(context, AppDatabase::class.java, "cars").build().carDao()
//    }

}