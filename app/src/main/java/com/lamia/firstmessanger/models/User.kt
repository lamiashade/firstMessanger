package com.lamia.firstmessanger.models

class User( val userName:String? = null, val email:String? = null, val uid:String? = null, val profilePic:String? = null){
    constructor():this("","","","")
}
