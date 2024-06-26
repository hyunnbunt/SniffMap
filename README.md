# ClipStudio
#### 위치 기반으로 반려동물의 산책 경로를 등록하고 정보를 공유하는 프로그램입니다.

## 개발 환경 및 사용 기술
![JAVA21 badge](https://img.shields.io/badge/Java-21-d9381e?style=for-the-badge)
![Spring Boot badge](https://img.shields.io/badge/Spring%20Boot-3.2.5-ff8f00?style=for-the-badge)
![Spring Security badge](https://img.shields.io/badge/Spring%20Security-f7ddbe?style=for-the-badge)
![Spring Data JPA badge](https://img.shields.io/badge/JPA%20hibernate-lightgreen?style=for-the-badge)
![MariaDB badge](https://img.shields.io/badge/MariaDB-dcffe4?style=for-the-badge)
![JDBC templates badge](http://img.shields.io/badge/JDBC%20templates-0f52ba?style=for-the-badge)
![DOCKER badge](https://img.shields.io/badge/Docker-9cf?style=for-the-badge)
![Github badge](https://img.shields.io/badge/Github-6488ea?style=for-the-badge)
![GitHub Actions badge](https://img.shields.io/badge/GitHub%20Actions-d9381e?style=for-the-badge)

## API 명세
### 반려동물 등록 API

내 계정에 반려동물을 등록합니다. 여럿 등록할 수 있습니다.

| url | method | request | response |
| --- | --- | --- | --- |
| dogs | POST | DogRegistrationDto | DogDto |


<details>
<summary>description</summary>
<div markdown="1">


- request(Content-type: application/json)
    ```json
  
    {
      "name": "Bunt",
      "age": 4,
      "weight": 7,
      "sex": "male"
    }
    ```
    
- response
    
    ```json
    
    {
      "number": 1,
      "parentName": "testNameOfParent",
      "name": "Bunt",
      "age": 4,
      "weight": 7,
      "sex": "male",
      "participatingEventIds": [],
      "friends": []
    }
    ```
    
- 서버 내부 동작 및 exception
    
    새로운 강아지 정보를 DogRegistrationDto에 담아 요청을 전송하면 서버는 강아지를 등록합니다. 
    

</div>
</details>

### 친구 등록, 취소 API

내 반려동물과 다른 반려동물을 친구로 설정하거나, 기존의 친구 관계를 취소합니다.

#### 친구 등록
| url | method | request | response |
| --- | --- | --- | --- |
| dogs/{number}/friends | POST | Long | DogDto |
#### 친구 취소
| url | method | request | response |
| --- | --- | --- | --- |
| dogs/{number}/friends | PATCH | Long | DogDto |

<details>
<summary>description</summary>
<div markdown="1">


- request(Content-type: application/json)
    ```json
    {
        3
    }
    ```
    
- response
    
    ```json
    
    {
      "number": 1,
      "parentName": "testNameOfParent",
      "name": "Bunt",
      "age": 4,
      "weight": 7,
      "sex": "male",
      "participatingEventIds": [],
      "friends": []
    }
    ```
    
- 서버 내부 동작 및 exception
    
    Long 타입의 강아지 넘버를 requestBody에 담아 요청을 전송하면 서버는 친구 관계를 업데이트합니다. 
    

</div>
</details>


### 산책 장소 등록, 삭제 API

반려동물 모임을 등록, 수정하거나 삭제합니다.

#### 산책 장소 등록
| url | method | request | response |
| --- | --- | --- | --- |
| locations | POST | LocationCreateDto | LocationDto |
#### 산책 장소 삭제
| url | method | request | response |
| --- | --- | --- | --- |
| locations/{number} | DELETE | {number} | LocationDto |

<details>
<summary>description</summary>
<div markdown="1">
    
  - 등록 request(Content-type: application/json)

    ```json
    {
      "longitude": 126.941575,
      "latitude": 37.50965556,
      "creatorDogNumber": 1,
    }
    ```
  - 삭제 request(Content-type: application/json)
    ```json
    
    {
      10
    }
    ```
- response 예시
    
    ```json
    {
      "number": 3,
      "longitude": 126.941575,
      "latitude": 37.50965556,
      "walkingDogNumbers": [ 2, 3, 4, 5 ]
    }
    ```
    
- 서버 내부 동작 및 exception
    
    등록하려는 산책 장소 정보를 LocationCreateDto 담아 전송하면 서버는 산책 장소를 등록하고 LocationDto를 반환합니다.
    삭제하려는 산책 장소 넘버를 Long 타입으로 전송하면 서버는 산책 장소를 삭제하거나 삭제를 요청하는 계정의 반려동물을 산책 장소에서 삭제하고 LocationDto를 반환합니다.

</div>
</details>



### 모임 등록, 수정, 삭제 API

반려동물 모임을 등록, 수정하거나 삭제합니다.

#### 모임 등록
| url | method | request | response |
| --- | --- | --- | --- |
| events | POST | EventCreateDto | EventDto |
#### 모임 수정
| url | method | request | response |
| --- | --- | --- | --- |
| events/{number} | PATCH | EventUpdateDto | EventDto |
#### 모임 삭제
| url | method | request | response |
| --- | --- | --- | --- |
| events/{number} | DELETE | {number} | EventDto |

<details>
<summary>description</summary>
<div markdown="1">

  - 등록 request(Content-type: application/json)
    ```json
    {
      "time": "Mon Jan 1 00:00:00 KST 2024", 
      "longitude": 126.941575,
      "latitude": 37.50965556,
      "creatorDogNumber": 1,
    }
    ```
  - 수정 request(Content-type: application/json)
    
    ```json
    {
      "time": "Mon Jan 1 00:00:00 KST 2024", 
      "longitude": 126.941575,
      "latitude": 37.50965556,
    }
    ```
  - 삭제 request(Content-type: application/json)
    ```json
    
    {
      3
    }
    ```
- response 예시
    
    ```json
    {
      "number": 3,
      "time": "Mon Jan 1 00:00:00 KST 2024",
      "longitude": 126.941575,
      "latitude": 37.50965556,
      "participantDogNumbers": [ 2, 25, 49 ]
    }
    ```
    
- 서버 내부 동작 및 exception
    
    등록하려는 모임 정보를 EventCreateDto에 담아 전송하거나, 수정하려는 모임 정보를 EventUpdateDto에 담아 전송하면 서버는 모임을 등록하거나 수정하고 EventDto를 반환합니다.

</div>
</details>



### 반려동물의 모임 참여 API

특정 모임에 반려동물의 참여 예약 정보를 등록합니다.

| url | method | request | response |
| --- | --- | --- | --- |
| dogs/{number}/events/join | PATCH | Long | DogDto |


<details>
<summary>description</summary>
<div markdown="1">

- response 예시
    
    ```json
        {
        100 
        }
    ```
    
- 서버 내부 동작 및 exception
    
   반려동물을 참여시키고 싶은 모임의 넘버를 Long 타입으로 requestBody에 담아 요청하면 서버는 반려동물과 모임 관계 데이터를 새로 생성하고 DogDto를 반환합니다.

</div>
</details>
