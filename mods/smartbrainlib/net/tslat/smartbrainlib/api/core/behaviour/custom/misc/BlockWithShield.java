package net.tslat.smartbrainlib.api.core.behaviour.custom.misc;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.UseAnim;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

public class BlockWithShield<E extends LivingEntity> extends ExtendedBehaviour<E> {
   protected InteractionHand hand = InteractionHand.MAIN_HAND;
   protected Predicate<E> stopCondition = entity -> false;

   @Deprecated(forRemoval = true)
   public BlockWithShield<E> stopWhen(Predicate<E> predicate) {
      this.stopCondition = predicate;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      if (entity.getMainHandItem().getUseAnimation() == UseAnim.BLOCK) {
         this.hand = InteractionHand.MAIN_HAND;
         return true;
      } else if (entity.getOffhandItem().getUseAnimation() == UseAnim.BLOCK) {
         this.hand = InteractionHand.OFF_HAND;
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void start(E entity) {
      entity.startUsingItem(this.hand);
   }

   @Override
   protected boolean shouldKeepRunning(E entity) {
      if (!entity.isUsingItem()) {
         return false;
      } else {
         return entity.getUseItem().getUseAnimation() != UseAnim.BLOCK ? false : !this.stopCondition.test(entity);
      }
   }

   @Override
   protected void stop(E entity) {
      if (entity.getUseItem().getUseAnimation() == UseAnim.BLOCK) {
         entity.stopUsingItem();
      }
   }
}
