package ru.yandex.practicum.filmorate.model.film;

/**
 * Рейтинг Ассоциации кинокомпаний определяет возрастное ограничение для фильма.
 * <p>
 * Значения могут быть следующими:
 * <ul>
 *     <li>G — у фильма нет возрастных ограничений,</li>
 *     <li>PG — детям рекомендуется смотреть фильм с родителями,</li>
 *     <li>PG-13 — детям до 13 лет просмотр не желателен,</li>
 *     <li>R — лицам до 17 лет просматривать фильм можно только в присутствии взрослого,</li>
 *     <li>NC-17 — лицам до 18 лет просмотр запрещён.</li>
 * </ul>
 */
public enum EMotionPictureAssociation {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String name;

    EMotionPictureAssociation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
