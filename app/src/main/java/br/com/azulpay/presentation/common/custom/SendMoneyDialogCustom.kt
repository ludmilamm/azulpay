package br.com.azulpay.presentation.common.custom

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.view.Window
import br.com.azulpay.R
import br.com.azulpay.common.getLocale
import br.com.azulpay.presentation.common.PHONE_MASK
import br.com.azulpay.presentation.common.model.ContactDisplayModel
import br.com.azulpay.presentation.common.setCircleImage
import br.com.tokenlab.edittextmasked.addCurrencyMask
import br.com.tokenlab.edittextmasked.removeCurrencyMask
import br.com.tokenlab.edittextmasked.setMask
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.custom_send_money_dialog.*
import java.math.BigDecimal
import java.util.*

@SuppressLint("CheckResult")
class SendMoneyDialogCustom(context: Context) : Dialog(context) {
    private lateinit var contactDisplayModel: ContactDisplayModel
    private val sendMoneySubject: PublishSubject<BigDecimal> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.custom_send_money_dialog)
    }

    fun onSendMoney(): Observable<BigDecimal> = sendMoneySubject

    fun show(contactDisplayModel: ContactDisplayModel) {
        this.contactDisplayModel = contactDisplayModel
        show().run { setupView() }
    }

    fun showErrorMessage(message: String) {
        textViewError.visibility = View.VISIBLE
        textViewError.text = message
    }

    fun startSendingMode() {
        setCancelable(false)
        editTextValue.isEnabled = false
        textViewError.visibility = View.INVISIBLE
        buttonSend.isEnabled = false
        buttonSend.text = context.getString(R.string.sending)
    }

    private fun setupView() {
        setCancelable(true)
        imageViewClose.clicks().subscribe { dismiss() }
        imageViewProfile.setCircleImage(context, contactDisplayModel.image)
        textViewName.text = contactDisplayModel.name
        textViewPhone.text = contactDisplayModel.phone.setMask(PHONE_MASK)
        editTextValue.isEnabled = true
        editTextValue.text = SpannableStringBuilder("R$ 0,00")
        editTextValue.setSelection(editTextValue.text.length)
        editTextValue.addCurrencyMask(getLocale())
        textViewError.visibility = View.INVISIBLE
        buttonSend.isEnabled = true
        buttonSend.text = context.getString(R.string.to_send)
        buttonSend.clicks().subscribe {
            sendMoneySubject.onNext(editTextValue.text.toString().removeCurrencyMask() ?: BigDecimal.ZERO)
        }
    }
}