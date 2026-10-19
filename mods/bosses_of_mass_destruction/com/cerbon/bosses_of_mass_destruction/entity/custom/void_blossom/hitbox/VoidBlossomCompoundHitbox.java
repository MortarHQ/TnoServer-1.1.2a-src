package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox;

import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityPart;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MutableBox;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;

public class VoidBlossomCompoundHitbox implements ICompoundHitbox, IDamageHandler, IEntityTick<ServerLevel> {
   private final VoidBlossomEntity entity;
   private final EntityBounds hitboxes;
   private final String root;
   private final AABB collisionHitbox;
   private final List<String> spikedBoxes;
   private String nextDamagedPart;

   public VoidBlossomCompoundHitbox(VoidBlossomEntity entity, EntityBounds hitboxes, String root, AABB collisionHitbox, List<String> spikedBoxes) {
      this.entity = entity;
      this.hitboxes = hitboxes;
      this.root = root;
      this.collisionHitbox = collisionHitbox;
      this.spikedBoxes = spikedBoxes;
   }

   @Override
   public void updatePosition() {
      EntityPart rootYaw = this.hitboxes.getPart(this.root);
      rootYaw.setRotation(0.0, -this.entity.getYRot(), 0.0, true);
      rootYaw.setX(this.entity.getX());
      rootYaw.setY(this.entity.getY());
      rootYaw.setZ(this.entity.getZ());
      MutableBox overrideBox = this.hitboxes.getOverrideBox();
      if (overrideBox != null) {
         overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-1.0, 0.0, -1.0));
      }
   }

   @Override
   public EntityBounds getBounds() {
      return this.hitboxes;
   }

   @Override
   public void setNextDamagedPart(String part) {
      this.nextDamagedPart = part;
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
      String part = this.nextDamagedPart;
      this.nextDamagedPart = null;
      if (result && this.spikedBoxes.contains(part) && !damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
         float damage = (float)this.entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
         if (damageSource.getEntity() != null) {
            damageSource.getEntity().hurt(this.entity.level().damageSources().thorns(this.entity), damage);
         }
      }
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      return true;
   }

   public void tick(ServerLevel level) {
      EntityPart rootYaw = this.hitboxes.getPart(this.root);
      rootYaw.setRotation(0.0, -this.entity.getYRot(), 0.0, true);
   }
}
