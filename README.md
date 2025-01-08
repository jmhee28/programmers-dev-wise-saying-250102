# 20250108 TIL
## 페이징

### 페이지 DTO
```
@AllArgsConstructor  
public class Page {  
  
    public List<WiseSaying> wiseSayings;  
    public int totalPages;  
    public int totalItems;  
  
    public List<WiseSaying> getWiseSayings() {  
        return wiseSayings;  
    }  
  
    public int getTotalItems() {  
        return totalItems;  
    }  
  
    public int getTotalPages() {  
        return totalPages;  
    }  
  
}
```
페이징을 구현하기 위해 필요한 것들은 아래와 같다
1. 전체 아이템 갯수
2. 한페이지에 보여줄 아이템 개수
3. 현재 페이지 번호
4. 한번에 보여줄 페이지 개수
5. 전체 페이지 개수
   위 정보들을 포함하는 객체를 만든다. 이 객체를 이용해 `findAll` 의 리턴값을 `List<WiseSaying>` 에서 `Page`로 변경한다.

**DTO**

- **Data Transfer Object**의 약자로, **데이터를 전달**하기 위한 객체
- DTO는 로직을 가지지 않는 순수한 데이터 객체(getter & setter 만 가진 클래스).
- 여러 레이어(Layer)간 데이터를 주고 받을 때 사용할 수 있고 **주로 View와 Controller 사이에서 활용**.
- **DTO는** **getter / setter 메소드**를 포함한다. 하지만, 이외의 다른 비즈니스 로직은 포함하지 않는다.
- **DTO**는 어떻게 구현하느냐에 따라 **가변 객체**로 활용할 수도 있고 **불변 객체**로 활용할 수도 있다.
- DTO는 데이터 전달 만을 위한 객체라는 게 핵심 정의이다. 그렇기 때문에 완전히 데이터 전달 용도로만 사용하는게 목적이라면, getter/setter만 필요하지, 이외의 비즈니스 로직은 굳이 있을 필요가 없다.

**DAO**

- **Data Access Object** 의 약자로, **Database에 접근하는 역할을 하는 객체.**
- 프로젝트의 서비스 모델에 해당하는 부분과 데이터베이스를 연결하는 역할
- **데이터의 CRUD 작업을 시행**하는 클래스. 즉, 데이터에 대한 **CRUD 기능을 전담하는 오브젝트**


그렇다면, DAO를 사용하는 이유가 무엇일까?

- **효율적인 커넥션 관리**와 **보안성.**
- DAO는 비즈니스 로직을 분리하여 도메인 로직으로부터 DB와 관련한 메커니즘을 숨기기 위해 사용.


## RepositoryProvider

![](https://i.imgur.com/JDW0AHh.png)



```JAVA
public class RepositoryProvider {  
  
    public static WiseSayingRepository provide() {  
        if(AppConfig.isFileDb()) {  
            return new WiseSayingFileRepository();  
        }  
        else {  
            return new WiseSayingMemRepository();  
        }  
    }  
}
```

- `RepositoryProvider`를 활용하면, 구현체를 변경해야 할 때 **Service 코드 자체를 수정하지 않아도 되도록 설계**

    - **간단한 프로젝트에 적합한 설계**

    - **간결성**: 프로젝트 규모가 작거나 간단한 경우, 복잡한 DI 프레임워크(Spring 등)를 도입하지 않고 `RepositoryProvider`를 통해 Repository 구현체를 관리하는 방식이 더 간단하고 유지보수가 쉬울 수 있습니다.
    - 개발 초기 단계에서는 빠른 구현이 더 중요할 수 있습니다. 따라서 구체적인 의존성 관리보다는 간단한 "Provider"를 통해 객체를 반환하도록 설계했을 가능성이 있습니다

### **이 코드에서 DIP를 완전히 만족하려면?**

의존성 역전 원칙을 완전히 만족하려면, **의존성 주입(Dependency Injection)** 방식을 사용해 `WiseSayingRepository`를 직접 주입받도록 설계해야 합니다.

#### 수정된 코드:

java

코드 복사

```JAVA
public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    // 생성자 주입 방식으로 Repository 주입
    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(content, author);
        return wiseSayingRepository.save(wiseSaying);
    }

    // 나머지 메서드들은 동일
}

```

