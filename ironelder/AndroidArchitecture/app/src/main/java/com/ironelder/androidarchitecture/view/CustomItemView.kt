package com.ironelder.androidarchitecture.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.ironelder.androidarchitecture.R
import com.ironelder.androidarchitecture.common.BLOG
import com.ironelder.androidarchitecture.common.NEWS
import com.ironelder.androidarchitecture.data.Item
import kotlinx.android.synthetic.main.item_custom_item_view.view.*

class CustomItemView : ConstraintLayout {
    private val mItemType: String

    constructor(context: Context?, type: String) : super(context) {
        mItemType = type
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.item_custom_item_view, this, true)

    }

    fun setData(item: Item) {
        itemTitle.text = HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
        itemContent.text = HtmlCompat.fromHtml(
            item.description ?: item.director,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        when (mItemType) {
            BLOG, NEWS -> {
                itemImage.visibility = View.GONE
            }
            else -> {
                itemImage.visibility = View.VISIBLE
                Glide.with(context).load(item.image).centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background).into(itemImage)
            }
        }
    }
}