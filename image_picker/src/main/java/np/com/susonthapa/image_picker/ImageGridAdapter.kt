package np.com.susonthapa.image_picker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import np.com.susonthapa.image_picker.databinding.ImagePickerItemBinding

class ImageGridAdapter(
    private val images: ArrayList<GridItem>,
    private val itemListener: (Int) -> Unit
) : RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder>() {

    private var nextSelectedIndex = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImagePickerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position], itemListener)
    }


    inner class ImageViewHolder(private val binding: ImagePickerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GridItem, itemListener: (Int) -> Unit) {
            // prefer low res, if not available then fallback to high res
            binding.imageDrawee.setImageURI(item.imageUri, null)
            toggleIndicator(item)
            // set the transition name
            binding.imageDrawee.setOnClickListener {
                // toggle the selection
                if (item.isSelected()) {
                    // reset the selection
                    adjustItemSelectionIndex(item.selectedIndex)
                    item.selectedIndex = -1
                    nextSelectedIndex--
                } else {
                    // the item is selected
                    item.selectedIndex = nextSelectedIndex
                    nextSelectedIndex++
                }
                itemListener(nextSelectedIndex - 1)
                notifyDataSetChanged()
            }
        }

        private fun adjustItemSelectionIndex(unSelectedIndex: Int) {
            images.forEach {
                // decrements all the index greater then unSelectedIndex
                if (it.isSelected() && it.selectedIndex > unSelectedIndex) {
                    it.selectedIndex--
                }
            }
        }

        private fun toggleIndicator(item: GridItem) {
            if (item.isSelected()) {
                binding.selectionIndicator.text = item.selectedIndex.toString()
                binding.selectionOverlay.visibility = View.VISIBLE
            } else {
                binding.selectionOverlay.visibility = View.INVISIBLE
            }
        }
    }
}