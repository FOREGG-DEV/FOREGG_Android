package com.foregg.domain.model.vo

import com.foregg.domain.model.enums.RecordType
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

data class ClassificationVo(
    val classification : RecordType = RecordType.MEDICINE,
    var classificationDetailEditText : String = "",
){
    val classificationDetailTitleText : String = when(classification){
        RecordType.MEDICINE -> "약 이름"
        RecordType.INJECTION -> "주사"
        RecordType.HOSPITAL -> "병원"
        RecordType.ETC -> "일정"
    }
}

data class ScheduleRepeatDayVo(
    val startDate : String = "",
    val endDate : String = "",
    val repeatDayList : List<String> = emptyList(),
    val repeatDayText : String = "",
    val isRepeatDay : Boolean = false,
){
    fun isNotEmpty() : Boolean = startDate.isNotEmpty() && endDate.isNotEmpty() && repeatDayText.isNotEmpty()
    fun isCorrectDay() : Boolean{
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)
        return start.isBefore(end) || start.isEqual(end)
    }
}

data class VolumeVo(
    var medicineVolumeDay : String = "",
    var medicineVolumeCount : String = "",
    var medicineVolumeRound : String = "",
    var injectionVolume : String = "",
){
    fun isNotEmpty(type : RecordType) : Boolean {
        return when(type){
            RecordType.MEDICINE -> medicineVolumeDay.isNotEmpty() && medicineVolumeCount.isNotEmpty() && medicineVolumeRound.isNotEmpty()
            RecordType.INJECTION -> injectionVolume.isNotEmpty()
            RecordType.HOSPITAL,
            RecordType.ETC -> true
        }
    }
}

data class MedicalRecord(
    var medicalRecord : String = "",
    val medicalSideEffect : List<SideEffectVo> = emptyList(),
)