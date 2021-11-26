package com.example.layoutsinjetpackcompose_wootaeng

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layoutsinjetpackcompose_wootaeng.ui.theme.LayoutsInJetpackCompose_wootaengTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsInJetpackCompose_wootaengTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PhotographerCardPreview()
                }
            }
        }
    }
}

//modifier 수정자를 사용하여 유연하게 수정할 수 있게 한다
@Composable
fun PhotographerCard(modifier: Modifier = Modifier){
    //클릭이 가능하게 수정
    //모서리를 둥글
    Row(modifier.padding(8.dp).clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface).clickable(onClick = {})
        .padding(16.dp)) {
        Surface(modifier = Modifier.size(50.dp),
        shape = CircleShape,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
        //이미지 위치
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)

        ) {
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)

            }
        }
    }


}


@Preview
@Composable
fun PhotographerCardPreview(){
    LayoutsInJetpackCompose_wootaengTheme {
        PhotographerCard()

    }
}

