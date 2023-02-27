package com.jiglgar.gifview

import android.content.res.Resources
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<GiphyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gifList = viewModel.gifList.observeAsState()
            if (gifList.value == null) {
                // Loading state
                CircularProgressIndicator()
            } else {
                // Loaded state
                GifList(gifList.value!!)
            }
            viewModel.getTrendingGifs("5etRp1q7SrNN8ogeuz1zeC5eISbUZBhk", 20)
        }
    }

}

@Composable
fun GifList(gifList: List<GifItem>) {
    LazyColumn {
        items(gifList) { gif ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    val imageLoader = ImageLoader.Builder(LocalContext.current)
                        .components {
                            if (SDK_INT >= 28) {
                                add(ImageDecoderDecoder.Factory())
                            } else {
                                add(GifDecoder.Factory())
                            }
                        }
                        .build()

                    //Test
                    //AsyncImage(R.drawable.work1 , contentDescription = null)

                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(gif.url)
                                .apply(block = {
                                    size(Size.ORIGINAL)
                                }).build(), imageLoader = imageLoader
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                    )

                }
            }
        }
    }
}

