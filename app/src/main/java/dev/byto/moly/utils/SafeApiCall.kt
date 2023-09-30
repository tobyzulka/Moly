package dev.byto.moly.utils

import com.orhanobut.logger.Logger
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SafeApiCall @Inject constructor(){
    suspend inline fun <T> execute(crossinline body:suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(
                body.invoke()
            )
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val codeHttp = throwable.code()
                    val codeResponse = throwable.response()?.code()
                    val messageResponse = throwable.response()?.message()

                    Logger.d("HttpException: $codeHttp, $codeResponse, $messageResponse")
                    ResultWrapper.GenericError(codeHttp, codeResponse, messageResponse)
                }
                else -> {
                    Logger.d("GenericError ${throwable.message}")
                    ResultWrapper.GenericError(null, null, null)
                }
            }
        }
    }
}
