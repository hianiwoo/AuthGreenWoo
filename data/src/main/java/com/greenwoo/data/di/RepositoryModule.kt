package com.greenwoo.data.di

import com.greenwoo.data.repository.*
import com.greenwoo.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()

    @Provides
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()

    @Provides
    fun provideModuleRepository(): ModuleRepository = ModuleRepositoryImpl()

    @Provides
    fun provideFolderRepository(): FolderRepository = FolderRepositoryImpl()

    @Provides
    fun provideCourseRepository(): CourseRepository = CourseRepositoryImpl()

    @Provides
    fun provideConnectRepository(): ConnectRepository = ConnectRepositoryImpl()
}