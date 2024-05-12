package org.d3if3139.rentara.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3139.rentara.R

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            text = { Text(text = stringResource(R.string.pesan_hapus)) },
            confirmButton = {
                TextButton(
                    onClick = { onConfirmation() }) {
                    Text(text = stringResource(id = R.string.tombol_hapus))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {onDismissRequest() }) {
                    Text(text = stringResource(id = R.string.tombol_batal))
                }
            },
            onDismissRequest = { onDismissRequest() })
    }
}

@Preview
@Composable
private fun DialogPreview() {
    DisplayAlertDialog(openDialog = true, onDismissRequest = { /*TODO*/ }) {

    }
}