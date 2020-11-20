# jnang-web  
Share : RGJ(sw1123r)  

0. JDK-1.8.0_251, Apache Tomcat v9.0 사용함  
     `src/main/webapp/WEB-INF/lib` 폴더 아래 jar파일 BuildPath 등록 후 사용  
1. `lombok.jar` 사용함  
     `java -jar lombok-1.18.12.jar` 이클립스에 인스톨 후 개발/빌드 사용 해야 함  
2. `mariadb-java-client-2.6.1.jar` 에서 오류가 발생되어 `mariadb-java-client-2.4.1.jar` 버전으로 다운하여 사용함  
3. 기상청 동네예보 api 변경으로 인한 새 api 적용  
 3-1. 동네예보 key 필요  (발급 URL : `https://www.data.go.kr/data/15057682/openapi.do`)  
 3-2. 대기정보 key 필요  (발급 URL : ?)  
4. 동네예보 아이콘 기상청 아이콘으로 적용  
5. 기상청 동네예보 API 에서 간헐적으로 오류가 발생 중  
 5-1. key 요청 한도 초과 오류..  
 5-2. api 리턴값이 근본없이 바뀜...  
6. RESTful 관련  
 6.1. Tomcat Server의 server.xml에 해당 내용 추가해서 수정  
          `<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="18443" parseBodyMethods="POST,PATCH,DELETE"/>`  
7. Tomcat Server Session이 공유되는 문제가 있음  
 7.1. Tomcat > server.xml > Context > sessionCookieName="Session name" 추가  
          `<Context docBase="jnang-web" sessionCookieName="JNANG_WEB_JSESSION"/>`  
8. 구글 애널리틱스 사용  
 8.1. 중랑구시설관리공단에서 SiteTag 발급 후 회신하면 해당 내용을 아래 jsp에 적용   
          `mainTemplate.jsp` Line 6 ~ Line 13 수정  
          `template.jsp Line` 5~Line 13 수정  
          `noneTemplate.jsp` Line 6 ~ Line 13 수정  
9. 배포방법  
 9.1. 최신 소스로 update  
 9.2. `src/main/resources/context/root-context.xml` Line 18 주석처리, Line 19 주석 해제  
 9.3. `jnang-web` 프로젝트 마우스 우클릭 후 Export... > Web > War File  
 9.4. 배포하고자 하는 원격지로 Putty 접속  
 9.5. 원격지의 Tomcat shutdown  
      `cd /usr/local/apache-tomcat-9.0.37-web/bin`, `./shutdown.sh`  
 9.6. 원격지 워크스페이스 파일질라, winscp 등 툴 이용해서 백업 후 삭제  
      (워크스페이스 경로 : /usr/local/apache-tomcat-9.0.37-web/webapps )  
 9.7. war 파일 복사  
 9.8. 원격지의 Tomcat start  
      `./startup.sh`  
10. 실서버 배포 시 `src/main/resources/log4j2.xml` log Level 을 `info`로 변경  
11. 실서버 Tomcat log 쌓이는 양 조절 관련..  
    (2020.11.16 기준, 위를 적용한 사용자쪽 log 폴더는 45.7MB 적용하지 않은 관리자쪽 log 폴더는 2.10GB)  
 11.1. `/usr/local/apach-tomcat-9.0.37-web/bin/catalina.sh`  
 `if [ -z "$CATALINA_OUT" ] ; then`  
 `CATALINA_OUT="$CATALINA_BASE"/logs/catalina.out` 이 부분을 `CATALINA_OUT="/dev/null"` 이렇게 수정  
 `fi`  
