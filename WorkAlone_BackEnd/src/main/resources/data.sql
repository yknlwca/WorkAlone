INSERT INTO exercise_info (title, sub_title, basic_pose, movement, breath, exercise_type)
VALUES ('스쿼트', '허벅지와 코어 근력 강화',
        '1. 발을 어깨너비로 벌리고, 발끝은 약간 바깥쪽을 향하게 합니다.\n2. 가슴은 펴고, 시선은 정면을 유지하며, 허리를 곧게 펴줍니다.',
        '1. 무릎을 구부리며 엉덩이를 뒤로 빼면서 천천히 앉습니다.\n2. 허벅지가 바닥과 평행할 때까지 내려가며, 무릎이 발끝을 넘지 않도록 주의합니다.\n3. 내려갔다가 다시 천천히 일어나면서 원래 자세로 돌아옵니다.',
        '내려갈 때 숨을 들이마시고, 일어설 때 숨을 내쉽니다.', 'SQUAT'),
       ('푸쉬업', '상체와 코어 근력 강화',
        '1. 손은 어깨 너비로 벌리고 바닥을 짚습니다.\n2. 몸을 곧게 펴고, 팔과 어깨, 엉덩이가 일직선을 이루도록 합니다.',
        '1. 팔꿈치를 구부리며 몸을 천천히 내려줍니다.\n2. 가슴이 바닥에 가까워질 때까지 내려가고, 팔로 밀어 원래 자세로 돌아옵니다.',
        '내려갈 때 숨을 들이마시고, 올라올 때 숨을 내쉽니다.', 'PUSHUP'),
       ('플랭크', '코어 안정성 및 체력 강화',
        '1. 팔꿈치를 어깨 아래에 두고, 몸을 곧게 편 상태로 바닥을 짚습니다.\n2. 어깨, 엉덩이, 발목이 일직선을 이루도록 유지합니다.',
        '1. 팔꿈치와 발로 체중을 지탱하며, 자세를 최대한 유지합니다.',
        '코어에 힘을 주면서 편안하게 호흡을 유지합니다.', 'PLANK'),
       ('윗몸 일으키기', '복근 강화 운동',
        '1. 무릎을 구부리고 발바닥을 바닥에 붙인 채로 누워 시작합니다.\n2. 손은 가볍게 머리 뒤에 둡니다.',
        '1. 복근에 힘을 주면서 상체를 들어 올려 무릎 쪽으로 향합니다.\n2. 천천히 내려가면서 원래 자세로 돌아옵니다.',
        '일어날 때 숨을 내쉬고, 내려갈 때 숨을 들이마십니다.', 'SITUP');

INSERT INTO member (name, nickname, email, phone_number, gender, is_trainer, height, weight)
VALUES ('John Doe', 'johnd', 'johndoe@example.com', '123-456-7890', true, false, 180, 75);

INSERT INTO exercise (member_id, title, rest_time, exercise_set, set_detail, exercise_type, set_type, deleted)
VALUES (1, '스쿼트 루틴', 30, 2, 10, 'SQUAT', 'COUNT', 1),
       (1, '푸쉬업 챌린지', 60, 3, 15, 'PUSHUP', 'TIMER', 0),
       (1, '플랭크 버티기', 30, 4, 12, 'PLANK', 'COUNT', 0),
       (1, '싯업 시리즈', 60, 2, 8, 'SITUP', 'TIMER', 0),
       (1, '통합 운동', 30, NULL, NULL, 'INTEGRATED', 'MIX', 0),
       (1, '푸쉬업 훈련', 60, 1, 7, 'PUSHUP', 'COUNT', 0),
       (1, '스쿼트 챌린지', 30, 2, 14, 'SQUAT', 'TIMER', 0),
       (1, '싯업 루틴', 60, 4, 18, 'SITUP', 'TIMER', 0),
       (1, '플랭크 인내력', 30, 3, 9, 'PLANK', 'COUNT', 0),
       (1, '전신 통합 운동', 60, NULL, NULL, 'INTEGRATED', 'MIX', 0);

-- 예시: 통합 운동 '통합 운동'은 '스쿼트 루틴', '푸쉬업 챌린지', '플랭크 버티기'로 구성
INSERT INTO exercise_order (integrated_id, individual_id, order_position)
VALUES ((SELECT id FROM exercise WHERE title = '통합 운동'), (SELECT id FROM exercise WHERE title = '스쿼트 루틴'), 2),
       ((SELECT id FROM exercise WHERE title = '통합 운동'), (SELECT id FROM exercise WHERE title = '푸쉬업 챌린지'),1),

-- 예시: 통합 운동 '전신 통합 운동'은 '싯업 시리즈', '스쿼트 챌린지', '푸쉬업 훈련'으로 구성
INSERT INTO exercise_order (integrated_id, individual_id, order_position)
VALUES ((SELECT id FROM exercise WHERE title = '전신 통합 운동'), (SELECT id FROM exercise WHERE title = '싯업 시리즈'), 2),
       ((SELECT id FROM exercise WHERE title = '전신 통합 운동'), (SELECT id FROM exercise WHERE title = '스쿼트 챌린지'), 1),
       ((SELECT id FROM exercise WHERE title = '전신 통합 운동'), (SELECT id FROM exercise WHERE title = '푸쉬업 훈련'), 3);