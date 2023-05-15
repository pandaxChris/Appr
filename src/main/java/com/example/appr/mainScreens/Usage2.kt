package com.example.appr.mainScreens


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

@Composable
fun UsageScreen2(){

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text="Network Rx(Mb):",
            color= MaterialTheme.colors.primary,
            fontSize= MaterialTheme.typography.h4.fontSize,
        )
    }

    Box(
        modifier=Modifier.padding(20.dp, 200.dp).height(400.dp)
    ){
        BarChartView2()

    }

}

@Composable
fun BarChartView2() {

    BarChart(
        barChartData=BarChartData(
            bars=listOf(
                BarChartData.Bar(
                    label=days[0],
                    value = 8.2f,
                    color=cols[0]
                ),
                BarChartData.Bar(
                    label=days[1],
                    value = 12f,
                    color=cols[1]
                ),
                BarChartData.Bar(
                    label=days[2],
                    value = 7f,
                    color=cols[2]
                ),
                BarChartData.Bar(
                    label=days[3],
                    value = 10.9f,
                    color=cols[3]
                ),
                BarChartData.Bar(
                    label=days[4],
                    value = 12.7f,
                    color=cols[4]
                ),
                BarChartData.Bar(
                    label=days[5],
                    value = 10.2f,
                    color=cols[5]
                ),
                BarChartData.Bar(
                    label=days[6],
                    value = 8.7f,
                    color=cols[6]
                )

            )
        ),
        // Optional properties.
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        animation = simpleChartAnimation(),
        barDrawer = SimpleBarDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        labelDrawer = SimpleValueDrawer(),

        )
}
