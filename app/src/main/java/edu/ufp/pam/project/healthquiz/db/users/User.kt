package edu.ufp.pam.project.healthquiz.db.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["username"])]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "username") var username: String? = "",
    @ColumnInfo(name = "password") var password: String? = ""
) {
    constructor() : this(0, "", "", "")
    override fun toString(): String = "${name!!}, ${username!!}"
}
