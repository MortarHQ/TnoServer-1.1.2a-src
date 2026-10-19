package net.p3pp3rf1y.sophisticatedcore.settings.main;

public enum Context {
   PLAYER(0),
   STORAGE(1);

   private final int id;

   Context(int id) {
      this.id = id;
   }

   public int getId() {
      return this.id;
   }

   public static Context fromId(int id) {
      return PLAYER.id == id ? PLAYER : STORAGE;
   }
}
