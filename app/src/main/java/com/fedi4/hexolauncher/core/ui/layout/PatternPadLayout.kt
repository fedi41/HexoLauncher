package com.fedi4.hexolauncher.core.ui.layout


import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import com.fedi4.hexolauncher.core.ui.PatternPadGeometry
import com.fedi4.hexolauncher.core.util.hexoPosition

@Composable
fun PatternPadLayout(
    modifier: Modifier = Modifier,
    geometry: PatternPadGeometry,
//    onNearestChild: (childIndex: Int) -> Unit,
//    onExitCircle: () -> Unit,
//    controller: HexoLayoutController,
    content: @Composable () -> Unit
) {
    val childCenters = remember { mutableStateMapOf<Int, Offset>() }

    val layoutSize = geometry.layoutSizePx
    val radius = geometry.radius
    val itemPx = geometry.itemSizePx
    val center = geometry.center

//    fun process(pos: Offset) {
//        if ((pos - center).getDistance() > outerRadius) {
//            onExitCircle()
//            return
//        }
//
//        childCenters.minByOrNull { (_, center) ->
//            (pos - center).getDistance()
//        }?.let { (index, _) ->
//            onNearestChild(index)
//        }
//    }

//    // expose function to outside
//    LaunchedEffect(Unit) {
//        controller.process = ::process
//    }

    Layout(
        modifier = Modifier,
//        modifier = modifier.pointerInput(center, outerRadius) {
////            detectDragGestures { change, dragAmount ->
////                process(change.position)
////            }
//        },
        content = content
    ) { measurables, constraints ->



        val placeables = measurables.map {
            it.measure(Constraints.fixed(itemPx, itemPx))
        }


        layout(layoutSize, layoutSize) {
            val cx = layoutSize / 2f
            val cy = layoutSize / 2f

            placeables.forEachIndexed { index, p ->
                var (x,y) = hexoPosition(index, center, radius)
                x -= itemPx / 2
                y -= itemPx / 2

                p.place(x.toInt(), y.toInt())
                childCenters[index] = Offset(x + itemPx / 2, y + itemPx / 2)
            }
        }
    }
}

//class HexoLayoutController {
//    var process: ((Offset) -> Unit)? = null
//
//    fun send(pos: Offset) {
//        process?.invoke(pos)
//    }
//}