package com.templeton.agricol_app.presentation.myoperations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.templeton.agricol_app.R
import com.templeton.agricol_app.data.models.Operation

class MyOperationsAdapter(private var operationList: List<Operation>) : RecyclerView.Adapter<MyOperationsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val operationTitleTextView: TextView = itemView.findViewById(R.id.operation_title_tv)
        val operationDateTextView: TextView = itemView.findViewById(R.id.operation_date_tv)
        val operationBalanceTextView: TextView = itemView.findViewById(R.id.operation_balance_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.operation_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val operation = operationList[position]
        holder.operationTitleTextView.text = operation.title
        holder.operationDateTextView.text = operation.date
        holder.operationBalanceTextView.text = operation.amount + " "+ holder.itemView.resources.getString(R.string.currency_symbole)
    }

    override fun getItemCount(): Int {
        return operationList.size
    }
}