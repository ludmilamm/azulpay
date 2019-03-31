package br.com.azulpay.presentation.scene.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.azulpay.R
import br.com.azulpay.common.Application
import br.com.azulpay.common.di.ApplicationComponent
import br.com.azulpay.presentation.common.BaseFragment
import br.com.azulpay.presentation.common.event.StateEvent
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.fragment_history.*
import javax.inject.Inject

class HistoryFragment : BaseFragment() {

    @Inject
    override lateinit var factory: ViewModelProvider.Factory
    override lateinit var viewModel: HistoryViewModel

    private val component: ApplicationComponent? by lazy { (activity?.application as Application).component }
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(HistoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        recyclerViewHistory.layoutManager = LinearLayoutManager(context)
        recyclerViewHistory.adapter = adapter
    }

    private fun bindViewModel() {
        viewModel.transactionsLiveData.observe(viewLifecycleOwner, Observer { handleTransactions(it) })
        viewModel.transactionsGroupedByUserLiveData.observe(viewLifecycleOwner, Observer { handleBarEntries(it) })
    }

    private fun handleTransactions(event: StateEvent<List<TransactionDisplayModel>>) {
        if (event is StateEvent.Success) {
            adapter.setData(event.data)
        }
    }

    private fun handleBarEntries(event: StateEvent<List<BarEntry>>) {
        if (event is StateEvent.Success) {
            barChartHistory.data = BarData(BarDataSet(event.data, ""))
            barChartHistory.invalidate()
        }
    }

}
