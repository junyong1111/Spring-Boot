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

**롬복 소개 및 설치하기**

**롬복은 자바 개발자들의 필수 라이브러리** 

롬복은 자바 개발할 때 자주 사용하는 코드 Getter, Setter, 기본생성자, toString 등을 어노테이션으로 자동 생성해 준다.

- 프롬젝트에 롬복 추가
    - build.gradle 에 다음 의존성 코드 추가
    - 롬복 버전은 자신의 jdk에 맞는걸 해야 오류가 안나는듯..
        
        ```java
        annotationProcessor 'org.projectlombok:lombok:1.18.20'
        implementation 'org.projectlombok:lombok:1.18.20'
        testAnnotationProcessor 'org.projectlombok:lombok:1.18.20'
        testImplementation 'org.projectlombok:lombok:1.18.20'
        
        configurations {
            compileOnly {
                extendsFrom annotationProcessor
            }
        }
        ```
        
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
            
                annotationProcessor 'org.projectlombok:lombok:1.18.20'
                implementation 'org.projectlombok:lombok:1.18.20'
                testAnnotationProcessor 'org.projectlombok:lombok:1.18.20'
                testImplementation 'org.projectlombok:lombok:1.18.20'
            }
            configurations {
                compileOnly {
                    extendsFrom annotationProcessor
                }
            }
            ```
            
    - ~~롬복 플러그인 설치~~
        - ~~Command + Shit + A 입력 후 Plugins 클릭~~
        
        **※IntelliJ 2020.03 이후 버전에서는 기본Plugin으로 Lombok이 설치되어 있습니다.**
        

**Hello Controller 코드를 롬복으로 전환**

- main web 패키지에 dto 패키지를 추가 후 HelloResponseDto 클래스를 생성
    - 모든 응답 Dto는 dto 패키지에 추가
        
        ![스크린샷 2022-12-27 오후 3 33 19 작게](https://user-images.githubusercontent.com/79856225/209628212-a599efe8-c5f7-4428-9c66-24b5f2b3092a.jpeg)
        
- HelloResponseDto 코드 작성
    
    ```java
    package com.example_test.admin.web.dto;
    
    import lombok.Getter;
    import lombok.RequiredArgsConstructor;
    
    @Getter
    @RequiredArgsConstructor
    public class HelloResponseDto{
        private final String name;
        private final int amount;
    }
    ```
    
    - `@Getter`
        - 선언된 모든 필드의 get 메소드를 생성
    - `@RequiredArgsConstructor`
        - 선언된 모든 final 필드가 포함된 생성자를 생성
        - final이 없는 필드는 생성자에 포함되지 않는다.
        
        —# final로 선언하면 한번 초기화된 변수는 변경할 수 없는 상수값이 됩니다.
        
- Test Code 작성
    - test web 패키지에 dto 패키지를 추가 후 HelloResponseDtoTest 클래스를 생성
        
        ![스크린샷 2022-12-27 오후 3 39 05 작게](https://user-images.githubusercontent.com/79856225/209628216-683b4dbf-7840-44ed-b3e3-3c197621cea2.jpeg)
        
    - 코드 작성
        
        ```java
        package com.example_test.admin.web.dto;
        
        import org.junit.Test;
        import static org.assertj.core.api.Assertions.assertThat;
        
        public class HelloResponseDtoTest{
            @Test
            public void 롬복_기능_테스트(){
                String name = "test";
                int amount = 1000;
        
                HelloResponseDto dto = new HelloResponseDto(name, amount);
        
                assertThat(dto.getName()).isEqualTo(name);
                assertThat(dto.getAmount()).isEqualTo(amount);
            }
        }
        ```
        
    - *`assertThat`*
        - assertj라는 테스트 검증 라이브러리의 검증 메소드
        - 검증하고 싶은 대상을 메소드 인자로 받음
        - 메소드 체이닝이 지원되어 isEqualTo와 같은 메소드를 이어서 사용가능
        
        —# Junit의 assertThat 보다 assertjThat을 사용
        
    - `isEqualTo`
        - assertj의 동등 비교 메소드
        - assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공
- 테스트 확인
    
    ![스크린샷 2022-12-27 오후 4 00 54 작게](https://user-images.githubusercontent.com/79856225/209628218-4e74bf4e-6438-4c39-ba85-686dc27e2590.jpeg)
    
- 테스트 확인 완료 후 Hellocontroller와 HellocontrollerTest에도 코드 추가
    - Hellocontroller
        
        ```java
        // Hellocontroller.java
        
        package com.example_test.admin.web;
        
        import com.example_test.admin.web.dto.HelloResponseDto;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;
        
        @RestController
        public class HelloController {
            @GetMapping("/hello")
            public String hello() {
                return "hello";
            }
        
            @GetMapping("/hello/dto")
            public HelloResponseDto helloDto(@RequestParam("name") String name,
                                             @RequestParam("amount") int amount)
            {
                return new HelloResponseDto(name, amount);
            }
        }
        ```
        
    - HellocontrollerTest
        
        ```java
        package com.example_test.admin.web;
        
        import com.example_test.admin.web.HelloController;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
        import org.springframework.test.context.junit4.SpringRunner;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.ResultActions;
        import static  org.hamcrest.Matchers.is;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
            @Test
            public void helloDto가_리턴된다() throws Exception{
                String name = "hello";
                int amount = 1000;
        
                mvc.perform(
                        get("/hello/dto")
                                .param("name", name)
                                .param("amount", String.
                                        valueOf(amount)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name", is(name)))
                                .andExpect(jsonPath("$.amount", is(amount)));
            }
        }
        ```
        
        - `param`
            - API 테스트할 때 사용될 요청 파라미터를 설정
            - 단, 값은 String만 허용
            - 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능
        - *`jsonPath`*
            - JSON 응답값을 필드별로 검증할 수 있는 메소드
            - $를 기준즈올 필드명을 명시
            - 위 코드에서는 name과 amount를 검증하니 $.name과 $.amount로 검증
    - 테스트 확인
        
        <img width="1587" alt="스크린샷 2022-12-27 오후 4 18 40" src="https://user-images.githubusercontent.com/79856225/209628221-607e536e-e0b0-48c0-aac6-5c4db30906a0.png">
        
        JSON이 리턴되는 API 역시 정상적으로 테스트가 통과

## 3. 스프링부트에서 JPA로 데이터베이스 다루기

**JAP : 자바 표준 ORM(Object Relational Mapping)**

아직 SI 환경에서는 Spring & My Batis를 많이 사용하지만, 쿠팡, 우아한 형제들, NHN등 자사 서비스를 개발하는 곳에서는 Spring Boot & JPA를 전사 표준으로 사용하고 있다.

### JPA 소개

현대의 웹 애플리케이션에서 관계형 데이터베이스(RDB)는 빠질 수 없는 요소이다. Oracle, MySQL, MSSQL등을 쓰지 않는 웹 애플리케이션은 거의 없다. 그러다 보니 **객체를 관계형 데이터 베이스에서 관리**하는 것이 무엇보다 중요하다. 관계형 데이터베이스가 게속해서 웹 서비스의 중심이 되면서 모든 코드는 SQL중심이 되어 현업 프로젝트 대부분이 **애플리케이션 코드보다 SQL**로 가득하게 된 것이다. 이는 관계형 데이터베이스가 SQL만 인식할 수 있기 때문인데, SQL로만 가능하니 각 테이블마다 기본적인 CRUD SQL을 매번 생성해야 한다. 뿐만 아니라 **SQL은 어떻게 데이터를 저장할 지**에 초점이 맞춰진 기술이고 **객체지향 프로그래밍은 기능과 속성은 할 곳에서 관리**하는 기술이다. **JPA는 중간에서 패러다임 일치**를 시켜주기 위한 기술이다.

- 단점으로는 높은 러닝 커브가 존재
    - **객체지향, 관계형 데이터베이스 모두 이해 필요**

### Spring data JPA

JPA는 인터페이스로서 자바 표준명세서이다. 인터페이스인 JPA를 사용하기 위해서는 구현체가 필요하다. 대표적으로는 Hibernate, Eclipse Link등이 존재, 하지만 Spring에서 JPA를 사용할 때는 이 구현체들을 직접 다루지 않는다. 구현체들을 좀 더 쉽게 사용하고자 추상화시킨 **Spring Data JPA라는 모듈을 이용하여 JPA 기술을 다룬다**

- Spring Data JPA → Hibernate → JPA

Spring Data JPA의 등장 이유

1. 구현체 교체의 용이성
    - Hibernate 외에 다른 구현체로 쉽게 교체하기 위함
        
        —# Hibernate가 수명이 다해서 새로운 JPA 구현체가 대세로 떠오를 때 Spring Data JPA를 사용중이면 쉽게 교체가 가능
        
2. 저장소 교체의 용이성
    - 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함
        
        —# 점점 트래픽이 많아져서 관계형 데이터베이스로는 도저히 감당이 안 될 때 쉽게 의존성을 교체하는것으로 MongoDB로 데이터베이스를 교체 가능
        

## WebService Project ****게시판 만들기 프로젝트

**요구사항 분석**

| 게시판 기능 | 회원 기능 |
| --- | --- |
| 게시글 조회 | 구글/네이버 로그인(소셜 로그인) |
| 게시글 등록 | 로그인한 사용자 글 작성 권한 |
| 게시글 수정 | 본인 작성 글에 대한 권한 관리 |
| 게시글 삭제 |  |

**프로젝트에  Spring Data JPA 적용하기**

- build.gradle에 다음과 같의 의존성들 등록
    
    ```java
    implementation('org.springframework.boot:spring-boot-starter-data-jpa')
    implementation('com.h2database:h2')
    ```
    
    - spring-boot-starter-jpa
        - 스프링 부트용 Spring Data JPA 추상화 라이브러리
        - 스프링 부트 버전에 맞춰 자동으로 JPA관련 라이브러리들의 버전을 관리
    - h2
        - 인메모리 관계형 데이터베이스
        - 별도의 설치가 필요 없이 프로젝트 의존성만으로 관리 가능
        - 메모리에서 실행되기 때문에 애플리케이션을 재시작할 때마다 초기화된다는 점을 이용하여 테스트 용도로 많이 사용
        - 이 책에서는 JPA의 테스트, 로컬 환경에서의 구동에서 사용할 예정

**Domain 패키지 만들기**

- 도메인을 담을 패키지 생성

—# 도메인 : 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제영역이라 생각

<img width="233" alt="스크린샷 2023-01-02 오후 4 14 43" src="https://user-images.githubusercontent.com/79856225/210216999-71d56546-780b-4586-ba8e-d57b7745d771.png">

- **domain 패키지에 posts 패키지와 Posts 클래스 생성**
- **Posts 클래스 코드 입력**
    
    ```java
    package com.example_test.admin.domain.posts;
    import lombok.Getter;
    import lombok.Builder;
    import lombok.NoArgsConstructor;
    
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    
    @Getter
    @NoArgsConstructor
    @Entity
    
    public class Posts{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @Column(length = 500, nullable = false)
        private String title;
    
        @Column(columnDefinition = "TEXT", nullable = false)
        private String content;
    
        private String author;
    
        @Builder
        public Posts(String title, String content, String author) {
            this.title = title;
            this.content = content;
            this.author = author;
        }
    
    }
    ```
    
    **주요 어노테이션을 클래스에 가깝게 위치**
    
    **Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다**
    
    @Entity : JPA의 어노테이션이며, @Getter와 @NoArgsConstructor는 롬복의 어노테이션
    
    - 롬복은 코드를 단순화시켜 주지만 **필수 어노테이션은 아니다.**
    - 주요 어노테이션인 @Entity를 클래스에 가깝게 두고, 롬복 어노테이션을 그 위로
    - 위와 같이 정렬 시 코틀린 등의 새 언어로 전화하여 롬복이 필요없는 경우 쉽게 삭제 가능
    
    Postst 클래스는 실제 DB의 테이블과 매칭될 클래스이며 보통 Entity클래스라고도 불림
    
    - JPA 사용 시 DB 데이터 작업할 경우 실제 쿼리를 날리기보다는 이 클래스의 수정을 통해 작업
    - @Entity
        - 테이블과 링크될 클래스임을 나타낸다.
        - 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭
            - [SalesManager.java](http://SalesManager.java) → sales_manager table
    - @id
        - 해당 테이블의 PK 필드를 나타냅니다.
    - @GeneratedValue
        - PK의 생성 규칙을 나타낸다.
        - 스프링 부트 2.0 에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increament가 된다.
    - @Column
        - 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다.
        - 사용 이유 : 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
        - 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나, 타입을 TEXT로 변경하고 싶거나 등의 경우에 사용
    - @NoArgsConstructor
        - 기본 생성자 자동 추가
        - public Posts(){} 와 같은 효과
    - @Getter
        - 클래스 내 모든 필드의 Getter 메소드를 자동생성
    - @Builder
        - 해당 클래스의 빌더 패턴 클래스를 생성
        - 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    
    **JpaRepository 인터페이스 생성**
    
    - **Posts 클래스로 Database를 접근하게 해줌**
        
        <img width="335" alt="스크린샷 2023-01-02 오후 4 35 12" src="https://user-images.githubusercontent.com/79856225/210217004-4037e490-df4a-4a2d-96b1-1b5d85441849.png">
        
        <img width="178" alt="스크린샷 2023-01-02 오후 4 35 20" src="https://user-images.githubusercontent.com/79856225/210217005-b51b2366-be4a-4db7-bbc4-944111673100.png">
        
    - 다음 코드 입력
        
        ```java
        package domain.posts;
        import org.springframework.data.jpa.repository.JpaRepository;
        
        public interface PostsRepository extends JpaRepository<Posts, Long>{
            
        }
        ```
        
        - Jarrepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메소드가 자동 생성
        - Entity 클래스와 기본 Entity Repository는 함께 위치해야 함
    
    **Spring Data JPA 테스트 코드 작성**
    
    - 테스트 폴더에 domain.posts 패키지를 생성하고, 테스트 클래스는 PostsRepositoryTest 이름으로 생성 후 다음 코드 입력
        
        <img width="276" alt="스크린샷 2023-01-02 오후 4 45 25" src="https://user-images.githubusercontent.com/79856225/210217006-2fbf4143-bed9-4794-9aa6-26362c2fb0fc.png">
        
        ```java
        package com.example_test.admin.domain.posts;
        
        import org.junit.After;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.junit4.SpringRunner;
        
        import java.time.LocalDateTime;
        import java.util.List;
        
        import static org.assertj.core.api.Assertions.assertThat;
        
        @RunWith(SpringRunner.class)
        @SpringBootTest
        public class PostsRepositoryTest {
        
            @Autowired
            PostsRepository postsRepository;
        
            @After
            public void cleanup() {
                postsRepository.deleteAll();
            }
        
            @Test
            public void 게시글저장_불러오기() {
                //given
                String title = "테스트 게시글";
                String content = "테스트 본문";
        
                postsRepository.save(Posts.builder()
                                .title(title)
                        .content(content)
                        .author("example@gmail.com")
                        .build());
        
                //when
                List<Posts> postsList = postsRepository.findAll();
        
                //then
                Posts posts = postsList.get(0);
                assertThat(posts.getTitle()).isEqualTo(title);
                assertThat(posts.getContent()).isEqualTo(content);
            }
        }
        ```
        
        - @After
            - Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 저장
            - 보통은 배포 전 전체 테스트를 수행할 때 테흐트간 데이터 침범을 막기 위해 사용
            - 여러 테스트가 동시에 수행되면 테스트용 DB인 H2에 데이터가 그대로 남아 다음 테스트 실행 시 테스트가 실패할 수 있음
        - @postsRepository.save
            - 테이블 posts에 insert/update 쿼리를 실행
            - id 값이 있다면 update, 없다면 insert 쿼리가 실행
        - postsRepository.findAll
            - 테이블 posts에 있는 모든 데이터를 조회해오는 메소드
        
        **별다른 설정 없이 @SpringBootTest를 사용할 경우 H2데이터 베이스를 자동으로 실행**
        
        **이후 게시글저장_불러오기를 테스트해보면 통과하는걸 확인할 수 있다.**
        
        <img width="976" alt="스크린샷 2023-01-02 오후 6 41 50" src="https://user-images.githubusercontent.com/79856225/210217007-234d0a6d-357c-4607-b9ff-610faf53db32.png">
        
        —# 만약 다음과 같은 에러 시 해결방법
        
        **Execution failed for taks ‘:test’.** 
        
        <img width="1047" alt="스크린샷 2023-01-02 오후 6 42 20" src="https://user-images.githubusercontent.com/79856225/210217010-100db398-bf79-4e87-bd21-fb4f0be241aa.png">
        
        <img width="1106" alt="스크린샷 2023-01-02 오후 6 58 08" src="https://user-images.githubusercontent.com/79856225/210217013-9f5ea656-1f66-4e19-b71e-933d705870aa.png">