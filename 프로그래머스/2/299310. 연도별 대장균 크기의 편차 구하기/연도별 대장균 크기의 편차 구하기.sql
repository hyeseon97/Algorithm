-- 코드를 작성해주세요
SELECT T.YEAR, (T.MAX - E.SIZE_OF_COLONY) AS YEAR_DEV, E.ID
FROM ECOLI_DATA E
INNER JOIN (SELECT YEAR(DIFFERENTIATION_DATE) AS YEAR, MAX(SIZE_OF_COLONY) AS MAX
            FROM ECOLI_DATA
            GROUP BY YEAR(DIFFERENTIATION_DATE)) T
            ON YEAR(E.DIFFERENTIATION_DATE) = T.YEAR
ORDER BY T.YEAR, YEAR_DEV


