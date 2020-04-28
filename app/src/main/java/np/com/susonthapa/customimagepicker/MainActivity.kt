package np.com.susonthapa.customimagepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import np.com.susonthapa.image_picker.GridItem
import np.com.susonthapa.image_picker.ImagePickerFragment

class MainActivity : AppCompatActivity(), ImagePickerFragment.OnImageSelectionDone {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun selectedImages(images: List<GridItem>) {
        Toast.makeText(this, "Total image selected: ${images.size}", Toast.LENGTH_SHORT).show()
    }


}
