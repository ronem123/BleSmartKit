package com.ram.mandal.blesmartkit.data.model

import android.os.Parcelable
import com.ram.mandal.blesmartkit.R
import kotlinx.parcelize.Parcelize


/**
 * Created by Ram Mandal on 30/01/2024
 * @System: Apple M1 Pro
 */
enum class MenuType {
    DOCUMENT_REQUIRED,
    EXAM_RELATED_NOTICE,
    TRAFFIC_SIGNS_AND_RULES,
    NUMBER_PLATES,
    FAQ,
    QUESTION_SAMPLE,
    QUESTION_ANSWER_TEST,
    EYE_TEST
}

object MenuConfig {

    fun getLabel(menuType: MenuType): String = when (menuType) {
        MenuType.DOCUMENT_REQUIRED -> "Document Required"
        MenuType.EXAM_RELATED_NOTICE -> "Exam Related Notice"
        MenuType.TRAFFIC_SIGNS_AND_RULES -> "Traffic Signs & Rules"
        MenuType.NUMBER_PLATES -> "Number Plate"
        MenuType.FAQ -> "FAQ"
        MenuType.QUESTION_SAMPLE -> "Question Sample"
        MenuType.QUESTION_ANSWER_TEST -> "Exam Test Practice"
        MenuType.EYE_TEST -> "Eye Test"
    }

    fun toRouteArg(menuType: MenuType): String = menuType.name.lowercase()

    fun fromRouteArg(arg: String): MenuType? =
        runCatching { MenuType.valueOf(arg.uppercase()) }.getOrNull()
}


@Parcelize
data class MyMenuItem(
    val menuType: MenuType,
    val icon: Int,
    val content: String,
) : Parcelable

fun getMainMenuItem() = listOf(
    MyMenuItem(
        menuType = MenuType.DOCUMENT_REQUIRED,
        icon = R.drawable.ic_document,
        "content"
    ),
    MyMenuItem(
        menuType = MenuType.EXAM_RELATED_NOTICE,
        icon = R.drawable.ic_bell,
        ""
    ),
)

fun getLearningMenu() = listOf(
    MyMenuItem(
        menuType = MenuType.TRAFFIC_SIGNS_AND_RULES,
        icon = R.drawable.ic_document,
        "content"
    ),
    MyMenuItem(
        menuType = MenuType.NUMBER_PLATES,
        icon = R.drawable.ic_document,
        "content"
    ),
    MyMenuItem(
        menuType = MenuType.FAQ,
        icon = R.drawable.ic_document,
        "content"
    ),
    MyMenuItem(
        menuType = MenuType.QUESTION_SAMPLE,
        icon = R.drawable.ic_document,
        "content"
    )
)

fun getPracticeMenu() = listOf(
    MyMenuItem(
        menuType = MenuType.QUESTION_ANSWER_TEST,
        icon = R.drawable.ic_document,
        "content"
    ),
    MyMenuItem(
        menuType = MenuType.EYE_TEST,
        icon = R.drawable.ic_document,
        "content"
    )
)