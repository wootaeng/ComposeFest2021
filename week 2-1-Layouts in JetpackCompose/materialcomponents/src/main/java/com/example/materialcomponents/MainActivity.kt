package com.example.materialcomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.materialcomponents.ui.theme.LayoutsInJetpackCompose_wootaengTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsInJetpackCompose_wootaengTheme {
                // A surface container using the 'background' color from the theme
                LayoutsCodelab()
            }
        }
    }
}



@Composable
fun LayoutsCodelab() {
    //머터리얼 구조를 가질 수 있도록 컴포저블 추가
    Scaffold(
        //topbar 추가
        topBar = {
            //TopAppBar 추가
            TopAppBar(
                title = {
                    Text(text = "Layout 코드랩")
                },
                //아이콘 추가
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
//            Text(text = "Layout 코드랩",
//            style = MaterialTheme.typography.h3)

        }
    ) { innerPadding->
        //Column 추가
//        Column(modifier = Modifier.padding(innerPadding)) {
//            Text(text = "Hi there")
//            Text(text = "코드랩은 어렵구마")
//        }
        //위에 코드와 동일. 코드구역을 나눈것뿐
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp))
    }

}
// fun BodyContent 에 직접 패딩을 설정하는 방법도 있고
// Column(modifier = modifier.padding(8.dp))
// layoutcodelab 의 내부에서 적용을 하는 두가지 방법이 있다
// BodyContent(Modifier.padding(innerPadding).padding(8.dp))
//코드를 나누기
@Composable
fun BodyContent(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Text(text = "Hi there")
        Text(text = "코드랩은 어렵구마")
    }
}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    LayoutsInJetpackCompose_wootaengTheme {
        LayoutsCodelab()
    }
}