INSERT INTO exercise_type (id, title, sub_title, basic_pose, movement, breath)
VALUES (1,'스쿼트', '허벅지와 코어 근력 강화',
        '1. 발을 어깨너비로 벌리고, 발끝은 약간 바깥쪽을 향하게 합니다.\n2. 가슴은 펴고, 시선은 정면을 유지하며, 허리를 곧게 펴줍니다.',
        '1. 무릎을 구부리며 엉덩이를 뒤로 빼면서 천천히 앉습니다.\n2. 허벅지가 바닥과 평행할 때까지 내려가며, 무릎이 발끝을 넘지 않도록 주의합니다.\n3. 내려갔다가 다시 천천히 일어나면서 원래 자세로 돌아옵니다.',
        '내려갈 때 숨을 들이마시고, 일어설 때 숨을 내쉽니다.'),
       (2,'푸쉬업', '상체와 코어 근력 강화',
        '1. 손은 어깨 너비로 벌리고 바닥을 짚습니다.\n2. 몸을 곧게 펴고, 팔과 어깨, 엉덩이가 일직선을 이루도록 합니다.',
        '1. 팔꿈치를 구부리며 몸을 천천히 내려줍니다.\n2. 가슴이 바닥에 가까워질 때까지 내려가고, 팔로 밀어 원래 자세로 돌아옵니다.',
        '내려갈 때 숨을 들이마시고, 올라올 때 숨을 내쉽니다.'),
       (3,'플랭크', '코어 안정성 및 체력 강화',
        '1. 팔꿈치를 어깨 아래에 두고, 몸을 곧게 편 상태로 바닥을 짚습니다.\n2. 어깨, 엉덩이, 발목이 일직선을 이루도록 유지합니다.',
        '1. 팔꿈치와 발로 체중을 지탱하며, 자세를 최대한 유지합니다.',
        '코어에 힘을 주면서 편안하게 호흡을 유지합니다.'),
       (4,'윗몸 일으키기', '복근 강화 운동',
        '1. 무릎을 구부리고 발바닥을 바닥에 붙인 채로 누워 시작합니다.\n2. 손은 가볍게 머리 뒤에 둡니다.',
        '1. 복근에 힘을 주면서 상체를 들어 올려 무릎 쪽으로 향합니다.\n2. 천천히 내려가면서 원래 자세로 돌아옵니다.',
        '일어날 때 숨을 내쉬고, 내려갈 때 숨을 들이마십니다.');

INSERT INTO member (name, nickname, email, phone_number, gender, is_trainer, height, weight)
VALUES ('John Doe', 'johnd', 'johndoe@example.com', '123-456-7890', true, false, 180, 75);


-- ExerciseGroup 테이블의 더미 데이터 (member_id가 1인 경우)
INSERT INTO exercise_group (id, member_id, title, rest_btw_exercise) VALUES (1, 1, '통합형 운동1', 90);
INSERT INTO exercise_group (id, member_id, title, rest_btw_exercise) VALUES (2, 1, '통합형 운동2', 90);
INSERT INTO exercise_group (id, member_id, title, rest_btw_exercise) VALUES (3, 1, '개별형 운동', null);

-- Exercise 테이블에 더미 데이터 추가
INSERT INTO exercise (id, group_id, type_id, seq, exercise_set, exercise_repeat, rest_btw_set, set_type, deleted)
VALUES
    -- 복합형 운동: group_id = 1 (Upper Body Workout)
    (1, 1, 1, 1, 2, 10, 60, 'COUNT', false),
    (2, 1, 1, 2, 3, 15, 45, 'COUNT', false),
    (3, 1, 2, 3, 2, 10, 30, 'COUNT', false),

    -- 복합형 운동: group_id = 2 (Lower Body Workout)
    (4, 2, 2, 1, 3, 15, 60, 'COUNT', false),
    (5, 2, 1, 2, 2, 10, 45, 'COUNT', false),
    (6, 2, 3, 3, 3, 30, 30, 'TIMER', false),  -- TIMER로 30 설정

    -- 단일 운동: group_id = 3 (Core Workout)
    (7, 3, 4, 1, 2, 15, 90, 'COUNT', false);

INSERT INTO exercise_summary (id, member_id, date, exercise_id, kcal, time)
VALUES
    (1, 1, '2024-11-01', 1, 150, '00:15:00'),
    (2, 1, '2024-11-01', 2, 200, '00:20:00'),
    (3, 1, '2024-11-01', 3, 250, '00:25:00'),

    (4, 1, '2024-11-01', 4, 180, '00:18:00'),
    (5, 1, '2024-11-01', 5, 220, '00:22:00'),
    (6, 1, '2024-11-01', 6, 270, '00:27:00'),

    (7, 1, '2024-11-01', 7, 120, '00:12:00');