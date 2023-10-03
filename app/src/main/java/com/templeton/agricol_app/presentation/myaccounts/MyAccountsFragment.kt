package com.templeton.agricol_app.presentation.myaccounts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.templeton.agricol_app.common.Resource
import com.templeton.agricol_app.databinding.FragmentMyAccountsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyAccountsFragment : Fragment() {

    lateinit var binding: FragmentMyAccountsBinding
    lateinit var expandableAgricoleAdapter: ExpandableListAdapter
    lateinit var expandableOtherBankAdapter: ExpandableListAdapter

    private val viewModel: MyAccountsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountsBinding.inflate(inflater, container, false)
        setupRecyclerViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.banks.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        hideProgressBar()
                        val banks = resource.data
                        Log.e("TAG", "onViewCreated: $banks")

                        // agricole banks
                        val agricoleBanks = banks!!.filter { bankApiResponseItem -> bankApiResponseItem.isCA == 1 }
                            .sortedBy { it.name }
                        expandableAgricoleAdapter = MyAccountsExpandableListAdapter(
                            requireContext(),
                            agricoleBanks
                        )

                        binding.expendableListAgricoleAccounts.setAdapter(expandableAgricoleAdapter)

                        binding.expendableListAgricoleAccounts.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                            findNavController().navigate(
                                MyAccountsFragmentDirections.actionMyAccountsFragmentToMyOperationsFragment(
                                    agricoleBanks[groupPosition].accounts[childPosition]
                                )
                            )
                            false
                        }

                        // others banks
                        val othersBanks = banks.filter { bankApiResponseItem -> bankApiResponseItem.isCA == 0 }
                            .sortedBy { it.name }
                        expandableOtherBankAdapter = MyAccountsExpandableListAdapter(
                            requireContext(),
                            othersBanks
                        )

                        binding.expendableListOthersAccounts.setAdapter(expandableOtherBankAdapter)

                        binding.expendableListOthersAccounts.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                            findNavController().navigate(
                                MyAccountsFragmentDirections.actionMyAccountsFragmentToMyOperationsFragment(
                                    othersBanks[groupPosition].accounts[childPosition]
                                )
                            )
                            false
                        }

                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        val error = resource.message ?: "An unexpected error occured"
                        Log.e("TAG", "onViewCreated: $error")
                        // Affichez le message d'erreur
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerViews() {
        // agricole
        expandableAgricoleAdapter = MyAccountsExpandableListAdapter(requireContext(), emptyList())

        binding.expendableListAgricoleAccounts.setAdapter(expandableAgricoleAdapter)

        // others
        expandableOtherBankAdapter = MyAccountsExpandableListAdapter(requireContext(), emptyList())

        binding.expendableListOthersAccounts.setAdapter(expandableOtherBankAdapter)

    }
}