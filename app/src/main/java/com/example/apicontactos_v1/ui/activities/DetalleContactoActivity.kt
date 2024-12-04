package com.example.apicontactos_v1.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.example.apicontactos_v1.R
import com.example.apicontactos_v1.databinding.ActivityContactoDetalleBinding
import com.example.apicontactos_v1.models.Contacto
import com.example.apicontactos_v1.models.Correo
import com.example.apicontactos_v1.models.Telefono
import com.example.apicontactos_v1.ui.viewmodels.ContactosViewModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DetalleContactoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactoDetalleBinding
    private val viewModel: ContactosViewModel by viewModels() // Usamos ViewModel
    private lateinit var contacto: Contacto
    private val PICK_IMAGE_REQUEST = 1
    private val phoneFields = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactoDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar el contacto desde el Intent
        contacto = intent.getParcelableExtra<Contacto>("contacto") ?: return
        contacto?.let {
            Glide.with(this)
                .load(it.profile_picture)
                .into(binding.imageViewProfile)
            val nombreCompleto = it.name + " " + it.last_name
            binding.editTextName.setText(nombreCompleto)

            // Si el contacto tiene teléfonos, los cargamos en el formulario
            if (it.phones.isNotEmpty()) {
                for (phone in it.phones) {
                    addPhoneField(phone.number, phone.label, phone.id)
                }
            }
            //tambien para los emails:
            if (it.emails.isNotEmpty()) {
                for (email in it.emails) {
                    addEmailField(email.email, email.label, email.id)
                }
            }
        }

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnSubmit.setOnClickListener {
            submitContact()
        }
        binding.btnUploadPhoto.setOnClickListener {
            subirFoto()
        }
        binding.buttonAddPhone.setOnClickListener {
            addPhoneField()
        }
        binding.buttonAddEmail.setOnClickListener {
            addEmailField()
        }
    }

    private fun addEmailField(email: String? = null, label: String? = null, emailId: Long? = null) {
        val inflater = LayoutInflater.from(this)
        val emailView = inflater.inflate(R.layout.item_email_field, binding.layoutEmails, false)

        val editTextEmail = emailView.findViewById<EditText>(R.id.editTextEmail)
        editTextEmail.setText(email)

        val spinnerLabelEmail = emailView.findViewById<Spinner>(R.id.spinnerLabelEmail)
        var labels = listOf("Etiqueta personalizada", "Trabajo", "Personal", "Otro")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, labels)
        spinnerLabelEmail.adapter = adapter

        // Verificar los casos para agregar las etiquetas
        if (label == null || label.isEmpty()) {
            labels = listOf("Trabajo", "Personal", "Otro", "Etiqueta personalizada")
        } else {
            if (label in labels) {
                labels = listOf("Trabajo", "Personal", "Otro", "Etiqueta personalizada")
            } else {
                labels = listOf("Trabajo", "Personal", "Otro", "Etiqueta personalizada", label)
            }
        }

        val adapterUpdated = ArrayAdapter(this, android.R.layout.simple_spinner_item, labels)
        spinnerLabelEmail.adapter = adapterUpdated

        // Configurar la selección de la etiqueta
        if (label != null) {
            spinnerLabelEmail.setSelection(labels.indexOf(label))
        }

        // Asignar el emailId al tag del campo para poder usarlo más tarde
        emailView.tag = emailId

        // Botón de eliminar para cada email
        val deleteButtonEmail = emailView.findViewById<View>(R.id.btnDeleteEmail)
        deleteButtonEmail.setOnClickListener {
            if (emailId != null) {
                viewModel.deleteEmail(emailId)
            }
            binding.layoutEmails.removeView(emailView)
        }

        binding.layoutEmails.addView(emailView)
    }


    @SuppressLint("MissingInflatedId")
    private fun addPhoneField(number: String? = null, label: String? = null, phoneId: Long? = null) {
        val inflater = LayoutInflater.from(this)
        val phoneView = inflater.inflate(R.layout.item_phone_field, binding.layoutPhoneNumbers, false)

        val editTextNumber = phoneView.findViewById<EditText>(R.id.editTextPhoneNumber)
        editTextNumber.setText(number)

        val spinnerLabel = phoneView.findViewById<Spinner>(R.id.spinnerLabel)
        var labels = listOf("Etiqueta personalizada", "Casa", "Trabajo", "Celular")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, labels)
        spinnerLabel.adapter = adapter

        // Verificar los casos para agregar las etiquetas
        if (label == null || label.isEmpty()) {
            // Caso 1: No hay etiqueta personalizada, agregar solo las etiquetas por defecto
            labels = listOf("Casa", "Trabajo", "Celular", "Etiqueta personalizada")
        } else {
            if (label in labels) {
                // Caso 2: Hay una etiqueta de las por defecto, añadir la etiqueta personalizada
                labels = listOf("Casa", "Trabajo", "Celular", "Etiqueta personalizada")
            } else {
                // Caso 3: Hay al menos una etiqueta diferente, agregamos una más
                labels = listOf("Casa", "Trabajo", "Celular", "Etiqueta personalizada", label)
            }
        }

        val adapterUpdated = ArrayAdapter(this, android.R.layout.simple_spinner_item, labels)
        spinnerLabel.adapter = adapterUpdated


        if (label != null) {
            spinnerLabel.setSelection(labels.indexOf(label))
        }

        phoneView.tag = phoneId

        // Obtener el TextView que quieres mostrar u ocultar
        val textViewLabel = binding.txtNewEtiqueta
        val textViewLabelEmail = binding.txtNewEtiquetaEmail
        spinnerLabel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                if (spinnerLabel.selectedItem == "Etiqueta personalizada") {
                    textViewLabel.visibility = View.VISIBLE
                    textViewLabelEmail.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Aquí puedes definir lo que pasa cuando no se selecciona nada
                textViewLabel.visibility = View.GONE  // Ejemplo: ocultar el texto cuando no hay selección
            }
        }



        // Botón de eliminar para cada teléfono
        val deleteButton = phoneView.findViewById<View>(R.id.btnDeletephone)
        deleteButton.setOnClickListener {
            if (phoneId != null) {
                viewModel.deleteTelefono(phoneId)
            }
            binding.layoutPhoneNumbers.removeView(phoneView)
            phoneFields.remove(phoneView)
        }

        binding.layoutPhoneNumbers.addView(phoneView)
        phoneFields.add(phoneView)
    }


    private fun subirFoto() {
        // Abrir la galería para seleccionar una foto
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data // URI de la imagen seleccionada

            selectedImageUri?.let {
                // Usar Glide para cargar la nueva imagen seleccionada en el ImageView
                Glide.with(this)
                    .load(it)
                    .into(binding.imageViewProfile)  // Actualiza el ImageView con la nueva imagen

                val filePart = createPartFromUri(it)
                filePart?.let { part ->
                    contacto.id?.let { id ->
                        viewModel.subirFoto(id, part)
                    }
                }
            }
        }
    }

    private fun createPartFromUri(uri: Uri): MultipartBody.Part? {
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val file = File(cacheDir, "profile_picture_${System.currentTimeMillis()}.jpg")

        inputStream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        if (!file.exists()) {
            Log.e("DetalleContactoActivity", "Error al crear el archivo temporal.")
            return null
        }

        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    private fun submitContact() {
        val phoneList = mutableListOf<Telefono>()
        val emailList = mutableListOf<Correo>()

        // Recorremos los campos de teléfono
        for (phoneField in phoneFields) {
            val editTextNumber = phoneField.findViewById<EditText>(R.id.editTextPhoneNumber)
            val spinnerLabel = phoneField.findViewById<Spinner>(R.id.spinnerLabel)
            val phoneNumber = editTextNumber.text.toString()
            var label = spinnerLabel.selectedItem.toString()
            val phoneId = phoneField.tag as? Long
            //si textViewLabel tiene algo reemplazar con label:
            if (binding.txtNewEtiqueta.visibility == View.VISIBLE) {
                label = binding.txtNewEtiqueta.text.toString()
            }

            if (phoneNumber.isNotBlank()) {
                val telefono = Telefono(id = phoneId, number = phoneNumber, persona_id = contacto.id, label = label)
                phoneList.add(telefono)
            }
        }

        // Recorremos los campos de correo
        for (emailField in binding.layoutEmails.children) {
            val editTextEmail = emailField.findViewById<EditText>(R.id.editTextEmail)
            val spinnerLabelEmail = emailField.findViewById<Spinner>(R.id.spinnerLabelEmail)
            val emailAddress = editTextEmail.text.toString()
            val emailLabel = spinnerLabelEmail.selectedItem.toString()
            val emailId = emailField.tag as? Long

            if (emailAddress.isNotBlank()) {
                val correo = Correo(id = emailId, email = emailAddress, persona_id = contacto.id, label = emailLabel)
                emailList.add(correo)
            }
        }

        // Procesar los teléfonos para hacer update o crear nuevos
        for (telefono in phoneList) {
            if (telefono.id != null) {
                viewModel.updateTelefono(telefono)  // Realiza un update si ya existe
            } else {
                viewModel.addTelefono(telefono)     // Realiza un post si es nuevo
            }
        }

        // Procesar los correos para hacer update o crear nuevos
        for (correo in emailList) {
            if (correo.id != null) {
                viewModel.updateEmail(correo)
            } else {
                viewModel.addEmail(correo)
            }
        }
        finish()
    }
}
