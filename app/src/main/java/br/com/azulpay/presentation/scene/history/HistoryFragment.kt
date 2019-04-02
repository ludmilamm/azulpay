package br.com.azulpay.presentation.scene.history

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.azulpay.R
import br.com.azulpay.common.Application
import br.com.azulpay.common.di.ApplicationComponent
import br.com.azulpay.presentation.common.BaseFragment
import br.com.azulpay.presentation.common.custom.CurrencyValueFormatter
import br.com.azulpay.presentation.common.event.StateEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.Utils
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

        with(barChartHistory) {
            xAxis.apply {
                setDrawGridLines(false)
                setDrawLabels(false)
            }
            axisLeft.apply {
                setDrawLabels(false)
                setDrawAxisLine(false)
            }
            axisRight.apply {
                setDrawLabels(false)
                setDrawAxisLine(false)
            }
            legend.isEnabled = false
            description.isEnabled = false
            animateY(2000)
            setNoDataText("")
            setTouchEnabled(false)
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
        }
    }

    private fun bindViewModel() {
        viewModel.transactionsLiveData.observe(viewLifecycleOwner, Observer { handleTransactions(it) })
    }

    private fun handleTransactions(event: StateEvent<List<TransactionDisplayModel>>) {
        if (event is StateEvent.Success) {
            adapter.setData(event.data.sortedBy { it.date })
            setupChartData(event.data)
        }
    }

    private fun setupChartData(transactions: List<TransactionDisplayModel>) {
        // organize data
        val groupedTransactions = transactions.groupBy { it.toUserId }
        val barEntriesToContact = groupedTransactions
                .map { it.key to it.value.map { it.value }.reduce { acc, decimal -> acc + decimal } }
                .sortedByDescending { it.second }
                .take(6)
                .mapIndexed { index, it -> it.first to BarEntry(index.toFloat(), it.second.toFloat()) }

        // set values data
        val dataSet = BarDataSet(barEntriesToContact.map { it.second }, "")
        with(dataSet) {
            setGradientColor(ContextCompat.getColor(context!!, R.color.persianGreen), ContextCompat.getColor(context!!, R.color.eggBlue))
            valueTextColor = ContextCompat.getColor(context!!, R.color.supernova)
            valueTextSize = 8f
            valueFormatter = CurrencyValueFormatter()
            valueTypeface = ResourcesCompat.getFont(context!!, R.font.semi_bold)
            isHighlightEnabled = false
        }

        val data = BarData(dataSet)
        data.barWidth = .03f
        barChartHistory.data = data
        barChartHistory.invalidate()

        // set image data
        groupedTransactions.map { it.value.first() }.forEach { transition ->
            Glide.with(this)
                    .asDrawable()
                    .apply(RequestOptions.circleCropTransform())
                    .load(transition.toUserImage)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return true
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            resource?.let { drawable ->
                                val bitmap = (drawable as BitmapDrawable).bitmap
                                val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, Utils.convertDpToPixel(35f).toInt(), Utils.convertDpToPixel(35f).toInt(), true))

                                barEntriesToContact.firstOrNull { it.first == transition.toUserId }?.apply {
                                    dataSet.addEntry(BarEntry(this.second.x, -2f, scaledDrawable))
                                    data.notifyDataChanged()
                                    barChartHistory.notifyDataSetChanged()
                                    barChartHistory.invalidate()
                                }
                            }
                            return true
                        }
                    }).submit()
        }
    }
}
