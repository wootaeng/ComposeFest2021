package com.example.basicscodelab_wootaeng

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab_wootaeng.ui.theme.BasicsCodelab_wootaengTheme

/*
 온보딩 화면을 추가한 코드
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelab_wootaengTheme() {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {

//    var shouldShowOnboarding by remember { mutableStateOf(true) }
    //다른 작업시 화면이 초기화 되고 온보딩화면으로 가는것을 방지
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("어서와 코드랩이야!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

//리스트 목록 만들기
@Composable
private fun Greetings(names: List<String> = List(1000){"$it"}){
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)){
        items(items = names){ name ->
            Greeting(name = name)
        }
    }
}

////@Composable
////private fun Greetings(names: List<String> = listOf("World", "Compose")) {
////    Column(modifier = Modifier.padding(vertical = 4.dp)) {
////        for (name in names) {
////            Greeting(name = name)
////        }
////    }
//}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelab_wootaengTheme() {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Composable
private fun Greeting(name: String) {

    var expanded by remember { mutableStateOf(false) }

//    val extraPadding = if (expanded.value) 48.dp else 0.dp
    //애니메이션 추가
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        //스프링 효과추가
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                    //스프링 닫힐때 돌아오는 코드추가
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}
//다크모드 프리뷰도 볼수있게 추가
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelab_wootaengTheme() {
        Greetings()
    }
}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            //컴포즈는 프로젝트 이름에 따라 테마명이 정해진다
//            BasicsCodelab_wootaengTheme {
////                // A surface container using the 'background' color from the theme
////                Surface(color = MaterialTheme.colors.background) {
////                    Greeting("Android")
////                }
//                MyApp()
//            }
//        }
//    }
//}


////코틀린 함수처럼 사용
//@Composable
//fun MyApp(names: List<String> = listOf("World", "Compose")){
//
////    Column (modifier = Modifier.padding(vertical = 4.dp)){
////        for (name in names){
////            Greeting(name = name)
////        }
////
////    }
//}

//컴포저블 재사용
//@Composable
//private fun MyApp(){
//    Surface(color = MaterialTheme.colors.background) {
//        Greeting("Android")
//
//    }
//}

//@Composable
//private fun Greetings(names: List<String> = listOf("World", "Compose")) {
//    Column(modifier = Modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
//}

//@Composable
//fun Greeting(name: String) {
//    //expended 에 값을 설정하면 컴포즈가 상태변경으로 감지하지않는다.
////    var expended = false
//    //composable에 내부 상태를 추가하려면 mutableStateOf 함수를 사용하면
//    //함수를 재구성가능
//    //하지만 가변 코드만 작생해서는 동작하지않는다.
////    var expended = mutableStateOf(false)
//    //remember 를 사용해야 재구성을 방지하면서 상태가 재설정 되지않는다.
//    val expended = remember {mutableStateOf(false)}
//    //확장 코드 추가
//    val extraPadding = if (expended.value) 48.dp else 0.dp
//
//    Surface(color = MaterialTheme.colors.primary,
//    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(bottom = extraPadding)) {
//                Text(text = "Hello, ")
//                Text(text = name)
//            }
//            //onClick 시 동작하게한다
//            OutlinedButton(onClick = {expended.value = !expended.value }) {
//                //버튼 클릭시 변경
//                Text(if (expended.value) "Show less" else "Shoe more")
//            }
//        }
//    }




//    //배경색
//    Surface(color = MaterialTheme.colors.primary,
//    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(text = "Helloe, ")
//                Text(text = name)
//            }
//            OutlinedButton(onClick = { /*TODO*/ }) {
//                Text(text = "Show more")
//            }
//        }
//        Column(modifier = Modifier
//            .fillMaxWidth()
//            .padding(24.dp)) {
//            Text(text = "Hello, ")
//            Text(text = name)
//        }
//        //패딩 수정 modifier 로 수정가능
//        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
        //column 적용 //위아래로
//        Column(modifier = Modifier.padding(24.dp)) {
//            Text(text = "Hello,")
//            Text(text = name)
//            Text(text = "잘되가나")
//        }
//        //raw 적용 //옆으로
//        Row(modifier = Modifier.padding(24.dp)) {
//            Text(text = "Hello,")
//            Text(text = name)
//            Text(text = "잘되가나")
//        }
        //box 적용 //겹치기
//        Box(modifier = Modifier.padding(24.dp)) {
//            Text(text = "Hello,")
//            Text(text = name)
//            Text(text = "잘되가나")
//        }
//    }

//}




////미리보기를 사용하려면 @Preview 주석을 달고 프로젝트를 빌드하기만 하면 된다.
////너비를 지정하지않으면 알아서 채우나 너비를 지정하면 너비안에서 채워진다
//@Preview(showBackground = true, widthDp = 320)
//@Composable
//fun DefaultPreview() {
//    BasicsCodelab_wootaengTheme {
////        Greeting("Android")
//        Greetings()
//    }
//}