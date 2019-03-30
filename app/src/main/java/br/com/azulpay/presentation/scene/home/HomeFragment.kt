package br.com.azulpay.presentation.scene.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import br.com.azulpay.R
import br.com.azulpay.common.Application
import br.com.azulpay.common.di.ApplicationComponent
import br.com.azulpay.presentation.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    override lateinit var factory: ViewModelProvider.Factory
    override lateinit var viewModel: HomeViewModel

    private val component: ApplicationComponent? by lazy { (activity?.application as Application).component }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        buttonSendMoney.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.contacts_fragment, null))
        buttonSentHistory.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.history_fragment, null))
    }
}
