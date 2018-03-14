package amhk.chronos.ui

import amhk.chronos.R
import amhk.chronos.model.Block
import amhk.chronos.model.BlockListViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.block_item.view.*
import kotlinx.android.synthetic.main.fragment_block_list.view.*

internal class BlockListFragment : Fragment() {
    private lateinit var viewModel: BlockListViewModel
    private lateinit var adapter: BlockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = BlockAdapter(context!!, activity as Navigator)

        viewModel = ViewModelProviders.of(this).get(BlockListViewModel::class.java)
        viewModel.liveData.observe(this, Observer<List<Block>> {
            it?.let {
                adapter.setItems(it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_block_list, container, false)

        view.block_list.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }

        return view
    }

    internal companion object {
        fun newInstance(): BlockListFragment {
            return BlockListFragment()
        }
    }
}

internal class BlockAdapter(private val context: Context,
                          private val navigator: Navigator,
                          private var items: List<Block> = ArrayList()) :
        RecyclerView.Adapter<BlockAdapter.ViewHolder>() {

    fun setItems(newItems: List<Block>) {
        if (items.isEmpty()) {
            items = newItems
            notifyItemRangeChanged(0, newItems.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                        items[oldItemPosition].id == newItems[newItemPosition].id

                override fun getOldListSize() = items.size

                override fun getNewListSize() = newItems.size

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                        items[oldItemPosition] == newItems[newItemPosition]
            })
            items = newItems
            result.dispatchUpdatesTo(this)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.block_item, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            val id = items[viewHolder.adapterPosition].id
            val fragment = BlockDetailsFragment.newInstance(id)
            navigator.goForward(fragment)
        }
        return viewHolder
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.text

        fun bind(item: Block) {
            textView.text = "Block ${item.id}"
        }
    }
}
