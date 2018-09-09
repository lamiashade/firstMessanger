package com.lamia.firstmessanger.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User( val userName:String? = null, val email:String? = null, val uid:String? = null, val profilePic:String? = null):Parcelable{
    constructor():this("","","","")
}

