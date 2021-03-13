package ua.lukyanov.catalogue.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ua.lukyanov.catalogue.R
import ua.lukyanov.catalogue.databinding.ItemProductBinding
import ua.lukyanov.catalogue.model.Product
import ua.lukyanov.catalogue.util.inflateImage
import java.util.*

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private var mProducts : MutableList<Product> = Collections.emptyList()
    private var mOnItemClickListener: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val context = holder.itemView.context
        val listItem = mProducts[position]
        holder.binding.product = listItem
        holder.binding.handler = this

        inflateImage(context, listItem.thumbnailUrl, holder.binding.imgProduct)

    }

    override fun getItemCount(): Int {
        return mProducts.size
    }

    fun setItems(products : MutableList<Product>) {
        this.mProducts = products
        notifyDataSetChanged()
    }

    fun clearItems() {
        mProducts.clear()
    }

    fun onItemClick(productId: String){
        mOnItemClickListener(productId)
    }

    fun setItemClickListener(listener: (String) -> Unit){
        this.mOnItemClickListener = listener
    }

    inner class ProductViewHolder(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root)

}