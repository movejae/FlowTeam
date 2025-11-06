// 전역 변수 (Thymeleaf에서 설정)
let MAX_LENGTH = 20;
let MAX_COUNT = 200;

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

// 토스트 메시지 표시 함수
function showToast(message, type = 'success', duration = 2000) {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = 'toast ' + type;

    // 애니메이션을 위한 약간의 딜레이
    setTimeout(() => {
        toast.classList.add('show');
    }, 10);

    // 지정된 시간 후 숨김
    setTimeout(() => {
        toast.classList.remove('show');
    }, duration);
}

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
                div.innerHTML = `
                    <input type="checkbox"
                           id="ext-${ext.name}"
                           ${ext.blocked ? 'checked' : ''}
                           onchange="toggleFixedExtension('${ext.name}', this.checked)">
                    <label for="ext-${ext.name}">${ext.name}</label>
                `;
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
    tag.innerHTML = `
        <span class="name">${name}</span>
        <button class="btn-remove" onclick="removeExtension('${name}')" title="삭제">✕</button>
    `;
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
            throw new Error('업데이트 실패');
        }
        const statusText = blocked ? '차단됨' : '차단해제됨';
        showToast(`${name} 확장자가 ${statusText}`, 'success');
    })
    .catch(error => {
        console.error('고정 확장자 업데이트 실패:', error);
        showToast('업데이트에 실패했습니다.', 'error');
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
        showError('확장자를 입력해주세요.');
        return;
    }

    if (extension.length > MAX_LENGTH) {
        showError(`확장자는 최대 ${MAX_LENGTH}자까지 입력 가능합니다.`);
        return;
    }

    // 현재 개수 확인
    const currentCount = parseInt(document.getElementById('currentCount').textContent);
    if (currentCount >= MAX_COUNT) {
        showError(`최대 ${MAX_COUNT}개까지만 추가할 수 있습니다.`);
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
                throw new Error(err.message || '추가 실패');
            });
        }
        return response.json();
    })
    .then(() => {
        input.value = '';
        showToast(`${extension} 확장자가 추가되었습니다`, 'success');
        loadCustomExtensions();
    })
    .catch(error => {
        console.error('확장자 추가 실패:', error);
        showError(error.message);
    });
}

// 커스텀 확장자 삭제
function removeExtension(extension) {
    if (!confirm(`'${extension}' 확장자를 삭제하시겠습니까?`)) {
        return;
    }

    fetch(`/api/extensions/custom/${extension}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('삭제 실패');
        }
        showToast(`${extension} 확장자가 삭제되었습니다`, 'info');
        loadCustomExtensions();
    })
    .catch(error => {
        console.error('확장자 삭제 실패:', error);
        showToast('삭제에 실패했습니다.', 'error');
    });
}

// 에러 메시지 표시
function showError(message) {
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message;
    errorMessage.classList.add('show');
}
