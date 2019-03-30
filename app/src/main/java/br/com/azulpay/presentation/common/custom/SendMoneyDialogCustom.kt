package br.com.azulpay.presentation.common.custom

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import br.com.azulpay.R
import br.com.azulpay.presentation.common.model.ContactDisplayModel
import br.com.tokenlab.edittextmasked.addCurrencyMask
import br.com.tokenlab.edittextmasked.setMask
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.custom_send_money_dialog.*
import java.math.BigDecimal
import java.util.*

@SuppressLint("CheckResult")
class SendMoneyDialogCustom(context: Context, private val contactDisplayModel: ContactDisplayModel) : Dialog(context) {
    private val sendMoneySubject: PublishSubject<BigDecimal> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_send_money_dialog)
        setupView()
    }

    fun onSendMoney(): Observable<BigDecimal> = sendMoneySubject

    fun showErrorMessage(message: String) {
        textViewError.visibility = View.VISIBLE
        textViewError.text = message
    }

    private fun setupView() {
        textViewName.text = contactDisplayModel.name
        textViewPhone.text = contactDisplayModel.phone.setMask("(##) #####-####")
        editTextValue.addCurrencyMask(Locale("pt", "BR"))
        editTextValue.textChanges().skipInitialValue().subscribe { textViewError.visibility = View.INVISIBLE }
        buttonSend.clicks()
                .subscribe {
                    sendMoneySubject.onNext(removeCurrencyMask(editTextValue.text.toString())
                            ?: BigDecimal.ZERO)
                }
    }

    private fun removeCurrencyMask(valueString: String): BigDecimal? {
        return try {
            val value = valueString.replace("[^0-9,]".toRegex(), "").replace(",", ".").toBigDecimal()
            if (value < BigDecimal.valueOf(0.01))
                BigDecimal.ZERO
            else value
        } catch (e: Exception) {
            null
        }
    }
}