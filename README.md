# 주변 약국 추천 및 길 안내 서비스

 이 프로젝트는 주변 약국 추천 및 길 안내 서비스이다.
[공공 데이터(약국 현황 정보)](https://www.data.go.kr/data/15065023/fileData.do)를 활용하고, 추천된 약국의 길 안내는 [카카오 지도 및 로드뷰 바로가기 URL](https://apis.map.kakao.com/web/guide/#routeurl)로 제공 된다.     

## 요구사항 분석 

- 주변 약국 추천 및 길 안내 서비스 요구사항
  - 약국 현황 데이터는 위도 경도의 위치 정보 데이터를 가지고 있다.
  - 해당 서비스로 주소 정보를 입력하여 요청하면 위치 기준에서 가까운 약국 3 곳을 추출 한다.
  - 주소는 도로명 주소 또는 지번을 입력하여 요청 받는다.
    - 정확한 주소를 입력 받기 위해 [Kakao 우편번호 서비스](https://postcode.map.daum.net/guide) 사용   
  - 주소는 정확한 상세 주소(동, 호수)를 제외한 주소 정보를 이용하여 추천 한다.   
  - 입력 받은 주소를 위도, 경도로 변환 하여 기존 약국 데이터와 비교 및 가까운 약국을 찾는다.   
    - 지구는 평면이 아니기 때문에, 구면에서 두 점 사이의 최단 거리 구하는 공식이 필요    
    - 두 위 경도 좌표 사이의 거리를 [haversine formula](https://en.wikipedia.org/wiki/Haversine_formula)로 계산  
  - 입력한 주소 정보에서 정해진 반경(10km) 내에 있는 약국만 추천 한다.   
  - 추출한 약국 데이터는 길안내 URL 및 로드뷰 URL로 제공 한다.   
  - 길안내 URL은 고객에게 제공 되기 때문에 가독성을 위해 shorten url로 제공 한다.
  - shorten url에 사용 되는 key값은 인코딩하여 제공 한다.
    - base62를 통한 인코딩    
  - shorten url의 유효 기간은 30일로 제한 한다.   

## Feature List   

- Spring Data JPA를 이용한 CRUD 메서드 구현하기      
- 테스트 코드 작성하기     
- Testcontainers를 이용하여 독립 테스트 환경 구축하기
- 카카오 주소검색 API 연동하여 주소를 위도, 경도로 변환하기   
- 추천 결과를 카카오 지도 URL로 연동하여 제공하기   
- 공공 데이터를 활용하여 개발하기 (약국 현황 데이터)
- Thymeleaf를 이용한 간단한 View 만들기   
- Docker를 사용하여 다중 컨테이너 애플리케이션 만들기   
- 애플리케이션을 클라우드 서비스에 배포하기   
- Spring retry를 이용한 재처리 구현하기 (카카오 API의 네트워크 오류 등에 대한 재처리)   
- base62를 이용한 shorten url 개발하기 (길안내 URL)   
- redis를 이용하여 성능 최적화하기   

## 기술 세부 스택 

- JDK 17
- Spring Boot 3.3.2
- Spring Data JPA
- Gradle
- Thymeleaf
- Lombok
- Docker
- AWS EC2
- Redis
- MySQL
- Testcontainers   

## 결과 화면  

- 주소 입력 화면
![image](https://github.com/user-attachments/assets/a72febe8-bb6a-4fd1-800b-df208823ae37)
![image](https://github.com/user-attachments/assets/2306501d-ade5-43fc-b731-bc92abd0b76c)

- 검색 결과 화면
![image](https://github.com/user-attachments/assets/ef0748b7-852a-40ca-8e9a-6650a1a0f771)

- 길안내 및 로드뷰는 카카오 API 이용한 화면으로 구성됨
