package com.example.vivira_arian_mostashari.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Repositories (
    @SerializedName("total_count")
    var totalCount: Int? = null,
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null,
    @SerializedName("items")
    var items: ArrayList<Item> = arrayListOf()

)