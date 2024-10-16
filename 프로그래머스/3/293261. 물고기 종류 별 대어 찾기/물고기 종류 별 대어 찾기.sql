-- 코드를 작성해주세요
# SELECT FN.FISH_NAME, MAX(F.LENGTH) AS LENGTH
# FROM FISH_INFO F INNER JOIN FISH_NAME_INFO FN ON F.FISH_TYPE = FN.FISH_TYPE
# GROUP BY FN.FISH_NAME
# ORDER BY F.ID

SELECT FI.ID, T.FISH_NAME, T.LENGTH
FROM (SELECT FN.FISH_TYPE, FN.FISH_NAME, MAX(F.LENGTH) AS LENGTH
      FROM FISH_INFO F INNER JOIN FISH_NAME_INFO FN ON F.FISH_TYPE = FN.FISH_TYPE
      GROUP BY FN.FISH_TYPE, FN.FISH_NAME) T
INNER JOIN FISH_INFO FI ON T.FISH_TYPE = FI.FISH_TYPE AND T.LENGTH = FI.LENGTH
ORDER BY FI.ID