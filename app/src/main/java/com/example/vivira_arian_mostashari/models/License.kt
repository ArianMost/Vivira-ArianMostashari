package com.example.vivira_arian_mostashari.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class License(

    @SerializedName("key") var key: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("spdx_id") var spdxId: String? = null,
    @SerializedName("node_id") var nodeId: String? = null,
    @SerializedName("html_url") var htmlUrl: String? = null

)