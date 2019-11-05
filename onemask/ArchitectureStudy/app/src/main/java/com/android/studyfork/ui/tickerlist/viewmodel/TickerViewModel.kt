package com.android.studyfork.ui.tickerlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.studyfork.base.BaseViewModel
import com.android.studyfork.network.model.Ticker
import com.android.studyfork.repository.UpbitRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class TickerViewModel : BaseViewModel() {

    private val _tickerList = MutableLiveData<List<Ticker>>()
    val tickerList : LiveData<List<Ticker>>
    get() = _tickerList

    fun getTicker(market: String) {
        UpbitRepositoryImpl.getTickers(market)
            .map { tickerList ->
                tickerList.map {
                    it.toTicker()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _tickerList.postValue(it)
            }, { it.printStackTrace() })
            .addTo(disposable)
    }
}

