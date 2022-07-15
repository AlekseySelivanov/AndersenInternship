package com.example.andersenhw.ui

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.andersenhw.R
import com.example.andersenhw.databinding.FragmentMainBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class MainFragment: Fragment() {

    private lateinit var viewModel: MainViewModel
    private var link = ""
    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding) { throw IllegalStateException("Binding is not initialized") }

    private val picker =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK ) {
                val intent = result.data
                viewModel.currentUri = intent?.data
                binding.imageView.setImageURI(intent?.data)
            }
        }

    private val isPermissionGranted: Boolean
        get() {
            return ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

        }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted: Boolean ->
            if (!granted) {
                showToastShort(getString(R.string.access_denied))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.imageView.setImageURI(viewModel.currentUri)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFlags.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_flagsFragment)
        }
        initViews()
    }

    private fun initViews() {
        binding.btnGallery.setOnClickListener {
            if (!isPermissionGranted) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else if (isPermissionGranted) {
                picker.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
            }
        }

        var bitmap: Bitmap
        binding.btnPicasso.setOnClickListener {
            link = binding.edText.text.toString().trim()
            if (link.isEmpty()) {
                showToastShort(getString(R.string.link_to_picture))
            } else {
                if (Patterns.WEB_URL.matcher(link).matches()) {
                    Picasso.get()
                        .load(link)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .placeholder(R.drawable.ic_image)
                        .error(R.drawable.ic_error)
                        .centerCrop()
                        .resize(600, 600)
                        .into(binding.imageView, object : Callback {
                            override fun onSuccess() {
                                val bm = (binding.imageView.getDrawable() as BitmapDrawable).bitmap
                                val uri: Uri = getImageUri(requireContext(), bm)
                                viewModel.currentUri = uri
                            }
                            override fun onError(e: Exception?) {
                                showToastShort ("Error: $e")
                            }
                        })
                } else {
                    showToastShort(getString(R.string.not_link))
                }
            }
        }
        binding.btnGlide.setOnClickListener {
            link = binding.edText.text.toString().trim()
            if (link.isEmpty()
            ) {
                showToastShort(getString(R.string.link_to_picture))
            } else {
                if (Patterns.WEB_URL.matcher(link).matches()) {
                    Glide
                        .with(this)
                        .asBitmap()
                        .load(link)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache( true )
                        .apply(
                            RequestOptions()
                                .error(R.drawable.ic_error)
                                .centerCrop()
                                .placeholder(R.drawable.ic_image)
                        )
                        .listener(object : RequestListener<Bitmap> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Bitmap>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                showToastShort("Error: $e")
                                return false
                            }

                            override fun onResourceReady(
                                resource: Bitmap,
                                model: Any?,
                                target: Target<Bitmap>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.imageView.setImageBitmap(resource)
                                bitmap = resource
                                val uri: Uri = getImageUri(requireContext(), bitmap)
                                viewModel.currentUri = uri
                                return false
                            }
                        })
                        .into(binding.imageView)
                } else {
                    showToastShort(getString(R.string.not_link))
                }
            }
        }
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            "Title",
            null
        )
        return Uri.parse(path);
    }

    private fun showToastShort(hint: String) {
            Toast.makeText(
                requireContext(),
                hint,
                Toast.LENGTH_SHORT
            ).show()
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}