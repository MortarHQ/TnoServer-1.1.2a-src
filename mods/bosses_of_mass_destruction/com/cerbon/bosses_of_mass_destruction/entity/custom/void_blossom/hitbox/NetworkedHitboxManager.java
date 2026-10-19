package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox;

import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import java.util.Map;
import net.minecraft.network.syncher.EntityDataAccessor;

public class NetworkedHitboxManager implements ICompoundHitbox {
   private final BaseEntity entity;
   private final Map<Byte, ICompoundHitbox> hitboxMap;
   public final EntityDataAccessor<Byte> hitbox;

   public NetworkedHitboxManager(BaseEntity entity, Map<Byte, ICompoundHitbox> hitboxMap, EntityDataAccessor<Byte> hitbox) {
      this.entity = entity;
      this.hitboxMap = hitboxMap;
      this.hitbox = hitbox;
   }

   @Override
   public void updatePosition() {
      for (ICompoundHitbox hitbox1 : this.hitboxMap.values()) {
         hitbox1.updatePosition();
      }
   }

   @Override
   public EntityBounds getBounds() {
      return this.hitboxMap.get(this.entity.getEntityData().get(this.hitbox)).getBounds();
   }

   @Override
   public void setNextDamagedPart(String part) {
      this.hitboxMap.get(this.entity.getEntityData().get(this.hitbox)).setNextDamagedPart(part);
   }
}
