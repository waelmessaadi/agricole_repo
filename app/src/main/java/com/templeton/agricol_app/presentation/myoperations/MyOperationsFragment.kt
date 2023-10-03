package com.templeton.agricol_app.presentation.myoperations

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.templeton.agricol_app.R
import com.templeton.agricol_app.data.models.Operation
import com.templeton.agricol_app.databinding.FragmentMyOperationsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MyOperationsFragment : Fragment() {

    lateinit var binding: FragmentMyOperationsBinding
    lateinit var myOperationsAdapter: MyOperationsAdapter
    val account: MyOperationsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyOperationsBinding.inflate(inflater, container, false)
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {

        myOperationsAdapter =
            MyOperationsAdapter(account.operationDataArgs.operations.map {
                it.copy(
                    date = timestampToDate(
                        it.date.toLong()
                    )
                )
            }.sortedWith(compareByDescending<Operation> { it.date }.thenBy { it.title }))

        binding.rcvMyOperations.apply {
            adapter = myOperationsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun timestampToDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.timeZone =
            TimeZone.getTimeZone("UTC")
        val date =
            Date(timestamp * 1000)
        return dateFormat.format(date)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.totalBalanceTv.text =
            account.operationDataArgs.balance.toString() + getString(R.string.currency_symbole)
        binding.accountTitleTv.text = account.operationDataArgs.label

        binding.myAccountsTitle.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}