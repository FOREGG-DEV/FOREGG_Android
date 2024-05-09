package com.foregg.data.dto.profile

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class MyMedicineInjectionResponse(
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