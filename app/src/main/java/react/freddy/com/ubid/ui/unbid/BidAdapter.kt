package react.freddy.com.ubid.ui.unbid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import react.freddy.com.ubid.R
import react.freddy.com.ubid.databinding.BidListItemBinding
import react.freddy.com.ubid.vo.EpicVo

/**
 * data :2020/7/17
 * auth :wjp
 * Description :
 */
class BidAdapter : ListAdapter<EpicVo, BidAdapter.EpicViewHolder>(BidDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpicViewHolder {
        return EpicViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.bid_list_item,
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: EpicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EpicViewHolder(
        private val binding: BidListItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(ev: EpicVo){
            with(binding){
                epicVo = ev
                executePendingBindings()
            }
        }
    }

}

private class BidDiffCallback : DiffUtil.ItemCallback<EpicVo>(){
    override fun areItemsTheSame(oldItem: EpicVo, newItem: EpicVo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EpicVo, newItem: EpicVo): Boolean {
        return oldItem == newItem
    }

}