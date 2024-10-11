-- 코드를 입력하세요
SELECT M.MEMBER_NAME, R.REVIEW_TEXT, DATE_FORMAT(R.REVIEW_DATE, '%Y-%m-%d') AS REVIEW_DATE
FROM MEMBER_PROFILE M
INNER JOIN REST_REVIEW R ON M.MEMBER_ID = R.MEMBER_ID
WHERE M.MEMBER_ID IN (SELECT M.MEMBER_ID
                      FROM MEMBER_PROFILE M
                      INNER JOIN REST_REVIEW R ON M.MEMBER_ID = R.MEMBER_ID
                      GROUP BY M.MEMBER_ID
                      HAVING COUNT(*) = (SELECT COUNT(*)
                                         FROM MEMBER_PROFILE M
                                         INNER JOIN REST_REVIEW R ON M.MEMBER_ID = R.MEMBER_ID
                                         GROUP BY M.MEMBER_ID
                                         ORDER BY COUNT(*) DESC
                                         LIMIT 1))
ORDER BY R.REVIEW_DATE, R.REVIEW_TEXT
