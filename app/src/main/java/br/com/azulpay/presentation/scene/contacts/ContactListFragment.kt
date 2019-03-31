package br.com.azulpay.presentation.scene.contacts

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
import kotlinx.android.synthetic.main.fragment_contact_list.*
import javax.inject.Inject

class ContactListFragment : BaseFragment() {

    @Inject
    override lateinit var factory: ViewModelProvider.Factory
    override lateinit var viewModel: ContactListViewModel

    private val component: ApplicationComponent? by lazy { (activity?.application as Application).component }
    private val adapter: ContactListAdapter by lazy { ContactListAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(ContactListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        recyclerViewContacts.layoutManager = LinearLayoutManager(context)
        recyclerViewContacts.adapter = adapter
    }

    private fun bindViewModel() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner, Observer { it?.let { handleContacts(it) } })
    }

    private fun handleContacts(event: StateEvent<List<ContactDisplayModel>>) {
        if (event is StateEvent.Success) {
            adapter.setData(event.data)
        }
    }
}
