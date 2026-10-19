package net.darkhax.bookshelf.common.api.commands;

import java.util.function.Predicate;
import net.minecraft.commands.CommandSourceStack;

public enum PermissionLevel implements Predicate<CommandSourceStack> {
   PLAYER(0),
   MODERATOR(1),
   GAMEMASTER(2),
   ADMIN(3),
   OWNER(4);

   final int level;

   PermissionLevel(int level) {
      this.level = level;
   }

   public int get() {
      return this.level;
   }

   public boolean test(CommandSourceStack source) {
      return source.hasPermission(this.level);
   }
}
