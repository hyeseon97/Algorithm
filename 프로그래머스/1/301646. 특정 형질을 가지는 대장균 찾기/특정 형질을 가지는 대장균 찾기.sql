-- 코드를 작성해주세요
SELECT COUNT(*) AS COUNT
FROM ECOLI_DATA E
WHERE ((E.GENOTYPE & (1<<0)) >0 OR (E.GENOTYPE & (1<<2)) >0) AND (E.GENOTYPE & (1<<1)) <= 0