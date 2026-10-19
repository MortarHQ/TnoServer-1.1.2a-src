package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.entity.living.monster.Gleech;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class GleechEgg extends ThrowableItemProjectile {
   public GleechEgg(EntityType<? extends GleechEgg> entityType, Level level) {
      super(entityType, level);
   }

   public GleechEgg(Level level, LivingEntity livingEntity) {
      super(ESEntities.GLEECH_EGG.get(), livingEntity, level);
   }

   public GleechEgg(Level level, double x, double y, double z) {
      super(ESEntities.GLEECH_EGG.get(), x, y, z, level);
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      this.discard();
   }

   protected void onHitEntity(EntityHitResult entityHitResult) {
      super.onHitEntity(entityHitResult);
      entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
      if (!this.level().isClientSide
         && entityHitResult.getEntity() instanceof LivingEntity livingEntity
         && !livingEntity.getType().is(ESTags.EntityTypes.GLEECH_IMMUNE)) {
         if (this.getOwner() instanceof ServerPlayer serverPlayer) {
            ((PlayerTrigger)ESCriteriaTriggers.THROW_GLEECH_EGG.get()).trigger(serverPlayer);
         }

         Gleech gleech = (Gleech)ESEntities.GLEECH.get().create(this.level());
         if (gleech != null) {
            gleech.setPos(this.position());
            gleech.setLarval(true);
            gleech.attachTo(livingEntity);
            this.level().addFreshEntity(gleech);
            gleech.setTarget(livingEntity);
            gleech.hurt(this.damageSources().mobAttack(livingEntity), 0.0F);
         }
      }
   }

   @NotNull
   protected Item getDefaultItem() {
      return ESItems.GLEECH_EGG.get();
   }
}
