package study.architecture.myarchitecture.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import study.architecture.myarchitecture.R
import study.architecture.myarchitecture.ui.main.MainAdapter
import study.architecture.myarchitecture.ui.model.TickerItem
import study.architecture.myarchitecture.ui.tickerlist.TickerAdapter

@BindingAdapter(value = ["android:setMarkets", "android:setTitles"], requireAll = true)
fun ViewPager.setMainItems(markets: Array<String>?, titles: Array<String>?) {
    (adapter as? MainAdapter)?.run {

        if (markets == null || titles == null) return

        setItems(markets)
        setTitles(titles)
        notifyDataSetChanged()
    }
}

@BindingAdapter("android:setItems")
fun RecyclerView.setItems(tickerItems: MutableList<TickerItem>?) {
    (adapter as? TickerAdapter)?.run {

        if (tickerItems != null) {
            setItems(tickerItems)
        }
    }
}

@BindingAdapter("android:isSelected")
fun ImageView.isSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("android:tradeDiffColor")
fun TextView.setTradeDiffColor(signedChangeRate: Double) {

    val color = if (signedChangeRate > 0) {
        ContextCompat.getColor(context, R.color.diff_up)
    } else if (signedChangeRate < 0) {
        ContextCompat.getColor(context, R.color.diff_down)
    } else {
        ContextCompat.getColor(context, R.color.gray5)
    }

    this.setTextColor(color)
}
