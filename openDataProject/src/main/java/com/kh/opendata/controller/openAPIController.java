package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class openAPIController {

	// 발급 받은 인증키
	public static final String SERVICEKEY = "OaxW9s0B8sunmEZRkpKfOk0W2cx5cBqW1J%2Bhil7QGj19htUdBm7N9iwj3uAcSuq6OZSfBGf%2BwluGeTkAuyogsw%3D%3D";

	// json 형식으로 대기오염 OpenAPI 활용
	// @RequestMapping(value="air", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String airPollution(String location) throws IOException {
		// 서울, 부산, 대구 중 하나가 담길 것

		// OpenAPI 서버로 요청하고자 하는 url 작성
		// 시도별 실시간 측정정보 조회(서비스키, 시도명 필수)
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICEKEY; // 서비스키가 제대로 부여되지 않았을 경우 -> SERVICE_KEY_IS_NOT_REGISTERED_ERROR
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8"); // 지역명 추가(한글이 들어가면 encoding 처리 해줘야한다)

		// 여기서부턴 필수가 아닌, 옵션
		url += "&returnType=json";
		url += "numOfRows=100";

		// 1.요청할주소를전달해서java.net.URL 객체생성하기
		URL requestUrl = new URL(url);

		// 2.생성된URL 객체를가지고HttpUrlConnection객체얻어내기
		HttpURLConnection urlConn = (HttpURLConnection) requestUrl.openConnection();

		// 3.요청시필요한Header 설정하기
		urlConn.setRequestMethod("GET");

		// 4.해당OpenAPI서버로요청보낸후입력스트림을통해응답데이터받기
		// 하나하나가 아니라 한줄로 읽기 위해서
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

		// 나는 한줄한줄이 아니라 한문자열로 하고 싶으면
		String line;
		String responseText = "";

		while ((line = br.readLine()) != null) {
			responseText += line;
		}
		// 5.다사용한스트림객체반납하기
		br.close();
		urlConn.disconnect();

		return responseText;

	}

	// xml 형식으로 대기오염 OpenAPI 활용
	@RequestMapping(value = "air", produces = "text/xml; charset=UTF-8")
	@ResponseBody
	public String airPollutionXML(String location) throws IOException {
		// 서울, 부산, 대구 중 하나가 담길 것

		// OpenAPI 서버로 요청하고자 하는 url 작성
		// 시도별 실시간 측정정보 조회(서비스키, 시도명 필수)
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICEKEY; // 서비스키가 제대로 부여되지 않았을 경우 -> SERVICE_KEY_IS_NOT_REGISTERED_ERROR
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8"); // 지역명 추가(한글이 들어가면 encoding 처리 해줘야한다)

		// 여기서부턴 필수가 아닌, 옵션
		url += "&returnType=xml";
		url += "numOfRows=100";

		// 1.요청할주소를전달해서java.net.URL 객체생성하기
		URL requestUrl = new URL(url);

		// 2.생성된URL 객체를가지고HttpUrlConnection객체얻어내기
		HttpURLConnection urlConn = (HttpURLConnection) requestUrl.openConnection();

		// 3.요청시필요한Header 설정하기
		urlConn.setRequestMethod("GET");

		// 4.해당OpenAPI서버로요청보낸후입력스트림을통해응답데이터받기
		// 하나하나가 아니라 한줄로 읽기 위해서
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

		// 나는 한줄한줄이 아니라 한문자열로 하고 싶으면
		String line;
		String responseText = "";

		while ((line = br.readLine()) != null) {
			responseText += line;
		}
		// 5.다사용한스트림객체반납하기
		br.close();
		urlConn.disconnect();

		return responseText;

	}
	
	// xml 형식으로 지진해일 대피 OpenAPI 활용
	@RequestMapping(value = "tsunamiShelter", produces = "text/xml; charset=UTF-8")
	@ResponseBody
	public String tsunamiShelterXML() throws IOException {
		// 긴급대피장소 장소가 담길 것

		// OpenAPI 서버로 요청하고자 하는 url 작성
		// 시도별 실시간 측정정보 조회(서비스키, 시도명 필수)
		String url = "http://apis.data.go.kr/1741000/TsunamiShelter4/getTsunamiShelter4List";
		

		// 필수로 뜸
		url += "?ServiceKey=" + SERVICEKEY;
		url += "&pageNo=";
		url += "&numOfRows=10";
		url += "&type=xml";
		
		// 옵션
		// url += "&sido_name=" + URLEncoder.encode(location, "UTF-8"); // 지역명 추가(한글이 들어가면 encoding 처리 해줘야한다)

		

		// 1.요청할주소를전달해서java.net.URL 객체생성하기
		URL requestUrl = new URL(url);

		// 2.생성된URL 객체를가지고HttpUrlConnection객체얻어내기
		HttpURLConnection urlConn = (HttpURLConnection) requestUrl.openConnection();

		// 3.요청시필요한Header 설정하기
		urlConn.setRequestMethod("GET");

		// 4.해당OpenAPI서버로요청보낸후입력스트림을통해응답데이터받기
		// 하나하나가 아니라 한줄로 읽기 위해서
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

		// 나는 한줄한줄이 아니라 한문자열로 하고 싶으면
		String line;
		String responseText = "";

		while ((line = br.readLine()) != null) {
			responseText += line;
		}
		// 5.다사용한스트림객체반납하기
		br.close();
		urlConn.disconnect();

		System.out.println("responseText1 : " + responseText);
		return responseText;

	}
		
	// xml 형식으로 계통한계가격 OpenAPI 활용
	@RequestMapping(value = "smpPredict", produces = "text/xml; charset=UTF-8")
	@ResponseBody
	public String smpPredictXML() throws IOException {
		
		String url = "http://apis.data.go.kr/B552115/SmpWithForecastDemand/getSmpWithForecastDemand";
		

		// 필수로 뜸
		url += "?serviceKey=" + SERVICEKEY;
		url += "&pageNo=10";
		url += "&numOfRows=5";
		url += "&dataType=xml";
		
		

		// 1.요청할주소를전달해서java.net.URL 객체생성하기
		URL requestUrl = new URL(url);

		// 2.생성된URL 객체를가지고HttpUrlConnection객체얻어내기
		HttpURLConnection urlConn = (HttpURLConnection) requestUrl.openConnection();

		// 3.요청시필요한Header 설정하기
		urlConn.setRequestMethod("GET");

		// 4.해당OpenAPI서버로요청보낸후입력스트림을통해응답데이터받기
		// 하나하나가 아니라 한줄로 읽기 위해서
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

		// 나는 한줄한줄이 아니라 한문자열로 하고 싶으면
		String line;
		String responseText = "";

		while ((line = br.readLine()) != null) {
			responseText += line;
		}
		// 5.다사용한스트림객체반납하기
		br.close();
		urlConn.disconnect();

		System.out.println("responseText2 : " + responseText);
		return responseText;

	}
	
}
