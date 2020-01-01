package com.siwon.prj.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.siwon.prj.*
import com.siwon.prj.repository.MovieSearchRepository
import com.siwon.prj.repository.MovieSearchRepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val CLIENT_ID = "KjLtxBCCy8ZTWORQ7uas"
//    private val CLIENT_SECRET = "PHBD6IlPgd"
//    private val BASE_URL = "https://openapi.naver.com"
//    lateinit var retrofit: Retrofit
//    lateinit var apiService: MovieSearchService
    lateinit var repository: MovieSearchRepository
    lateinit var mAdapter: MovieAdapter
    var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
//        apiServiceSet()
        mAdapter = MovieAdapter { link: String -> itemClick(link) }
        repository = MovieSearchRepositoryImpl()

        // 검색버튼
        search_btn.setOnClickListener {
            if (edit_text_input.text.isNullOrBlank()) {
                // scope함수중에 null체크해주는거 사용
                // with는 확장함수개념 null 체크안돼
                toast?.let {
                    it.setText("검색어를 입력해주세요.")
                    it.show()
                }
                // 토스트 재활용 가능하도록..!!
                // 토스트 보다는 스낵바 사용
            }else{
                toast?.let {
                    it.setText("입력한 겁색어: ${edit_text_input.text.toString()}")
                    it.show()
                }

                getMovies(edit_text_input.text.toString())
//                apiService.search(CLIENT_ID, CLIENT_SECRET, edit_text_input.text.toString()).enqueue(object : Callback<Movies>{
//                    override fun onFailure(call: Call<Movies>, t: Throwable) {
//                        Log.i("로그로그", "####실패메시지: ${t.message}")
//                        toast?.let {
//                            it.setText("검색에 실패하였습니다.")
//                            it.show()
//                        }
//                    }
//
//                    override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
//                        Log.i("로그로그", "####응답: ${response.body().toString()}\n ${response.message()}")
//                        mAdapter.setItems(response.body()!!.movies)
//                        movieListRv.adapter = mAdapter
//                    }
//                })
            }
        }
    }

    fun getMovies(input: String) {
        repository.searchMovies(input,
            success = {
                mAdapter.setItems(it)
                movieListRv.adapter = mAdapter
            },
            fail = {
                Log.e("에러", "에러메시지: ${it.message}")
            })
    }
//    fun apiServiceSet() {
//        retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        apiService = retrofit.create(MovieSearchService::class.java)
//    }

    fun itemClick(link: String){
        val detailWebview = Intent(this@MainActivity, DetailWebview::class.java)
        detailWebview.putExtra("link", link)
        startActivity(detailWebview)
    }
}
