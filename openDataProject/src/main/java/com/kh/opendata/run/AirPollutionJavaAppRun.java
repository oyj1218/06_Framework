package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.dto.Air;

public class AirPollutionJavaAppRun {
	
	// 발급 받은 인증키
	public static final String SERVICEKEY = "OaxW9s0B8sunmEZRkpKfOk0W2cx5cBqW1J%2Bhil7QGj19htUdBm7N9iwj3uAcSuq6OZSfBGf%2BwluGeTkAuyogsw%3D%3D";
	public static void main(String[] args) throws IOException {
		
		// OpenAPI 서버로 요청하고자 하는 url 작성
		// 시도별 실시간 측정정보 조회
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICEKEY; // 서비스키가 제대로 부여되지 않았을 경우 -> SERVICE_KEY_IS_NOT_REGISTERED_ERROR
		url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8");
		
		// 여기서부턴 필수가 아닌, 옵션
		url += "&returnType=json";
		
		// 확인
		// System.out.println(url);
		
		// ** HttpURLConnection 객체를 활용해서 OpenAPI 요청 절차 **
		// 1.요청할주소를전달해서java.net.URL 객체생성하기
		URL requestUrl = new URL(url);
		
		// 2.생성된URL 객체를가지고HttpUrlConnection객체얻어내기
		HttpURLConnection urlConn = (HttpURLConnection)requestUrl.openConnection(); 
		// 강제로 다운캐스팅 작업
		
		
		// 3.요청시필요한Header 설정하기
		urlConn.setRequestMethod("GET");
		
		// 4.해당OpenAPI서버로요청보낸후입력스트림을통해응답데이터받기
		// 한줄단위로 읽고 싶음 -> BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		// 보조스트림이라 단독으로 못 사용하고 기반스트림이 필요함
		// 기반스트림 : 입력스트림이 기반이 될 것임
		// => urlConn.getInputStream()
		
		// 둘의 차이? Input 1바이트, Reader 2바이트 
		// 안 맞음 근데 무조건 사용해야함 그럼? 둘을 연결하기 위해 InputStreamReader를 넣는다
		
		// 한 줄을 끝까지 읽어와야하고, 매번 저장해주기 위해서
		String line;
		

		// 나는 한줄한줄이 아니라 한문자열로 하고 싶으면
		String responseText = "";
		
		while( (line = br.readLine()) != null) { // 한 줄씩 읽어올 데이터가 존재하는 동안 반복
			// 읽어들이자마자 라인에 넣기
			// 근데 이건 조건식이 아닌데? true or false로 조건식을 넣어줘야하니깐
			// 끝까지 가서 더 이상 담을 것이 없다면 null값이 나오거니깐 () 감싸주고 != null, == null 사용으로 true, false 처리
			
			// System.out.println("line" + line);
			responseText += line;
		}
		//System.out.println(responseText);
		/*
	      {
	         "response":
	            {
	               "body":
	            {
	               "totalCount":40,
	               "items":
	                  [
	                      {
	                         "so2Grade":"1",
	                         "coFlag":null,
	                         "khaiValue":"52",
	                         "so2Value":"0.003",
	                         "coValue":"0.5",
	                         "pm10Flag":null,
	                         "o3Grade":"1",
	                         "pm10Value":"15",
	                         "khaiGrade":"2",
	                         "sidoName":"서울",
	                         "no2Flag":null,
	                         "no2Grade":"2",
	                         "o3Flag":null,
	                         "so2Flag":null,
	                         "dataTime":"2023-08-23 14:00",
	                         "coGrade":"1",
	                         "no2Value":"0.032",
	                         "stationName":"정릉로",
	                         "pm10Grade":"1",
	                         "o3Value":"0.029"
	                      },
	                      {
	                         "so2Grade":"1",
	                         "coFlag":null,
	                         "khaiValue":"-",
	                         "so2Value":"0.002",
	                         "coValue":"0.5",
	                         "pm10Flag":null,
	                         "o3Grade":"2",
	                         "pm10Value":"3",
	                         "khaiGrade":null,
	                         "sidoName":"서울",
	                         "no2Flag":null,
	                         "no2Grade":"1",
	                         "o3Flag":null,
	                         "so2Flag":null,
	                         "dataTime":"2023-08-23 14:00",
	                         "coGrade":"1",
	                         "no2Value":"0.013",
	                         "stationName":"도봉구",
	                         "pm10Grade":"1",
	                         "o3Value":"0.039"
	                         }, ...
	                      ]
	               }
	            }
	      }
	      */
		
		// JsonObject, JsonArray 이용해서 파싱할 수 있음(gson 라이브러리)
		
		// json 데이터에서 원하는 데이터만 추출해서 DTO에 담기
		
		// 응답데이터 text를 JsonObject화 시키는 작업
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		// System.out.println("total : " + totalObj);
		
		// response 속성 접근
		JsonObject responseObj = totalObj.getAsJsonObject("response");
		// System.out.println("responseObj : " + responseObj);
		
		// body 속성 접근
		JsonObject bodyObj = responseObj.getAsJsonObject("body");
		// System.out.println("SbodyObj : " + bodyObj);
		
		// totalCount 속성 접근
		int totalCount = bodyObj.get("totalCount").getAsInt();
		// System.out.println("totalCount : " + totalCount);
		
		// items(JsonArray형태) 속성 접근
		JsonArray itemArr = bodyObj.getAsJsonArray("items");
		// System.out.println("itemArr : " + itemArr);
		
		ArrayList<Air> list = new ArrayList<Air>();
		for(int i=0; i<itemArr.size(); i++) {
			JsonObject item = itemArr.get(i).getAsJsonObject();
			// System.out.println(item);
			
			Air air = new Air();
	         air.setStationName(item.get("stationName").getAsString());
	         air.setDataTime(item.get("dataTime").getAsString());
	         air.setKhaiValue(item.get("khaiValue").getAsString());
	         air.setPm10Value(item.get("pm10Value").getAsString());
	         air.setSo2Value(item.get("so2Value").getAsString());
	         air.setCoValue(item.get("coValue").getAsString());
	         air.setNo2Value(item.get("no2Value").getAsString());
	         air.setO3Value(item.get("o3Value").getAsString());
	         
	         list.add(air);
					
			
		}
		System.out.println("list : " + list);
		
		for(Air air : list) {
			System.out.println("air : " +air);
		}
		
		// 5.다사용한스트림객체반납하기
		br.close();
		urlConn.disconnect();
		
		
		
		
		
	}
}
