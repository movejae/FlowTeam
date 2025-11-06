// 메시지 상수
const MESSAGES = {
    SUCCESS: {
        BLOCKED: (name) => `${name} 확장자 차단`,
        UNBLOCKED: (name) => `${name} 확장자 차단 해제`
    },
    ERROR: {
        EMPTY_INPUT: '확장자를 입력해주세요',
        MAX_LENGTH: (max) => `확장자는 최대 ${max}자까지 입력 가능합니다`,
        MAX_COUNT: (max) => `최대 ${max}개까지만 추가할 수 있습니다`,
        INVALID_FORMAT: '확장자는 영문자와 숫자만 입력 가능합니다',
        DUPLICATE: (name) => `${name} 확장자는 이미 차단되어 있습니다`,
        UPDATE_FAILED: '업데이트 실패',
        ADD_FAILED: '추가 실패',
        DELETE_FAILED: '삭제 실패'
    },
    CONFIRM: {
        DELETE: (name) => `'${name}' 확장자 차단을 해제하시겠습니까?`
    }
};

// 페이지 로드시 데이터 불러오기
document.addEventListener('DOMContentLoaded', function() {
    loadFixedExtensions();
    loadCustomExtensions();

    // Enter 키로 추가
    document.getElementById('extensionInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            addExtension();
        }
    });
});

// 고정 확장자 로드
function loadFixedExtensions() {
    fetch('/api/extensions/fixed')
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('fixedExtensions');
            container.innerHTML = '';

            data.forEach(ext => {
                const div = document.createElement('div');
                div.className = 'checkbox-item';

                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.id = `ext-${ext.name}`;
                checkbox.checked = ext.blocked;
                checkbox.onchange = () => toggleFixedExtension(ext.name, checkbox.checked);

                const label = document.createElement('label');
                label.htmlFor = `ext-${ext.name}`;
                label.textContent = ext.name;  // textContent로 XSS 방지

                div.appendChild(checkbox);
                div.appendChild(label);
                container.appendChild(div);
            });
        })
        .catch(error => console.error('고정 확장자 로드 실패:', error));
}

// 커스텀 확장자 로드
function loadCustomExtensions() {
    fetch('/api/extensions/custom')
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('extensionsList');
            const emptyMessage = document.getElementById('emptyMessage');
            const count = document.getElementById('currentCount');

            list.innerHTML = '';
            count.textContent = data.length;

            if (data.length === 0) {
                emptyMessage.style.display = 'block';
            } else {
                emptyMessage.style.display = 'none';
                data.forEach(ext => {
                    const tag = createExtensionTag(ext.extension);
                    list.appendChild(tag);
                });
            }
        })
        .catch(error => console.error('커스텀 확장자 로드 실패:', error));
}

// 확장자 태그 생성
function createExtensionTag(name) {
    const tag = document.createElement('div');
    tag.className = 'extension-tag';

    const span = document.createElement('span');
    span.className = 'name';
    span.textContent = name;

    const button = document.createElement('button');
    button.className = 'btn-remove';
    button.title = '삭제';
    button.textContent = '✕';
    button.onclick = () => removeExtension(name);

    tag.appendChild(span);
    tag.appendChild(button);

    return tag;
}

// 고정 확장자 토글
function toggleFixedExtension(name, blocked) {
    fetch(`/api/extensions/fixed/${name}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ blocked: blocked })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(MESSAGES.ERROR.UPDATE_FAILED);
        }
        const message = blocked ? MESSAGES.SUCCESS.BLOCKED(name) : MESSAGES.SUCCESS.UNBLOCKED(name);
        const type = blocked ? 'success' : 'success-unblock';
        showToast(message, type);
    })
    .catch(error => {
        console.error('고정 확장자 업데이트 실패:', error);
        showToast(MESSAGES.ERROR.UPDATE_FAILED, 'error');
        loadFixedExtensions(); // 원래 상태로 복구
    });
}

// 커스텀 확장자 추가
function addExtension() {
    const input = document.getElementById('extensionInput');
    const errorMessage = document.getElementById('errorMessage');
    const extension = input.value.trim().toLowerCase();

    // 에러 메시지 초기화
    errorMessage.textContent = '';
    errorMessage.classList.remove('show');

    // 유효성 검사
    if (!extension) {
        showError(MESSAGES.ERROR.EMPTY_INPUT);
        return;
    }

    if (extension.length > EXTENSION_NAME_MAX_LENGTH) {
        showError(MESSAGES.ERROR.MAX_LENGTH(EXTENSION_NAME_MAX_LENGTH));
        return;
    }

    // 영문자와 숫자만 허용 (정규식 검증)
    const validPattern = /^[a-z0-9]+$/;
    if (!validPattern.test(extension)) {
        showError(MESSAGES.ERROR.INVALID_FORMAT);
        return;
    }

    // 현재 개수 확인
    const currentCount = parseInt(document.getElementById('currentCount').textContent);
    if (currentCount >= EXTENSION_MAX_COUNT) {
        showError(MESSAGES.ERROR.MAX_COUNT(EXTENSION_MAX_COUNT));
        return;
    }

    // 서버에 추가 요청
    fetch('/api/extensions/custom', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ extension: extension })
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => {
                // 중복 에러 메시지 처리
                if (err.message && err.message.includes('이미 존재')) {
                    throw new Error(MESSAGES.ERROR.DUPLICATE(extension));
                }
                throw new Error(err.message || MESSAGES.ERROR.ADD_FAILED);
            });
        }
        return response.json();
    })
    .then(() => {
        input.value = '';
        showToast(MESSAGES.SUCCESS.BLOCKED(extension), 'success');
        loadCustomExtensions();
    })
    .catch(error => {
        console.error('확장자 추가 실패:', error);
        showError(error.message);
    });
}

// 커스텀 확장자 삭제
function removeExtension(extension) {
    if (!confirm(MESSAGES.CONFIRM.DELETE(extension))) {
        return;
    }

    fetch(`/api/extensions/custom/${extension}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(MESSAGES.ERROR.DELETE_FAILED);
        }
        showToast(MESSAGES.SUCCESS.UNBLOCKED(extension), 'success-unblock');
        loadCustomExtensions();
    })
    .catch(error => {
        console.error('확장자 삭제 실패:', error);
        showToast(MESSAGES.ERROR.DELETE_FAILED, 'error');
    });
}

// 에러 메시지 표시
function showError(message) {
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message;
    errorMessage.classList.add('show');
}
