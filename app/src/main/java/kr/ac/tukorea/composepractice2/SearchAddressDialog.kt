package kr.ac.tukorea.composepractice2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@Composable
fun SearchAddressDialog(
    dialogState: Boolean,
    onDissmissRequest: (dialogState: Boolean) -> Unit
) {
    if (dialogState) {
        AlertDialog(
            backgroundColor = Color.White,
            onDismissRequest = {
                onDissmissRequest(dialogState)
            },
            title = null,
            text = {
                Column() {
                    Text(
                        "찾으시려는 동(읍/면/리)과 번지수or건물명을 정확하게 입력해 주세요.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(),
                        fontSize = 16.sp,
                        lineHeight = 17.sp
                    )
                    DialogUI()
                }
            },
            buttons = {
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            shape = RoundedCornerShape(9.dp)
        )
    }
}

@Composable
fun DialogUI() {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Column {
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        /*Divider(
            color = Color.DarkGray,
            thickness = 0.8.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )*/
        TextField(
            modifier = Modifier.padding(20.dp)
                .fillMaxWidth(),
            value = textState,
            onValueChange = { textFieldValue -> textState = textFieldValue },
            trailingIcon = {
                painterResource(id = R.drawable.ic_baseline_search_24)
            }
        )
        AddressList(addressList = RegisterInformViewModel().setAddress(textState.text))
    }
}

// sharedPreference 사용 필요
@Composable
fun AddressList(addressList: List<AddressDetail.HtReturnValue.Result.Zipcode>) {
    LazyColumn() {
        itemsIndexed(
            AddressAdapter(addressList)
        ) { index, item ->
            Text(
                text = item
                /* fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                * */
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    Surface() {
        var dialogState by remember {
            mutableStateOf(true)
        }

        SearchAddressDialog(dialogState = dialogState, onDissmissRequest = {
            dialogState = !it
        })
    }
}
