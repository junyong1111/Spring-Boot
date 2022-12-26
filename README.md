# Spring-Boot
Spring Boot와 AWS로 웹 서비스 구현

## 0. 인텔리제이 설치

**인텔리제이는 스프링 부트 개발하는 하는 데 있어 조금 더 친숙하며 많은 IT 서비스 회사들이 사용**

**인텔리제이의 장점**

1. 강력한 추천 기능
2. 다양한 리팩토링과 디버깅 기능
3. git에 대한 높은 자유도
4. 프로젝트 시작 시 인덱싱을 하여 자원들에 대한 빠른 검색 속도
5. HTML과 CSS, JS, XML에 대한 강력 기능 지원
6. 자바, 스프링 부트 버전업에 맞춘 빠른 업데이트 

### **젝브레인의 젯품 전체를 관리해 주는 데스크톱 앱 설치**

[JetBrains Toolbox App: 도구를 간편하게 관리](https://www.jetbrains.com/ko-kr/toolbox-app/)

1. 해당 링크 접속 후 자신의 OS에 맞게 다운로드 후 실행 (설치 시 별다른 옵션없이 진행)
2. 작업표시줄에 생긴 툴 박스 클릭
    
    ![Untitled](https://user-images.githubusercontent.com/79856225/209473887-59cdfc4a-1aeb-46d7-86d8-1feba11f76ee.png)

3. Intellij Community(무료버전) 설치

    ![Untitled2](https://user-images.githubusercontent.com/79856225/209473894-24eef8db-7887-4c6e-b42f-e4229713e332.png)


4. 최대 메모리 설정
    
    ![Untitled](https://user-images.githubusercontent.com/79856225/209473998-ff55001a-62f7-4d5c-9e1d-eb9cad18f9e0.png)
    
    메모리가 4G 이하를 가정하고 설정되어 있으므로 자신의 메모리 상황에 맞게 설정
    
    8G → 1024~2048
    
    16G → 2048 ~ 4096
    
    ![Untitled](https://user-images.githubusercontent.com/79856225/209474006-d253553d-1bd2-43d1-a9a8-2b62d831c552.png)




## 1. 프로젝트 생성

- **아티팩트는 프로젝트의 이름**
    
    <img width="799" alt="스크린샷 2022-12-26 오후 6 44 04" src="https://user-images.githubusercontent.com/79856225/209546527-502b6484-07a2-46c0-8977-55527aa49eea.png">
    
- 이 후 다음을 누르고 다음 라이브러리 설정은 체크하지 않고 넘어간다.

**그레이들 프로젝트를 스프링 부트로 변경하기**

- build.gradle 파일 오픈 후 다음 코드를 맨 윗줄에 추가
    
    ```java
    buildscript{
        ext{
            springBootVersion = '2.1.7.RELEASE'
        }
        repositories {
            mavenCentral()
            jcenter()
        }
        dependencies {
            classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        }
    }
    ```
    
    - **ext**라는 키워드는 build.gradle에서 사용하는 전역변수를 설정하겠다는 의미로 여기서는 springBootVersion 전역변수를 생성하고 그 값을 2.1.7로 하겠다는 의미이다.
    - **repositories는** 각종 의존성(라이브러리)들을 어떤 원격 저장소에 받을 지를 정한다.
        - mavencentral → 라이브러리를 업로드하기 위해 많은 과정과 설정이 필요
        - jcenter → 위 문제를 개선하여 업로드를 간단하게 함
            
            일단 2개 모두 사용
            
    - **dependencies**는 프로젝트 개발에 필요한 의존성들을 선언하는 곳
        - 특정 버전을 명시하면 안된다
        - 이렇게 관리할 경우 각 라이브러리들의 버전 관리가 한 곳에 집중되고 버전 충돌 문제도 해결
- plugins{} 아래에 다음 코드 입력
    
    ```java
    apply plugin: 'java'
    apply plugin: 'eclipse'
    apply plugin: 'org.springframework.boot'
    apply plugin: 'io.spring.dependency-management'
    ```
    
    - 위 4개의 플러그인은 자바와 스프링 부트를 사용하기 위해서는 필수이므로 항상 추가
    
- 전체 코드
    
    ```java
    buildscript{
        ext{
            springBootVersion = '2.1.7.RELEASE'
        }
        repositories {
            mavenCentral()
            jcenter()
        }
        dependencies {
            classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        }
    }
    
    apply plugin: 'java'
    apply plugin: 'eclipse'
    apply plugin: 'org.springframework.boot'
    apply plugin: 'io.spring.dependency-management'
    
    group 'com.example.project'
    version '1.0-SNAPSHOT'
    sourceCompatibility = 1.8
    
    repositories {
        mavenCentral()
    }
    
    dependencies {
        implementation('org.springframework.boot:spring-boot-starter-web')
        testImplementation('org.springframework.boot:spring-boot-starter-test')
    }
    ```
    
- 우측 상단에 변경 반영 클릭
    
    <img width="278" alt="스크린샷 2022-12-26 오후 7 17 19" src="https://user-images.githubusercontent.com/79856225/209546532-91364b86-2231-4fab-b867-c76aae1600ee.png">
    

**깃허브 연동하기**

- Command + Shift + A를 이용하여 Action 검색창 오픈
    - share project on github 검색 후 계정 연동
- Command + K 명령어를 통해 깃 터미널을 열 수 있음

## 2. 스프링 부트에서 테스트 코드를 작성

### TDD

![스크린샷 2022-12-26 오후 7 40 07 작게](https://user-images.githubusercontent.com/79856225/209546740-32e201f5-af49-4211-b1f3-ca334f68acb4.jpeg)


- 항상 실패하는 테스트를 먼저 작성 (RED)
- 테스트과 통과하는 프로덕션 코드를 작성(Green)
- 테스트가 통과하면 프로덕션 코드를 리팩토링(Refactor)

 —# TDD와 단위 테스트(Unit Test)는 다른 이야기이다.

**단위 테스트의 장점**

1. 개발단계 초기에 문제를 발견하게 도와줌
2. 개발자가 나중에 코드를 리팩토링하거나 라이브러리 업그레이드 등에서 기존 기능이 올바르게 작동하는지 확인 가능
3. 기능에 대한 불확실성 감소
4. 시스템에 대한 실제 문서를 제공, 단위 테스트 자체가 문서로 사용 가능

**단위 테스트를 사용하면 개발자가 만든 기능을 안전하게 보호해주며 문제 발생을 빠르게 확인할 수 있다**

**반드시 익혀야 할 기술이자 습관**

**테스트 코드 를 도와주는 프레임 워크**

- **JUnit - Java → JUnit5를 사용**
- DBUnit - DB
- CppUnit - C++
- NUnit - .net

**Hello Controller 테스트 코드 작성하기**

- **1장에서 만들었던 프로젝트에서 패키지 하나 생성**
    
    일반적으로 패키지명은 웹 사이트 주소의 역순
    
    ex) com.example_test.admin
    
    <img width="682" alt="스크린샷 2022-12-26 오후 7 47 44" src="https://user-images.githubusercontent.com/79856225/209546742-efb0d15e-4182-4dd6-967a-766ad13ddc17.png">
    
- **생성한 패키지에 Java 클래스 생성**
    
    클래스 이름은 Application으로 
    
    <img width="664" alt="스크린샷 2022-12-26 오후 7 51 22" src="https://user-images.githubusercontent.com/79856225/209546746-52e21422-b627-4368-b1e6-97933bc60781.png">
    

- **클래스의 다음 코드를 작성**
    
    ```java
    package com.example_test.admin;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    
    @SpringBootApplication
    public class Application {
        public static  void main(String[] args){
            SpringApplication.run(Application.class, args);
        }
    
    }
    ```
    
    - **Application 클래스는 프로젝트의 메인 클래스**
    - `@SpringBootApplication`
        - 스프링 부트의 자동 설정, Bean읽기와 생성을 모두 자동으로 설정
        - 현재 코드가 있는 위치부터 설정을 읽어가기 대문에 항상 **프로젝트 최상단에 위치**
    - `SpringApplication.*run*`
        - 별도로 외부의 WAS를 두지 않고 내장 WAS를 실행
        - 언제 어디서나 같은 환경에서 스프링 부트를 배포가능
            
            —# WAS(Web Application Server)
            

**테스트를 위한 Controller 생성**

- 현재 패키지 하위에 web 패키지 생성
    - 컨트롤러와 관련된 클래스들은 모두 이 패키지에 담음
- 클래스 생성
    - HelloController
        
        ```java
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RestController;
        
        @RestController
        public class HelloController{
            @GetMapping("/hello")
            public  String hello(){
                return "hello";
            }
        }
        ```
        
    - `@RestController`
        - 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다.
    - `@GetMapping`
        - HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 준다.

**테스트 코드로 검증**

- src/test/java 디렉토리에 앞에서 생성했던 패키지를 그대로 다시 생성 하지만 테스트 컨트롤러만 수정
    
    <img width="313" alt="스크린샷 2022-12-26 오후 8 42 17" src="https://user-images.githubusercontent.com/79856225/209546747-6e72326c-1934-4bb4-8e2c-1996ceaeb27a.png">
    
    - 이후 해당 코드를 HelloControllerTest 클래스에 작성
    
    ```java
    
    package com.example_test.admin.web;
    
    import com.example_test.admin.web.HelloController;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowire;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
    import org.springframework.test.context.junit4.SpringRunner;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.ResultActions;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
    
    @RunWith(SpringRunner.class)
    @WebMvcTest(controllers = HelloController.class)
    public class HelloControllerTest{
        @Autowired
        private MockMvc mvc;
    
        @Test
        public void hello가_리턴된다() throws Exception{
            String hello = "hello";
    
            mvc.perform(get("/hello"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(hello));
        }
    }
    ```
    
    - `@RunWith`
        - 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행
        - 여기서는 SpringRunner 실행자 사용
        - 스프링 부트 테스트와 Junit 사이 연결자
    - `@WebMvcTest`
        - 여러 스프링 테스트 어노테이션 중, Web에 집중할 수 있는 어노테이션
        - 선언할 경우 컨트롤러 사용가능
    - `@Autowired`
        - 스프링이 관리하는 빈(Bean)을 주입 받음
    - `private MockMvc mvc;`
        - 웹 API를 테스트할 때 사용
        - 스프링 MVC테스트의 시작점
        - HTTP, GET, POST 등에 대한 API 테스트 가능
    - `mvc.perform`
        - MockMvc를 통해 해당 주소로 HTTP GET 요청
        - 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언 가능
    - `.andExpect(*status*().isOk())`
        - mvc.perform의 결과를 검증
        - HTTP Header의 Status를 검증
        - 현재는 200인지 아닌지를 검증
    - `.andExpect(*content*().string(hello))`
        - mvc.perform의 결과를 검증
        - 응답 본문의 내용을 검증
        - Controller에서 “hello”를 리턴하기 때문에 이 값이 맞는지 검증

**테스트 확인**

<img width="470" alt="스크린샷 2022-12-26 오후 8 48 30" src="https://user-images.githubusercontent.com/79856225/209546748-9b196a73-a34e-4ce9-bf17-789b38eeb090.png">

<img width="1256" alt="스크린샷 2022-12-26 오후 8 49 22" src="https://user-images.githubusercontent.com/79856225/209546749-4724e274-8882-47a8-9ae4-bfb2f40bd64b.png">

테스트 통과 확인!