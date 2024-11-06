package com.ssafy.workalone.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ExerciseMLKitViewModel(): ViewModel() {
    private val _nowSet: MutableState<Int> = mutableStateOf(1)
    private val _totalSet: MutableState<Int> = mutableStateOf(3)
    private val _nowRep: MutableState<Int> = mutableStateOf(0)
    private val _totalRep: MutableState<Int> = mutableStateOf(15)

    val nowSet: MutableState<Int> = _nowSet
    val totalSet: MutableState<Int> = _totalSet
    val nowRep: MutableState<Int> = _nowRep
    val totalRep: MutableState<Int> = _totalRep

    fun addSet(){
        if(_nowRep.value == _totalRep.value){
            _nowSet.value += 1
            _nowRep.value = 0
        }
    }
    fun exerciseFinish(move:()->Unit){
        move()
    }


}