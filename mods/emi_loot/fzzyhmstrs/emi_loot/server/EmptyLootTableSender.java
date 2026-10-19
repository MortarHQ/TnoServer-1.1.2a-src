package fzzyhmstrs.emi_loot.server;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;

public class EmptyLootTableSender implements LootSender<SimpleLootPoolBuilder> {
   @Override
   public String getId() {
      return "";
   }

   @Override
   public void send(ServerPlayer player) {
   }

   public void addBuilder(SimpleLootPoolBuilder builder) {
   }

   @Override
   public List<SimpleLootPoolBuilder> getBuilders() {
      return new LinkedList<>();
   }

   @Override
   public void build() {
   }
}
