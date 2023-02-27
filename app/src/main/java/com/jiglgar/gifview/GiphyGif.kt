package com.jiglgar.gifview

data class GifItem(
    val id: String,
    val title: String,
    val url: String
)

data class GiphyResponse(
    val data: List<GifItem>
)





