package team.creative.creativecore.common.config.premade;

import java.util.Map.Entry;
import net.minecraft.world.entity.player.Player;
import team.creative.creativecore.CreativeCore;

public class Permission<T> extends NamedList<T> {
   private T value;

   public Permission(T defaultValue) {
      this.value = defaultValue;
      this.put("default", this.value);
   }

   public T getDefault() {
      return this.value;
   }

   public Permission<T> add(String usergroup, T value) {
      this.put(usergroup, value);
      return this;
   }

   public T put(String key, T value) {
      if (key.equals("default")) {
         this.value = value;
      }

      return super.put(key, value);
   }

   public T get(Player player) {
      for (Entry<String, T> pair : this.entrySet()) {
         if (!pair.getKey().equals("default") && CreativeCore.CONFIG.is(player, pair.getKey())) {
            return pair.getValue();
         }
      }

      return this.value;
   }

   @Deprecated
   public T getDirect(Object key) {
      return super.get(key);
   }

   @Deprecated
   @Override
   public T get(Object key) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   @Override
   public T getOrDefault(Object key, T defaultValue) {
      throw new UnsupportedOperationException();
   }
}
