package de.fklappan.app.repositoryloader.ui.overviewrepository

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.fklappan.app.repositoryloader.R
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import kotlinx.android.synthetic.main.list_item_repository.view.*

/**
 * The adapter implementation to show a list of repositories. Items can be set via the items
 * variable. A click listener can be passed through the constructor and will receive the
 * RepositoryGuiModel of the clicked item.
 */
class OverviewRepositoryAdapter(private val clickListener: (RepositoryGuiModel) -> Unit) : RecyclerView.Adapter<OverviewRepositoryAdapter.ViewHolder>() {

    var items: MutableList<RepositoryGuiModel> = ArrayList()
        set(value) {
            items.clear()
            items.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(LOG_TAG, "onCreateViewHolder")
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_repository,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(LOG_TAG, "onBindViewHolder")
        holder.bindItem(items[position], clickListener)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(repositoryGuiModel: RepositoryGuiModel, clickListener: (RepositoryGuiModel) -> Unit) {
            Log.d(LOG_TAG, "bindItem for repository: ${repositoryGuiModel.repositoryName}")
            itemView.textViewRepositoryName.text = repositoryGuiModel.repositoryName
            itemView.textViewStargazersCount.text = repositoryGuiModel.stargazersCount.toString()
            itemView.setOnClickListener { clickListener.invoke(repositoryGuiModel) }
            if (repositoryGuiModel.bookmark) {
                itemView.imageViewBookmark.setImageResource(R.drawable.bookmark)
                itemView.imageViewBookmark.imageTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.colorAccent))
            } else {
                itemView.imageViewBookmark.setImageResource(R.drawable.unbookmark)
                itemView.imageViewBookmark.imageTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.gray))
            }
        }
    }
}