package com.ssafy.workalone.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ExerciseMLKitViewModel(): ViewModel() {
    private val _nowSet: MutableState<Int> = mutableStateOf(1)
    private val _totalSet: MutableState<Int> = mutableStateOf(3)
    private val _nowRep: MutableState<Int> = mutableStateOf(0)
    private val _totalRep: MutableState<Int> = mutableStateOf(15)
    private val _restTime: MutableState<Int> = mutableStateOf(15)
    private val _stage: MutableState<String> = mutableStateOf("ready")
    private val _isExercising: MutableState<Boolean> = mutableStateOf(false)
    //운동시작전: ready, 쉬는시간 : rest, 다음세트 : nextSet, 다음운동 : nextExercise

    val nowSet: MutableState<Int> = _nowSet
    val totalSet: MutableState<Int> = _totalSet
    val nowRep: MutableState<Int> = _nowRep
    val totalRep: MutableState<Int> = _totalRep
    val restTime: MutableState<Int> = _restTime
    val stage: MutableState<String> = _stage
    val isExercising: MutableState<Boolean> = _isExercising

    fun addSet(){
        if(_nowRep.value == _totalRep.value){
            _nowSet.value += 1
            _nowRep.value = 0
        }
    }
    fun exerciseFinish(move:()->Unit){
        move()
    }
    fun decreaseTime(){
        _totalRep.value --
    }

}