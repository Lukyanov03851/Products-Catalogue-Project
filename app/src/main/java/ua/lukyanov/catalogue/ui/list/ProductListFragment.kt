package ua.lukyanov.catalogue.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_product_list.*
import ua.lukyanov.catalogue.R
import ua.lukyanov.catalogue.databinding.FragmentProductListBinding
import ua.lukyanov.catalogue.ui.detail.ARG_PRODUCT_ID
import javax.inject.Inject
import javax.inject.Provider

private const val TAG = "ProductListFragment"
private const val ARG_SEARCH_TEXT = "search_text"
private const val ARG_SEARCH_VIEW_ICONIFIED = "search_view_iconified"
private const val DEFAULT_QUERY = "phone"

class ProductListFragment : Fragment() {

    @Inject lateinit var factory: ViewModelProvider.Factory
    @Inject lateinit var mProductsAdapter: ProductListAdapter
    @Inject lateinit var mLayoutManager: Provider<LinearLayoutManager>

    private lateinit var mProductsViewModel: ProductListViewModel

    private var searchViewIconified = true
    private var searchQueryText: String = DEFAULT_QUERY

    //view
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setHasOptionsMenu(true)
        AndroidSupportInjection.inject(this)
        mProductsViewModel = ViewModelProvider(this, factory).get(ProductListViewModel::class.java)

        if (savedInstanceState != null) {
            searchViewIconified = savedInstanceState.getBoolean(ARG_SEARCH_VIEW_ICONIFIED)
            searchQueryText = savedInstanceState.getString(ARG_SEARCH_TEXT) ?: DEFAULT_QUERY
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentProductListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        binding.viewModel = mProductsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)

        mProductsViewModel.findProductsList(searchQueryText)
        observeProductsList()

        errorView?.setRetryListener { mProductsViewModel.findProductsList(searchQueryText) }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.search)

        searchView = menuItem.actionView as SearchView
        searchView?.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchView?.isIconified = searchViewIconified

        searchView?.setQuery(searchQueryText, false)


        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                submitSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchQueryText = newText
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    fun submitSearchQuery(query: String){
        hideKeyboard()
        searchQueryText = query
        mProductsAdapter.clearItems()
        mProductsAdapter.notifyDataSetChanged()

        mProductsViewModel.findProductsList(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ARG_SEARCH_VIEW_ICONIFIED, searchView?.isIconified ?: false)
        outState.putString(ARG_SEARCH_TEXT, searchQueryText)
    }

    private fun hideKeyboard() {
        val manager = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        val viewBinder = view?.windowToken
        if (manager != null && viewBinder != null){
            val inputManager = manager as InputMethodManager
            inputManager.hideSoftInputFromWindow(viewBinder, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}
