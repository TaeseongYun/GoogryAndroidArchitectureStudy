package com.example.architecturestudy.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturestudy.R
import com.example.architecturestudy.data.model.MovieData
import com.example.architecturestudy.network.Api
import com.example.architecturestudy.network.ApiClient
import kotlinx.android.synthetic.main.fragment_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment() {

    val restClient: Api = ApiClient.getRetrofitService(Api::class.java)

    private lateinit var movieAdapter : MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieAdapter = MovieAdapter()
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_search.setOnClickListener {
            if(input_text != null) {
                val edit = edit_text.text.toString()
                searchMovieList(edit)
            }
        }
    }


    private fun searchMovieList(keyWord: String) {
        restClient.requestMovies(keyWord).enqueue(object : Callback<MovieData> {

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                error(message = t.toString())
            }

            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                if(response.isSuccessful) {
                    response.body()?.items?.let {
                        movieListAdapter()
                        movieAdapter.update(it)
                    }
                }
            }
        })
    }

    private fun movieListAdapter() {
        recycleview.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            )
        }
    }
}