package com.mamacare.app.ui.healthlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamacare.app.databinding.FragmentHealthLogListBinding
import com.mamacare.app.ui.MamaCareViewModelFactory

class HealthLogListFragment : Fragment() {

    private var _binding: FragmentHealthLogListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HealthLogViewModel
    private lateinit var adapter: HealthLogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthLogListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btnAdd).setOnClickListener {

        val factory = MamaCareViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[HealthLogViewModel::class.java]

        setupRecyclerView()
        observeHealthLogs()

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(
                HealthLogListFragmentDirections.actionHealthLogListToAddEdit()
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = HealthLogAdapter(
            onItemClick = { log ->
                findNavController().navigate(
                    HealthLogListFragmentDirections.actionHealthLogListToAddEdit(logId = log.id.toLong())
                )
            },
            onDeleteClick = { log ->
                viewModel.delete(log)
            }
        )
        binding.rvHealthLogs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHealthLogs.adapter = adapter
    }

    private fun observeHealthLogs() {
        viewModel.logs.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            if (list.isNullOrEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvHealthLogs.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvHealthLogs.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
