# API 명세

## 고정 확장자

### 고정 확장자 전체 조회
```http
GET /api/extensions/fixed
```

**Response**
```json
[
  {
    "id": 1,
    "extension": "bat",
    "blocked": true
  },
  {
    "id": 2,
    "extension": "cmd",
    "blocked": false
  }
]
```
---

### 고정 확장자 차단 설정 변경 (ID 기반)
```http
PUT /api/extensions/fixed/{id}
```

**요청 예시:**
```json
{
  "blocked": true
}
```

**Response**
```json
{
  "id": 1,
  "extension": "bat",
  "blocked": true
}
```
---

### 고정 확장자 차단 설정 변경 (이름 기반)
```http
PATCH /api/extensions/fixed/{name}
```

**요청 예시:**
```json
{
  "blocked": false
}
```

**Response**
```json
{
  "id": 1,
  "extension": "bat",
  "blocked": false
}
```

---

## 커스텀 확장자

### 커스텀 확장자 전체 조회
```http
GET /api/extensions/custom
```

**Response**
```json
[
  {
    "id": 1,
    "extension": "zip"
  },
  {
    "id": 2,
    "extension": "rar"
  }
]
```

---

### 커스텀 확장자 추가
```http
POST /api/extensions/custom
```

**요청 예시:**
```json
{
  "extension": "zip"
}
```

**응답 예시 (201 Created):**
```json
{
  "id": 1,
  "extension": "zip"
}
```
---

### 커스텀 확장자 삭제 (ID 기반)
```http
DELETE /api/extensions/custom/id/{id}
```

**응답: 204 No Content (본문 없음)**

---

### 커스텀 확장자 삭제 (이름 기반)
```http
DELETE /api/extensions/custom/{extension}
```

**응답: 204 No Content (본문 없음)**

---

### 커스텀 확장자 개수 조회
```http
GET /api/extensions/custom/count
```

**Response**
```json
42
```
---