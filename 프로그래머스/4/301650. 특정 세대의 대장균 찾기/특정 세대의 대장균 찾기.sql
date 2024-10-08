-- 코드를 작성해주세요
SELECT E3.ID
FROM ECOLI_DATA E3
    LEFT JOIN ECOLI_DATA E2 ON E3.PARENT_ID = E2.ID
    LEFT JOIN ECOLI_DATA E1 ON E2.PARENT_ID = E1.ID
WHERE E1.PARENT_ID IS NULL AND E1.ID IS NOT NULL
ORDER BY E3.ID