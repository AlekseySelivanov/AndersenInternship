package com.example.andersenhw.ui.home

import android.content.Context
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenhw.FinalApp
import com.example.andersenhw.R
import com.example.andersenhw.databinding.FragmentLoggedInBinding
import com.example.andersenhw.ui.home.cardView.CustomCreditCard
import com.example.andersenhw.ui.home.cardView.animations.shake
import com.example.common.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@Suppress("DEPRECATION")
class LoggedInFragment : Fragment() {

    companion object {
        fun newInstance(): LoggedInFragment = LoggedInFragment()
    }

    var cursor = 0
    private val cards = arrayListOf(
        CustomCreditCard.TYPE.RUBLE,
        CustomCreditCard.TYPE.DOLLAR,
        CustomCreditCard.TYPE.EURO,
    )
    private val dogsAdapter by lazy { DogsAdapter() }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoggedInViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentLoggedInBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as FinalApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoggedInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupListeners()
        setupObserves()
    }

    private fun setupViews() = with(binding) {
        rvDogs.apply {
            adapter = dogsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupListeners() = with(binding) {
        card.setOnClickListener {
            if (++cursor < cards.size) card.setType(cards[cursor]) else {
                cursor = 0
                card.setType(cards[cursor])
            }
            card.shake()
            vibrateOnce()
        }
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logOut -> {
                    viewModel.logOutClicked()
                    true
                }
                else -> {
                    false
                }
            }
        }
        dogsAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                val visibleItemsCount = rvDogs.layoutManager?.childCount ?: 0
                if (visibleItemsCount < (rvDogs.layoutManager?.itemCount ?: 0))
                    viewModel.readFacts()
                else
                    dogsAdapter.unregisterAdapterDataObserver(this)
            }
        })

        binding.rvDogs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.readFacts()
                }
            }
        })
    }

    private fun setupObserves() {
        viewModel.name.onEach {
            binding.welcomeTextView.text = resources.getString(R.string.logged_in_welcome, it)
        }.launchWhenStarted(lifecycleScope)

        viewModel.dogFacts.onEach { result ->
            dogsAdapter.submitList(result)
        }.launchWhenStarted(lifecycleScope)
    }

    private fun vibrateOnce() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vib = vibratorManager.defaultVibrator
            vib.vibrate(VibrationEffect.createOneShot(100, 1))
        } else {
            val vib = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vib.vibrate(500)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}