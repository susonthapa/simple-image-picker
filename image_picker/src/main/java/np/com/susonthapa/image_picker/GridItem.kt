package np.com.susonthapa.image_picker

import android.net.Uri

data class GridItem(
    val name: String,
    val imageUri: Uri,
    var selectedIndex: Int = -1
) {
    fun isSelected(): Boolean {
        return selectedIndex != -1
    }
}