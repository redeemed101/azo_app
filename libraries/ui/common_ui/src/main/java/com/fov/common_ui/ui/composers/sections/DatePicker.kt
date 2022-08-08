package com.fov.common_ui.ui.composers.sections

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePicker(
    context: Context,
    show : Boolean,
    getValue : (date : String) -> Unit
) {
    val now = Calendar.getInstance()

    val mYear: Int = now.get(Calendar.YEAR)
    val mMonth: Int = now.get(Calendar.MONTH)
    val mDay: Int = now.get(Calendar.DAY_OF_MONTH)
    now.time = Date()
    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            date.value = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
            getValue(date.value)
        }, mYear, mMonth, mDay
    )
    if(show){
        datePickerDialog.show()
    }else{
        datePickerDialog.hide()
    }
}