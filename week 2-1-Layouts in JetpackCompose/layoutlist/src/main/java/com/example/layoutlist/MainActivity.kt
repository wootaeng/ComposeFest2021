package com.example.layoutlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.layoutlist.ui.theme.LayoutsInJetpackCompose_wootaengTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsInJetpackCompose_wootaengTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    SimpleList()
//                    ScrollingList()
//                    TextWithNormalPaddingPreview()
//                    TextWithPaddingToBaselinePreview()
//                    BodyContent()
//                    ChipPreview()
//                    BodyContent2()
//                    ConstraintLayoutContentPreview()
                    TwoTextsPreview()

                }
            }
        }
    }
}

//리스트만들기
@Composable
fun SimpleList(){
//    //스크롤 기능 추가
//    val scrollState = rememberScrollState()
//
//    //스크롤 기능 추가
//    Column(Modifier.verticalScroll(scrollState)) {
//        repeat(100){
//            Text("Item #$it")
//        }
//    }

    //낭비되는 메모리를 방지하기 위해 LazyColumn 사용
    //LazyColumn = recyclerView
    //스크롤 기능 축
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState){
        items(20){
//            Text("Item #$it")
            //이미지로 적용
            ImageListItem(index = it)
        }
    }

}

@Composable
fun ScrollingList() {
    val listSize = 100
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    // 0 is the first item index
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }
}

@Composable
fun ImageListItem(index: Int){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ), contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    LayoutsInJetpackCompose_wootaengTheme {
////        SimpleList()
////        ScrollingList()
//    }
//}

/**
 * ====================================================
 */
//custom layout 구조
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // 여백이 있는 컴포저블의 높이 - 첫번째 베이스라인
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // 컴포저블이 자리잡을 위치
            placeable.placeRelative(0, placeableY)
        }
    }
)

//@Preview
@Composable
fun TextWithPaddingToBaselinePreview(){
    LayoutsInJetpackCompose_wootaengTheme() {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

//@Preview
@Composable
fun TextWithNormalPaddingPreview(){
    LayoutsInJetpackCompose_wootaengTheme() {
        Text(text = "Hi there!", Modifier.padding(top = 32.dp))
    }
}

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // 주어진 제약조건들로 하위 요소들을 측정하고, 배치한다.
        // 하위 뷰들을 제한하지 말고, 주어진 constraints로 측정하자.
        // 측정된 하위 요소 목록들
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }

        // 수직으로 배치하기 위해 하위 요소들의 y 좌표값을 추적
        var yPosition = 0

        // 가능한 레이아웃의 사이즈를 크게 설정
        layout(constraints.maxWidth, constraints.maxHeight) {
            // 상위 레이아웃내 하위 요소들을 배치
            placeables.forEach { placeable ->
                // 화면상에 항목들을 배치한다
                placeable.placeRelative(x = 0, y = yPosition)

                // y 좌표값을 기록한다.
                yPosition += placeable.height
            }
        }
    }
}
@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    MyOwnColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
        Text("되나!")
        Text("되니?!")

    }
}

/**
 * =======================================================
 */
//복잡한 커스텀 레이아웃 만들기

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
){
    Layout(
        modifier = Modifier,
        content = content
    ){ measurable, constraints ->
        // 여기서 주어진 제약조건을 사용하고 측정하고 배치한다.
        // 각 행에 대한 너비를 추적한다
        val rowWidths = IntArray(rows){0}

        // 각 행에 대한 최대 높이를 추적한다.
        val rowHeights = IntArray(rows){0}

        // 하위 view 들을 제한하지 않고, 주어진 제약조건들과 함께 측정한다.
        // List of measured childern
        val placeables = measurable.mapIndexed { index, measurable ->

            // 각 하위 요소를 측정 한다.
            val placeable = measurable.measure(constraints)

            // 각 행의 너비 및 최대 높이 추적
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid의 너비는 가장 넓은 행이다.
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid의 높이는 높이 제약조건으로 인해 강제로 변한된 각 행의 가장 높은 요소의 합이다.
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // 이전 행들의 누적된 높이를 기반한, 각 행의 Y
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i-1] + rowHeights[i-1]
        }

        // 상위 레이아웃의 사이즈를 설정하자.
        layout(width, height) {
            // 각 행마다, 배치해야할 x좌표
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String ){
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ){
        Row (
            modifier = Modifier.padding(start = 8.dp, top = 4.dp,end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(modifier = Modifier
                .size(16.dp, 16.dp)
                .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)

        }
    }
}
val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)


@Composable
fun BodyContent2(modifier: Modifier = Modifier) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        StaggeredGrid(modifier = modifier) {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}

//@Preview
//@Composable
//fun ChipPreview(){
//    LayoutsInJetpackCompose_wootaengTheme {
////        Chip(text = "Hi there")
//        BodyContent2()
//    }
//}

/**
 * ====================================================
 */
//Constraint Layout

// Decoupled API ( 분리된 API )
@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin= margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}




//ex3
/**
 * Dimension의 동작원리
 * preferredWrapContent - 레이아웃은 해당 차원의 제약 조건이 적용되는 랩 콘텐츠입니다.
 * wrapContent - 제약 조건에서 허용하지 않는 경우에도 레이아웃은 콘텐츠를 줄 바꿈합니다.
 * fillToConstraints - 레이아웃은 해당 치수의 제약 조건에 의해 정의된 공간을 채우도록 확장됩니다.
 * preferredValue - 레이아웃은 해당 차원의 제약 조건에 따라 고정 dp 값입니다.
 * value - 레이아웃은 해당 차원의 제약 조건에 관계없이 고정 dp 값입니다.
 * 또한 어떤 Dimension들은 강제될 수 있다.
 * width = Dimension.preferredWrapContent.atLeast(100.dp)
 *
 */
