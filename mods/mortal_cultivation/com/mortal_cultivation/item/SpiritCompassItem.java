package com.mortal_cultivation.item;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class SpiritCompassItem extends CompassItem {
   private static final int SEARCH_RADIUS_CHUNKS = 256;
   private static final int SEARCH_INTERVAL_TICKS = 40;
   private static final TagKey<Structure> STELE_GROTTO_STRUCTURES = TagKey.create(
      Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "stele_grotto")
   );

   public SpiritCompassItem(Properties properties) {
      super(properties.stacksTo(1));
   }

   public boolean isFoil(ItemStack stack) {
      return false;
   }

   public String getDescriptionId(ItemStack stack) {
      return this.getDescriptionId();
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
      super.inventoryTick(stack, level, entity, slot, selected);
      if (level instanceof ServerLevel serverLevel && entity instanceof Player player && level.getGameTime() % 40L == 0L) {
         LodestoneTracker tracker = (LodestoneTracker)stack.get(DataComponents.LODESTONE_TRACKER);
         boolean pointsIntoCurrentDimension = tracker != null
            && tracker.target().isPresent()
            && ((GlobalPos)tracker.target().get()).dimension().equals(serverLevel.dimension());
         if (!pointsIntoCurrentDimension) {
            locateNearestStele(stack, serverLevel, player);
         }
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (level instanceof ServerLevel serverLevel) {
         locateNearestStele(stack, serverLevel, player);
      }

      return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
   }

   public InteractionResult useOn(UseOnContext context) {
      return InteractionResult.PASS;
   }

   private static void locateNearestStele(ItemStack stack, ServerLevel level, Player player) {
      BlockPos target = level.findNearestMapStructure(STELE_GROTTO_STRUCTURES, player.blockPosition(), 256, false);
      if (target == null) {
         stack.remove(DataComponents.LODESTONE_TRACKER);
      } else {
         ResourceKey<Level> dimension = level.dimension();
         stack.set(DataComponents.LODESTONE_TRACKER, new LodestoneTracker(Optional.of(GlobalPos.of(dimension, target)), false));
      }
   }
}
