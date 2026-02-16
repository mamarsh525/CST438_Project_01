package com.example.project01group03.API

import com.google.gson.annotations.SerializedName

data class DiscogsSearchResponse(
    @SerializedName("results") val results: List<SearchResult>
)

data class SearchResult(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("thumb") val thumb: String?,
    @SerializedName("year") val year: String?,
    @SerializedName("type") val type: String,
    @SerializedName("cover_image") val coverImage: String?,
    @SerializedName("resource_url") val resourceUrl: String
)

data class ArtistReleasesResponse(
    @SerializedName("releases") val releases: List<VinylRelease>,
    @SerializedName("pagination") val pagination: Pagination?
)

data class Pagination(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("items") val items: Int
)

data class VinylRelease(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("year") val year: Int?,  // Changed to Int? - Discogs returns this as a number
    @SerializedName("format") val format: String?,
    @SerializedName("label") val label: String?,
    @SerializedName("thumb") val thumb: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("artist") val artist: String?,
    @SerializedName("resource_url") val resourceUrl: String?
)

// Helper function to parse format string into parts
fun VinylRelease.formatParts(): List<String> {
    val raw = format ?: return emptyList()
    return raw.split(",")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
}

// Improved vinyl detection - more lenient
fun VinylRelease.isVinyl(): Boolean {
    // If format is null or empty, assume it could be vinyl
    // Many older releases in Discogs don't have format specified
    val raw = format?.lowercase() ?: return true

    // Check if format contains common vinyl indicators
    val isVinyl = raw.contains("vinyl") ||
            raw.contains("lp") ||
            raw.contains("12\"") ||
            raw.contains("7\"") ||
            raw.contains("10\"") ||
            (raw.contains("ep") && !raw.contains("cd"))

    // Exclude obvious non-vinyl formats
    val isNotVinyl = raw.contains("cd") && !raw.contains("vinyl") ||
            raw.contains("cassette") ||
            raw.contains("digital") ||
            raw.contains("file") ||
            raw.contains("dvd") ||
            raw.contains("blu-ray")

    // If format is present but doesn't mention vinyl OR explicitly mentions non-vinyl, exclude it
    // Otherwise include it (this catches null/empty formats and ambiguous formats)
    return if (format.isNullOrBlank()) true else isVinyl && !isNotVinyl
}

// Helper to get year as string
fun VinylRelease.getYearString(): String? {
    return year?.toString()
}

data class ArtistDetails(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("images") val images: List<ArtistImage>?,
    @SerializedName("profile") val profile: String?
)

data class ArtistImage(
    @SerializedName("uri") val uri: String,
    @SerializedName("type") val type: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)