package com.dpa.esan.pc02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnRegistrarUsuario: Button = findViewById(R.id.btnRegistrarUsuario)
        val txtIngresarDni: TextView = findViewById(R.id.txtIngresarDni)
        val txtIngresarNombre: TextView = findViewById(R.id.txtIngresarNombre)
        val txtIngresarClave: TextView = findViewById(R.id.txtIngresarClave)
        val txtIngresarClavaRep: TextView = findViewById(R.id.txtIngresarClavaRep)
        val db = FirebaseFirestore.getInstance()

        btnRegistrarUsuario.setOnClickListener{
            if(isValidForm(txtIngresarDni, txtIngresarNombre, txtIngresarClave, txtIngresarClavaRep)
                && validatePassword(txtIngresarClave.text.toString(),
                    txtIngresarClavaRep.text.toString()))
            {
                db.collection("loginPC02").document(txtIngresarDni.text.toString())
                    .set(hashMapOf("nombres" to txtIngresarNombre.text.toString(),
                        "password" to txtIngresarClave.text.toString())
                    )

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else
            {
                txtIngresarClave.error = "Las claves no coinciden"
                txtIngresarClavaRep.error = "Las claves no coinciden"
            }
        }
    }

    private fun validatePassword(clave: String, claveRep: String): Boolean
    {
        return clave.equals(claveRep)
    }

    private fun isValidForm(dni: TextView, nombre: TextView, clave: TextView,
                            claveRep: TextView): Boolean
    {
        var response = true
        if (TextUtils.isEmpty(dni.text.toString()))
        {
            dni.error = "Ingrese su DNI"
            response = false
        }
        if (TextUtils.isEmpty(nombre.text.toString())) {
            nombre.error = "Ingrese su nombre completo"
            response = false
        }
        if (TextUtils.isEmpty(clave.text.toString())) {
            clave.error = "Ingrese la clave"
            response = false
        }
        if (TextUtils.isEmpty(claveRep.text.toString())) {
            claveRep.error = "Ingrese la clave"
            response = false
        }
        return response
    }
}