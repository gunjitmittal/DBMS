.import --csv --skip 1 players.csv players
.import --csv --skip 1 teams.csv teams
.import --csv --skip 1 coaches_career.csv coaches_career
.import --csv --skip 1 coaches_season.csv coaches_season
.import --csv --skip 1 player_playoffs.csv player_playoffs
.import --csv --skip 1 player_playoffs_career.csv player_playoffs_career
.import --csv --skip 1 player_regular_season_career.csv player_regular_season_career
.import --csv --skip 1 player_regular_season.csv player_regular_season

update players set ilkid = upper(trim(ilkid));
update player_playoffs set ilkid = upper(trim(ilkid));
update player_playoffs_career set ilkid = upper(trim(ilkid));
update player_regular_season set ilkid = upper(trim(ilkid));
update player_regular_season_career set ilkid = upper(trim(ilkid));
update coaches_career set coachid = upper(trim(coachid));
update coaches_season set coachid = upper(trim(coachid));