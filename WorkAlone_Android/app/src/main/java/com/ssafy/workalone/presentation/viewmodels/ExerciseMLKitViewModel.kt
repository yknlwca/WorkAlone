package com.ssafy.workalone.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.workalone.data.model.Exercise

class ExerciseMLKitViewModel(): ViewModel() {
//    private val _exercises = mutableStateOf<List<Exercise>>(listOf())
//    private val _nowExercise = mutableStateOf<Exercise?>(_exercises.value[0])
    private val _nowSet: MutableState<Int> = mutableStateOf(1)
    private val _totalSet: MutableState<Int> = mutableStateOf(3)
    private val _nowRep: MutableState<Int> = mutableStateOf(0)
    private val _totalRep: MutableState<Int> = mutableStateOf(1)
    private val _restTime: MutableState<Int> = mutableStateOf(3)
    private val _stage: MutableState<String> = mutableStateOf("ready")
    private val _isExercising: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private val _preSetText: MutableState<String> = mutableStateOf("")
    private val _restText: MutableState<String> = mutableStateOf("")
    private val _plankPause: MutableState<Boolean> = mutableStateOf(false)
    //운동시작전: ready, 쉬는시간 : rest, 다음세트 : nextSet, 다음운동 : nextExercise


//    val exercises: MutableState<List<Exercise>> = _exercises
//    val nowExercise: MutableState<Exercise?> = _nowExercise
    val nowSet: MutableState<Int> = _nowSet
    val totalSet: MutableState<Int> = _totalSet
    val nowRep: MutableState<Int> = _nowRep
    val totalRep: MutableState<Int> = _totalRep
    val restTime: MutableState<Int> = _restTime
    val stage: MutableState<String> = _stage
    val isExercising: MutableLiveData<Boolean> = _isExercising
    val restText: MutableState<String> = _restText
    val preSetText: MutableState<String> = _preSetText
    val plankPause: MutableState<Boolean> = _plankPause



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
        _isExercising.postValue(false)
        _stage.value="rest"
    }
//    fun exerciseFinish(){
//        if((_nowExercise.value?.exerciseId?.toInt() ?: 0) == _exercises.value.size){
//            //운동 인증 화면으로 이동
//        }else{
//            //다음 운동으로 이동
//            _nowExercise.value = _exercises.value[_nowExercise.value?.exerciseId?.toInt()!!+1]
//            _nowRep.value = 0
//            _nowSet.value = 1
//            //다음 운동의 세트로 적용
//            _totalSet.value = _nowExercise.value!!.totalSet
//            //전체 횟수도 다음 운동 것으로 적용
//            _totalRep.value = _nowExercise.value!!.totalRep
//        }
//    }
    fun addRep(type: String, plankTime:Long = 0){
        if(type == "플랭크") {
            _totalRep.value -= plankTime.toInt()
        }else{
            _nowRep.value++
        }
    }
    fun countDownRestTime(){
        if(_isExercising.value != true){
            _restTime.value --
            if(_restTime.value==0){
                _restTime.value = 20
                _isExercising.postValue(true)

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
        if(_nowSet.value < _totalSet.value){
            _stage.value = "nextSet"
        }else if(_nowSet.value == _totalSet.value){
            _stage.value = "nextExercise"
        }
    }
    fun decreaseTime() {
        _totalRep.value--
    }
    fun startPlank(){
        _plankPause.value = false
    }
    fun stopPlank(){
        _plankPause.value = true
    }
    fun startResting() {
        _isExercising.postValue(false)
    }
    fun stopResting() {
        _isExercising.postValue(true)
    }
}