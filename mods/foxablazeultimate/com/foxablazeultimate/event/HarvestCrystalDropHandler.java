package com.foxablazeultimate.event;

import com.foxablazeultimate.config.ShubNiggurathConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateItems;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class HarvestCrystalDropHandler {
   private HarvestCrystalDropHandler() {
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onCropHarvest(BreakEvent event) {
      if (!event.isCanceled()) {
         if (event.getLevel() instanceof ServerLevel level) {
            if (event.getPlayer() instanceof ServerPlayer player) {
               if (!player.isCreative() && !player.isSpectator()) {
                  BlockState state = event.getState();
                  if (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state)) {
                     if (SkillUtils.hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get())) {
                        double chance = Math.clamp(ShubNiggurathConfig.get().ShubNiggurath.harvestCrystalDropChance, 0.0, 1.0);
                        if (!(level.getRandom().nextDouble() >= chance)) {
                           Block.popResource(level, event.getPos(), new ItemStack((ItemLike)FoxAblazeUltimateItems.HARVEST_CRYSTAL.get()));
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
