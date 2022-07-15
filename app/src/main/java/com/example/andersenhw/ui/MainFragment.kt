package com.example.andersenhw.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.andersenhw.R
import com.example.andersenhw.data.DataProvider
import com.example.andersenhw.databinding.FragmentMainBinding
import com.example.andersenhw.domain.models.Category
import com.example.andersenhw.ui.adapter.CategoriesAdapter

class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding) { throw IllegalStateException("Binding is not initialized") }
    private val  adapter: CategoriesAdapter by lazy {
        CategoriesAdapter(::navigateToDetailedContact
        )
    }

    private var selectedContact: Category = Category()

    companion object {
        const val REQUIRED_ID_KEY = "id"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RVCategories.adapter = adapter
        showContent(DataProvider.data)
        if (arguments != null) {
            selectedContact =
                arguments?.getParcelable<Category>(DetailsFragment.EDITED_CONTACT) as Category
            binding.TextView.text = selectedContact.name
        }
    }

    private fun navigateToDetailedContact(selectedCategory: Category) {
        val bundle = Bundle()
        bundle.putParcelable(REQUIRED_ID_KEY, selectedCategory)
        findNavController().navigate(
            R.id.action_mainFragment_to_photosFragment,
            bundle
        )
    }

    private fun showContent(data: List<Category>) {
        adapter.setData(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}