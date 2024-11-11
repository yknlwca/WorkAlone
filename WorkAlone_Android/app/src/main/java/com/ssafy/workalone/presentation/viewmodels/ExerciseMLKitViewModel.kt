package com.ssafy.workalone.presentation.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ssafy.workalone.data.local.ExerciseInfoPreferenceManager
import com.ssafy.workalone.data.model.exercise.ExerciseData

class ExerciseMLKitViewModel(context: Context): ViewModel() {
    private val exerciseInfoPreferenceManager = ExerciseInfoPreferenceManager(context)

    private val _exercises = exerciseInfoPreferenceManager.getExerciseList()
    private val _nowExercise: MutableState<ExerciseData> = mutableStateOf(_exercises[0])
    private val _nowSet: MutableState<Int> = mutableStateOf(1)
    private val _totalSet: MutableState<Int> = mutableStateOf(_nowExercise.value.exerciseSet)
    private val _nowRep: MutableState<Int> = mutableStateOf(0)
    private val _totalRep: MutableState<Int> = mutableStateOf(_nowExercise.value.exerciseRepeat)
    private val _restTime: MutableState<Int> = mutableStateOf(5)
    private val _stage: MutableState<String> = mutableStateOf("ready")
    private val _isResting: MutableState<Boolean> = mutableStateOf(true)
    private val _isExercising: MutableState<Boolean> = mutableStateOf(true)
    private val _preSetText: MutableState<String> = mutableStateOf("")
    private val _restText: MutableState<String> = mutableStateOf("")
    private val _isExit: MutableState<Boolean> = mutableStateOf(false)
    private val _isFinish: MutableState<Boolean> = mutableStateOf(false)
    //운동시작전: ready, 쉬는시간 : rest, 다음세트 : nextSet, 다음운동 : nextExercise


    val exercises: List<ExerciseData> = _exercises
    val nowExercise: MutableState<ExerciseData> = _nowExercise
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



    fun setNowReps(rep:Int){
        _nowRep.value = rep
    }
    fun setTotalReps(rep:Int){
        _totalRep.value = rep
    }
    fun setNowSets(rep:Int){
        _nowSet.value = rep
    }
    fun setTotalSets(rep:Int){
        _totalSet.value = rep
    }
    fun setRestTime(rep:Int){
        _restTime.value = rep
    }


    fun addSet(){
        _nowSet.value += 1
        _nowRep.value = 0
        startResting()
        _stage.value="rest"
    }
    fun exerciseFinish(){
        if(_nowExercise.value.seq == _exercises.size){
            //운동 종료 알림
            _isFinish.value = true
        }else{
            //다음 운동으로 이동
            _nowExercise.value = _exercises[_nowExercise.value.seq]
            _nowRep.value = 0
            _nowSet.value = 1
            //다음 운동의 세트로 적용
            _totalSet.value = _nowExercise.value.exerciseSet
            //전체 횟수도 다음 운동 것으로 적용
            _totalRep.value = _nowExercise.value.exerciseRepeat
        }
    }
    fun addRep(type: String, plankTime:Long = 0){
        if(type == "플랭크") {
            _totalRep.value -= plankTime.toInt()
        }else{
            _nowRep.value++
        }
    }
    fun countDownRestTime(){
        if(_isResting.value){
            _restTime.value --
            if(_restTime.value==0){
                _restTime.value = _nowExercise.value.restBtwSet
                stopResting()
                _stage.value = "rest"
            }
        }

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
    fun changeStage(){
        if(_nowSet.value <= _totalSet.value){
            _stage.value = "nextSet"
        }else if(_nowSet.value == _totalSet.value+1){
            _stage.value = "nextExercise"
        }
    }
    fun decreaseTime() {
        _totalRep.value--
    }
    fun startResting() {
        _isResting.value = true
//        _isExercising.value = false
    }
    fun stopResting() {
        _isResting.value = false
        _isExercising.value = true
    }
    fun stopExercise(){
        _isExercising.value = false
    }
    fun startExercise(){
        _isExercising.value = true
    }
    fun clickExit(){
        _isExit.value = true
        _isExercising.value = false;
    }
    fun cancelExit(){
        _isExit.value = false
    }
}
