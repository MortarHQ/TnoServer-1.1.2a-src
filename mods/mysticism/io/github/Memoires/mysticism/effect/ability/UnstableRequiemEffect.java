package io.github.Memoires.mysticism.effect.ability;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.magic.field.MagicExplosion;
import java.awt.Color;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;

public class UnstableRequiemEffect extends TensuraMobEffect implements DamageAction {
   public UnstableRequiemEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 181, 0, 255).getRGB());
   }

   public boolean onBeingDamaged(LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      return false;
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      super.onEffectRemoved(entity, instance);
      MagicExplosion explosion = new MagicExplosion(entity.level(), entity);
      explosion.setDamage(250.0F);
      explosion.setSecondaryDamage(50.0F);
      explosion.setTickEachHit(1);
      explosion.setLife(50);
      explosion.setPos(entity.getX(), entity.getY(), entity.getZ());
      explosion.setSize(15.0F);
      entity.level().addFreshEntity(explosion);
      if (!(entity instanceof Player)) {
         entity.remove(RemovalReason.DISCARDED);
      }
   }
}
