# 🖥 프로젝트 소개
### **서울시 공공 와이파이 API를 활용한 위치 기반 공공 와이파이 정보를 제공하는 웹서비스 데모 프로젝트**
<br>
<br>

### 기능 설명
- 사용자 위치 정보 기반 또는 원하는 위치를 기반으로 서울시 공공 와이파이 정보를 조회할 수 있습니다.
- 조회시 입력한 위치 기반으로 가까운 20개의 공공 와이파이 정보를 조회할 수 있습니다.
- 조회한 위치 정보를 조회할 수 있습니다.
- 북마크 그룹을 생성해 원하는 와이파이 정보를 저장할 수 있습니다.
- 생성된 북마크 그룹은 우선순위를 설정해 조회 및 북마크 그룹 정보 수정 및 삭제할 수 있습니다.
- 저장된 북마크를 삭제할 수 있습니다.
<br>
<br>

### 실행 화면
![GIFMaker_me](https://github.com/wooooozin/WifiProject/assets/95316662/ba80bf10-ae57-4bb7-a5d7-aebe5b5974bb)
<br>
<br>


# ⏱️ 개발 기간 및 사용 기술
- 개발 기간: 2023.06.25 ~ 2023.07.09
- 환경 및 라이브러리:  `eclips`, `datagrip`, `jsp`, `mariadb`, `tomcat`, `okhttp3`, `gson`
<br>
<br>

# 🦊 이슈
- 공공API 약 2만3천개 데이터 INSERT, UPDATE 속도 개선
  - 처음에 API를 호출한 후 json 데이터를 DB에 저장할 때 너무 오래 걸림
  - 조회된 위치 기반으로 distance 업데이트 시에도 너무 오래 걸림
  - Bulk insert/update 로 변경 
    ```java
    preparedStatement.addBatch();
    preparedStatement.executeBatch();
    ```

- Bulk Insert에 따른 auto_increment 값 id와 데이터 갯수 불일치
  - 초기 해결 : innodb_autoinc_lock_mode 0으로 변경 -> id 와 갯수 일치
  - 문제 발생 : 0으로 설정하면 모든 insert 를 대상으로 테이블 레벨의 AUTO_LOC 을 사용해 터미널로 접근시 에러 발생 및 insert 결과를 예측하여 순서를 보장하기 위해 구문마다 락을 걸어 성능 다소 저하
  - 최종 선택 : id 값으로 데이터에 접근하려 했기에 증가 횟수는 신경쓰지 않기로 하고 다시 락모드를 초기 설정인 1로 변경, wifi 데이터 중 관리번호를 유닉크 인덱스로 잡아 중복된 데이터가 추가 되지 않도록 변경
    ```java
    StringBuilder sqlBuilder = new StringBuilder();
	sqlBuilder.append("INSERT INTO wifi_info ");
	sqlBuilder.append("(mgr_no, wrdofc, main_nm, adress1, adress2, instl_floor, instl_ty, ");
	sqlBuilder.append("instl_mby, svc_se, cmcwr, cnstc_year, inout_door, remars3, lat, lnt, work_dttm) ");
	sqlBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
	sqlBuilder.append("ON DUPLICATE KEY UPDATE mgr_no = mgr_no");
    ```
<br>
<br>

# 😢 후기
처음 만들어 본 프로젝트인 만큼 ERD 설계부터 많은 어려움과 수정이 있었다.
만들어야 하는 테이블은 몇개인지 어떤 필드를 가지고 있어야 하는지 등등..
주어진 기획 또는 파일을 보고 어떻게 ERD를 효율적으로 설계해야 하는지에 대한 이해가 조금 생겼다.
<br>
<br>
자바만 공부하면 될 줄 알았는데 웹서비스를 구현한다는 것은 프론트에 대한 깊은 이해가 동반되어야 한다는 것을 느낄 수 있었다.
<br>
<br>
이번 프로젝트를 통해 DB, DBMS, SQL, Front, library 사용 등 전반적인 이해도를 높일 수 있었다. 
<br>
<br>
하지만 DB나 라이브러리 사용이 서툴러 초기 세팅과 잦은 ERD 설계로 많은 시간을 할애해 정작 기능 구현에는 많은 고민을 하지 못한 것이 아쉽고 시간이 지나 배움이 는다면 꼭 수정 및 보완할 예정이다.

