package com.mrdev.androidtestassesment.post.postModel


import com.google.gson.annotations.SerializedName
import android.os.Parcel
import android.os.Parcelable


data class PostBean(
    @SerializedName("backupDetails")
    val backupDetails: BackupDetails,
    @SerializedName("coverageURL")
    val coverageURL: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("mediaType")
    val mediaType: Int,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("publishedBy")
    val publishedBy: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    @SerializedName("title")
    val title: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(BackupDetails::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(Thumbnail::class.java.classLoader)!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(backupDetails, flags)
        parcel.writeString(coverageURL)
        parcel.writeString(id)
        parcel.writeString(language)
        parcel.writeInt(mediaType)
        parcel.writeString(publishedAt)
        parcel.writeString(publishedBy)
        parcel.writeParcelable(thumbnail, flags)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostBean> {
        override fun createFromParcel(parcel: Parcel): PostBean {
            return PostBean(parcel)
        }

        override fun newArray(size: Int): Array<PostBean?> {
            return arrayOfNulls(size)
        }
    }

    data class BackupDetails(
        @SerializedName("pdfLink")
        val pdfLink: String,
        @SerializedName("screenshotURL")
        val screenshotURL: String
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(pdfLink)
            parcel.writeString(screenshotURL)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<BackupDetails> {
            override fun createFromParcel(parcel: Parcel): BackupDetails {
                return BackupDetails(parcel)
            }

            override fun newArray(size: Int): Array<BackupDetails?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Thumbnail(
        @SerializedName("aspectRatio")
        val aspectRatio: Int,
        @SerializedName("basePath")
        val basePath: String,
        @SerializedName("domain")
        val domain: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("key")
        val key: String,
        @SerializedName("qualities")
        val qualities: List<Int>,
        @SerializedName("version")
        val version: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.createIntArray()!!.toList(),
            parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(aspectRatio)
            parcel.writeString(basePath)
            parcel.writeString(domain)
            parcel.writeString(id)
            parcel.writeString(key)
            parcel.writeIntArray(qualities.toIntArray())
            parcel.writeInt(version)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Thumbnail> {
            override fun createFromParcel(parcel: Parcel): Thumbnail {
                return Thumbnail(parcel)
            }

            override fun newArray(size: Int): Array<Thumbnail?> {
                return arrayOfNulls(size)
            }
        }
    }
}
