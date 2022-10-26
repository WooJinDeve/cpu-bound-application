## artillery.io

- `node` 및 `visual studio` 설치
- `artillery.io` : [`https://www.artillery.io/docs`](https://www.artillery.io/docs)
- `npm install -g artillery`

#### 스크립트
- `.yaml` 확장자로 작성

```jsx
config:
  target: "https://example.com/api"
  phases:
    - duration: 60
      arrivalRate: 5
      name: Warm up
    - duration: 120
      arrivalRate: 5
      rampTo: 50
      name: Ramp up load
    - duration: 600
      arrivalRate: 50
      name: Sustained load
  payload:
    path: "keywords.csv"
    fields:
      - "keyword"

scenarios:
  - name: "Search and buy"
    flow:
      - post:
          url: "/search"
          json:
            kw: "{{ keyword }}"
          capture:
            - json: "$.results[0].id"
              as: "productId"
      - get:
          url: "/product/{{ productId }}/details"
      - think: 5
      - post:
          url: "/cart"
          json:
            productId: "{{ productId }}"
```

#### Test

- 실행 :  `artillery.cmd run --output report.json ./cpu-test.yaml`
- html : `artillery.cmd report ./report.json`


## nginder 
- docker intsall 
``` docker
docker run -d -v ~/ngrinder-controller:/opt/ngrinder-controller --name controller -p 80:80 -p 16001:16001 -p 12000-12009:12000-12009 ngrinder/controller
docker run -d --name agent --link controller:controller ngrinder/agent
```

![image](https://user-images.githubusercontent.com/106054507/197990460-754a6666-661c-43be-a916-b9bf20bc26fe.png)

#### 스크립트
``` groovy
@RunWith(GrinderRunner)
class TestRunner {
	public static GTest test1
	public static HTTPRequest request
	public static Map<String, String> headers = [:]
	public static Map<String, Object> params = [:]
	public static List<Cookie> cookies = []

	@BeforeProcess
	public static void beforeProcess() {
		HTTPRequestControl.setConnectionTimeout(300000)
		
		test1 = new GTest(1, "GET /hash/1")
		
		request = new HTTPRequest()
		
		grinder.logger.info("before process.")
	}

	@BeforeThread
	public void beforeThread() {
		test1.record(this, "test1")
		
		grinder.statistics.delayReports = true
		grinder.logger.info("before thread.")
	}

	@Test
	public void test1() {
		HTTPResponse response = request.GET("http://localhost:8080/hash/1", params)
		if (response.statusCode == 301 || response.statusCode == 302) {
			grinder.logger.warn("Warning. The response may not be correct. The response code was {}.", response.statusCode)
		} else {
			assertThat(response.statusCode, is(200))
		}
	}
}
```
