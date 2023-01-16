package desperate.giphytestcase.data.remote

import desperate.giphytestcase.BuildConfig
import desperate.giphytestcase.data.remote.model.GiphyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("trending?api_key=${BuildConfig.API_KEY}&limit=20")
    suspend fun getTrending(
        @Query("offset") offset: Int
    ) : Response<GiphyResponse>


    @GET("search?api_key=${BuildConfig.API_KEY}&limit=20&offset=0rating=g&lang=en")
    suspend fun searchGifs(
        @Query("q") query: String
    ): Response<GiphyResponse>

}