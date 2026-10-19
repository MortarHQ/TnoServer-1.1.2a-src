package nadiendev.constructionwand.containers;

import java.util.HashSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class ContainerTrace {
   private final HashSet<Integer> visited = new HashSet<>();
   public final ServerLevel level;
   @Nullable
   public final ServerPlayer player;

   public ContainerTrace(ServerLevel level) {
      this.level = level;
      this.player = null;
      this.visited.add(-1);
   }

   public ContainerTrace(ServerPlayer player) {
      this.player = player;
      this.level = player.serverLevel();
      this.visited.add(-1);
   }

   public boolean push(int sig) {
      if (this.visited.contains(sig)) {
         return false;
      }

      this.visited.add(sig);
      return true;
   }
}
