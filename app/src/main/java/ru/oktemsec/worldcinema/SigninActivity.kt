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

class SigninActivity : AppCompatActivity() {

    lateinit var btnSignin:Button
    lateinit var etEmail:EditText
    lateinit var etPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val btnRegister: Button = findViewById(R.id.btnRegisterGo)

        btnSignin = findViewById(R.id.btnSignin)
        etEmail = findViewById(R.id.etEmailIn)
        etPassword = findViewById(R.id.etPasswordIn)

        // Нажатие кнопки "Регистрация" и переход в окно Регистрации
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnSignin.setOnClickListener {
            if (etEmail.text.isEmpty() || etPassword.text.isEmpty()) {
                ShowAlertDialogWindow("Есть пустые поля")
            }

            else {
                loginUser()
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

    private fun loginUser() {
        val loginRequest:LoginRequest = LoginRequest(etEmail.text.toString(), etPassword.text.toString())
        val apiClient:ApiClient = ApiClient()
        val loginResponseCall: Call<LoginResponse> = apiClient.getLogin().loginUser(loginRequest)

        loginResponseCall.enqueue(object : Callback<LoginResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                Log.d("brearey", "Response received: ${response.body()}")
                if (response.isSuccessful) {
                    val intent:Intent = Intent(this@SigninActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@SigninActivity, "Вы успешно вошли!", Toast.LENGTH_LONG).show()
                    finish()
                }
                else {
                    Toast.makeText(this@SigninActivity, "Что-то пошло не так", Toast.LENGTH_LONG).show()
                    Log.d("brearey", "Don't success: ${response.errorBody().toString()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("brearey", "Failed", t)
                Toast.makeText(this@SigninActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}