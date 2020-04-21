package ua.lukyanov.catalogue.network

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import ua.lukyanov.catalogue.util.Resource
import java.io.IOException

object Network {

    suspend fun <T> request(call: Deferred<Response<T>>): Resource<T> {
        return try {
            val response = call.await()
            if (response.isSuccessful){
                Resource.success(response.body())
            }else {
                Resource.error(getErrorMessage(response.errorBody()))
            }

        } catch (httpException: HttpException) {
            Resource.error("Connection error")
        } catch (ioException: IOException) {
            Resource.error("Connection error")
        }
    }

    private fun getErrorMessage(errorBody: ResponseBody?): String{
        var errorMsg = "Something was wrong"
        try {
            val jObjError = JSONObject(errorBody?.string() ?: "{}")
            errorMsg = jObjError.getString("message")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return errorMsg
    }
}