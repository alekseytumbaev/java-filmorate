drop table if exists FRIENDSHIP cascade;

drop table if exists FRIENDSHIP_STATUSES cascade;

drop table if exists GENRE_FILMS cascade;

drop table if exists GENRES cascade;

drop table if exists LIKES cascade;

drop table if exists FILMS cascade;

drop table if exists MOTION_PICTURE_ASSOCIATIONS cascade;

drop table if exists USERS cascade;



CREATE TABLE IF NOT EXISTS genres(
    genre_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS motion_picture_associations(
    mpa_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    mpa_name varchar(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS films(
    film_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_name varchar(200) NOT NULL,
    mpa_id BIGINT NOT NULL,
    description varchar(200),
    release_date timestamp NOT NULL,
    duration INTEGER NOT NULL,
    CONSTRAINT fk_film_mpa_id
        FOREIGN KEY(mpa_id)
        REFERENCES motion_picture_associations(mpa_id)
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS genre_films(
    genre_film_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id BIGINT,
    genre_id BIGINT,
    CONSTRAINT fk_genre_film_film_id
        FOREIGN KEY(film_id)
        REFERENCES films(film_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_genre_film_genre_id
        FOREIGN KEY(genre_id)
        REFERENCES genres(genre_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email varchar(255) NOT NULL,
    login varchar(100) NOT NULL,
    name varchar(150) NOT NULL,
    birthday timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS likes(
    like_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id BIGINT,
    user_id BIGINT,
    CONSTRAINT fk_like_user_id
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friendship_statuses(
    friendship_status_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status varchar(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS friendship(
    friendship_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id BIGINT,
    friend_id BIGINT,
    status_id BIGINT,
    CONSTRAINT fk_friendship_user_id
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_friendship_friend_id
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_friendship_status_id
        FOREIGN KEY(status_id)
        REFERENCES friendship_statuses(friendship_status_id)
        ON DELETE RESTRICT
);