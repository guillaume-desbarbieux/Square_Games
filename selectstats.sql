## Temps passé sur l'ensemble des jeux pour Nora
SELECT sum(timediff(date_end, date_start)) / 3600
from matches
where id_matches in (select id_matches
                     from played
                     where id_user = (select id_user
                                      from user
                                      where pseudo = 'nora_b'));


## Proportion de victoires pour chaque joueur
SELECT pseudo,
       ROUND(SUM(IF(played.issue = 'win', 1, 0)) * 100.0 / COUNT(*),2) AS win_rate
FROM user
         JOIN played ON user.id_user = played.id_user
GROUP BY user.id_user, user.pseudo
ORDER BY win_rate DESC;

## Nombre de parties jouées pour chaque jeu les 30 derniers jours
SELECT name, count(*) as times_played_last_month from game
join matches on game.id_game = matches.id_game
                                                 where datediff(now(), date_end)<31
group by game.id_game
order by times_played_last_month desc ;