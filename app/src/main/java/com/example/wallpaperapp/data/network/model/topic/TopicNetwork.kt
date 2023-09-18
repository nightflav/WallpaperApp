package com.example.wallpaperapp.data.network.model.topic

import kotlinx.serialization.Serializable

@Serializable
data class TopicNetwork(
    val categories: ArrayList<TopicNetworkItem>
)