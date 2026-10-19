package com.tno_tensura_extras.event;

import com.tno_tensura_extras.registry.ExtrasAttachments;
import com.tno_tensura_extras.registry.ExtrasItems;
import io.github.manasmods.tensura.entity.monster.ArmyWaspEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.tslat.smartbrainlib.util.BrainUtils;

public final class HighQualityHoneyEvents {
   private static final long HARVEST_COOLDOWN_TICKS = 14400L;

   private HighQualityHoneyEvents() {
   }

   public static void onEntityInteract(EntityInteractSpecific event) {
      if (event.getTarget() instanceof ArmyWaspEntity wasp && event.getItemStack().is(Items.BUCKET)) {
         event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
         event.setCanceled(true);
         if (!event.getLevel().isClientSide()) {
            long gameTime = event.getLevel().getGameTime();
            if (gameTime >= (Long)wasp.getData(ExtrasAttachments.NEXT_HIGH_QUALITY_HONEY_TIME.get())) {
               wasp.setData(ExtrasAttachments.NEXT_HIGH_QUALITY_HONEY_TIME.get(), gameTime + 14400L);
               Player player = event.getEntity();
               ItemStack honey = new ItemStack((ItemLike)ExtrasItems.HIGH_QUALITY_HONEY.get());
               player.setItemInHand(event.getHand(), ItemUtils.createFilledResult(event.getItemStack(), player, honey));
               player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
               wasp.setSleeping(false);
               wasp.setOrderedToSit(false);
               wasp.setInSittingPose(false);
               wasp.setPersistentAngerTarget(player.getUUID());
               wasp.startPersistentAngerTimer();
               BrainUtils.setTargetOfEntity(wasp, player);
            }
         }
      }
   }
}
