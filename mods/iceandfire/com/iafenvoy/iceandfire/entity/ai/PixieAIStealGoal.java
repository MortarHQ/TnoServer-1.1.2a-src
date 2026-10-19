package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.PixieEntity;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.tag.IafItemTags;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PixieAIStealGoal extends Goal {
   private final PixieEntity temptedEntity;
   private Player temptingPlayer;
   private int delayTemptCounter = 0;
   private boolean isRunning;

   public PixieAIStealGoal(PixieEntity temptedEntityIn) {
      this.temptedEntity = temptedEntityIn;
   }

   public boolean canUse() {
      if ((Boolean)IafCommonConfig.INSTANCE.pixie.stealItems.getValue()
         && this.temptedEntity.getMainHandItem().isEmpty()
         && this.temptedEntity.stealCooldown <= 0) {
         if (this.temptedEntity.getRandom().nextInt(200) == 0) {
            return false;
         } else if (this.temptedEntity.isTame()) {
            return false;
         } else if (this.delayTemptCounter > 0) {
            this.delayTemptCounter--;
            return false;
         } else {
            this.temptingPlayer = this.temptedEntity.level().getNearestPlayer(this.temptedEntity, 10.0);
            return this.temptingPlayer != null
               && this.temptedEntity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()
               && !this.temptingPlayer.getInventory().isEmpty()
               && !this.temptingPlayer.isCreative();
         }
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      return !this.temptedEntity.isTame()
         && this.temptedEntity.getMainHandItem().isEmpty()
         && this.delayTemptCounter == 0
         && this.temptedEntity.stealCooldown == 0;
   }

   public void start() {
      this.isRunning = true;
   }

   public void stop() {
      this.temptingPlayer = null;
      if (this.delayTemptCounter < 10) {
         this.delayTemptCounter += 10;
      }

      this.isRunning = false;
   }

   public void tick() {
      this.temptedEntity.getLookControl().setLookAt(this.temptingPlayer, this.temptedEntity.getMaxHeadYRot() + 20, this.temptedEntity.getMaxHeadXRot());
      ArrayList<Integer> slotlist = new ArrayList<>();
      if (this.temptedEntity.distanceToSqr(this.temptingPlayer) < 3.0 && !this.temptingPlayer.getInventory().isEmpty()) {
         for (int i = 0; i < this.temptingPlayer.getInventory().getContainerSize(); i++) {
            ItemStack targetStack = this.temptingPlayer.getInventory().getItem(i);
            if (!Inventory.isHotbarSlot(i) && !targetStack.isEmpty() && targetStack.isStackable() && !targetStack.is(IafItemTags.PIXIE_STOLEN_BLACKLIST)) {
               slotlist.add(i);
            }
         }

         if (!slotlist.isEmpty()) {
            int slot = slotlist.size() == 1 ? slotlist.getFirst() : slotlist.get(ThreadLocalRandom.current().nextInt(slotlist.size()));
            ItemStack randomItem = this.temptingPlayer.getInventory().getItem(slot);
            this.temptedEntity.setItemInHand(InteractionHand.MAIN_HAND, randomItem);
            this.temptingPlayer.getInventory().removeItemNoUpdate(slot);
            this.temptedEntity.playSound((SoundEvent)IafSounds.PIXIE_TAUNT.get(), 1.0F, 1.0F);

            for (PixieEntity pixie : this.temptingPlayer.level().getEntitiesOfClass(PixieEntity.class, this.temptedEntity.getBoundingBox().inflate(40.0))) {
               pixie.stealCooldown = 1000 + pixie.getRandom().nextInt(3000);
            }

            if (this.temptingPlayer != null) {
               this.temptingPlayer.addEffect(new MobEffectInstance(this.temptedEntity.negativePotions[this.temptedEntity.getColor()], 100));
            }
         } else {
            this.delayTemptCounter = 200;
         }
      } else {
         this.temptedEntity.getMoveControl().setWantedPosition(this.temptingPlayer.getX(), this.temptingPlayer.getY() + 1.5, this.temptingPlayer.getZ(), 1.0);
      }
   }

   public boolean isRunning() {
      return this.isRunning;
   }
}
