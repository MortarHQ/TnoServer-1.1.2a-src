package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

public class ItemBlowgun extends BowItem {
   public static final Predicate<ItemStack> DARTS = stack -> stack.getItem() == ItemHandler.DART.get();

   public ItemBlowgun(Properties properties) {
      super(properties);
   }

   public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
      if (entityLiving instanceof Player player) {
         ItemStack itemstack = player.getProjectile(stack);
         if (!itemstack.isEmpty()) {
            int duration = this.getUseDuration(stack, entityLiving) - timeLeft;
            duration = EventHooks.onArrowLoose(stack, level, player, duration, !itemstack.isEmpty());
            if (duration < 0) {
               return;
            }

            float power = getPowerForTime(duration);
            if (!(power < 0.1)) {
               List<ItemStack> ammo = draw(stack, itemstack, player);
               if (level instanceof ServerLevel serverlevel && !ammo.isEmpty()) {
                  this.shoot(serverlevel, player, player.getUsedItemHand(), stack, ammo, power * 3.0F, 1.0F, power == 1.0F, null);
               }

               level.playSound(
                  null,
                  player.getX(),
                  player.getY(),
                  player.getZ(),
                  (SoundEvent)MMSounds.ENTITY_UMVUTHANA_BLOWDART.get(),
                  SoundSource.PLAYERS,
                  1.0F,
                  1.0F / (player.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F
               );
               player.awardStat(Stats.ITEM_USED.get(this));
            }
         }
      }
   }

   public static float getPowerForTime(int charge) {
      float power = charge / 5.0F;
      power = (power * power + power * 2.0F) / 3.0F;
      if (power > 1.0F) {
         power = 1.0F;
      }

      return power;
   }

   public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
      return 72000;
   }

   public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.2").setStyle(ItemHandler.TOOLTIP_STYLE));
   }

   @NotNull
   protected Projectile createProjectile(@NotNull Level level, @NotNull LivingEntity shooter, @NotNull ItemStack weapon, ItemStack ammo, boolean isCrit) {
      ArrowItem arrowitem = ammo.getItem() instanceof ItemDart dart ? dart : (ArrowItem)ItemHandler.DART.get();
      AbstractArrow arrow = arrowitem.createArrow(level, ammo, shooter, weapon);
      if (isCrit) {
         arrow.setCritArrow(true);
      }

      return this.customArrow(arrow, ammo, weapon);
   }

   @NotNull
   public Predicate<ItemStack> getSupportedHeldProjectiles() {
      return DARTS;
   }

   @NotNull
   public ItemStack getDefaultCreativeAmmo(@Nullable Player player, @NotNull ItemStack projectileWeaponItem) {
      return ((ItemDart)ItemHandler.DART.get()).getDefaultInstance();
   }
}
