// 토스트 타이머 저장용 전역 변수
let toastTimer = null;

// 토스트 메시지 표시 함수
function showToast(message, type = 'success', duration = 2000) {
    const toast = document.getElementById('toast');

    // 기존 타이머가 있다면 취소하고 토스트 즉시 제거
    if (toastTimer) {
        clearTimeout(toastTimer);
        toast.classList.remove('show');
    }

    toast.textContent = message;
    toast.className = 'toast ' + type;

    // 애니메이션을 위한 약간의 딜레이
    setTimeout(() => {
        toast.classList.add('show');
    }, 10);

    // 지정된 시간 후 숨김
    toastTimer = setTimeout(() => {
        toast.classList.remove('show');
        toastTimer = null;
    }, duration);
}