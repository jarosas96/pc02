package com.dpa.esan.pc02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = FirebaseFirestore.getInstance()
        val txtDni: TextView = findViewById(R.id.txtDni)
        val txtPassword: TextView = findViewById(R.id.txtPassword)
        val btnIngresar: Button = findViewById(R.id.btnIngresar)
        val btnCrearCuenta: Button = findViewById(R.id.btnCrearCuenta)
        var clave: String = ""

        btnIngresar.setOnClickListener {

            if (isValidForm(txtDni, txtPassword)) {
                db.collection("loginPC02").document(txtDni.text.toString()).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            clave = document.get("password") as String
                            if (clave.equals(txtPassword.text.toString())) {
                                Toast.makeText(
                                    this, "ACCESO PERMITIDO",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    this, "“EL USUARIO Y/O CLAVE\n" +
                                            "NO EXISTE EN EL SISTEMA",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this, "“EL USUARIO Y/O CLAVE\n" +
                                        "NO EXISTE EN EL SISTEMA",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }

        btnCrearCuenta.setOnClickListener {
             val intent = Intent(this, RegistroActivity::class.java)
             startActivity(intent)
        }
    }

    private fun isValidForm(dni: TextView, clave: TextView): Boolean
    {
        var response = true
        if (TextUtils.isEmpty(dni.text.toString()))
        {
            dni.error = "El DNI no puede estar vacío"
            response = false
        }
        if (TextUtils.isEmpty(clave.text.toString())) {
            clave.error = "La clave no puede estas vacía"
            response = false
        }
        return response
    }
}