import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.phonebook.R

class ContactFragment : Fragment() {

    private var nameTextView: TextView? = null
    private var phoneTextView: TextView? = null
    private var profileImageView: ImageView? = null

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_PHONE = "phone"
        private const val ARG_IMAGE_URI = "image_uri"

        fun newInstance(name: String, phone: String, imageUri: String) = ContactFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, name)
                putString(ARG_PHONE, phone)
                putString(ARG_IMAGE_URI, imageUri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contact_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        nameTextView = view.findViewById(R.id.tv_name)
        phoneTextView = view.findViewById(R.id.tv_phone)
        profileImageView = view.findViewById(R.id.iv_profile)

        // Retrieve arguments
        val name = arguments?.getString(ARG_NAME)
        val phone = arguments?.getString(ARG_PHONE)
        val imageUri = arguments?.getString(ARG_IMAGE_URI)

        // Update views
        nameTextView?.text = name
        phoneTextView?.text = phone
        profileImageView?.setImageURI(Uri.parse(imageUri))
    }
}
