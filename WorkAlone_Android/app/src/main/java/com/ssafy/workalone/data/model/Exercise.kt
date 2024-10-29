package com.ssafy.workalone.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.workalone.R

@Entity(tableName = "exercise-table")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "subtitle")
    val subTitle: String = "",
    @ColumnInfo(name = "content")
    val content: String = "",
    @ColumnInfo(name = "basic-pose")
    val basicPose: String = "",
    @ColumnInfo(name = "movement")
    val movement: String = "",
    @ColumnInfo(name = "breath")
    val breath: String = "",
    @ColumnInfo(name = "picture")
    val picture: Int
)

object DummyExercise {
    val exerciseList = listOf(
        Exercise(
            title = "스쿼트",
            subTitle = "허벅지와 코어 근력 강화",
            content = "https://www.youtube.com/watch?v=50f62PSGY7k&pp=ygUJ7Iqk7L-87Yq4",
            basicPose = "1. 발을 어깨너비로 벌리고, 발끝은 약간 바깥쪽을 향하게 합니다.\n" +
                    "2. 가슴은 펴고, 시선은 정면을 유지하며, 허리를 곧게 펴줍니다.",
            movement = "1. 무릎을 구부리며 엉덩이를 뒤로 빼면서 천천히 앉습니다.\n" +
                    "2. 허벅지가 바닥과 평행할 때까지 내려가며, 무릎이 발끝을 넘지 않도록 주의합니다.\n" +
                    "3. 내려갔다가 다시 천천히 일어나면서 원래 자세로 돌아옵니다.",
            breath = "내려갈 때 숨을 들이마시고, 일어설 때 숨을 내쉽니다.",
            picture = R.drawable.squat
        ),
        Exercise(
            title = "푸쉬업",
            subTitle = "상체와 코어 근력 강화",
            content = "https://www.youtube.com/watch?v=fh4y5dGZX9c&pp=ygUJ7ZG47Ims7JeF",
            basicPose = "1 .손은 어깨 너비로 벌리고 바닥을 짚습니다.\n" +
                    "2. 몸을 곧게 펴고, 팔과 어깨, 엉덩이가 일직선을 이루도록 합니다.",
            movement = "1. 팔꿈치를 구부리며 몸을 천천히 내려줍니다.\n" +
                    "2. 가슴이 바닥에 가까워질 때까지 내려가고, 팔로 밀어 원래 자세로 돌아옵니다.",
            breath = "내려갈 때 숨을 들이마시고, 올라올 때 숨을 내쉽니다.",
            picture = R.drawable.push_up
        ),
        Exercise(
            title = "플랭크",
            subTitle = "코어 안정성 및 체력 강화",
            content = "https://www.youtube.com/watch?v=Zq8nRY9P_cM&pp=ygUJ7ZSM656t7YGs",
            basicPose = "1. 팔꿈치를 어깨 아래에 두고, 몸을 곧게 편 상태로 바닥을 짚습니다.\n" +
                    "2. 어깨, 엉덩이, 발목이 일직선을 이루도록 유지합니다.",
            movement = "1. 팔꿈치와 발로 체중을 지탱하며, 자세를 최대한 유지합니다.",
            breath = "코어에 힘을 주면서 편안하게 호흡을 유지합니다.",
            picture = R.drawable.plank
        ),
        Exercise(
            title = "윗몸 일으키기",
            subTitle = "복근 강화 운동",
            content = "https://www.youtube.com/watch?v=BQsM_zUkFUw&pp=ygUG7Iuv7JeF",
            basicPose = "1. 무릎을 구부리고 발바닥을 바닥에 붙인 채로 누워 시작합니다.\n" +
                    "2. 손은 가볍게 머리 뒤에 둡니다.",
            movement = "1. 복근에 힘을 주면서 상체를 들어 올려 무릎 쪽으로 향합니다.\n" +
                    "2. 천천히 내려가면서 원래 자세로 돌아옵니다.",
            breath = "일어날 때 숨을 내쉬고, 내려갈 때 숨을 들이마십니다.",
            picture = R.drawable.sit_up
        )
    )
}