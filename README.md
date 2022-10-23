## 기본 준비

- `node` 및 `visual studio` 설치
- `artillery.io` : [`https://www.artillery.io/docs`](https://www.artillery.io/docs)
- `npm install -g artillery`

## 스크립트

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

## Test

- 실행 :  `artillery.cmd run --output report.json ./cpu-test.yaml`
- html : `artillery.cmd report ./report.json`