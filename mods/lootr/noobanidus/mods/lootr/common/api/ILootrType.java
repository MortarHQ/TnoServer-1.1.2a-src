package noobanidus.mods.lootr.common.api;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import noobanidus.mods.lootr.common.api.data.DefaultLootFiller;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import noobanidus.mods.lootr.common.api.data.LootFiller;
import org.jetbrains.annotations.Nullable;

public interface ILootrType {
   String getName();

   @Nullable
   Block getReplacementBlock();

   @Nullable
   EntityType<?> getReplacementEntity();

   default void callback() {
   }

   default LootFiller getDefaultFiller() {
      return DefaultLootFiller.getInstance();
   }

   default boolean canDecay() {
      return true;
   }

   default boolean canRefresh() {
      return true;
   }

   default boolean isEntity() {
      return false;
   }

   default boolean canBeMarkedUnopened() {
      return true;
   }

   default boolean canDropContentsWhenBroken() {
      return true;
   }

   default boolean displaysUnopenedParticle() {
      return true;
   }

   @Nullable
   default Container getContainer(ILootrInfo info, ServerLevel level) {
      if (this.isEntity() && this.getReplacementEntity() != null) {
         if (level.getEntity(info.getInfoUUID()) instanceof Container container) {
            return container;
         }
      } else if (!this.isEntity() && level.getBlockEntity(info.getInfoPos()) instanceof Container container) {
         return container;
      }

      return null;
   }
}
