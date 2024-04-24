package com.example.splitper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitper.ui.theme.Purple40
import com.example.splitper.ui.theme.SplitPerTheme
import java.util.Vector

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()

        }
    }

}

@Preview(showBackground = true)
@Composable
fun MyApp() {
    var Amount by remember {
        mutableStateOf("")
    }
    Column {
        Calc(Amount = Amount,
            amountchange = {Amount = it}
        )
    }
}


@Composable
fun totalAmount(amount: Float = 0f) {
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = Purple40,
        shadowElevation = 15.dp
    ) {
        Column(
            modifier =Modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Amount to pay",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "$$amount",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            )
        }
    }
}


@Composable
fun Calc(Amount:String = "",amountchange:(String)->Unit = {},Count : Int = 1) {
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = Amount,
                onValueChange = {
                                amountchange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Split",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.5f))
                customInAndOut(ImageVector = Icons.Filled.KeyboardArrowUp,{})
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "$Count",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(5.dp))
                customInAndOut(ImageVector = Icons.Filled.KeyboardArrowDown,{})
            }
        }
    }
}


@Composable
fun customInAndOut(ImageVector : ImageVector,onClick : () -> Unit) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .clickable {
                onClick()
            }
    ) {
        Icon(imageVector = ImageVector,
            contentDescription = "NULL",
            tint = Color.Black,

        )
    }
}