package odyssey.backend.domain.node;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Subject {

    MATH_AND_AI("수학과 인공지능", "인공지능 알고리즘에 적용되는 수학적 원리 구현 및 이해, 데이터 처리, 학습 및 최적화, 확률 이론, 머신러닝 프로젝트"),
    AI_PROGRAMMING("인공지능 프로그래밍", "비정형 데이터 처리, 객체 감지, 데이터 수집 및 분석, 생성형 인공지능 활용, 최신 기술 탐구"),
    SCREEN_IMPLEMENTATION("화면구현", "HTML, CSS, JS를 이용한 화면 구현, ES6 JS와 React 활용"),
    ADVANCED_SCREEN_IMPLEMENTATION("응용프로그래밍 화면구현", "졸업작품 UX/UI 디자인, 프로젝트 화면 구현 심화"),
    APPLICATION_PROGRAMMING("응용프로그래밍", "FastAPI 백엔드 + Jinja 템플릿 기반 프로젝트"),
    WEB_PROGRAMMING("웹프로그래밍", "React, Next.js, Socket.io 활용 프론트엔드 수업"),
    WEB_PROGRAMMING_PRACTICE("웹프로그래밍 실무", "Next.js 기반 포트폴리오용 프로젝트, Auth.js 활용"),
    FRONTEND_BASIC("프론트엔드 기초", "React, Next.js, Socket.io 기반 프로젝트 실습"),
    SQL("SQL활용", "SQL 작성 능력 함양, 데이터베이스 쿼리 실습"),
    APPLICATION_DEV("응용프로그래밍개발", "FastAPI 활용 백엔드 API 개발 및 웹 서비스 제작"),
    DATABASE_PROGRAMMING("데이터베이스프로그래밍", "MySQL 기반 SQL 쿼리 학습, Redis 활용, 데이터베이스 기초 지식"),
    JAVA_PROGRAMMING("JAVA프로그래밍", "Spring Boot 기반 백엔드 API 개발"),
    PROGRAMMING("프로그래밍", "Python 기초 문법 및 문제 해결, 기본 알고리즘 학습"),
    NETWORK_PROGRAMMING("네트워크 프로그래밍", "라즈베리파이를 활용한 웹 서버 구축 및 웹프로그래밍 개발"),
    AWS_BASIC("AWS 기초 입문 실전", "EC2, 로드밸런서, RDS, S3, 도메인 활용 환경 구축"),
    MICROPROCESSOR("마이크로 프로세서", "ESP32를 활용한 센서와 MCU 간 통신"),
    MICROPROCESSOR_CONTROL("마이크로 프로세서 제어", "Unity 기반 디지털 트윈 로봇 제어, C# 활용"),
    EMBEDDED_AUTO_CONTROL("임베디드 자동제어 프로그래밍", "PLC XG5000 활용 자동제어 프로그램 실습"),
    STM32("STM32", "STM32 기반 센서 제어 실습"),
    COMPUTER_GRAPHIC("컴퓨터 그래픽", "Illustrator, Photoshop, Figma 활용 벡터/비트맵 그래픽 제작"),
    MOTION_GRAPHIC("모션 그래픽", "Figma, Photoshop, Phase, After Effects, Premiere 활용 영상 모션 애니메이션"),
    DESIGN_GENERAL("디자인 일반", "아이디어 발상법, 브랜딩, UX/UI 프로젝트"),
    ALGORITHM_DESIGN("알고리즘 설계", "Python으로 프로그램 알고리즘 설계"),
    ALGORITHM_BASIC("알고리즘 기초", "Python 정렬, 탐색 등 알고리즘 기초 학습"),
    AI_GENERAL("인공지능 일반", "인공지능 원리 실습, 뉴런, 딥러닝 기본 흐름 이해"),
    BIGDATA_ANALYSIS("빅데이터분석", "빅데이터 분석 프로젝트, 외부 데이터 시각화"),
    ROBOT_PROGRAMMING("로봇 프로그래밍", "자율주행 로봇 운영, 산학 프로젝트 수업"),
    NETWORK_THEORY("네트워크 이론", "TCP/IP 메시지 전달, 전반적인 네트워크 이론 학습"),
    APP_PROGRAMMING("앱 프로그래밍", "Flutter 기반 모바일 앱 개발"),
    MOBILE_PROGRAMMING("모바일 프로그래밍", "Flutter 기반 고급 모바일 앱 개발"),
    BIGDATA_EXCEL("빅데이터 엑셀", "엑셀 활용 빅데이터 분석 및 자동화"),
    PROJECT_PRACTICE("프로젝트 실무", "산업체 연계 팀 프로젝트, 졸업작품 프로젝트"),
    SYSTEM_MANAGEMENT("시스템 관리 및 지원", "FastAPI 기반 프로젝트 진행 및 졸업작품전 관리"),
    JAVA("자바", "객체지향 기초, 제네릭, 컬렉션 프레임워크 학습"),
    COMPUTER_STRUCTURE("컴퓨터 구조", "기본 회로도, 논리 게이트, 로직 이해"),
    DATA_STRUCTURE("자료구조", "스택, 큐 등 기본 자료구조 실습"),
    IOT_AND_SENSOR("사물 인터넷과 센서 제어", "Arduino 활용 IoT 및 센서 제어 실습"),
    AI_MATH_BASIC("ai 기초수학", "기초 수학으로 인공지능 구현, Pandas 활용"),
    PROBLEM_SOLVING_BASIC("문제해결 기초", "C 언어 기초문법, 자료구조, 알고리즘 기초"),
    ROBOT_SOFTWARE_DEV("로봇소프트웨어개발", "ROS, ROS2 활용 로봇 소프트웨어 개발 수업"),
    EMBEDDED_SYSTEM("임베디드시스템", "FPGA 기반 비메모리 반도체 실습, Verilog HDL 활용"),
    ELECTRONIC_CIRCUIT("전자회로", "센서, 전자기 기초 심화, 실습 병행"),
    ELECTRONIC_CAD("전자캐드", "캐드 활용 실습"),
    PCB("pcb 수업", "PCB 제작 및 전공 동아리 실습");

    private final String title;
    private final String description;
}
