# Spring-Boot
Spring Boot와 AWS로 웹 서비스 구현

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