package com.example.customerlist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uf_table")
data class Uf(
    @PrimaryKey
    var ufId: Int,

    @ColumnInfo(name = "uf")
    var uf: String = ""
)