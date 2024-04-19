package com.mrdev.androidtestassesment.post.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mrdev.androidtestassesment.R
import com.mrdev.androidtestassesment.base.ImageDiskCache
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.postModel.PostBean
import java.net.HttpURLConnection
import java.net.URL

class PostAdapter(
    private val context: Context,
    private val onItemClick: CallBack<PostBean>,
    private val originalData: ArrayList<PostBean>
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    // Local cache to store bitmaps
    private val localCache: HashMap<String, Bitmap> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return originalData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = originalData[position]
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: ImageView = itemView.findViewById(R.id.ivImages)

        fun bind(post: PostBean) {
            // Load image asynchronously in the background
            loadImageFromNetwork(post.thumbnail, title)
        }

        private fun loadImageFromNetwork(thumbnail: PostBean.Thumbnail, imageView: ImageView) {
            val imageUrl = "${thumbnail.domain}/${thumbnail.basePath}/0/${thumbnail.key}"
            // Check if the bitmap exists in the local cache
            val cachedBitmap = localCache[imageUrl]
            if (cachedBitmap != null) {
                // If bitmap is found in cache, use it directly
                imageView.setImageBitmap(cachedBitmap)
            } else {
                // Otherwise, load image from network
                val thread = Thread {
                    try {
                        val url = URL(imageUrl)
                        val connection = url.openConnection() as HttpURLConnection
                        connection.doInput = true
                        connection.connect()
                        val inputStream = connection.inputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        // Set the bitmap to the ImageView on the main UI thread
                        imageView.post {
                            imageView.setImageBitmap(bitmap)
                        }

                        // Cache the bitmap locally to avoid reloading
                        localCache[imageUrl] = bitmap

                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Handle the case when the image fails to load
                    }
                }
                thread.start()
            }
        }
    }
}

