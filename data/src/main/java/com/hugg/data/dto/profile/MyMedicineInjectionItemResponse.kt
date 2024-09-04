package com.hugg.data.dto.profile

import com.hugg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class MyMedicineInjectionResponse(
    @SerializedName("myPageRecordResponseDTO")
    val myPageRecordResponseDTO : List<MyMedicineInjectionItemResponse> = emptyList(),
) : DataDto

data class MyMedicineInjectionItemResponse(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("date")
    val date : String? = null,
    @SerializedName("startDate")
    val startDate : String? = null,
    @SerializedName("endDate")
    val endDate : String? = null,
    @SerializedName("repeatDays")
    val repeatDays : String? = null,
    @SerializedName("name")
    val name : String = "",
) : DataDto