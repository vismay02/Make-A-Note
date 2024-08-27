package com.vismay.makeanote.data.local.db.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "note") var note: String? =null,
    @ColumnInfo(name = "color") var color: Int = -1,
    @ColumnInfo(name = "date") var date: String? = null,
) : Parcelable

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