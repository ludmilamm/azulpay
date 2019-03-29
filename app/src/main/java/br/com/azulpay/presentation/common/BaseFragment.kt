package br.com.azulpay.presentation.common

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.azulpay.presentation.common.event.SingleEvent
import br.com.azulpay.presentation.common.model.DialogDisplayModel
import br.com.azulpay.presentation.common.model.ErrorDialogDisplayModel

abstract class BaseFragment : Fragment() {

    abstract val factory: ViewModelProvider.Factory
    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBaseEventsLiveData().observe(viewLifecycleOwner, Observer { e -> e?.let { handleBaseEvents(it) } })
    }

    private fun handleBaseEvents(event: SingleEvent<*>) {
        if (event !is SingleEvent.Loading)
            dismissLoading()

        when (event) {
            is SingleEvent.Error -> event.error?.let { showErrorDialog(it) }
            is SingleEvent.Loading -> displayLoading()
            is SingleEvent.DismissLoading -> dismissLoading()
        }
    }

    fun showErrorDialog(error: ErrorDialogDisplayModel,
                        positiveAction: (dialog: DialogInterface, which: Int) -> Unit = { _, _ -> }) {
        showDialog(error, positiveAction)
    }

    fun showDialog(dialogDisplayModel: DialogDisplayModel,
                   positiveAction: (dialog: DialogInterface, which: Int) -> Unit = { _, _ -> },
                   negativeAction: (dialog: DialogInterface, which: Int) -> Unit = { _, _ -> }) {

        context?.let {
            with(dialogDisplayModel) {
                val builder = AlertDialog.Builder(it)
                    .setMessage(message)
                    .setCancelable(cancelable)


                negativeLabel?.let { negativeLabel -> builder.setNegativeButton(negativeLabel, negativeAction) }
                positiveLabel?.let { positiveLabel -> builder.setPositiveButton(positiveLabel, positiveAction) }
                title?.let { title -> builder.setTitle(title) }

                builder.show()
            }
        }
    }

    fun displayLoading() {
        //loadingLayout?.visibility = View.VISIBLE
    }

    fun dismissLoading() {
        //loadingLayout?.visibility = View.GONE
    }
}