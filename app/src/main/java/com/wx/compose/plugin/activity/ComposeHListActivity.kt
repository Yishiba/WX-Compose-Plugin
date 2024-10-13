package com.wx.compose.plugin.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wx.compose1.ui.composable.baseUI
import com.wx.compose1.ui.theme.WXComposePlugin
import com.wx.compose.plugin.viewmodel.ComposeViewModel
import kotlinx.coroutines.launch

class ComposeHListActivity : ComponentActivity() {

    val viewModel by viewModels<ComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXComposePlugin {
                    baseUI(content = { paddingvalues ->
                        ListExample2(paddingvalues, viewModel)
                    }, onClick = {
                        finish()
                    })
                }
            }
        }
        viewModel.add()
    }
}

@Composable
fun ListExample2(innerPadding: PaddingValues, viewModel: ComposeViewModel) {
    val datas by viewModel.datas.observeAsState(initial = emptyList())
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(innerPadding)
            .background(Color.Red)
    ) {
        items(datas) { data ->
            Column(
                modifier = Modifier
                    .width(80.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = data.resID), contentDescription = "小姐姐", modifier = Modifier
                        .wrapContentWidth()
                        .size(80.dp)
                        .padding(5.dp), contentScale = ContentScale.Fit
                )
                AsyncImage(
                    modifier = Modifier.wrapContentWidth(), model = data.imgUrl, contentDescription = "", contentScale = ContentScale.Crop
                )
                val modelBuilder = ImageRequest.Builder(LocalContext.current).data(data.imgUrl ?: "").crossfade(false).allowHardware(true).build()
                Image(
                    painter = rememberAsyncImagePainter(model = modelBuilder), contentDescription = "小姐姐", modifier = Modifier
                        .wrapContentWidth()
                        .size(80.dp)
                        .padding(5.dp), contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .background(Color.Yellow)
                        .border(2.dp, SolidColor(Color.Green), RoundedCornerShape(20.dp))
                        .padding(0.dp, 5.dp, 0.dp, 0.dp),
                    fontSize = 16.sp,
                    text = data.title,
                    textAlign = TextAlign.Center,
                    color = Color.Magenta,
                    softWrap = true
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .background(Color.Yellow)
                        .border(2.dp, SolidColor(Color.Green), RoundedCornerShape(20.dp))
                        .padding(0.dp, 5.dp, 0.dp, 0.dp), fontSize = 16.sp, text = data.title, textAlign = TextAlign.Center, color = Color.Magenta
                )
            }
        }
    }
}




