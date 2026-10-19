package com.blackgear.vanillabackport.common.api.leash;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Leashable.LeashData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class LeashPhysics {
   public static final double DEFAULT_SNAP_DISTANCE = 12.0;
   public static final double DEFAULT_ELASTIC_DISTANCE = 6.0;
   public static final Vec3 AXIS_SPECIFIC_ELASTICITY = new Vec3(0.8, 0.2, 0.8);
   private static final double STIFFNESS = 0.11;
   private static final List<Vec3> ENTITY_ATTACHMENT = ImmutableList.of(new Vec3(0.0, 0.5, 0.5));
   private static final List<Vec3> LEASHER_ATTACHMENT = ImmutableList.of(new Vec3(0.0, 0.5, 0.0));
   private static final List<Vec3> QUAD_ATTACHMENT = ImmutableList.of(
      new Vec3(-0.5, 0.5, 0.5), new Vec3(-0.5, 0.5, -0.5), new Vec3(0.5, 0.5, -0.5), new Vec3(0.5, 0.5, 0.5)
   );

   private LeashPhysics() {
   }

   public static double snapDistance(Entity entity) {
      return entity instanceof LeashableCallback cb ? cb.vb$leashSnapDistance() : 12.0;
   }

   public static double elasticDistance(Entity entity) {
      return entity instanceof LeashableCallback cb ? cb.vb$leashElasticDistance() : 6.0;
   }

   public static double distanceBetween(Entity a, Entity b) {
      return b.getBoundingBox().getCenter().distanceTo(a.getBoundingBox().getCenter());
   }

   public static boolean canAttachLeash(Leashable leashable, Entity holder) {
      return leashable == holder ? false : distanceBetween((Entity)leashable, holder) <= snapDistance((Entity)leashable) && leashable.canBeLeashed();
   }

   private static List<Vec3> resolveHolderAttachmentPoints(Entity holder, boolean quad) {
      if (!quad) {
         return LEASHER_ATTACHMENT;
      } else {
         return holder instanceof LeashHolderCallback cb ? Arrays.asList(cb.vb$getQuadLeashHolderOffsets()) : QUAD_ATTACHMENT;
      }
   }

   private static List<Vec3> resolveEntityAttachmentPoints(Entity entity, boolean quad) {
      if (!quad) {
         return ENTITY_ATTACHMENT;
      } else if (entity instanceof LeashableCallback cb) {
         return Arrays.asList(cb.vb$getQuadLeashOffsets());
      } else {
         Vec3[] registry = QuadLeashRegistry.getOffsets(entity);
         return registry != null ? Arrays.asList(registry) : QUAD_ATTACHMENT;
      }
   }

   private static boolean isQuadConnection(Entity entity, Entity holder) {
      boolean entitySupports = entity instanceof LeashableCallback cb ? cb.vb$supportsQuadLeash() : QuadLeashRegistry.supports(entity);
      boolean holderSupports = holder instanceof LeashHolderCallback cb ? cb.vb$supportsQuadLeashAsHolder() : QuadLeashRegistry.supportsAsHolder(holder);
      return entitySupports && holderSupports;
   }

   public static boolean supportsQuadLeash(Entity entity) {
      return entity instanceof LeashableCallback cb ? cb.vb$supportsQuadLeash() : QuadLeashRegistry.supports(entity);
   }

   public static boolean supportsQuadLeashAsHolder(Entity entity) {
      return entity instanceof LeashHolderCallback cb ? cb.vb$supportsQuadLeashAsHolder() : QuadLeashRegistry.supportsAsHolder(entity);
   }

   public static Vec3[] getQuadLeashOffsets(Entity entity) {
      if (entity instanceof LeashableCallback cb) {
         return cb.vb$getQuadLeashOffsets();
      } else {
         Vec3[] registry = QuadLeashRegistry.getOffsets(entity);
         return registry != null ? registry : createQuadOffsets(entity, 0.0, 0.5, 0.5, 0.5);
      }
   }

   public static Vec3[] getQuadLeashHolderOffsets(Entity entity) {
      return entity instanceof LeashHolderCallback cb ? cb.vb$getQuadLeashHolderOffsets() : createQuadOffsets(entity, 0.0, 0.5, 0.5, 0.0);
   }

   public static float getPreciseBodyRotation(Entity entity, float partialTicks) {
      return entity instanceof LivingEntity living
         ? Mth.lerp(partialTicks, living.yBodyRotO, living.yBodyRot)
         : Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
   }

   public static float angularFriction(Entity entity) {
      if (entity.onGround()) {
         return entity.level().getBlockState(entity.getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.91F;
      } else {
         return entity.isInLiquid() ? 0.8F : 0.91F;
      }
   }

   public static Vec3 holderMovement(Entity holder) {
      return holder instanceof Mob mob && mob.isNoAi() ? Vec3.ZERO : holder.getKnownMovement();
   }

   public static <E extends Entity & Leashable> boolean checkElasticInteractions(E entity, Entity holder, LeashData data) {
      boolean quad = isQuadConnection(entity, holder);
      List<Vec3> entityPts = resolveEntityAttachmentPoints(entity, quad);
      List<Vec3> holderPts = resolveHolderAttachmentPoints(holder, quad);
      List<LeashPhysics.Wrench> wrenches = computeElasticInteraction(entity, holder, entityPts, holderPts);
      if (wrenches.isEmpty()) {
         return false;
      }

      LeashPhysics.Wrench result = LeashPhysics.Wrench.accumulate(wrenches).scale(quad ? 0.25 : 1.0);
      LeashDataAccess acc = (LeashDataAccess)data;
      acc.vb$setAngularMomentum(acc.vb$getAngularMomentum() + 10.0 * result.torque());
      Vec3 relVel = holderMovement(holder).subtract(entity.getKnownMovement());
      entity.addDeltaMovement(result.force().multiply(AXIS_SPECIFIC_ELASTICITY).add(relVel.scale(0.11)));
      return true;
   }

   private static <E extends Entity & Leashable> List<LeashPhysics.Wrench> computeElasticInteraction(
      E entity, Entity holder, List<Vec3> entityPts, List<Vec3> holderPts
   ) {
      float eYaw = entity.getYRot() * (float) (Math.PI / 180.0);
      float hYaw = holder.getYRot() * (float) (Math.PI / 180.0);
      Vec3 eDim = new Vec3(entity.getBbWidth(), entity.getBbHeight(), entity.getBbWidth());
      Vec3 hDim = new Vec3(holder.getBbWidth(), holder.getBbHeight(), holder.getBbWidth());
      Vec3 eMove = holderMovement(entity);
      List<LeashPhysics.Wrench> out = new ArrayList<>();

      for (int i = 0; i < entityPts.size(); i++) {
         Vec3 eOff = entityPts.get(i).multiply(eDim).yRot(-eYaw);
         Vec3 hOff = holderPts.get(i).multiply(hDim).yRot(-hYaw);
         dampenedSpring(holder.position().add(hOff), entity.position().add(eOff), elasticDistance(entity), eMove, eOff).ifPresent(out::add);
      }

      return out;
   }

   private static Optional<LeashPhysics.Wrench> dampenedSpring(Vec3 pivot, Vec3 pos, double slack, Vec3 motion, Vec3 lever) {
      double dist = pos.distanceTo(pivot);
      if (dist < slack) {
         return Optional.empty();
      }

      Vec3 force = pivot.subtract(pos).normalize().scale(dist - slack);
      double torque = LeashPhysics.Wrench.torqueFromForce(lever, force);
      if (motion.dot(force) >= 0.0) {
         force = force.scale(0.3);
      }

      return Optional.of(new LeashPhysics.Wrench(force, torque));
   }

   public static void resetAngularMomentum(Leashable leashable) {
      LeashData data = leashable.getLeashData();
      if (data != null) {
         ((LeashDataAccess)data).vb$setAngularMomentum(0.0);
      }
   }

   public static Vec3[] createQuadOffsets(Entity entity, double fwd, double side, double w, double h) {
      float bw = entity.getBbWidth();
      double f = fwd * bw;
      double s = side * bw;
      double wi = w * bw;
      double hi = h * entity.getBbHeight();
      return new Vec3[]{new Vec3(-wi, hi, s + f), new Vec3(-wi, hi, -s + f), new Vec3(wi, hi, -s + f), new Vec3(wi, hi, s + f)};
   }

   public static List<Leashable> leashedTo(Entity entity) {
      return inArea(entity, l -> l.getLeashHolder() == entity);
   }

   public static List<Leashable> inArea(Entity entity, Predicate<Leashable> test) {
      return inArea(entity.level(), entity.getBoundingBox().getCenter(), test);
   }

   public static List<Leashable> inArea(Level level, Vec3 pos, Predicate<Leashable> test) {
      AABB box = AABB.ofSize(pos, 32.0, 32.0, 32.0);
      return level.getEntitiesOfClass(Entity.class, box, e -> e instanceof Leashable l && test.test(l)).stream().map(Leashable.class::cast).toList();
   }

   public static boolean dropAllConnections(Entity entity, @Nullable Player player) {
      List<Leashable> leashed = leashedTo(entity);
      boolean any = !leashed.isEmpty();
      if (entity instanceof Leashable self && self.isLeashed()) {
         self.dropLeash(true, true);
         any = true;
      }

      for (Leashable l : leashed) {
         l.dropLeash(true, true);
      }

      if (any) {
         entity.gameEvent(GameEvent.SHEAR, player);
         return true;
      } else {
         return false;
      }
   }

   public record Wrench(Vec3 force, double torque) {
      public static final LeashPhysics.Wrench ZERO = new LeashPhysics.Wrench(Vec3.ZERO, 0.0);

      public static double torqueFromForce(Vec3 lever, Vec3 force) {
         return lever.z * force.x - lever.x * force.z;
      }

      public static LeashPhysics.Wrench accumulate(List<LeashPhysics.Wrench> list) {
         if (list.isEmpty()) {
            return ZERO;
         }

         double x = 0.0;
         double y = 0.0;
         double z = 0.0;
         double t = 0.0;

         for (LeashPhysics.Wrench w : list) {
            x += w.force.x;
            y += w.force.y;
            z += w.force.z;
            t += w.torque;
         }

         return new LeashPhysics.Wrench(new Vec3(x, y, z), t);
      }

      public LeashPhysics.Wrench scale(double f) {
         return new LeashPhysics.Wrench(this.force.scale(f), this.torque * f);
      }
   }
}
