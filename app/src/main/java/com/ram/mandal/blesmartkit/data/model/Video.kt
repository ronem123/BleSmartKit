package com.ram.mandal.blesmartkit.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Created by Ram Mandal on 30/01/2024
 * @System: Apple M1 Pro
 */
@Parcelize
data class MyVideo(val id: String, val icon: String, val name: String, val url: String) : Parcelable

fun getVideos() = listOf(
    MyVideo(
        "1",
        "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/music-songs-youtube-thumbnails-design-template-f0fcb6a767bf70426fb7a5109709c4fe_screen.jpg?ts=1600929244",
        "Music on load Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bi",
        "ufKj1sBrC4Q"
    ),
    MyVideo(
        "1",
        "https://png.pngtree.com/template/20230325/ourmid/pngtree-psd-templates-for-youtube-thumbnail-or-website-banner-about-music-festival-image_1955846.jpg",
        "Popular music organized Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bi",
        "_dG84q889O4"
    ),
    MyVideo(
        "1",
        "https://img.freepik.com/free-vector/gradient-colorful-music-festival-youtube-thumbnail_23-2149081356.jpg",
        "Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bishwokarma",
        "4D7_mev5Mkw"
    ),
    MyVideo(
        "1",
        "https://i.pinimg.com/736x/5b/2f/5e/5b2f5e020eb4ab271ae09641092cfddd.jpg",
        "Popular music organized Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bi",
        "ufKj1sBrC4Q"
    ),
    MyVideo(
        "1",
        "https://images.hamro-files.com/v9GZkYK9NEZ-8-UokyqD2P5cFsM=/500x500/https://sgp1.digitaloceanspaces.com/everestdb/hamropatro-backend/radio/292b9eee-d64d-41db-a57a-d098be260500",
        "Popular music organized Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bi",
        "ufKj1sBrC4Q"
    ),
    MyVideo(
        "1",
        "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/music-songs-youtube-thumbnails-design-template-f0fcb6a767bf70426fb7a5109709c4fe_screen.jpg?ts=1600929244",
        "Music on load Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bi",
        "ufKj1sBrC4Q"
    ),
    MyVideo(
        "1",
        "https://png.pngtree.com/template/20230325/ourmid/pngtree-psd-templates-for-youtube-thumbnail-or-website-banner-about-music-festival-image_1955846.jpg",
        "Popular music organized Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bi",
        "_dG84q889O4"
    ),
    MyVideo(
        "1",
        "https://img.freepik.com/free-vector/gradient-colorful-music-festival-youtube-thumbnail_23-2149081356.jpg",
        "Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bishwokarma",
        "4D7_mev5Mkw"
    ),
    MyVideo(
        "1",
        "https://i.pinimg.com/736x/5b/2f/5e/5b2f5e020eb4ab271ae09641092cfddd.jpg",
        "Popular music organized Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bi",
        "ufKj1sBrC4Q"
    ),
    MyVideo(
        "1",
        "https://images.hamro-files.com/v9GZkYK9NEZ-8-UokyqD2P5cFsM=/500x500/https://sgp1.digitaloceanspaces.com/everestdb/hamropatro-backend/radio/292b9eee-d64d-41db-a57a-d098be260500",
        "Popular music organized Katta Handinchhu - Khem Century • Eleena Chauhan • Obi Rayamajhi • Aashma Bi",
        "ufKj1sBrC4Q"
    ),
)