package fzzyhmstrs.emi_loot.server;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;

public class NestedLootTableResults implements LootSender<ComplexLootPoolBuilder> {
   final List<ComplexLootPoolBuilder> builderList = new LinkedList<>();

   @Override
   public void build() {
      this.builderList.forEach(ComplexLootPoolBuilder::build);
   }

   @Override
   public String getId() {
      return null;
   }

   @Override
   public void send(ServerPlayer player) {
   }

   public void addBuilder(ComplexLootPoolBuilder builder) {
      this.builderList.add(builder);
   }

   @Override
   public List<ComplexLootPoolBuilder> getBuilders() {
      return this.builderList;
   }
}
