package com.icesi.fiufiu.model.holders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.icesi.fiufiu.R
import com.icesi.fiufiu.seller.SellerOrderOverviewFragment
import com.icesi.fiufiu.model.Order
import com.icesi.fiufiu.model.User
import com.icesi.fiufiu.util.Util

class SellerOrdersToConfirmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    //STATE
    lateinit var order: Order
    lateinit var onOrderConfirmObserver:  SellerOrderOverviewFragment.OnConfirmOrderListener
    lateinit var onChangesInOrderListener:  SellerOrdersToConfirmViewHolder.OnChangesInOrderListener

    var productImg: ImageView = itemView.findViewById(R.id.product_img_order)
    var productName: TextView = itemView.findViewById(R.id.productname_text)
    var username: TextView = itemView.findViewById(R.id.username_text)
    var amount: TextView = itemView.findViewById(R.id.amount_text)
    var price: TextView = itemView.findViewById(R.id.price_text)

    var accepted_btn: Button = itemView.findViewById(R.id.accepted_btn)
    var declined_btn: Button = itemView.findViewById(R.id.declined_btn)
    var partially_btn: Button = itemView.findViewById(R.id.partially_btn)

    lateinit var orderId: String

    init {
        accepted_btn.setOnClickListener{
            onOrderConfirmObserver.confirmOrder(order.idOrder, order.idUser)
            onChangesInOrderListener.deleteOrder(order)
        }

        declined_btn.setOnClickListener{
            onOrderConfirmObserver.cancelOrder(order.idOrder, order.idUser)
            onChangesInOrderListener.deleteOrder(order)
        }

        partially_btn.setOnClickListener{
            onOrderConfirmObserver.editOrder(order)
        }
    }

    fun bindProduct(order: Order) {
        this.order = order
        findUser()
        productName.setText(order.name)
        amount.setText(order.amount.toString())
        price.setText("$" + order.totalPrice)
        orderId = order.idOrder
        Util.loadImage(order.imageID,productImg, "product-images")

    }

    private fun findUser() {
        Firebase.firestore.collection("users").document(order.idUser).get()
            .addOnSuccessListener {
                var currentUser = it.toObject(User::class.java)!!
                username.setText(currentUser.name)
        }
    }

    interface OnChangesInOrderListener{
        fun deleteOrder(order:Order)
    }
}