@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}




//ex2
@Composable
fun ConstraintLayoutContent2() {
    ConstraintLayout {
        // 3개의 컴포저블에 대한 참조를 생성합니다.
        // ConstraintLayout의 본문에서
        val (button1, button2, text) = createRefs()

        Button(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        // constrainAs 안에서는 만들 수 없음. 밖에서 만들자
        val barrier = createEndBarrier(button1, text) // button1 과 text 를 감싼 End(제약)
        Button(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier) // End 장벽부분을 start 로
            }
        ) {
            Text("Button 2")
        }

    }
}



//ex1
@Composable
fun ConstraintLayoutContent(){
    ConstraintLayout {
        //컴포저블을 통제하기 위해 참조들을 생성한다.
        val (button, text) = createRefs()

        Button(
            onClick = { /*Do something */},
            // "button" 참조를 Button 컴포저블에 배정한다.
            // 그리고  ConstraintLayout 의 top 에 제약조건을 설정한다.
            modifier = Modifier.constrainAs(button){
                top.linkTo(parent.top, margin = 16.dp)
            }
        ){
            Text(text = "Button")
        }

        // "text" 참조를 Text 컴포저블에 배정한다.
        // 그리고 Button 컴포저블 bottom 에 제약조건을 설정.
        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            //텍스트를 ConstraintLayout 중아에 정렬하려면
            centerHorizontallyTo(parent)
        })

    }
}

//@Preview
//@Composable
//fun ConstraintLayoutContentPreview(){
//    LayoutsInJetpackCompose_wootaengTheme {
////        ConstraintLayoutContent()
////        ConstraintLayoutContent2()
////        LargeConstraintLayout()
//        DecoupledConstraintLayout()
//    }
//}

/**
 * ======================================================
 * 컴포즈 규칙 중 하나는 하위 요소를 한번만 측정해야 한다는 것이다.
 * 하위 요소를 두번 측정하는 것은 런타임 예외를 발생시킨다.
 * 하지만 떄로는 측정하기 전에 하위요소에 대한 몇가지 정보가 필요하다.
 * Intrinsics를 사용하면 실제로 측정되기 전에 하위요소에 질의할 수 있다.
 * 개발자는 intrinsicWidth 또는 intrinsicHeight를 컴포저블에게 요청할 수 있다.
 * (min|max)IntrinsicWidth: 높이(height)가 주어지고, 적절히 콘텐츠를 그릴 수 있는 최소/최대 너비
 * (min|max)IntrinsicHeight: 너비(width)가 주어지고, 적절히 콘텐츠를 그릴 수 있는 최소/최대 너비
 *
 * 예를 들어, 무한한 너비의 텍스트에 minlntrinsicHeight 를 요청하면 텍스트가 한 줄에 그려진
 * 것 처럼 텍스트의 높이가 반환된다.
 *
 */

@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
//    Row(modifier = modifier) {
//        Text(
//            modifier = Modifier
//                .weight(1f)
//                .padding(start = 4.dp)
//                .wrapContentWidth(Alignment.Start),
//            text = text1
//        )
//
//        Divider(color = Color.Black, modifier = Modifier
//            .fillMaxHeight()
//            .width(1.dp))
//        Text(
//            modifier = Modifier
//                .weight(1f)
//                .padding(end = 4.dp)
//                .wrapContentWidth(Alignment.End),
//
//            text = text2
//        )
//    }
//    위의 코드로 작성하면 Divider 가 전체 스크린 영역까지 확장된다.
//    이런일이 발생하는 원인은 Row 각 하위요소를 독립적으로 측정하고
//    Text의 높이 Divider를 제한하기 위해 사용되지 않는다.
//    우리가 원하는 것은 주어진 높이 내에서 Divider가 가능한 공간을 모두 채우는 것이다.
//    이를 위해, height(IntrinsicSize.Min) modifier를 사용할 수 있다.
//    height(IntrinsicSize.Min)는 최소한의 고유한 높이만큼 커지도록 강제되는
//    하위요소의 사이즈를 조정한다. 이는 재귀적이므로, Row와 하위요소 minIntrinsicHeight에
//    질의한다.
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
//    Row의 minIntrinsicHeight는 하위요소의 최대 minIntrinsicHeight가 된다.
//    Divider에 제약조건이 주어지지 않으면 공간을 차지 하지 않으므로,
//    Divider의 minIntrinsicHeight는 0이다. Text의 minIntrinsicHeight는
//    지정된 너비의 텍스트 높이가 된다.
//    그러므로 Row의 height 제약조건은 Text들 중 최대치의 minIntrinsicHeight가
//    된다. Divider는 그런뒤 주어진 Row의 height에 맞게 높이를 확장시킨다.
}

/**
 * DIY
 * 커스텀 레이아웃을 만들때마다, MeasurePolicy 인터페이스의
 * (min|max)Intrinsic(Width|Height)와 함께 intrinsics가 어떻게 계산되는지
 * 수정할 수 있다. 그러나 수정하지 않고 보통의 경우 기본값이면 충분하다.
 * 또한 좋은 기본값을 가지고 있는 Modifier 인터페이스의
 * Density.(min|max)Intrinsic(Width|Height)Of 메서드를 재정의한 것을
 * 사용하여 intrinsics를 수정할 수도 있다.
 */

@Preview
@Composable
fun TwoTextsPreview() {
    LayoutsInJetpackCompose_wootaengTheme() {
        Surface {
            TwoTexts(text1 = "Hi", text2 = "there")
        }
    }
}

