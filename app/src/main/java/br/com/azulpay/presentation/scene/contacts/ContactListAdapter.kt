package br.com.azulpay.presentation.scene.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.azulpay.R
import br.com.azulpay.presentation.common.setCircleImage
import br.com.tokenlab.edittextmasked.setMask
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {
    private var contacts: List<ContactDisplayModel> = emptyList()

    fun setData(contacts: List<ContactDisplayModel>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    inner class ContactViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {

        fun bind(contact: ContactDisplayModel) {
            with(rootView) {
                imageViewContact.setCircleImage(context, contact.image)
                textViewName.text = contact.name
                textViewPhone.text = contact.phone.setMask("(##) #####-####")
            }
        }
    }
}