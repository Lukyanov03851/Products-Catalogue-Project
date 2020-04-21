package ua.lukyanov.catalogue.ui.recent

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_recent_product_list.*
import ua.lukyanov.catalogue.R
import ua.lukyanov.catalogue.databinding.FragmentRecentProductListBinding
import ua.lukyanov.catalogue.ui.detail.ARG_PRODUCT_ID
import ua.lukyanov.catalogue.ui.list.ProductListAdapter
import javax.inject.Inject
import javax.inject.Provider

private const val TAG = "RecentProductsFragment"

class RecentProductListFragment : Fragment() {

    @Inject lateinit var factory: ViewModelProvider.Factory
    @Inject lateinit var mProductsAdapter: ProductListAdapter
    @Inject lateinit var mLayoutManager: Provider<LinearLayoutManager>

    private lateinit var mProductsViewModel: RecentProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setHasOptionsMenu(true)
        AndroidSupportInjection.inject(this)
        mProductsViewModel = ViewModelProvider(this, factory).get(RecentProductListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentRecentProductListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_product_list, container, false)
        binding.viewModel = mProductsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)

        mProductsViewModel.loadRecentProductsList()
        observeProductsList()

        errorView?.setRetryListener { mProductsViewModel.loadRecentProductsList() }
    }

    private fun initRecyclerView(view: View){
        mProductsAdapter.setItemClickListener { productId ->
            val bundle = bundleOf(ARG_PRODUCT_ID to productId)
            Navigation.findNavController(view).navigate(R.id.action_detail, bundle)
        }
        rvProductList.apply {
            layoutManager = mLayoutManager.get()
            adapter = mProductsAdapter
        }
    }

    private fun observeProductsList() {
        mProductsViewModel.productsMutableData.observe(viewLifecycleOwner, Observer { productsList ->
            mProductsAdapter.setItems(productsList.toMutableList())
        })
    }

}
