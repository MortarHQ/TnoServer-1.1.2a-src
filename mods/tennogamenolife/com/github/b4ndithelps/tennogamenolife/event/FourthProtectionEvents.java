package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic.FourthProtectionManager;
import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.registry.skill.TenreincarnationSkill;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent.Detonate;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;

@EventBusSubscriber(modid = "tennogamenolife")
public final class FourthProtectionEvents {
   private static boolean initialized;

   private FourthProtectionEvents() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         TensuraSkillEvents.SKILL_GRIEF_PRE.register(FourthProtectionEvents::onSkillGrief);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      Vec3 targetCenter = target.getBoundingBox().getCenter();
      DamageSource source = event.getSource();
      if (isTengekiDamage(source) && FourthProtectionManager.isInsideBarrier(target.level(), targetCenter)) {
         event.setCanceled(true);
      } else if (source.getDirectEntity() instanceof Projectile projectile) {
         Vec3 previous = new Vec3(projectile.xo, projectile.yo, projectile.zo);
         if (FourthProtectionManager.crossesBoundary(target.level(), previous, targetCenter)) {
            event.setCanceled(true);
         } else {
            Entity owner = projectile.getOwner();
            if (owner != null && FourthProtectionManager.crossesBoundary(target.level(), owner.getBoundingBox().getCenter(), targetCenter)) {
               event.setCanceled(true);
            }
         }
      } else {
         Vec3 sourcePosition = source.getSourcePosition();
         if (sourcePosition != null && FourthProtectionManager.crossesBoundary(target.level(), sourcePosition, targetCenter)) {
            event.setCanceled(true);
         } else {
            Entity causingEntity = source.getEntity();
            if (causingEntity != null
               && causingEntity != target
               && FourthProtectionManager.crossesBoundary(target.level(), causingEntity.getBoundingBox().getCenter(), targetCenter)) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onExplosion(Detonate event) {
      Vec3 origin = event.getExplosion().center();
      event.getAffectedEntities().removeIf(entity -> FourthProtectionManager.crossesBoundary(event.getLevel(), origin, entity.getBoundingBox().getCenter()));
      event.getAffectedBlocks().removeIf(pos -> FourthProtectionManager.crossesBoundary(event.getLevel(), origin, Vec3.atCenterOf(pos)));
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onProjectileTickPre(Pre event) {
      Entity entity = event.getEntity();
      if (!entity.level().isClientSide() && entity instanceof Projectile projectile && !projectile.isRemoved()) {
         Entity owner = projectile.getOwner();
         if (owner != null
            && FourthProtectionManager.crossesBoundary(projectile.level(), owner.getBoundingBox().getCenter(), projectile.getBoundingBox().getCenter())) {
            projectile.discard();
         } else {
            Vec3 start = projectile.position();
            Vec3 end = start.add(projectile.getDeltaMovement());
            if (FourthProtectionManager.intersectsBoundary(projectile.level(), start, end)) {
               projectile.discard();
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onProjectileTickPost(Post event) {
      Entity entity = event.getEntity();
      if (!entity.level().isClientSide() && entity instanceof Projectile projectile && !projectile.isRemoved()) {
         Vec3 previous = new Vec3(projectile.xo, projectile.yo, projectile.zo);
         if (FourthProtectionManager.intersectsBoundary(projectile.level(), previous, projectile.position())) {
            projectile.discard();
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onEntityMovementPost(Post event) {
      Entity entity = event.getEntity();
      if (!entity.level().isClientSide() && !entity.isRemoved() && !(entity instanceof Projectile) && !(entity instanceof DragonRoarCircle)) {
         if (!(entity instanceof ServerPlayer)) {
            Vec3 previousPosition = new Vec3(entity.xo, entity.yo, entity.zo);
            Vec3 currentPosition = entity.position();
            Vec3 currentCenter = entity.getBoundingBox().getCenter();
            Vec3 previousCenter = currentCenter.subtract(currentPosition.subtract(previousPosition));
            if (FourthProtectionManager.intersectsBoundary(entity.level(), previousCenter, currentCenter)) {
               entity.teleportTo(previousPosition.x, previousPosition.y, previousPosition.z);
               entity.setDeltaMovement(Vec3.ZERO);
               entity.hurtMarked = true;
            }
         }
      }
   }

   @SubscribeEvent
   public static void onServerTick(net.neoforged.neoforge.event.tick.ServerTickEvent.Post event) {
      FourthProtectionManager.tick();
   }

   @SubscribeEvent
   public static void onServerStopping(ServerStoppingEvent event) {
      FourthProtectionManager.clear();
   }

   private static EventResult onSkillGrief(ManasSkillInstance instance, Level level, Entity owner, double x, double y, double z) {
      Vec3 target = new Vec3(x + 0.5, y + 0.5, z + 0.5);
      if (instance != null && instance.getSkill() == TenreincarnationSkill.TENGEKI.get() && FourthProtectionManager.isInsideBarrier(level, target)) {
         return EventResult.interruptFalse();
      }

      if (owner == null) {
         return EventResult.pass();
      }

      Vec3 source = owner.getBoundingBox().getCenter();
      return FourthProtectionManager.crossesBoundary(level, source, target) ? EventResult.interruptFalse() : EventResult.pass();
   }

   private static boolean isTengekiDamage(DamageSource source) {
      ManasSkillInstance ability = ((TensuraDamageSource)source).tensura$getAbilityInstance();
      return ability != null && ability.getSkill() == TenreincarnationSkill.TENGEKI.get();
   }
}
