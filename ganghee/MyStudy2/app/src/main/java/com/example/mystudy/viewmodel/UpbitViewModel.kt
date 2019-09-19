package com.example.mystudy.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.mystudy.adapter.TickerAdapter
import com.example.mystudy.adapter.ViewPagerAdapter
import com.example.mystudy.data.UpbitRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpbitViewModel(
    private val repository: UpbitRepository,
    private val upbitAdapter: TickerAdapter
) : ViewModel() {

    val tickerAdapter = ObservableField<TickerAdapter>()

    @SuppressLint("CheckResult")
    fun getTicker(firstMarket: String?) {
        repository.getMarket()
            .observeOn(Schedulers.newThread())
            .subscribe { it ->
                repository.getTicker(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        it.filter { TickerResponse ->
                            TickerResponse.market.split("-")[0] == firstMarket
                        }
                    }
                    .subscribe({
                        upbitAdapter.setData(it.map { it.toTicker() })

                    }, {
                        showFailedUpbitTickerList()
                    })
            }
    }

    private fun showFailedUpbitTickerList() {
        Log.d("TickerFail", "Ticker is not show")
    }
}