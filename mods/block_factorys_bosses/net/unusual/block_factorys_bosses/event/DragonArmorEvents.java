package net.unusual.block_factorys_bosses.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@EventBusSubscriber
public class DragonArmorEvents {
   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (player.level().getGameTime() % 20L == 0L) {
            ItemStack helmet = player.getInventory().getArmor(3);
            ItemStack chestplate = player.getInventory().getArmor(2);
            ItemStack leggings = player.getInventory().getArmor(1);
            ItemStack boots = player.getInventory().getArmor(0);
            boolean hadHelmet = false;
            boolean hadChestplate = false;
            boolean hadLeggings = false;
            boolean hadBoots = false;
            if (!helmet.isEmpty() && helmet.getItem().asItem() == ((Item)BossesRiseItems.DRAGON_SKULL.get()).asItem()) {
               player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 60, 0, true, false));
               hadHelmet = true;
            }

            if (!chestplate.isEmpty() && chestplate.getItem() == ((Item)BossesRiseItems.DRAGON_BONES_CHESTPLATE.get()).asItem()) {
               hadChestplate = true;
            }

            if (!leggings.isEmpty() && leggings.getItem().asItem() == ((Item)BossesRiseItems.DRAGON_BONES_LEGGINGS.get()).asItem()) {
               if (player.isCrouching()) {
                  player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, true, false));
                  player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, true, false));
               }

               hadLeggings = true;
            }

            if (!boots.isEmpty() && boots.getItem() == ((Item)BossesRiseItems.DRAGON_BONES_BOOTS.get()).asItem()) {
               hadBoots = true;
            }

            if (hadHelmet && hadChestplate && hadLeggings && hadBoots) {
               player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, true, false));
               player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, true, false));
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerHurt(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ServerLevel var5 = (ServerLevel)player.level();
         ItemStack chestplate = player.getInventory().getArmor(2);
         ItemStack boots = player.getInventory().getArmor(0);
         if (boots.is(BossesRiseItems.DRAGON_BONES_BOOTS)) {
            handleDragonBonesBoots(var5, player, event);
         }

         if (chestplate.is(BossesRiseItems.DRAGON_BONES_CHESTPLATE)) {
            handleDragonBonesChestplate(var5, player, event);
         }
      }
   }

   private static void handleDragonBonesBoots(ServerLevel level, ServerPlayer player, net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post event) {
      if (!(player.fallDistance < 3.0F)) {
         double downwardsMomentum = Math.max(player.getKnownMovement().dot(new Vec3(0.0, -1.0, 0.0)), player.getDeltaMovement().dot(new Vec3(0.0, -1.0, 0.0)));
         if (!(downwardsMomentum < 0.5)) {
            float explosionRadius = Mth.clamp((float)downwardsMomentum, 0.0F, 4.5F);
            level.explode(player, player.position().x, player.position().y, player.position().z, explosionRadius, ExplosionInteraction.NONE).explode();
            level.getNearbyPlayers(TargetingConditions.DEFAULT, player, AABB.ofSize(player.position(), 4.0, 4.0, 4.0)).forEach(p -> {
               if (p != player) {
                  p.setRemainingFireTicks(250);
               }
            });
         }
      }
   }

   private static void handleDragonBonesChestplate(
      ServerLevel level, ServerPlayer player, net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post event
   ) {
      if (!(Math.random() > 0.1)) {
         if (event.getSource().getDirectEntity() instanceof Projectile projectile) {
            Entity causingEntity = projectile.getOwner() != null ? projectile.getOwner() : event.getSource().getEntity();
            if (causingEntity != null) {
               if (!(causingEntity.distanceTo(player) < 3.0F)) {
                  BlazingFireBallEntity fireball = new BlazingFireBallEntity(
                     (EntityType<? extends BlazingFireBallEntity>)BossesRiseEntities.BLAZING_FIRE_BALL.get(), level
                  );
                  fireball.setOwner(player);
                  fireball.setBaseDamage(1.0);
                  fireball.setSilent(true);
                  fireball.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
                  Vec3 directionToTarget = causingEntity.position()
                     .add(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5)
                     .subtract(player.getEyePosition())
                     .normalize();
                  fireball.shoot(directionToTarget.x, directionToTarget.y, directionToTarget.z, 1.5F, 3.0F);
                  level.addFreshEntity(fireball);
               }
            }
         }
      }
   }

   public static boolean shouldDisableNightVisionFlashing(LivingEntity entity) {
      return entity.getItemBySlot(EquipmentSlot.HEAD).is(BossesRiseItems.DRAGON_SKULL);
   }
}
