package np.com.susonthapa.image_picker

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import kotlin.math.max

class AutoGridLayoutManager(
    val context: Context
) : GridLayoutManager(context, 1) {


    private val columnWidth = context.resources.getDimensionPixelSize(R.dimen.image_item_size)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        val totalSpace = if (orientation == VERTICAL) {
            width - paddingRight - paddingLeft
        } else {
            height - paddingTop - paddingBottom
        }
        val spanCount = max(1, totalSpace / columnWidth)
        setSpanCount(spanCount)
        super.onLayoutChildren(recycler, state)
    }
}