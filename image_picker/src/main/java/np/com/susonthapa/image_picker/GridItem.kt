package np.com.susonthapa.image_picker

import android.net.Uri

data class GridItem(
    val name: String,
    val imageUri: Uri,
    var selectedIndex: Int = -1,
    var isVisible: Boolean = false
) {
    fun isSelected(): Boolean {
        return selectedIndex != -1
    }
}