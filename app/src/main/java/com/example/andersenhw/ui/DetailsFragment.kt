package com.example.andersenhw.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.andersenhw.R
import com.example.andersenhw.databinding.FragmentDetailsBinding
import com.example.andersenhw.domain.models.Category

class DetailsFragment: Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = checkNotNull(_binding) { throw IllegalStateException("Binding is not initialized") }

    private var selectedCategory: Category = Category()
    private lateinit var category: TextView

    companion object {
        const val EDITED_CONTACT = "id"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            selectedCategory =
                arguments?.getParcelable<Category>(MainFragment.REQUIRED_ID_KEY) as Category
        }

        category = view.findViewById(R.id.DetailsTextView)
        category.text = "${selectedCategory.name}. Some Details"
        binding.Button.setOnClickListener {
            selectedCategory.name = "${selectedCategory.name}: closed!"
            val bundle = Bundle()
            bundle.putParcelable(EDITED_CONTACT, selectedCategory)
            findNavController().navigate(
                R.id.action_detailsFragment_to_mainFragment,
                bundle
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}