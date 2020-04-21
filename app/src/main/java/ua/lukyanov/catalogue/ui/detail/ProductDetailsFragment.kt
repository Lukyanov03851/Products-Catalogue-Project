package ua.lukyanov.catalogue.ui.detail

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_product_details.*
import ua.lukyanov.catalogue.R
import ua.lukyanov.catalogue.databinding.FragmentProductDetailsBinding
import ua.lukyanov.catalogue.util.inflateImage
import javax.inject.Inject

const val ARG_PRODUCT_ID = "productId"

class ProductDetailsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var mProductDetailsViewModel: ProductDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        mProductDetailsViewModel = ViewModelProvider(this, factory).get(ProductDetailsViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentProductDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        binding.viewModel = mProductDetailsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mProductDetailsViewModel.getProductById(arguments?.getString(ARG_PRODUCT_ID)!!)
        observeImage()

        errorView?.setRetryListener { mProductDetailsViewModel.getProductById(arguments?.getString(ARG_PRODUCT_ID)!!) }
    }

    private fun observeImage() {
        mProductDetailsViewModel.productMutableData.observe(viewLifecycleOwner, Observer { product ->
            context?.let {
                inflateImage(it, product.thumbnailUrl, imgProduct)
            }
        })
    }

}