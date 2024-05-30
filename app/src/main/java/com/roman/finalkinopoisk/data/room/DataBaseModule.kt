package com.roman.finalkinopoisk.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Provides
    fun provideCollectionDao(collectionDB:DataBaseFilms):CollectionDao{
        return collectionDB.collectionDao()
    }
    @Provides
    fun provideFilmsDao(filmDB:DataBaseFilms):FilmsDao{
        return filmDB.filmsDao()
    }
    @Provides
    fun provideStaffDao(staffDB:DataBaseFilms):StaffDao{
        return staffDB.staffDao()
    }

    @Provides
    @Singleton
    fun ProvideAppDatabase(@ApplicationContext appContext: Context): DataBaseFilms {
        return Room.databaseBuilder(
            appContext,
            DataBaseFilms::class. java,
            "Data_base"
        ).build()
    }

}