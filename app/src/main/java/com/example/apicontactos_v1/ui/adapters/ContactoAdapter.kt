// ContactoAdapter.kt
package com.example.apicontactos_v1.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apicontactos_v1.R
import com.example.apicontactos_v1.models.Contacto

class ContactoAdapter(
    private var contactList: List<Contacto>,
    var onContactClick: (Contacto) -> Unit,
    var onContactDelete: (Long) -> Unit,
    var onContactUpdate: (Long) -> Unit
) : RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contacto, parent, false)
        return ContactoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        val contacto = contactList[position]
        holder.bind(contacto)
        holder.itemView.setOnClickListener { onContactClick(contacto) }

        // Set the click listener for the delete button
        holder.btnDelete.setOnClickListener { onContactDelete(contacto.id!!) }
        holder.btnEditar.setOnClickListener { onContactUpdate(contacto.id!!)}
    }

    override fun getItemCount(): Int = contactList.size

    fun updateData(newContacts: List<Contacto>) {
        contactList = newContacts
        notifyDataSetChanged()
    }

    class ContactoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNombreApellido: TextView = view.findViewById(R.id.tvNombreApellido)
        private val tvEmpresa: TextView = view.findViewById(R.id.tvEmpresa)
        val btnDelete: ImageView = view.findViewById(R.id.btnDelete)
        val btnEditar: ImageView = view.findViewById(R.id.btnEditar)

        fun bind(contacto: Contacto) {
            tvNombreApellido.text = "${contacto.name} ${contacto.last_name}"
            tvEmpresa.text = contacto.company ?: "Sin empresa"
        }
    }
}
