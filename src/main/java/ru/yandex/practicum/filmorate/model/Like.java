package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Like extends Entity {

   private long userId;
   private long filmId;

   public Like(long id, long userId, long filmId) {
      super(id);
      this.filmId = filmId;
      this.userId = userId;
   }

   public Like(long userId, long filmId) {
      this.filmId = filmId;
      this.userId = userId;
   }
}
