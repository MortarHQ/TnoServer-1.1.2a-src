package me.wesley1808.servercore.mixin.features.activation_range.inactive_ticks;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
   private VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
      super(entityType, level);
   }

   @Shadow
   protected abstract void maybeDecayGossip();

   @Shadow
   protected abstract void customServerAiStep();

   public void servercore$inactiveTick() {
      if (this.getUnhappyCounter() > 0) {
         this.setUnhappyCounter(this.getUnhappyCounter() - 1);
      }

      this.maybeDecayGossip();
      super.servercore$inactiveTick();
   }
}
