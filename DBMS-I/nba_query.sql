-- query 1
select position,count(firstname) from players group by position;

-- query 2
with sgp_playoff(year, sgp_playoff) as (select year,sum(gp) as sgp from player_playoffs  group by year order by sgp desc), sgp_regular(year, sgp_regular) as (select year,sum(gp) as sgp from player_regular_season  where team<>'TOT' group by year order by sgp desc) select T.year from sgp_playoff as T,sgp_regular as S where T.year=S.year order by T.sgp_playoff+S.sgp_regular desc limit 5;

-- query 3
alter TABLE player_regular_season_career add eff NUMERIC(6,0);
update player_regular_season_career set eff = (pts + reb + asts + stl + blk - ((fga - fgm) + (fta - ftm) + turnover));
with updated_table(ilkid,firstname,lastname,gp,eff) as (select ilkid,firstname,lastname,sum(gp),sum(eff) from player_regular_season_career group by ilkid,firstname,lastname)select * from updated_table where gp>500 order by eff desc limit 10;

-- query 4
with updated_wol(ilkid,year,leag,gp) as (select ilkid,year,leag,max(gp) from player_regular_season group by ilkid,year), updated_table (ilkid,year,gp) as (select ilkid,year,sum(gp) from updated_wol group by ilkid,year), gp_1990(ilkid,gp) as (select ilkid,gp from updated_table where year=1990) select count(ilkid) from gp_1990 as A where A.gp> ifnull((select max(B.gp) from updated_table as B where A.ilkid=B.ilkid and b.year<>1990),0);

-- query 5
with updated_table(ilkid,firstname,lastname,gp,eff) as (select ilkid,firstname,lastname,sum(gp),sum(eff) from player_regular_season_career group by ilkid,firstname,lastname) select distinct S.ilkid,S.firstname,S.lastname,S.gp,S.eff from updated_table as S where S.gp >(select max(gp) from updated_table as T where T.ilkid<>S.ilkid) or  S.eff >(select max(eff) from updated_table as T where T.ilkid<>S.ilkid) order by S.ilkid;