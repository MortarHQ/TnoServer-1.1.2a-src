package com.bobmowzie.mowziesmobs.server.power;

import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public abstract class Power {
   private final PlayerData data;

   public Power(PlayerData data) {
      this.data = data;
   }

   public void tick(PlayerTickEvent event) {
   }

   public void onRightClickEmpty(RightClickEmpty event) {
   }

   public void onRightClickBlock(RightClickBlock event) {
   }

   public void onRightClickWithItem(RightClickItem event) {
   }

   public void onRightClickEntity(EntityInteract event) {
   }

   public void onLeftClickEmpty(LeftClickEmpty event) {
   }

   public void onLeftClickBlock(LeftClickBlock event) {
   }

   public void onLeftClickEntity(AttackEntityEvent event) {
   }

   public void onTakeDamage(Post event) {
   }

   public void onJump(LivingJumpEvent event) {
   }

   public void onRightMouseDown(Player player) {
   }

   public void onLeftMouseDown(Player player) {
   }

   public void onRightMouseUp(Player player) {
   }

   public void onLeftMouseUp(Player player) {
   }

   public void onSneakDown(Player player) {
   }

   public void onSneakUp(Player player) {
   }

   public boolean canUse(Player player) {
      return true;
   }

   public PlayerData getProperties() {
      return this.data;
   }

   public List<LivingEntity> getEntityLivingBaseNearby(LivingEntity player, double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(player, LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(LivingEntity player, Class<T> entityClass, double r) {
      return player.level().getEntitiesOfClass(entityClass, player.getBoundingBox().inflate(r, r, r), e -> e != player && player.distanceTo(e) <= r);
   }

   public <T extends Entity> List<T> getEntitiesNearby(LivingEntity player, Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return player.level().getEntitiesOfClass(entityClass, player.getBoundingBox().inflate(dX, dY, dZ), e -> e != player && player.distanceTo(e) <= r);
   }
}
