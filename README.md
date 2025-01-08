### MAP

Map은 키-값 쌍으로 데이터를 저장하는 컬렉션입니다. 순서를 보장하지 않으며, 중복된 키는 허용되지 않습니다.

- **HashMap**: 기본적인 Map 구현체로, 순서를 보장하지 않음.
- **LinkedHashMap**: 삽입 순서를 보장하는 Map.
    - 삽입 순서 유지: 데이터를 삽입한 순서를 유지하여 순차적으로 접근 가능.
    - 접근 순서 유지 옵션: 생성 시 `accessOrder` 매개변수를 true로 설정하면 최근 접근 순서에 따라 정렬.
    - LRU(Least Recently Used) 캐시 구현에 유용.

```java
Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
linkedHashMap.put("one", 1);
linkedHashMap.put("two", 2);
linkedHashMap.put("three", 3);
System.out.println(linkedHashMap); // {one=1, two=2, three=3}
```

- **TreeMap**: 키의 자연 순서(정렬된 상태)를 보장하는 Map.

```java
Map<String, Integer> map = new HashMap<>();
map.put("one", 1);
map.put("two", 2);
map.put("three", 3);
System.out.println(map);
```

### StringBuilder

StringBuilder는 문자열을 동적으로 관리하고 조작하는 클래스입니다. 문자열을 이어붙일 때 `+` 연산자보다 더 효율적입니다.

- **append()**: 문자열 추가.
- **insert()**: 특정 위치에 문자열 삽입.
- **replace()**: 문자열 대체.
- **reverse()**: 문자열 뒤집기.

#### StringBuilder가 더 효율적인 이유

- **불변성 문제 해결**: `String` 클래스는 불변(immutable)이므로 문자열 연산이 발생할 때마다 새로운 객체를 생성합니다. 반면, `StringBuilder`는 가변(mutable) 객체이므로 추가적인 객체 생성을 피할 수 있습니다.
- **성능 향상**: 문자열을 여러 번 연결하거나 수정할 때, `StringBuilder`는 내부 버퍼를 사용하여 메모리와 성능을 최적화합니다.
- **메모리 절약**: `String`은 새로운 객체를 계속 생성하지만, `StringBuilder`는 동일한 객체 내에서 문자열을 수정하므로 메모리 사용량이 줄어듭니다.

```java
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i; // 비효율적: 새로운 객체가 계속 생성됨
}

StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i); // 효율적: 동일 객체 내에서 수정됨
}
System.out.println(sb.toString());
```

## lombok

Lombok은 Java 코드에서 반복되는 코드를 자동으로 생성하는 라이브러리입니다.

- 장점
    - 어노테이션 기반의 코드 자동 생성을 통한 생산성 향상
    - 반복되는 코드 다이어트를 통한 가독성 및 유지보수성 향상
    - Getter, Setter 외에 빌더 패턴이나 로그 생성 등 다양한 방면으로 활용 가능
- `@EqualsAndHashCode` : 클래스에 대한 equals 함수와 hashCode 함수를 자동으로 생성해준다. 만약 서로 다른 두 객체에서 특정 변수의 이름이 똑같은 경우 같은 객체로 판단을 하고 싶다면 아래와 같이 해줄 수 있다.
- `@RequiredArgsConstructor`는 특정 변수만을 활용하는 생성자를 자동완성 시켜주는 어노테이션이다. 생성자의 인자로 추가할 변수에 `@NonNull` 어노테이션을 붙여서 해당 변수를 생성자의 인자로 추가할 수 있다. 아니면 해당 변수를 `final`로 선언해도 의존성을 주입받을 수 있다.

```
@Getter
@RequiredArgsConstructor

public class Store extends Common {
@NonNull
    private String companyName;                                 // 상호명
    private final String industryTypeCode;                      // 업종코드
    private String businessCodeName;                            // 업태명
    private String industryName;                                // 업종명(종목명)
    private String telephone;                                   // 전화번호
   

    /* RequiredArgsConstructor 통해 아래의 생성자를 자동 생성할 수 있다.
    public Store(String companyName, String industryTypeCode) {
        this.companyName = companyName;
        this.industryTypeCode = industryTypeCode;
    }
    */

}

```


- **@Data**:
    - @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor를 포함.

```java
@Data
public class User {
    private String name;
    private int age;
}
```

이 코드로 name과 age에 대한 getter, setter, toString 메서드가 자동 생성됩니다.

## repository interface 생성

Repository 패턴은 데이터 접근을 추상화하여 유지보수와 테스트 용이성을 높입니다.

- **의존성 역전 원칙 (DIP)**:
    - 상위 모듈은 하위 모듈에 의존하지 않고, 추상화에 의존.
    - 구현체의 변경에도 코드 수정 최소화.

```java
public interface UserRepository {
    User findById(Long id);
    List<User> findAll();
}
```

## Files

Files 클래스는 파일 및 디렉터리를 조작할 수 있는 유틸리티 메서드를 제공합니다.

- **읽기 및 쓰기**:

```java
List<String> lines = Files.readAllLines(Paths.get("file.txt"));
Files.write(Paths.get("output.txt"), lines);
```

- **파일 복사**:

```java
Files.copy(Paths.get("source.txt"), Paths.get("target.txt"), StandardCopyOption.REPLACE_EXISTING);
```

### [Files.walk](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html)

디렉터리 내의 파일과 하위 폴더를 순회합니다.

- **탐색 예시**:

```java
Files.walk(Paths.get("dir"))
     .filter(Files::isRegularFile)
     .forEach(System.out::println);
```

- **깊이 제한**:

```java
Files.walk(Paths.get("dir"), 2)
     .forEach(System.out::println);
```



## Path

Path 클래스는 파일 및 디렉터리 경로를 표현합니다.

- **경로 결합**:

```java
Path path = Paths.get("/home/user", "documents");
System.out.println(path.toString());
```

- **상대 경로와 절대 경로 변환**:

```java
Path relative = Paths.get("docs");
Path absolute = relative.toAbsolutePath();
System.out.println(absolute);
```

### 반환값 타입

메소드의 반환값 타입 정할 때 필요한 값을 반환

예를들어 아래와 같이 `delete` 의 경우 `junit`에서 테스트를 하는데, 이 때 성공여부만 필요하므로 `boolean`을 반환한다.

```java
public static boolean delete(String file) {  
    Path filePath = Paths.get(file);  
    if (!Files.exists(filePath)) return false;  
    try {  
        Files.delete(filePath);  
        return true;  
    } catch (IOException e) {  
        e.printStackTrace();  
        return false;  
    }  
}
```


