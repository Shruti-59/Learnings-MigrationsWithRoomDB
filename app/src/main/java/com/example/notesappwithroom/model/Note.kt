package com.example.notesappwithroom.model

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import org.jetbrains.annotations.NotNull

@VersionedParcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NonNull
    val title: String?,
    @NonNull
    val description: String?,
    @NonNull
    val priority: Int,
    @NonNull
    val profilePhoto: Bitmap?,
    @NotNull
    val isDone : Int,
    @NotNull
    @ColumnInfo(defaultValue = "Indore")
    val address : String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readParcelable(Bitmap::class.java.classLoader),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(priority)
        parcel.writeParcelable(profilePhoto, flags)
        parcel.writeInt(isDone)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}
