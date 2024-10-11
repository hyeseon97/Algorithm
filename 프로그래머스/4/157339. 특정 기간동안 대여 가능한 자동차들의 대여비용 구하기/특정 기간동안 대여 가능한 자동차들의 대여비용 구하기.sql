-- 코드를 입력하세요
SELECT DISTINCT(C.CAR_ID), C.CAR_TYPE, ROUND((C.DAILY_FEE * 30 * (100 - P.DISCOUNT_RATE) / 100 ), 0) AS FEE
FROM CAR_RENTAL_COMPANY_CAR C
INNER JOIN CAR_RENTAL_COMPANY_RENTAL_HISTORY H ON C.CAR_ID = H.CAR_ID
INNER JOIN CAR_RENTAL_COMPANY_DISCOUNT_PLAN P ON C.CAR_TYPE = P.CAR_TYPE AND P.DURATION_TYPE = '30일 이상'
WHERE (C.CAR_TYPE = '세단' OR C.CAR_TYPE = 'SUV')
      AND ROUND((C.DAILY_FEE * 30 * (100 - P.DISCOUNT_RATE) / 100 ), 0) BETWEEN 500000 AND 2000000
      AND C.CAR_ID NOT IN (SELECT CAR_ID FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY WHERE START_DATE <= '2022-11-30' AND END_DATE >= '2022-11-01')
ORDER BY FEE DESC, C.CAR_TYPE, C.CAR_ID DESC