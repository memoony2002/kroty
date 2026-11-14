package com.kroty.kashaf.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * تمثيل لكائن "اكتشاف" داخل التطبيق.
 * يمثل أي مكان أو فكرة أو عنصر تم اكتشافه واستكشافه.
 */
@Parcelize
data class Discovery(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val location: String,
    val date: String
) : Parcelable
