# Spring-Boot

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