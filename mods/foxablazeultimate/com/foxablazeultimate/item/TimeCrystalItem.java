package com.foxablazeultimate.item;

import com.foxablazeultimate.ability.skill.yogsothoth.YogSothothSkill;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent.ChorusFruit;

public final class TimeCrystalItem extends WisdomCrystalItem {
   public TimeCrystalItem(Properties properties) {
      super(
         properties,
         () -> (ManasSkill)FoxAblazeUltimateSkills.yogSothoth().orElse(null),
         List.of(new WisdomCrystalItem.MasteryBoost(YogSothothSkill.TIME_TRAVELER, 0.25))
      );
   }

   @Override
   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
      ItemStack result = super.finishUsingItem(stack, level, living);
      if (level.isClientSide) {
         return result;
      }

      for (int attempt = 0; attempt < 16; attempt++) {
         double targetX = living.getX() + (living.getRandom().nextDouble() - 0.5) * 16.0;
         double targetY = Mth.clamp(
            living.getY() + living.getRandom().nextInt(16) - 8.0,
            level.getMinBuildHeight(),
            level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1
         );
         double targetZ = living.getZ() + (living.getRandom().nextDouble() - 0.5) * 16.0;
         if (living.isPassenger()) {
            living.stopRiding();
         }

         Vec3 origin = living.position();
         ChorusFruit event = EventHooks.onChorusFruitTeleport(living, targetX, targetY, targetZ);
         if (event.isCanceled()) {
            return result;
         }

         if (living.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
            level.gameEvent(GameEvent.TELEPORT, origin, Context.of(living));
            SoundEvent sound = living instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
            SoundSource source = living instanceof Fox ? SoundSource.NEUTRAL : SoundSource.PLAYERS;
            level.playSound(null, living.getX(), living.getY(), living.getZ(), sound, source);
            living.resetFallDistance();
            break;
         }
      }

      if (living instanceof Player player) {
         player.resetCurrentImpulseContext();
         player.getCooldowns().addCooldown(this, 20);
      }

      return result;
   }
}
