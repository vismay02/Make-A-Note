package com.vismay.makeanote.data.local.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "color") val color: Int = -1,
    @ColumnInfo(name = "date") val date: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeInt(id)
        parcel?.writeString(note)
        parcel?.writeInt(color)
        parcel?.writeString(date)
    }

    companion object CREATOR : Parcelable.Creator<NoteEntity> {
        override fun createFromParcel(parcel: Parcel): NoteEntity {
            return NoteEntity(parcel)
        }

        override fun newArray(size: Int): Array<NoteEntity?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity(tableName = "notes_fts")
@Fts4(contentEntity = NoteEntity::class)
data class NoteEntityFTS(
    @ColumnInfo(name = "note") val note: String
)

data class LaunchWithMatchInfo(
    @Embedded
    val note: NoteEntity,
    @ColumnInfo(name = "matchInfo")
    val matchInfo: ByteArray
) {

    // We need to override equals and hashcode due to the presence of an array property in the data class
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LaunchWithMatchInfo

        if (note != other.note) return false
        if (!matchInfo.contentEquals(other.matchInfo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = note.hashCode()
        result = 31 * result + matchInfo.contentHashCode()
        return result
    }
}