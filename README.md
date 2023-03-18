# java-filmorate

## База данных
### Cхема
- films
  - У одного фильма может быть  много жанров, поэтому связь many-to-many.
  Так же, много человек могут поставить лайк фильму.
  - Один рейтинг может быть у нескольких фильмов, но у фильма может быть только одни рейтинг, следовательно, one-to-many.
- user
    - Пользователь может отправить много запросов в друзья, так же, ему могут отправить много запросов, 
  поэтому таблица users имеет отношение many-to-many сама с собой.
    - Когда пользователь с id=1 отправляет запрос на дружбу пользователю с id=2, создаются две записи:
      1) `1,2,requested`
      2) `2,1,received`
    - Когда 2 принимает предложение статусы меняются на `confirmed`
      1) `1,2,confirmed`
      2) `2,1,confirmed`
![filmorate_db](https://user-images.githubusercontent.com/106385986/226096004-f3acab75-e846-4938-bf89-d451e06b2f6f.png)
### Запросы
#### Получить фильм по id
    SELECT *
    FROM films
    WHERE id = &id
### Получить топ n самых популярных фильмов
    SELECT *
    FROM films
    WHERE film_id IN (SELECT film_id
                      FROM likes
                      GROUP BY film_id
                      ORDER BY COUNT(film_id) DESC
                      LIMIT &n)
### Получить пользователя по id
    SELECT *
    FROM users
    WHERE user_id = &id
### Получить фильмы, которые пользователь лайкнул
    SELECT *
    FROM films
    WHERE film_id IN (SELECT DISTINCT film_id
                      FROM likes
                      WHERE user_id = &id)
### Получить друзей пользователя
    SELECT *
    FROM users
    WHERE user_id IN (SELECT f.friend_id
                      FROM friendship AS f
                      INNER JOIN friendship_status AS fs ON f.status_id = fs.friendship_status_id
                      WHERE f.user_id = &id AND status = confirmed)
### Получить тех, кто отправил запрос дружбы пользователю
    SELECT *
    FROM users
    WHERE user_id IN (SELECT f.friend_id
                      FROM friendship AS f
                      INNER JOIN friendship_status AS fs ON f.status_id = fs.friendship_status_id
                      WHERE f.user_id = &id AND status = recieved)
### Получить тех, кому пользователь отправил запрос
    SELECT *
    FROM users
    WHERE user_id IN (SELECT f.friend_id
                      FROM friendship AS f
                      INNER JOIN friendship_status AS fs ON f.status_id = fs.friendship_status_id
                      WHERE f.user_id = &id AND status = requested)
