package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import java.util.UUID;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class DazzledEffect extends TensuraMobEffect {
   public DazzledEffect() {
      super(MobEffectCategory.NEUTRAL, new Color(255, 181, 0, 255).getRGB());
   }

   public boolean applyEffectTick(LivingEntity livingEntity, int i) {
      if (livingEntity.level().isClientSide) {
         return false;
      }

      Holder<MobEffect> dazzled = TensuraMobEffects.getReference(MysticismMobEffects.DAZZLED);
      MobEffectInstance instance = livingEntity.getEffect(dazzled);
      if (instance == null) {
         return false;
      }

      UUID sourceUuid = instance.tensura$getSource();
      Entity applier = null;
      if (sourceUuid != null && !livingEntity.level().isClientSide && livingEntity.level() instanceof ServerLevel serverLevel) {
         applier = serverLevel.getEntity(sourceUuid);
      }

      if (applier != null) {
         Vec3 target = applier.position().add(0.0, applier.getEyeHeight(), 0.0);
         if (livingEntity instanceof ServerPlayer player) {
            player.connection.send(new ClientboundPlayerLookAtPacket(Anchor.EYES, target.x, target.y, target.z));
         } else if (livingEntity instanceof Mob mob) {
            mob.getNavigation().stop();
            mob.setAggressive(false);
            mob.setTarget(null);
            mob.getLookControl().setLookAt(target.x, target.y, target.z, 180.0F, 180.0F);
         }

         livingEntity.setDeltaMovement(Vec3.ZERO);
         livingEntity.setJumping(false);
         livingEntity.hurtMarked = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean shouldApplyEffectTickThisTick(int i, int j) {
      return true;
   }
}
