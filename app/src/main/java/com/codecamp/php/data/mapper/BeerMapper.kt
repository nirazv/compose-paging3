package com.codecamp.php.data.mapper

import com.codecamp.php.data.local.BeerEntity
import com.codecamp.php.data.remote.BeerDto
import com.codecamp.php.domain.model.Beer

fun BeerDto.toEntityModel(): BeerEntity {
    return BeerEntity(
        id = this.id,
        tagline = this.tagline,
        description = this.description,
        firstBrewed = this.first_brewed,
        imageUrl = this.image_url ?: "",
        name = this.name
    )
}

fun BeerEntity.toDomainModel(): Beer {
    return Beer(
        id = this.id,
        tagline = this.tagline,
        description = this.description,
        firstBrewed = this.firstBrewed,
        imageUrl = this.imageUrl,
        name = this.name
    )
}