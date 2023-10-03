package com.templeton.agricol_app.presentation.myaccounts

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.templeton.agricol_app.R
import com.templeton.agricol_app.data.models.Account
import com.templeton.agricol_app.data.models.BankApiResponseItem

class MyAccountsExpandableListAdapter internal constructor(
    private val context: Context,
    private val bankList: List<BankApiResponseItem>,
) : BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Account {
        return this.bankList[listPosition].accounts[expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var accountConvertView = convertView
        val expandedListAccount = getChild(listPosition, expandedListPosition)
        if (accountConvertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            accountConvertView = layoutInflater.inflate(R.layout.account_item, null)
        }
        val expandedListAccountTitle =
            accountConvertView!!.findViewById<TextView>(R.id.account_item_title_tv)
        val expandedListAccountBalance =
            accountConvertView.findViewById<TextView>(R.id.account_item_balance_tv)
                expandedListAccountTitle.text = expandedListAccount.label

        expandedListAccountBalance.text =
            expandedListAccount.balance.toString() + " " + context.resources.getString(R.string.currency_symbole)
        return accountConvertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.bankList[listPosition].accounts.size
    }

    override fun getGroup(listPosition: Int): BankApiResponseItem {
        return this.bankList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.bankList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var bankConvertView = convertView
        val listBank = getGroup(listPosition) //as String
        if (bankConvertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            bankConvertView = layoutInflater.inflate(R.layout.bank_item, null)
        }
        val listBankTitle = bankConvertView!!.findViewById<TextView>(R.id.bank_title_tv)
        val listBankBalance = bankConvertView.findViewById<TextView>(R.id.bank_balance_tv)
        val collapseImg = bankConvertView.findViewById<ImageView>(R.id.collapse_img)


        listBankTitle.text = listBank.name
        listBankBalance.text = String.format("%.2f", listBank.accounts.sumOf { it.balance }) + " " +context.resources.getString(R.string.currency_symbole)


        if (isExpanded) {
            collapseImg.setImageResource(R.drawable.arrow_up);
        } else {
            collapseImg.setImageResource(R.drawable.arrow_down);
        }
        return bankConvertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}