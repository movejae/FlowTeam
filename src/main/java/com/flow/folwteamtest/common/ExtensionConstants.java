package com.flow.folwteamtest.common;

/**
 * <h1>파일 확장자 관련 상수 관리 클래스</h1>
 * <p>이 클래스의 상수들은 프로젝트 전체에서 참조됩니다.</p>
 * 값을 변경할 경우 다음 영향도를 고려해야 합니다:
 * <br><br>
 *<p> 1. EXTENSION_MAX_LENGTH 변경 시 영향:
 *    <li>Entity: CustomExtension, FixedExtension의 DB 컬럼 길이</li>
 *     <li>DTO: CustomExtensionCreateRequest의 validation</li>
 *     <li>Frontend: index.html의 input maxlength</li>
 *    <p>주의: DB 컬럼 길이를 줄일 경우 기존 데이터 마이그레이션 필요</p>
 *</p>
 *<br>
 *<p>2. MAX_CUSTOM_EXTENSIONS 변경 시 영향:
 *     <li>Service: ExtensionService의 추가 가능 개수 체크 로직</li>
 *     <li>Frontend: index.html의 최대 개수 표시</li>
 *</p>
 *<br>
 * <p>3. FIXED_EXTENSION_NAMES 변경 시 영향:
 *     <li>Service: ExtensionService의 초기화 로직</li>
 *     <li>Frontend: index.html의 고정 확장자 체크박스 목록</li>
 *     <p>주의: 항목 제거 시 기존 DB 데이터 처리 방안 고려 필요</p>
 * </p>
 */
public final class ExtensionConstants {

    // ==================== 인스턴스화 방지 ====================
    /**
     * 유틸리티 클래스이므로 인스턴스화를 방지합니다.
     * @throws AssertionError 인스턴스화 시도 시
     */
    private ExtensionConstants() {
        throw new AssertionError("ExtensionConstants는 인스턴스화할 수 없습니다.");
    }

    // ==================== 확장자 길이 제한 ====================
    /**
     * 확장자 최대 길이
     * 운용중 변경 금지: DB 컬럼 길이와 연동되어 있습니다.
     * 변경 시 마이그레이션 스크립트 작성 필요!
     */
    public static final int EXTENSION_MAX_LENGTH = 20;

    // ==================== 커스텀 확장자 개수 제한 ====================
    /**
     * 커스텀 확장자 최대 개수
     * 운용중 변경 금지
     * 변경 시 영향: Service 레이어의 검증 로직, 프론트엔드 UI
     */
    public static final int MAX_CUSTOM_EXTENSIONS = 200;

    // ==================== 고정 확장자 목록 ====================
    /**
     * 고정 확장자 목록 (기본 7개)
     *
     * 변경 주의:
     * - 추가: 새로운 고정 확장자가 DB에 초기화됩니다.
     * - 제거: 기존 DB 레코드 처리 방안을 먼저 고려하세요.
     */
    public static final String[] FIXED_EXTENSION_NAMES = {
        "bat", "cmd", "com", "cpl", "exe", "scr", "js"
    };

    // ==================== 에러 메시지 ====================
    /**
     * 확장자 길이 초과 에러 메시지
     */
    public static final String ERROR_EXTENSION_TOO_LONG =
        String.format("확장자는 최대 %d자까지 입력 가능합니다.", EXTENSION_MAX_LENGTH);

    /**
     * 커스텀 확장자 개수 초과 에러 메시지
     */
    public static final String ERROR_EXTENSION_LIMIT_EXCEEDED =
        String.format("커스텀 확장자는 최대 %d개까지 등록할 수 있습니다.", MAX_CUSTOM_EXTENSIONS);
}
