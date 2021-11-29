package ru.oktemsec.worldcinema

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var btnSignUp:Button
    lateinit var edEmail:EditText
    lateinit var edPassword:EditText
    lateinit var edFirstName:EditText
    lateinit var edSecName:EditText
    lateinit var edRePassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnSigninGo: Button = findViewById(R.id.btnSigninGo)
        btnSignUp = findViewById(R.id.btnRegisterNow)
        edEmail = findViewById(R.id.etEmail)
        edPassword = findViewById(R.id.etPasswordReg)
        edRePassword = findViewById(R.id.etPasswordReg2)
        edFirstName = findViewById(R.id.etName)
        edSecName = findViewById(R.id.etSurname)

        // Нажатие кнопки "У меня уже есть аккаунт" и переход в окно Входа
        btnSigninGo.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener {
            if (edEmail.text.isEmpty() || edPassword.text.isEmpty() || edSecName.text.isEmpty() || edFirstName.text.isEmpty() || edRePassword.text.isEmpty()) {
                ShowAlertDialogWindow("Есть пустые поля")
            }
            else if (edPassword.text.toString() != edRePassword.text.toString()) {
                ShowAlertDialogWindow("Пароли не совадают")
            }
            else {
                registerUser()
            }
        }
    }

    private fun ShowAlertDialogWindow(s: String) {
        let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                setTitle("Внимание")
                setMessage(s)
            }
            builder.create()
        }?.show()
    }

    private fun registerUser() {
        val registerRequest:RegisterRequest = RegisterRequest(edEmail.text.toString(), edPassword.text.toString(), edFirstName.text.toString(), edSecName.text.toString())
        val apiClient:ApiClient = ApiClient()
        val registerResponseCall: Call<RegisterResponse> = apiClient.getRegister().registerUser(registerRequest)

        registerResponseCall.enqueue(object : Callback<RegisterResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.d("brearey", "Response received: ${response.body()}")
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Все ок...", Toast.LENGTH_LONG).show()
                    finish()
                }
                else {
                    Log.d("brearey", "Don't success: ${response.code()}")
                    Toast.makeText(this@RegisterActivity, "Что-то пошло не так", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("brearey", "Failed", t)
                Toast.makeText(this@RegisterActivity, "Регистрация прошла успешно", Toast.LENGTH_LONG).show()
                val intent:Intent = Intent(this@RegisterActivity, SigninActivity::class.java)
                startActivity(intent)
            }
        })
    }
}