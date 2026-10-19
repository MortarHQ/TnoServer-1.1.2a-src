package net.jadenxgamer.netherexp.core.item;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.jadenxgamer.elysium_api.api.util.ElysiumTimeTracker;
import net.jadenxgamer.elysium_api.api.util.ElysiumTimeTracker.IntervalTimer;
import net.jadenxgamer.elysium_api.api.util.ElysiumTimeTracker.TimeSource;
import net.jadenxgamer.netherexp.core.item.components.LocatorCompass;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEDataComponents;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SanctumCompassItem extends ProjectileWeaponItem {
   private static final int ACTIVATION_DURATION_SECONDS = 240;
   private static final IntervalTimer intervalTimer = ElysiumTimeTracker.createIntervalTimer(20L, TimeSource.SERVER);

   public SanctumCompassItem(Properties properties) {
      super(properties.component(JNEDataComponents.LOCATOR_COMPASS, LocatorCompass.DEFAULT));
   }

   @NotNull
   public Predicate<ItemStack> getAllSupportedProjectiles() {
      return stack -> stack.is(JNEItems.WRAITHING_FLESH.get());
   }

   public int getDefaultProjectileRange() {
      return 15;
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      if (!level.isClientSide()) {
         LocatorCompass compass = (LocatorCompass)stack.get(JNEDataComponents.LOCATOR_COMPASS);
         if (compass != null && compass.isActive()) {
            if (intervalTimer.check()) {
               int newTime = compass.activeTime() - 1;
               if (newTime <= 0) {
                  stack.set(JNEDataComponents.LOCATOR_COMPASS, new LocatorCompass(compass.structurePos(), compass.dimension(), compass.bound(), false, 0));
                  stack.remove(DataComponents.CUSTOM_MODEL_DATA);
                  level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.LODESTONE_PLACE, SoundSource.PLAYERS, 0.7F, 1.0F);
               } else {
                  stack.set(JNEDataComponents.LOCATOR_COMPASS, new LocatorCompass(compass.structurePos(), compass.dimension(), compass.bound(), true, newTime));
               }
            }

            if (entity.tickCount % 40 == 0 && isSelected) {
               level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), JNESoundEvents.COMPASS_TICK.get(), SoundSource.PLAYERS, 0.7F, 1.0F);
            }
         }
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      LocatorCompass compass = (LocatorCompass)stack.get(JNEDataComponents.LOCATOR_COMPASS);
      if (compass != null && !compass.isActive()) {
         BlockPos pos = this.findStructure(level, player, stack);
         if (pos == null) {
            return InteractionResultHolder.pass(stack);
         }

         ItemStack ammo = player.getProjectile(stack);
         if (ammo.isEmpty() && !player.getAbilities().instabuild) {
            return InteractionResultHolder.pass(stack);
         }

         if (!player.getAbilities().instabuild) {
            ammo.shrink(1);
         }

         if (!this.activateCompass(stack, level, player, pos)) {
            return InteractionResultHolder.pass(stack);
         }

         player.swing(hand, true);
         return InteractionResultHolder.success(stack);
      } else {
         return InteractionResultHolder.pass(stack);
      }
   }

   public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
      if (stack.getCount() == 1 && action == ClickAction.SECONDARY) {
         LocatorCompass compass = (LocatorCompass)stack.get(JNEDataComponents.LOCATOR_COMPASS);
         if (compass != null && !compass.isActive()) {
            ItemStack other = slot.getItem();
            if (!other.isEmpty() && other.is(JNEItems.WRAITHING_FLESH.get())) {
               BlockPos pos = this.findStructure(player.level(), player, stack);
               if (pos == null) {
                  return false;
               }

               other.shrink(1);
               slot.set(other);
               return this.activateCompass(stack, player.level(), player, pos);
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess slotAccess) {
      if (stack.getCount() == 1 && action == ClickAction.SECONDARY) {
         LocatorCompass compass = (LocatorCompass)stack.get(JNEDataComponents.LOCATOR_COMPASS);
         if (compass == null || compass.isActive()) {
            return false;
         }

         if (!other.isEmpty() && other.is(JNEItems.WRAITHING_FLESH.get())) {
            BlockPos pos = this.findStructure(player.level(), player, stack);
            if (pos == null) {
               return false;
            }

            other.shrink(1);
            slotAccess.set(other);
            return this.activateCompass(stack, player.level(), player, pos);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      LocatorCompass compass = (LocatorCompass)stack.get(JNEDataComponents.LOCATOR_COMPASS);
      if (compass != null && compass.bound()) {
         tooltip.add(Component.translatable("sanctum_compass.tracking").withStyle(ChatFormatting.GRAY));
      }

      if (compass == null || !compass.isActive()) {
         if (!tooltip.isEmpty()) {
            tooltip.add(Component.empty());
         }

         tooltip.add(Component.translatable("sanctum_compass.to_activate").withStyle(ChatFormatting.BLUE));
         tooltip.add(Component.translatable("item.netherexp.wraithing_flesh").withStyle(ChatFormatting.DARK_PURPLE));
      }
   }

   @Nullable
   public static GlobalPos getStructurePosition(ItemStack stack) {
      LocatorCompass compass = (LocatorCompass)stack.get(JNEDataComponents.LOCATOR_COMPASS);
      if (compass != null && compass.bound()) {
         Optional<BlockPos> pos = compass.structurePos();
         Optional<ResourceLocation> dim = compass.dimension();
         return pos.isPresent() && dim.isPresent() ? GlobalPos.of(ResourceKey.create(Registries.DIMENSION, dim.get()), pos.get()) : null;
      } else {
         return null;
      }
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !oldStack.is(this) || !newStack.is(this);
   }

   protected void shootProjectile(
      LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target
   ) {
   }

   @Nullable
   private BlockPos findStructure(Level level, Player player, ItemStack stack) {
      if (level instanceof ServerLevel serverLevel) {
         LocatorCompass compass = (LocatorCompass)stack.get(JNEDataComponents.LOCATOR_COMPASS);
         if (compass == null) {
            return null;
         } else {
            return compass.bound() && compass.structurePos().isPresent() && compass.dimension().isPresent()
               ? compass.structurePos().get()
               : serverLevel.findNearestMapStructure(JNETags.Structures.SANCTUM_COMPASS_LOCATED, player.blockPosition(), 100, false);
         }
      } else {
         return null;
      }
   }

   private boolean activateCompass(ItemStack stack, Level level, Player player, BlockPos pos) {
      if (level.isClientSide()) {
         return false;
      }

      stack.set(JNEDataComponents.LOCATOR_COMPASS, new LocatorCompass(Optional.of(pos), Optional.of(level.dimension().location()), true, true, 240));
      stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1));
      if (player instanceof ServerPlayer sp) {
         JNECriteriaTriggers.ACTIVATE_SANCTUM_COMPASS.get().trigger(sp);
      }

      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 0.7F, 1.0F);
      player.awardStat(Stats.ITEM_USED.get(this));
      return true;
   }
}
