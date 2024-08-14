<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<script src="https://code.jquery.com/jquery-3.7.0.min.js"
			integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>


		<title>대기오염 공공데이터</title>
	</head>

	<style>
		#btn2 {
			padding: 20px 30px;
			border: none;
			border-radius: 30px;
			font-size: 16px;
		}

		#btn2:hover {
			background-color: rgb(214, 231, 235);
			cursor: pointer;
		}

		.ts-table {
			text-align: center;
			background-color: rgb(131, 169, 202);
			color: aliceblue;
			width: 1000px;
			height: 400px;
			margin: 0px auto;
			padding: 70px 30px;
			background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
			background-size: 400% 400%;
			animation: gradient 15s ease infinite;
		}

		@keyframes gradient {
			0% {
				background-position: 0% 50%;
			}

			50% {
				background-position: 100% 50%;
			}

			100% {
				background-position: 0% 50%;
			}
		}

		.ts-tbody {
			background: rgb(240, 248, 255);
			color: rgb(48, 48, 48);
			border: none;

		}
	</style>

	<body>
		<h1>실시간 대기오염 정보</h1>

		지역 :
		<select id="location">
			<option>서울</option>
			<option>부산</option>
			<option>대구</option>
		</select>

		<button id="btn1">해당 지역 대기오염 정보</button>

		<!-- <table id="result1" border="1"> -->
		<table id = "result1" border = "1">
			<thead>
				<tr>
					<th>측정소명</th>

					<th>측정일시</th>

					<th>통합대기환경수치</th>

					<th>미세먼지농도</th>

					<th>일산화탄소농도</th>

					<th>이산화질소농도</th>

					<th>아황산가스농도</th>

					<th>오존농도</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>

		<hr>

		<h1>실시간 지진해일 긴급대피장소</h1>

		<button id="btn2">실시간 지진해일 대피소 정보</button>
		<br><br>

		<table border="1" id="result2" class="ts-table">
			<thead>
				<tr>
					<th>시도명</th>
					<th>시군구명</th>
					<th>대피지구명</th>
					<th>대피장소명</th>
					<th>주소</th>
					<th>경도</th>
					<th>위도</th>
					<th>수용가능인원수</th>
					<th>대피소 분류명</th>
				</tr>
			</thead>
			<tbody class="ts-tbody">

			</tbody>
		</table>


		<!-- <h1>한국전력거래소_계통한계가격 및 수요예측</h1>

		<button id="btn3">한국전력거래소 정보</button>
		<br><br>

		<table border="1" id="result3">
			<thead>
				<tr>
					<th>일시</th>
					<th>시간</th>
					<th>지역</th>
					<th>계통한계가격</th>
					<th>육지 예측수요</th>
					<th>제주 예측수요</th>
					<th>총 예측수요</th>
					<th>순번</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table> -->

		<h1>지진해일 긴급대피장소</h1>

		<button id="btn4">긴급대피장소 정보</button>
		<br><br>

		<table border="1" id="result4">
			<thead>
				<tr>
					<th>시도명</th>
					<th>시군구명</th>
					<th>대피지구명</th>
					<th>대피장소명</th>
					<th>주소</th>
					<th>경도</th>
					<th>위도</th>
					<th>수용가능인원수</th>
					<th>대피소 분류명</th>
				</tr>
			</thead>
			<tbody class="ts-tbody">

			</tbody>
		</table>


		<script>
			$("#btn1").click(function () {
					$.ajax({ 
					url: "air.do", // 주소는 내 마음대로 정할 수 있다
					data: { location: $("#location").val() },
					success: function (result) {

						// console.log(result.response.body.items);
						const itemArr = result.response.body.items;
						let value = "";
						for (let item of itemArr) {
							value += "<tr>"
								+ "<td>" + item.stationName + "</td>"
								+ "<td>" + item.dataTime + "</td>"
								+ "<td>" + item.khaiValue + "</td>"
								+ "<td>" + item.pm10Value + "</td>"
								+ "<td>" + item.coValue + "</td>"
								+ "<td>" + item.no2Value + "</td>"
								+ "<td>" + item.so2Value + "</td>"
								+ "<td>" + item.o3Value + "</td>"
							+ "</tr>"
						}
						$("#result1 > tbody").html(value);
								
					},
					error: function () { console.log("통신 실패") }
				})

				// -----------------------------------------

				// 응답 데이터를 xml 형식으로 받을 때

				// $.ajax({
				// 	url: "air.do", // 주소는 내 마음대로 정할 수 있다
				// 	data: { location: $("#location").val() },
				// 	success: function (result) {
				// 		// console.log(result);

				// 		// $('요소명').find(매개변수)
				// 		// - 기준이 되는 요소의 하위 요소들 중 특정 요소를 찾을 때 사용
				// 		// - html, xml은 같은 markup language이기 때문에 사용 가능하다
				// 		// console.log($(result).find("item"))D

				// 		// xml 형식의 응답데이터를 받았을 때
				// 		// 1. 넘겨받은 데이터를 $() 제이쿼리화 시킨 후
				// 		// 응답데이터 안에 실제 데이터가 담겨있는 요소 선택
				// 		const itemArr = $(result).find("item");
				// 		let value = "";
				// 		// 2. 반복문을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들면 된다
				// 		itemArr.each(function (index, item) {
				// 			// console.log(index + "번째 : " + item)

				// 			// console.log($(item).find("stationName").text())
				// 			value += "<tr>"
				// 				+ "<td>" + $(item).find("stationName").text() + "</td>"
				// 				+ "<td>" + $(item).find("dataTime").text() + "</td>"
				// 				+ "<td>" + $(item).find("khaiValue").text() + "</td>"
				// 				+ "<td>" + $(item).find("pm10Value").text() + "</td>"
				// 				+ "<td>" + $(item).find("coValue").text() + "</td>"
				// 				+ "<td>" + $(item).find("no2Value").text() + "</td>"
				// 				+ "<td>" + $(item).find("so2Value").text() + "</td>"
				// 				+ "<td>" + $(item).find("o3Value").text() + "</td>"
				// 				+ "</tr>"
				// 		})

				// 		// 3. 동적으로 만들어낸 요소를 화면에 출력
				// 		$('#result1 > tbody').html(value);

				// 	},

				// 	error: function () {
				// 		console.log("통신 실패");
				// 	}

				// }) // ajax 끝

			})

			// $("#btn2").click(function () {
			// 	// 응답 데이터를 xml 형식으로 받을 때
			// 	// https://www.data.go.kr/data/3058512/openapi.do
			// 	$.ajax({
			// 		url: "tsunamiShelter", // 주소는 내 마음대로 정할 수 있다
			// 		success: function (result) {
			// 			console.log(result);
			// 			// console.log($(row).find("sido_name").text())


			// 			const tsunamiShelterArr = $(result).find("row");
			// 			let value = "";
			// 			// 2. 반복문을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들면 된다
			// 			tsunamiShelterArr.each(function (index, row) {
			// 				value += "<tr>"

			// 					+ "<td>" + $(row).find("sido_name").text() + "</td>"
			// 					+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
			// 					+ "<td>" + $(row).find("remarks").text() + "</td>"
			// 					+ "<td>" + $(row).find("shel_nm").text() + "</td>"
			// 					+ "<td>" + $(row).find("address").text() + "</td>"
			// 					+ "<td>" + $(row).find("lon").text() + "</td>"
			// 					+ "<td>" + $(row).find("lat").text() + "</td>"
			// 					+ "<td>" + $(row).find("shel_av").text() + "</td>"
			// 					+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
			// 					+ "</tr>"
			// 			})

			// 			// 3. 동적으로 만들어낸 요소를 화면에 출력
			// 			$('#result2 > tbody').html(value);

			// 		},

			// 		error: function () {
			// 			console.log("통신 실패");
			// 		}

			// 	})
			// })


			$("#btn2").click(function () {
				// 응답 데이터를 json 형식으로 받을 때
				// https://www.data.go.kr/data/3058512/openapi.do
				$.ajax({
					url: "tsunamiShelter", // 주소는 내 마음대로 정할 수 있다
					success: function (result) {
						console.log(result);
						// console.log($(row).find("sido_name").text())


						const rowArr = result.TsunamiShelter[1].row;

						console.log(rowArr)
						let value = "";
						// 2. 반복문을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들면 된다
						for (let row of rowArr) {
							value += "<tr>"

								+ "<td>" + row.sido_name + "</td>"
								+ "<td>" + row.sigungu_name + "</td>"
								+ "<td>" + row.remarks + "</td>"
								+ "<td>" + row.shel_nm + "</td>"
								+ "<td>" + row.address + "</td>"
								+ "<td>" + row.lon + "</td>"
								+ "<td>" + row.lat + "</td>"
								+ "<td>" + row.shel_av + "</td>"
								+ "<td>" + row.shel_div_type + "</td>"
								+ "</tr>"
						}

						// 3. 동적으로 만들어낸 요소를 화면에 출력
						$('#result2 > tbody').html(value);

					},

					error: function () {
						console.log("통신 실패");
					}

				})
			})


			// document.getElementById("btn2").addEventListener("click", function () {
			// 	fetch("tsunamiShelter")
			// 		.then(response => {
			// 			if (!response.ok) {
			// 				throw new Error("에러 발생");
			// 			}
			// 			return response.text();
			// 		})
			// 		.then(result => {
			// 			// 결과를 jQuery 없이 처리합니다.
			// 			const parser = new DOMParser();
			// 			const xmlDoc = parser.parseFromString(result, "text/xml");
			// 			const tsunamiShelterArr = xmlDoc.getElementsByTagName("row");
			// 			let value = "";

			// 			// 반복문을 통해 데이터 요소들에 접근합니다.
			// 			for (let i = 0; i < tsunamiShelterArr.length; i++) {
			// 				const row = tsunamiShelterArr[i];
			// 				value += "<tr>"
			// 					+ "<td>" + row.getElementsByTagName("sido_name")[0].textContent + "</td>"
			// 					+ "<td>" + row.getElementsByTagName("sigungu_name")[0].textContent + "</td>"
			// 					+ "<td>" + row.getElementsByTagName("remarks")[0].textContent + "</td>"
			// 					+ "<td>" + row.getElementsByTagName("shel_nm")[0].textContent + "</td>"
			// 					+ "<td>" + row.getElementsByTagName("address")[0].textContent + "</td>"
			// 					+ "<td>" + row.getElementsByTagName("lon")[0].textContent + "</td>"
			// 					+ "<td>" + row.getElementsByTagName("lat")[0].textContent + "</td>"
			// 					+ "<td>" + row.getElementsByTagName("shel_av")[0].textContent + "</td>"
			// 					+ "<td>" + row.getElementsByTagName("shel_div_type")[0].textContent + "</td>"
			// 					+ "</tr>";
			// 			}

			// 			// 동적으로 만들어낸 요소를 화면에 출력합니다.
			// 			document.querySelector('#result2 > tbody').innerHTML = value;
			// 		})
			// 		.catch(error => {
			// 			// 오류가 발생한 경우 콘솔에 로그를 남깁니다.
			// 			console.error("통신 실패", error);
			// 		});
			// });

			// xml로 풀어보기
			// https://www.data.go.kr/data/15131225/openapi.do#tab_layer_detail_function
			// $("#btn3").click(function () {
			// 	$.ajax({
			// 		url: "smpPredict", // 주소는 내 마음대로 정할 수 있다
			// 		success: function (result) {
			// 			console.log(result);
			// 			// console.log($(row).find("sido_name").text())


			// 			const smpPredictArr = $(result).find("item");
			// 			let value = "";
			// 			smpPredictArr.each(function (index, item) {
			// 				value += "<tr>"
			// 					+ "<td>" + $(item).find("date").text() + "</td>"
			// 					+ "<td>" + $(item).find("hour").text() + "</td>"
			// 					+ "<td>" + $(item).find("areaName").text() + "</td>"
			// 					+ "<td>" + $(item).find("smp").text() + "</td>"
			// 					+ "<td>" + $(item).find("mlfd").text() + "</td>"
			// 					+ "<td>" + $(item).find("jlfd").text() + "</td>"
			// 					+ "<td>" + $(item).find("slfd").text() + "</td>"
			// 					+ "<td>" + $(item).find("rn").text() + "</td>"
			// 					+ "</tr>"

			// 			})
			// 			$('#result3 > tbody').html(value);
			// 		},
			// 		error: function () {
			// 			console.log("통신 실패");
			// 		}

			// 	})

			// })

			$("#btn3").click(function () {
				$.ajax({
					url: "smpPredict", // 주소는 내 마음대로 정할 수 있다
					success: function (result) {
						console.log(result);

						const smpPredictArr = result.response.body.items;
						console.log(result.response.body.items);
						let value = "";
						for (let item of smpPredictArr) {
							value += "<tr>"
								+ "<td>" + item.date + "</td>"
								+ "<td>" + item.hour + "</td>"
								+ "<td>" + item.areaName + "</td>"
								+ "<td>" + item.smp + "</td>"
								+ "<td>" + item.mlfd + "</td>"
								+ "<td>" + item.jlfd + "</td>"
								+ "<td>" + item.slfd + "</td>"
								+ "<td>" + item.rn + "</td>"
								+ "</tr>"

						}
						$('#result3 > tbody').html(value);
					},
					error: function () {
						console.log("통신 실패");
					}

				})

			})


		</script>


	</body>

	</html>