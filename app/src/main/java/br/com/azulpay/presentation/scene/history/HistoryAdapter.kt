package br.com.azulpay.presentation.scene.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.azulpay.R
import br.com.azulpay.presentation.common.PHONE_MASK
import br.com.azulpay.presentation.common.setCircleImage
import br.com.tokenlab.edittextmasked.setMask
import kotlinx.android.synthetic.main.item_contact.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var transactions: List<TransactionDisplayModel> = emptyList()

    fun setData(transactions: List<TransactionDisplayModel>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    inner class HistoryViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        fun bind(transaction: TransactionDisplayModel) {
            with(rootView) {
                textViewName.text = transaction.toUserName
                textViewPhone.text = transaction.toUserPhone.setMask(PHONE_MASK)
                textViewValue.visibility = View.VISIBLE
                textViewValue.text = "R$ ${transaction.value.toString().replace(".", ",")}"
                imageViewContact.setCircleImage(context, transaction.toUserImage)
            }
        }
    }
}