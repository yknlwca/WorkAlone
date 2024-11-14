package com.ssafy.workalone.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.workalone.data.local.ExerciseInfoPreferenceManager
import com.ssafy.workalone.data.model.exercise.ExerciseData
import com.ssafy.workalone.data.model.result.ExerciseResult
import com.ssafy.workalone.data.model.result.ResultList

class ExerciseMLKitViewModel(context: Context): ViewModel() {
    private val exerciseInfoPreferenceManager = ExerciseInfoPreferenceManager(context)

    private val _exercises = exerciseInfoPreferenceManager.getExerciseList()
    private val _nowExercise: MutableState<ExerciseData> = mutableStateOf(_exercises[0])
    private val _exerciseType: MutableLiveData<String> =
        MutableLiveData<String>()
    private val _nowSet: MutableState<Int> = mutableStateOf(1)
    private val _totalSet: MutableState<Int> = mutableStateOf(_nowExercise.value.exerciseSet)
    private val _nowRep: MutableState<Int> = mutableStateOf(0)
    private val _totalRep: MutableState<Int> = mutableStateOf(_nowExercise.value.exerciseRepeat)
    private val _restTime: MutableState<Int> = mutableStateOf(5)
    private val _restTimeBtwExercise = exerciseInfoPreferenceManager.getRestBtwExercise()
    private val _stage: MutableState<String> = mutableStateOf("ready")
    private val _isResting: MutableState<Boolean> = mutableStateOf(true)
    private val _isExercising: MutableState<Boolean> = mutableStateOf(true)
    private val _preSetText: MutableState<String> = mutableStateOf("")
    private val _restText: MutableState<String> = mutableStateOf("")
    private val _isExit: MutableState<Boolean> = mutableStateOf(false)
    private val _isFinish: MutableState<Boolean> = mutableStateOf(false)
    //운동시작전: ready, 쉬는시간 : rest, 다음세트 : nextSet, 다음운동 : nextExercise
    private val _showExitMessage:MutableState<Boolean> = mutableStateOf(false)
    private val _exerciseResult: MutableState<ExerciseResult> = mutableStateOf(ExerciseResult(0L,"",""))
    private val _exerciseResultList: MutableState<ResultList> = mutableStateOf(ResultList(listOf(),"",""))
    private val _exercisingTime: MutableState<Int> = mutableStateOf(0)
    init {
        // 기본값 설정 (필요에 따라 수정)
        if(_nowExercise != null)
            _exerciseType.value = _nowExercise.value.title
    }

    val exercises: List<ExerciseData> = _exercises
    val nowExercise: MutableState<ExerciseData> = _nowExercise
    val exerciseType: MutableLiveData<String> = _exerciseType
    val nowSet: MutableState<Int> = _nowSet
    val totalSet: MutableState<Int> = _totalSet
    val nowRep: MutableState<Int> = _nowRep
    val totalRep: MutableState<Int> = _totalRep
    val restTime: MutableState<Int> = _restTime
    val stage: MutableState<String> = _stage
    val isResting: MutableState<Boolean> = _isResting
    val isExercising: MutableState<Boolean> = _isExercising
    val restText: MutableState<String> = _restText
    val preSetText: MutableState<String> = _preSetText
    val isExit: MutableState<Boolean> = _isExit
    val isFinish: MutableState<Boolean> = _isFinish
    val showExitMessage: MutableState<Boolean> = _showExitMessage
    val exercisingTime: MutableState<Int> = _exercisingTime
    var exerciseStartTime: Int = 0


    fun addRep(type: String){
        if(type == "플랭크") {
            _totalRep.value -= 1;
        }else{
            _nowRep.value++
        }
    }
    fun addSet(){
        if(_nowExercise.value.title=="플랭크"){
            _totalRep.value = _nowExercise.value.exerciseRepeat
        }else{
            _nowRep.value = 0
        }
        _nowSet.value += 1
        startResting()
        _stage.value="rest"

    }
    fun countDownRestTime(){
        if(_isResting.value){
            _restTime.value --
            if(_restTime.value==0){
                if(_nowSet.value==_totalSet.value){

                    _restTime.value = _restTimeBtwExercise

                }else{
                    _restTime.value = _nowExercise.value.restBtwSet
                }
                stopResting()
                _stage.value = "rest"
            }
        }
    }
    fun changeStage(){
        if(_nowSet.value <= _totalSet.value && _nowSet.value != 1){
            _stage.value = "nextSet"
        }else if(_nowSet.value == 1){
            _stage.value = "nextExercise"
        }
    }
    @SuppressLint("DefaultLocale")
    fun exerciseFinish() {

        // 운동 결과 저장 -> mutable타입이므로 복사본 사용
        _exerciseResult.value = _exerciseResult.value.copy(
            exerciseId = _nowExercise.value.exerciseId,
            exerciseType = _nowExercise.value.title,
            time = String.format(
                "%02d:%02d",
                (_exercisingTime.value - exerciseStartTime) / 60,
                (_exercisingTime.value - exerciseStartTime) % 60
            )
        )
        val resultCopy = _exerciseResult.value.copy()
        _exerciseResultList.value.result += resultCopy

        // 다음 운동의 시간은 지금까지 진행한 시간 + 운동 변경 쉬는시간
        exerciseStartTime = _exercisingTime.value + _restTimeBtwExercise
        if (_nowExercise.value.seq == _exercises.size) {
            val totalTime: Int = _exercisingTime.value
            _exerciseResultList.value.totalTime = String.format("%02d:%02d", totalTime / 60, totalTime % 60)
            exerciseInfoPreferenceManager.setExerciseResult(_exerciseResultList.value)
            Log.d("1234", exerciseInfoPreferenceManager.getExerciseResult().toString())
            _isFinish.value = true
        } else {
            // 다음 운동으로 이동
            _nowExercise.value = _exercises[_nowExercise.value.seq]
            _nowRep.value = 0
            _nowSet.value = 1
            _totalSet.value = _nowExercise.value.exerciseSet
            _totalRep.value = _nowExercise.value.exerciseRepeat
            _exerciseType.postValue(_nowExercise.value.title)
        }
    }
    fun startResting() {
        _isResting.value = true
    }
    fun stopResting() {
        _isResting.value = false
        if(_nowExercise.value.title!="플랭크")
            _isExercising.value = true
    }
    fun stopExercise(){
        _isExercising.value = false
        _showExitMessage.value = true
    }
    fun startExercise(){
        _isExercising.value = true
    }
    fun stopShowExitMessage(){
        _showExitMessage.value = false
    }
    fun clickExit(){
        _isExit.value = true
        _isExercising.value = false
    }
    fun cancelExit(){
        _isExit.value = false
    }
    fun stageDescribe(stage:String){
        when(stage){
            "rest" -> {
                _restText.value = "훌륭해요! \n잠시 휴식하세요."
            }
            "nextSet" -> {
                _preSetText.value = "잠시후, 다음 세트를 진행합니다."
            }
            "nextExercise" -> {
                _preSetText.value = "잠시후, 다음 운동을 진행합니다."
            }
            else -> {
                _restText.value = ""
                _preSetText.value = ""
            }
        }
    }
    fun addExercisingTime(){
        _exercisingTime.value++
    }
}
