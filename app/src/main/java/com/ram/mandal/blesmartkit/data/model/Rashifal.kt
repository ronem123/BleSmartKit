package com.ram.mandal.blesmartkit.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Created by Ram Mandal on 29/01/2024
 * @System: Apple M1 Pro
 */
@Parcelize
data class Rashifal(val id: String, val title: String, val detail: String) : Parcelable

fun getRashifal() = listOf(
    Rashifal(
        "1",
        "मेष ( चु, चे, चो, ला, लि, लु, ले, लो, अ )",
        "आज नाताकुटुम्बको आगमन हुने एवं परिवारमा नयाँ सदस्य थपिन पनि सक्ने सम्भावना छ । आफूसँग भएको क्षमता प्रदर्शन गर्ने अवसर पनि जुट्ला ।"
    ),
    Rashifal(
        "1",
        "मेष ( चु, चे, चो, ला, लि, लु, ले, लो, अ )",
        "आज नाताकुटुम्बको आगमन हुने एवं परिवारमा नयाँ सदस्य थपिन पनि सक्ने सम्भावना छ । आफूसँग भएको क्षमता प्रदर्शन गर्ने अवसर पनि जुट्ला ।"
    ),
    Rashifal(
        "1",
        "मेष ( चु, चे, चो, ला, लि, लु, ले, लो, अ )",
        "आज नाताकुटुम्बको आगमन हुने एवं परिवारमा नयाँ सदस्य थपिन पनि सक्ने सम्भावना छ । आफूसँग भएको क्षमता प्रदर्शन गर्ने अवसर पनि जुट्ला ।"
    )
)