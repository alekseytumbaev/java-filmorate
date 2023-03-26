package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotBlank;

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
@Data
@EqualsAndHashCode(callSuper = true)
public class MotionPictureAssociation extends Entity {
    @NotBlank
    private String name;
}
