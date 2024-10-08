-- 코드를 작성해주세요
SELECT INFO.ITEM_ID, INFO.ITEM_NAME, INFO.RARITY
FROM ITEM_INFO I INNER JOIN ITEM_TREE T ON I.ITEM_ID = T.PARENT_ITEM_ID
INNER JOIN ITEM_INFO INFO ON INFO.ITEM_ID = T.ITEM_ID
WHERE I.RARITY = 'RARE'
ORDER BY INFO.ITEM_ID DESC
