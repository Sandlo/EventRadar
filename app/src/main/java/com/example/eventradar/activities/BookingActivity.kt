package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class BookingActivity : BaseActivity(), RecyclerViewHelperInterface {

    companion object {
        private const val PAYMENT_PROVIDER_ITEM = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SimpleListAdapter(
            listOf(
                SimpleListItem(
                    "Placeholder",
                    resources.getString(R.string.booking_title),
                    R.drawable.ic_circle_tag
                ),
                SimpleListItem(
                    "Placeholder",
                    resources.getString(R.string.booking_price),
                    R.drawable.ic_circle_euro
                ),
                SimpleListItem(
                    resources.getString(R.string.booking_payment_google),
                    resources.getString(R.string.booking_payment),
                    R.drawable.ic_square_google_pay
                ),
                SimpleListItem("", resources.getString(R.string.booking_info))
            ),
            this
        )

        findViewById<ExtendedFloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            startActivity(Intent(this, TicketActivity::class.java))
        }
    }

    override fun onItemClicked(view: View, position: Int) {
        if (position == PAYMENT_PROVIDER_ITEM) OutOfScopeDialog.show(this)
    }
}