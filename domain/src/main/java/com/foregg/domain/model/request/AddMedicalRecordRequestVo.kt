package com.foregg.domain.model.request

import com.google.gson.annotations.SerializedName

data class AddMedicalRecordRequestVo(
    val id : Long,
    val request : AddMedicalRecordRequest
)

data class AddMedicalRecordRequest(
    @SerializedName("medicalRecord")
    val medicalRecord: String,
)