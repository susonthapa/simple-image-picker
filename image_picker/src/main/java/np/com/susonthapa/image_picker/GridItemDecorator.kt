package np.com.susonthapa.image_picker

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecorator(
    private val top: Int,
    private val right: Int,
    private val bottom: Int,
    private val left: Int,
    private val gridLayoutManager: AutoGridLayoutManager
) : RecyclerView.ItemDecoration() {

    constructor(margin: Int, gridLayoutManager: AutoGridLayoutManager): this(margin, margin, margin, margin, gridLayoutManager)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val columnIndex = position % gridLayoutManager.spanCount
        outRect.left = left
        // add the top margin for the only first row elements
        if (position < gridLayoutManager.spanCount) {
            outRect.top = top
        }
        // add the right margin to the last element of the row
        if (columnIndex == (gridLayoutManager.spanCount - 1)) {
            outRect.right = right
        }
        outRect.bottom = bottom
    }
}