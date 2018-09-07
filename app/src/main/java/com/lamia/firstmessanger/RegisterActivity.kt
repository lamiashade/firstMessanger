package com.lamia.firstmessanger

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    val Tag = "RegisterActivity"

    // Activity variables
    var reg_btn:Button? = null
    var regUserName:TextView? = null
    var regEmail:TextView? = null
    var regPassword:TextView? = null
    var already_registered: TextView?= null
    var userPhotBtn:Button? = null
    var user_picture:CircleImageView? = null

    //Firebase variables
    var mAuth:FirebaseAuth? = null
    var db:FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //initiallizing user register views
        reg_btn = register_btn_main
        regUserName = name_editText_register
        regEmail = email_editText_register
        regPassword = passworder_editText_register
        userPhotBtn = user_login_pic
        user_picture = user_profile_picture
        already_registered = already_have_account_textView

        //Firebase variables
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Create a user account
        reg_btn!!.setOnClickListener() {

            performRegister()
        }

        //Swith to login screen
        already_registered!!.setOnClickListener(){
            Log.d(Tag,"Switch to LoginActivity")

            //Launch login Activity
            var launchLogin = Intent(this,LoginActivity::class.java)
            startActivity(launchLogin)

        }

        //select a user photo
        userPhotBtn?.setOnClickListener(){
            Log.d(Tag,"Selecting a User photo")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var userPicUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // set profile picture on login screen
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){

            Log.d(Tag, "A Photo was Selected")

            userPicUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, userPicUri)

            user_picture!!.setImageBitmap(bitmap)
            userPhotBtn!!.alpha = 0f
        }

    }

    private fun performRegister(){

        var email = regEmail!!.text.toString()
        var password = regPassword!!.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter an email or Password", Toast.LENGTH_LONG).show()
            return
        }

        Log.d(Tag,"Attempting to Log in Username: $email and Password: $password")

        mAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener

                    Log.d(Tag, "Created user: Success")

                    //upload image to firebase
                    uploadImage()
                }
                .addOnFailureListener(){
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                    Log.d(Tag, "Failed to create user: ${it.message}")
                }
    }

    private fun uploadImage(){

        if(userPicUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/imgae/$filename")

        ref.putFile(userPicUri!!).addOnSuccessListener {
            Log.d(Tag, "Successfully uploaded user profile picture: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {it->
                Log.d(Tag, "File Location: $it")

                saveUser(it.toString())
            }
        }
                .addOnFailureListener(){e->
                    Log.d(Tag, " Error: ${e.message}")

                }
    }

    private fun saveUser(userPic:String){

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user:User? = User(regUserName!!.text.toString(),regEmail!!.text.toString(),uid,userPic)

        ref.setValue(user).addOnSuccessListener {
            Log.d(Tag,"Successfully saved user")
            Toast.makeText(this, "Successfully created an account",Toast.LENGTH_LONG).show()

            val loginIntent = Intent(this,LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
        }
    }
}
