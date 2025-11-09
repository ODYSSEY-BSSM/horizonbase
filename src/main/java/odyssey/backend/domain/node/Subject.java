package odyssey.backend.domain.node;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Subject {

    MATH_AND_AI("수학과 인공지능"),
    AI_PROGRAMMING("인공지능 프로그래밍"),
    SCREEN_IMPLEMENTATION("화면구현"),
    ADVANCED_SCREEN_IMPLEMENTATION("응용프로그래밍 화면구현"),
    APPLICATION_PROGRAMMING("응용프로그래밍"),
    WEB_PROGRAMMING("웹프로그래밍"),
    WEB_PROGRAMMING_PRACTICE("웹프로그래밍 실무"),
    FRONTEND_BASIC("프론트엔드 기초"),
    SQL("SQL활용"),
    APPLICATION_DEV("응용프로그래밍개발"),
    DATABASE_PROGRAMMING("데이터베이스프로그래밍"),
    JAVA_PROGRAMMING("JAVA프로그래밍"),
    PROGRAMMING("프로그래밍"),
    NETWORK_PROGRAMMING("네트워크 프로그래밍"),
    AWS_BASIC("AWS 기초 입문 실전"),
    MICROPROCESSOR("마이크로 프로세서"),
    MICROPROCESSOR_CONTROL("마이크로 프로세서 제어"),
    EMBEDDED_AUTO_CONTROL("임베디드 자동제어 프로그래밍"),
    STM32("STM32"),
    COMPUTER_GRAPHIC("컴퓨터 그래픽"),
    MOTION_GRAPHIC("모션 그래픽"),
    DESIGN_GENERAL("디자인 일반"),
    ALGORITHM_DESIGN("알고리즘 설계"),
    ALGORITHM_BASIC("알고리즘 기초"),
    AI_GENERAL("인공지능 일반"),
    BIGDATA_ANALYSIS("빅데이터분석"),
    ROBOT_PROGRAMMING("로봇 프로그래밍"),
    NETWORK_THEORY("네트워크 이론"),
    APP_PROGRAMMING("앱 프로그래밍"),
    MOBILE_PROGRAMMING("모바일 프로그래밍"),
    BIGDATA_EXCEL("빅데이터 엑셀"),
    PROJECT_PRACTICE("프로젝트 실무"),
    SYSTEM_MANAGEMENT("시스템 관리 및 지원"),
    JAVA("자바"),
    COMPUTER_STRUCTURE("컴퓨터 구조"),
    DATA_STRUCTURE("자료구조"),
    IOT_AND_SENSOR("사물 인터넷과 센서 제어"),
    AI_MATH_BASIC("ai 기초수학"),
    PROBLEM_SOLVING_BASIC("문제해결 기초"),
    ROBOT_SOFTWARE_DEV("로봇소프트웨어개발"),
    EMBEDDED_SYSTEM("임베디드시스템"),
    ELECTRONIC_CIRCUIT("전자회로"),
    ELECTRONIC_CAD("전자캐드"),
    PCB("pcb 수업");

    private final String description;
}
