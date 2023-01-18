package desperate.giphytestcase.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import desperate.giphytestcase.data.local.GiphyDatabase
import desperate.giphytestcase.data.remote.GiphyApi
import desperate.giphytestcase.data.remote.connectivity.ConnectivityObserver
import desperate.giphytestcase.data.remote.connectivity.NetworkConnectivityObserver
import desperate.giphytestcase.data.repository.GiphyRepositoryImpl
import desperate.giphytestcase.domain.repository.GiphyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideRepository(giphyApi: GiphyApi, giphyDb: GiphyDatabase) : GiphyRepository {
        return GiphyRepositoryImpl(giphyApi, giphyDb)
    }

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideNetworkObserver(@ApplicationContext context: Context) : ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }
}