package com.example.customerlist.customerlist

import androidx.databinding.BindingAdapter
import com.example.customerlist.database.Customer
import xyz.schwaab.avvylib.AvatarView

@BindingAdapter("avatarText")
fun AvatarView.setAvatarText(item: Customer) {
    text = item.name
}
