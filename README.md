# pharmacy-recommendation
외부 API([카카오 주소 검색 API](https://developers.kakao.com/docs/latest/ko/local/dev-guide))와 공공 데이터([약국 현황 정보](https://www.data.go.kr/data/15115622/fileData.do#))를 활용하여 가까운 약국 찾기 서비스를 만드는 프로젝트.  
패스트 캠퍼스 강의를 보고 공부하는 목적의 프로젝트이다.

- 요구사항
  - 약국 현황 데이터는 위도, 경도의 위치 정보 데이터를 가지고 있다.
  - 주소 정보를 입력하면 해당 위치에서 가장 가까운 약국 3곳을 추출한다.
  - 주소는 도로명 주소 또는 지번을 입력 받는다.
    - 정확한 주소 입력을 위해 [카카오 우편번호 서비스](https://postcode.map.daum.net/guide)를 사용
  - 주소는 정확한 상세 주소를 제외한 주소 정보를 이용하여 추천한다.
    - ex) 서울 강서구 마곡동로2길
  - 입력 받은 주소를 위도, 경도로 변환하여 기존 약국 데이터와 비교 및 가까운 약국을 찾는다.
    - 지구는 평면이 아니기 때문에 구면에서 두 점 사이의 최단 거리를 구하는 공식 필요
    - 두 좌표 사이의 거리를 [Haversine fomula](https://en.wikipedia.org/wiki/Haversine_formula)로 계산
    - 지구가 완벽한 구가 아니므로 오차가 있다.
  - 입력 받은 주소 기준으로 반경 10km 내에 있는 약국만 추천한다.
  - 추출한 약국 데이터는 길안내 URL 및 로드뷰 URL로 제공한다.
  - 길안내 URL은 고객에게 제공되기 때문에 가독성을 위해 shorten url로 제공한다.
  - shorten url에 사용되는 key값은 base62로 인코딩하여 제공한다.
    - 유효 기간은 30일로 제한 한다.

