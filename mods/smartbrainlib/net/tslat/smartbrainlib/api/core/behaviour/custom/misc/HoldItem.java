package net.tslat.smartbrainlib.api.core.behaviour.custom.misc;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

public class HoldItem<E extends LivingEntity> extends ExtendedBehaviour<E> {
   protected Function<E, ItemStack> stackFunction = entity -> ItemStack.EMPTY;
   protected Function<E, InteractionHand> handDecider = entity -> InteractionHand.MAIN_HAND;
   protected BiPredicate<E, ItemStack> dropItemOnUnequip = (entity, stack) -> false;

   public HoldItem<E> toHand(Function<E, InteractionHand> function) {
      this.handDecider = function;
      return this;
   }

   public HoldItem<E> withStack(Function<E, ItemStack> function) {
      this.stackFunction = function;
      return this;
   }

   public HoldItem<E> dropItemOnUnequip() {
      return this.dropItemOnUnequip((entity, stack) -> true);
   }

   public HoldItem<E> dropItemOnUnequip(BiPredicate<E, ItemStack> dropPredicate) {
      this.dropItemOnUnequip = dropPredicate;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }

   @Override
   protected void start(E entity) {
      InteractionHand hand = this.handDecider.apply(entity);
      ItemStack previousStack = entity.getItemInHand(hand);
      if (this.dropItemOnUnequip.test(entity, previousStack)) {
         entity.spawnAtLocation(previousStack);
      }

      entity.setItemInHand(hand, this.stackFunction.apply(entity));
   }
}
