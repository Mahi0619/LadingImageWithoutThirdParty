package com.mrdev.androidtestassesment.base
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jakewharton.disklrucache.DiskLruCache
import java.io.BufferedOutputStream
import java.io.IOException
import java.security.MessageDigest

class ImageDiskCache(context: Context) {

    private val diskCache: DiskLruCache
    init {
        val cacheDir = context.cacheDir
        val cacheSize = 10 * 1024 * 1024 // 10MB
        diskCache = DiskLruCache.open(cacheDir, 1, 1, cacheSize.toLong())
    }

    fun put(url: String, bitmap: Bitmap) {
        val key = hashKeyForDisk(url)
        try {
            val editor = diskCache.edit(key)
            val out = BufferedOutputStream(editor.newOutputStream(0))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
            editor.commit()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun get(url: String): Bitmap? {
        val key = hashKeyForDisk(url)
        try {
            val snapshot = diskCache.get(key)
            if (snapshot != null) {
                val inputStream = snapshot.getInputStream(0)
                return BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun hashKeyForDisk(url: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(url.toByteArray())
        val digest = md5.digest()
        val builder = StringBuilder()
        for (b in digest) {
            val hex = Integer.toHexString(b.toInt() and 0xFF)
            if (hex.length == 1) {
                builder.append('0')
            }
            builder.append(hex)
        }
        return builder.toString()
    }

    fun clearCache() {
        try {
            diskCache.delete()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
