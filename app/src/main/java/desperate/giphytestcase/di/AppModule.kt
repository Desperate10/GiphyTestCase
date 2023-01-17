package desperate.giphytestcase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import desperate.giphytestcase.data.local.GiphyDatabase
import desperate.giphytestcase.data.remote.GiphyApi
import desperate.giphytestcase.data.repository.GiphyRepositoryImpl
import desperate.giphytestcase.domain.repository.GiphyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(giphyApi: GiphyApi, giphyDb: GiphyDatabase) : GiphyRepository {
        return GiphyRepositoryImpl(giphyApi, giphyDb)
    }
}