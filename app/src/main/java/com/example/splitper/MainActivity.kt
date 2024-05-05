@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.splitper

import android.os.Bundle
import android.util.Log
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.splitper.ui.theme.Purple40
import com.example.splitper.ui.theme.PurpleGrey40
import com.example.splitper.ui.theme.SplitPerTheme
import java.util.Vector

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavBar()
        }
    }

}

    @Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyApp() {
    var amount by remember {
        mutableStateOf("")
    }
    var counter by remember {
        mutableStateOf(1)
    }
    var tipPer by remember {
        mutableStateOf(0f)
    }
    var buttonchange by remember {
        mutableStateOf(0)
    }
    var isenabled by remember {
        mutableStateOf(false)
    }
    var imagevector by remember {
        mutableStateOf(Icons.Filled.KeyboardArrowDown)
    }
    Column(){
            TopBar(isenabled, { isenabled = !isenabled })
            totalAmount(amount, counter, tipPer, buttonchange, isenabled)
            Calc(
                amount,
                { amount = it },
                counter,
                { counter++ },
                { if (counter > 1) counter-- },
                tipPer,
                { tipPer = it },
                buttonchange,
                { buttonchange++ })
        }
}

@Composable
fun TopBar(
    isenabled : Boolean = false,
    onclick: () -> Unit,
) {
    Row {
        Text(
            text = "SplitPer",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
        )
        Spacer(modifier = Modifier.fillMaxWidth(0.8f))
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "down",
            modifier = Modifier

                .clip(CircleShape)
                .clickable { onclick() }
                .scale(1f, if (isenabled) -1f else 1f)
        )
    }
}


@Composable
fun totalAmount(
    total: String,
    counter: Int,
    tipPer: Float,
    buttonchange: Int,
    isenabled: Boolean = false
) {
    if(isenabled == true){
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(10.dp),
        color = Purple40,
        shadowElevation = 15.dp
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Amount to pay",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text =
                if (buttonchange == 0) {
                    "$0"
                } else {
                    "$${totalcalci(total, counter, tipPer)}"
                },
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            )
        }
    }
    }
}


@Composable
fun Calc(
    amount : String,
    changefun:(String)->Unit,
    counter : Int,
    inc:()->Unit,
    dec:()->Unit,
    tipPer : Float,
    tipPercentage : (Float)->Unit,
    buttonchange : Int,
    onclick : () -> Unit
) {
    val localKeyboard = LocalSoftwareKeyboardController.current
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
            OutlinedTextField(value = amount,
                onValueChange = {
                                changefun(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, autoCorrect = true, imeAction = ImeAction.Done),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Split",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.7f))
                customInAndOut(ImageVector = Icons.Filled.KeyboardArrowUp,inc)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "$counter",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(5.dp))
                customInAndOut(ImageVector = Icons.Filled.KeyboardArrowDown,dec)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Tip", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.fillMaxWidth(0.75f))
                Text(text = "$ ${getTipAmount(amount,tipPer)}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "$tipPer",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Slider(value = tipPer,
                onValueChange = {tipPercentage(it)},
                valueRange = 0f..100f, steps = 5,
                modifier = Modifier
                    .padding(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {onclick()},
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            {
                Text(text = "Calculate",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 15.sp
                        ),
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
    }
}


@Composable
fun customInAndOut(ImageVector : ImageVector,onClick : () -> Unit) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .clip(CircleShape)
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


fun getTipAmount(amount: String,tipPer: Float):String{
    return when{
        amount.isEmpty()->{
            "0"
        }
        else->{
            val floatamount = amount.toFloat()
            (floatamount*tipPer.div(100)).toString()
        }
    }
}

fun totalcalci(amount: String,counter: Int,tipPer: Float):String{
    return when{
        amount.isEmpty()-> {
            "0"
        }
        else->{
            ((amount.toFloat()+tipPer).div(counter)).toString()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBar() {
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = "calculator"){
        composable("calculator"){
            calculate()
        }
        composable("Profile"){
            Profile()
        }
        composable("History"){
            History()
        }
    }
    var changecolor by remember {
        mutableStateOf(false)
    }
    Box (
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
            ,modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(PurpleGrey40)
        ){
            NavBarItem(changecolor,"calculate",Icons.Filled.AddCircle,{navController.navigate("calculator")},{changecolor=true})
            NavBarItem(changecolor,"History",Icons.Filled.Delete,{navController.navigate("History")},{changecolor=true})
            NavBarItem(changecolor,"Profile",Icons.Filled.Person,{navController.navigate("Profile")},{changecolor=true})
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NavBarItem(
    changeColor:Boolean,
    Name: String,
    Icon : ImageVector,
    click : () ->Unit,
    change:() ->Unit
) {
    Box (contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable { change() }
            .clickable { click() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icon, contentDescription = "filled",
                modifier = Modifier
                    .size(25.dp),
                tint = if (changeColor == true) Color.Green else Color.White
            )
            Text(
                text = Name,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun calculate() {
    MyApp()
}

@Composable
fun Profile() {
    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Text(text = "Profile")
    }

}

@Composable
fun History() {
    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Text(text = "History")
    }
}