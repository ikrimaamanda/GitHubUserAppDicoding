package com.ikrima.practice.dicoding.githubuserappdicoding.data.responses

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("total_count") val totalCount: Int? = 0,
    val items: List<DetailUserResponse>
)
