package fastcampus.aop.part3.chapter04

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import fastcampus.aop.part3.chapter04.api.BookService
import fastcampus.aop.part3.chapter04.model.BestSellerDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks("C6831E3682CDC7A02D55D54967E6C005D41C0DDA3120947FACD9FF757F6C3334")
            .enqueue(object: Callback<BestSellerDto>{
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {
                    //성공처리
                    if(response.isSuccessful.not()){
                        return
                    }

                    response.body()?.let {
                        Log.d(TAG, it.toString())

                        it.books.forEach { book ->
                            Log.d(TAG, book.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    //실패처리

                    Log.e(TAG, t.toString())
                }

            })
    }
    companion object {
        private const val TAG = "MainActivity"
    }
}