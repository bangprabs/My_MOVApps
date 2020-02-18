package net.prabowoaz.movapps.mywallet.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction  (
    var information: String? = "",
    var date: String? = "",
    var nominal: String? = ""
) : Parcelable