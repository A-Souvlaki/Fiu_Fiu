package com.icesi.fiufiu.model.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icesi.fiufiu.R
import com.icesi.fiufiu.seller.SellerOrderOverviewFragment
import com.icesi.fiufiu.model.Order
import com.icesi.fiufiu.model.holders.SellerOrdersToConfirmViewHolder

class SellerOrdersToConfirmAdapter: RecyclerView.Adapter<SellerOrdersToConfirmViewHolder>(),
    SellerOrdersToConfirmViewHolder.OnChangesInOrderListener {

    lateinit var onOrderConfirmObserver:  SellerOrderOverviewFragment.OnConfirmOrderListener
    private val orders = ArrayList<Order>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerOrdersToConfirmViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.seller_order_confirmation_row,parent,false)
            val productViewHolder = SellerOrdersToConfirmViewHolder(view)
            productViewHolder.onOrderConfirmObserver = onOrderConfirmObserver
            productViewHolder.onChangesInOrderListener = this
            return productViewHolder
        }

        override fun onBindViewHolder(holder: SellerOrdersToConfirmViewHolder, position: Int) {
            val order = orders[position]
            holder.bindProduct(order)
        }

        override fun getItemCount(): Int {
            return orders.size
        }

        fun clear() {
            val size = orders.size
            orders.clear()
            notifyItemRangeRemoved(0,size)
        }

        fun addOrder(order: Order){
            if(order.orderFlag=="Pendiente"){
                orders.add(order)
                notifyItemInserted(orders.size-1)
            }
        }

    override fun deleteOrder(order: Order) {
        Log.e("Order eliminada ", orders.indexOf(order).toString())
        var index = orders.indexOf(order)
        orders.removeAt(index)
        notifyItemRemoved(index)
    }
}