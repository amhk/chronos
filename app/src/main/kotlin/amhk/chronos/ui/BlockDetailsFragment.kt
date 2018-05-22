package amhk.chronos.ui

import amhk.chronos.R
import amhk.chronos.database.ID_NOT_IN_DATABASE
import amhk.chronos.model.Block
import amhk.chronos.model.BlockDetailsViewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.fragment_block_details.view.*

internal class BlockDetailsFragment : Fragment() {
    private var id: Long = ID_NOT_IN_DATABASE
    private lateinit var viewModel: BlockDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        id = arguments!!.getLong("id", ID_NOT_IN_DATABASE)
        if (id == ID_NOT_IN_DATABASE) {
            throw IllegalArgumentException("fragment argument 'id' missing")
        }

        val view = inflater.inflate(R.layout.fragment_block_details, container, false)

        viewModel = ViewModelProviders.of(this).get(BlockDetailsViewModel::class.java)
        viewModel.getBlockById(id).observe(this, Observer<Block> {
            it?.let {
                view.block_details_id.apply {
                    text = it.id.toString()
                }
                view.block_details_begin.apply {
                    text = it.begin.toString()
                }
                view.block_details_end.apply {
                    text = it.end.toString()
                }
            }
        })

        return view
    }

    internal companion object {
        fun newInstance(id: Long): BlockDetailsFragment {
            val fragment = BlockDetailsFragment()
            fragment.arguments = bundleOf(Pair("id", id))
            return fragment
        }
    }
}
