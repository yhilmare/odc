delete from `regulation_riskdetect_rule` where `risk_level_id` in (select `id` from `regulation_risklevel` where `level` = 0);