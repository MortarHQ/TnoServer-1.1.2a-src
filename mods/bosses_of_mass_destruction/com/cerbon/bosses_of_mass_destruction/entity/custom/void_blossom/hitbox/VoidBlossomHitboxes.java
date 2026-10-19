package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox;

import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityTick;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VoidBlossomHitboxes {
   private final LinkedHashMap<Byte, ICompoundHitbox> hitboxMap;

   public VoidBlossomHitboxes(VoidBlossomEntity entity) {
      AABB collisionHitbox = new AABB(Vec3.ZERO, new Vec3(2.0, 8.0, 2.0));
      String flowerBottom = "flowerBottom";
      String flower = "flower";
      String neck = "neck";
      String rootBoxYaw = "rootYaw";
      final VoidBlossomCompoundHitbox idleHitbox = new VoidBlossomCompoundHitbox(
         entity,
         EntityBounds.builder()
            .add(rootBoxYaw)
            .setBounds(1.0, 5.5, 1.5)
            .setOffset(0.0, 2.75, 0.0)
            .build()
            .add(neck)
            .setBounds(1.0, 1.0, 3.5)
            .setOffset(0.0, 3.75, 1.25)
            .setParent(rootBoxYaw)
            .build()
            .add(flower)
            .setBounds(4.0, 4.0, 1.0)
            .setOffset(0.0, 3.75, 3.5)
            .setParent(rootBoxYaw)
            .build()
            .add(flowerBottom)
            .setBounds(1.0, 1.0, 0.5)
            .setOffset(0.0, -2.5, -0.5)
            .setParent(flower)
            .build()
            .overrideCollisionBox(collisionHitbox)
            .getFactory()
            .create(),
         rootBoxYaw,
         collisionHitbox,
         Arrays.asList(neck, rootBoxYaw)
      );
      idleHitbox.getBounds().getPart(neck).setRotation(-15.0, 0.0, 0.0, true);
      idleHitbox.getBounds().getPart(flower).setRotation(10.0, 0.0, 0.0, true);
      final VoidBlossomCompoundHitbox spikeHitbox = new VoidBlossomCompoundHitbox(
         entity,
         EntityBounds.builder()
            .add(rootBoxYaw)
            .setBounds(1.0, 5.5, 1.5)
            .setOffset(0.0, 2.75, 0.0)
            .build()
            .add(neck)
            .setBounds(1.0, 1.0, 1.0)
            .setOffset(0.0, 2.25, 1.25)
            .setParent(rootBoxYaw)
            .build()
            .add(flower)
            .setBounds(4.0, 4.0, 1.0)
            .setOffset(0.0, 2.0, 2.0)
            .setParent(rootBoxYaw)
            .build()
            .overrideCollisionBox(collisionHitbox)
            .getFactory()
            .create(),
         rootBoxYaw,
         collisionHitbox,
         Arrays.asList(neck, rootBoxYaw)
      );
      spikeHitbox.getBounds().getPart(flower).setRotation(20.0, 0.0, 0.0, true);
      final VoidBlossomCompoundHitbox petalHitbox = new VoidBlossomCompoundHitbox(
         entity,
         EntityBounds.builder()
            .add(rootBoxYaw)
            .setBounds(1.0, 9.0, 1.0)
            .setOffset(0.0, 4.5, 0.0)
            .build()
            .add(flower)
            .setBounds(4.0, 1.0, 4.0)
            .setOffset(0.0, 5.0, 0.0)
            .setParent(rootBoxYaw)
            .build()
            .overrideCollisionBox(collisionHitbox)
            .getFactory()
            .create(),
         rootBoxYaw,
         collisionHitbox,
         Arrays.asList(rootBoxYaw)
      );
      final VoidBlossomCompoundHitbox spikeHitbox1 = new VoidBlossomCompoundHitbox(
         entity,
         EntityBounds.builder()
            .add(rootBoxYaw)
            .setBounds(1.0, 6.5, 1.0)
            .setOffset(0.0, 3.25, 0.0)
            .build()
            .add(flower)
            .setBounds(4.0, 1.0, 4.0)
            .setOffset(0.0, 3.75, 0.0)
            .setParent(rootBoxYaw)
            .build()
            .overrideCollisionBox(collisionHitbox)
            .getFactory()
            .create(),
         rootBoxYaw,
         collisionHitbox,
         Arrays.asList(rootBoxYaw)
      );
      final VoidBlossomCompoundHitbox spikeHitbox2 = new VoidBlossomCompoundHitbox(
         entity,
         EntityBounds.builder()
            .add(rootBoxYaw)
            .setBounds(1.0, 4.0, 1.0)
            .setOffset(0.0, 2.0, 0.0)
            .build()
            .add(flower)
            .setBounds(4.0, 1.0, 4.0)
            .setOffset(0.0, 2.5, 0.0)
            .setParent(rootBoxYaw)
            .build()
            .overrideCollisionBox(collisionHitbox)
            .getFactory()
            .create(),
         rootBoxYaw,
         collisionHitbox,
         Arrays.asList(rootBoxYaw)
      );
      final VoidBlossomCompoundHitbox spikeHitbox3 = new VoidBlossomCompoundHitbox(
         entity,
         EntityBounds.builder()
            .add(rootBoxYaw)
            .setBounds(1.5, 3.75, 1.5)
            .setOffset(0.0, 1.875, 0.0)
            .build()
            .overrideCollisionBox(collisionHitbox)
            .getFactory()
            .create(),
         rootBoxYaw,
         collisionHitbox,
         Arrays.asList(rootBoxYaw)
      );
      final VoidBlossomCompoundHitbox sporeHitbox = new VoidBlossomCompoundHitbox(
         entity,
         EntityBounds.builder()
            .add(rootBoxYaw)
            .setBounds(1.0, 8.0, 1.0)
            .setOffset(0.0, 4.0, 0.0)
            .build()
            .add(flower)
            .setBounds(2.0, 3.0, 3.0)
            .setOffset(0.0, 5.5, -1.0)
            .setParent(rootBoxYaw)
            .build()
            .overrideCollisionBox(collisionHitbox)
            .getFactory()
            .create(),
         rootBoxYaw,
         collisionHitbox,
         Arrays.asList(rootBoxYaw)
      );
      this.hitboxMap = new LinkedHashMap<Byte, ICompoundHitbox>() {
         {
            this.put(HitboxId.Idle.getId(), idleHitbox);
            this.put(HitboxId.Spike.getId(), spikeHitbox);
            this.put(HitboxId.Petal.getId(), petalHitbox);
            this.put(HitboxId.SpikeWave3.getId(), spikeHitbox3);
            this.put(HitboxId.SpikeWave1.getId(), spikeHitbox1);
            this.put(HitboxId.SpikeWave2.getId(), spikeHitbox2);
            this.put(HitboxId.Spore.getId(), sporeHitbox);
         }
      };
   }

   public Map<Byte, ICompoundHitbox> getMap() {
      return this.hitboxMap;
   }

   public CompositeEntityTick<ServerLevel> getTickers() {
      return new CompositeEntityTick(this.hitboxMap.values().stream().filter(IEntityTick.class::isInstance).toArray(IEntityTick[]::new));
   }

   public CompositeDamageHandler getDamageHandlers() {
      return new CompositeDamageHandler(this.hitboxMap.values().stream().filter(IDamageHandler.class::isInstance).toArray(IDamageHandler[]::new));
   }
}
