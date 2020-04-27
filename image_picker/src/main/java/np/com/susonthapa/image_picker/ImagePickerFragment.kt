package np.com.susonthapa.image_picker

import android.content.ContentUris
import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import np.com.susonthapa.image_picker.databinding.FragmentImagePickerBinding
import java.lang.IllegalArgumentException

/**
 * A simple [Fragment] subclass.
 */
class ImagePickerFragment : Fragment() {

    private var _binding: FragmentImagePickerBinding? = null
    private val binding get() = _binding!!

    private val images = ArrayList<GridItem>()
    private lateinit var gridAdapter: ImageGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImagePickerBinding.inflate(inflater, container, false)
        val gridLayoutManager = AutoGridLayoutManager(context!!)
        gridAdapter = ImageGridAdapter(images) {
            binding.appToolBar.apply {
                title = if (it == 0) {
                    // reset the title
                    "Pick Photo"
                } else {
                    "Pick Photo: $it"
                }
            }
        }
        val itemDecorator = GridItemDecorator(
            context!!.resources.getDimensionPixelSize(R.dimen.grid_spacing),
            gridLayoutManager
        )
        binding.imageRecycler.apply {
            layoutManager = gridLayoutManager
            adapter = gridAdapter
            addItemDecoration(itemDecorator)
        }

        // setup the navigation with the toolBar
        NavigationUI.setupWithNavController(binding.appToolBar, findNavController())
        binding.appToolBar.apply {
            title = "Pick Photo"
            inflateMenu(R.menu.image_pick_menu)
            setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
            // override the default "back" icon with "X"
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24dp)
        }
        loadImages()

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.select_images) {
            val selectedImages = images.filter {
                it.isSelected()
            }
            if (selectedImages.isNotEmpty()) {
                (activity as OnImageSelectionDone).selectedImages(selectedImages)
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.root, "Select some images to send", Snackbar.LENGTH_LONG).show()
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadImages() {
        images.clear()
        val projections = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)
        val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

        val query = context!!.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projections,
            null,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(projections[0])
            val nameColumn = cursor.getColumnIndexOrThrow(projections[1])

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameColumn)
                val id = cursor.getLong(idColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                images.add(
                    GridItem(
                        name,
                        uri
                    )
                )
            }
        }
        gridAdapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is OnImageSelectionDone) {
            throw IllegalArgumentException("activity must implement OnImageSelectionDone")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnImageSelectionDone {
        fun selectedImages(images: List<GridItem>)
    }
}
