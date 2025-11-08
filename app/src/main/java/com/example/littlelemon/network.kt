package com.example.littlelemon

import kotlinx.serialization.Serializable

// ---------- Network Models ----------

@Serializable
data class MenuNetwork(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

// ---------- Mapping Function ----------

// 👇 Put this *after* the data classes, still in this same file.
fun MenuItemNetwork.toEntity(): MenuItemEntity {
    return MenuItemEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = category
    )
}
