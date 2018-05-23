package amhk.chronos.ui

import amhk.chronos.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

internal class BarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bar, container, false)
    }

    internal companion object {
        fun newInstance(): BarFragment {
            return BarFragment()
        }
    }
}
