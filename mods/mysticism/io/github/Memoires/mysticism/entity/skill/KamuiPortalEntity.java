package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;

public class KamuiPortalEntity extends TensuraProjectile implements GeoEntity {
   private static final EntityDataAccessor<Boolean> TRACK_OWNER = SynchedEntityData.defineId(KamuiPortalEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> FOLLOWING_ROTATION = SynchedEntityData.defineId(KamuiPortalEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> FOLLOWING_OFFSET = SynchedEntityData.defineId(KamuiPortalEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Vector3f> FOLLOW_OFFSET_LOCAL = SynchedEntityData.defineId(KamuiPortalEntity.class, EntityDataSerializers.VECTOR3);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public KamuiPortalEntity(EntityType<? extends KamuiPortalEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.noPhysics = true;
   }

   public KamuiPortalEntity(Level level, LivingEntity entity) {
      this((EntityType<? extends KamuiPortalEntity>)MysticismSkillEntityTypes.KAMUI_PORTAL.get(), level);
      this.setOwner(entity);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(TRACK_OWNER, false);
      builder.define(FOLLOWING_ROTATION, false);
      builder.define(FOLLOWING_OFFSET, false);
      builder.define(FOLLOW_OFFSET_LOCAL, new Vector3f(0.0F, 0.0F, 0.0F));
   }

   protected void addAdditionalSaveData(CompoundTag pCompound) {
      super.addAdditionalSaveData(pCompound);
      pCompound.putBoolean("TrackOwner", this.isTrackingOwner());
      pCompound.putBoolean("FollowingRotation", this.isFollowingRotation());
      pCompound.putBoolean("FollowingOffset", this.isFollowingOffset());
   }

   protected void readAdditionalSaveData(CompoundTag pCompound) {
      super.readAdditionalSaveData(pCompound);
      this.setTrackOwner(pCompound.getBoolean("TrackOwner"));
      this.setFollowingRotation(pCompound.getBoolean("FollowingRotation"));
      this.setFollowingOffset(pCompound.getBoolean("FollowingOffset"));
   }

   public void setFollowOffsetLocal(Vec3 off) {
      this.entityData.set(FOLLOW_OFFSET_LOCAL, new Vector3f((float)off.x, (float)off.y, (float)off.z));
   }

   public Vec3 getFollowOffsetLocal() {
      Vector3f v = (Vector3f)this.entityData.get(FOLLOW_OFFSET_LOCAL);
      return new Vec3(v.x, v.y, v.z);
   }

   public void setTrackOwner(boolean follow) {
      this.getEntityData().set(TRACK_OWNER, follow);
   }

   public boolean isTrackingOwner() {
      return (Boolean)this.getEntityData().get(TRACK_OWNER);
   }

   public void setFollowingRotation(boolean follow) {
      this.getEntityData().set(FOLLOWING_ROTATION, follow);
   }

   public boolean isFollowingRotation() {
      return (Boolean)this.getEntityData().get(FOLLOWING_ROTATION);
   }

   public void setFollowingOffset(boolean follow) {
      this.getEntityData().set(FOLLOWING_OFFSET, follow);
   }

   public boolean isFollowingOffset() {
      return (Boolean)this.getEntityData().get(FOLLOWING_OFFSET);
   }

   protected boolean canHitEntity(Entity pTarget) {
      return false;
   }

   @NotNull
   public EntityDimensions getDimensions(Pose pPose) {
      return this.getType().getDimensions().scale(this.getSize(), 1.0F);
   }

   public boolean shouldDiscardInLava() {
      return false;
   }

   public boolean shouldDiscardInWater() {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public boolean isOnFire() {
      return false;
   }

   public void refreshDimensions() {
      double d0 = this.getX();
      double d1 = this.getY();
      double d2 = this.getZ();
      super.refreshDimensions();
      this.setPos(d0, d1, d2);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "loopController",
            0,
            event -> this.getLife() - this.getAge() < 20
               ? event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.kamui_portal.close"))
               : event.setAndContinue(RawAnimation.begin().thenLoop("animation.kamui_portal.loop"))
         )
      );
      controllers.add(
         new AnimationController(this, "controller", 0, event -> PlayState.STOP)
            .triggerableAnim("start", RawAnimation.begin().then("animation.kamui_portal.open", LoopType.PLAY_ONCE))
      );
   }

   public void tick() {
      super.tick();
      Entity owner = this.getOwner();
      if (owner instanceof LivingEntity le) {
         if (this.isFollowingRotation()) {
            this.setRot(owner.getYHeadRot(), 0.0F);
         }

         if (this.isTrackingOwner()) {
            this.teleportTo(le.getX(), le.getY() + 1.0, le.getZ());
         }

         if (this.isFollowingOffset()) {
            Vec3 local = this.getFollowOffsetLocal();
            float yawRad = (float)Math.toRadians(le.getYHeadRot());
            Vec3 world = local.yRot(-yawRad);
            this.teleportTo(le.getX() + world.x, le.getY() + world.y, le.getZ() + world.z);
         }
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public static void summonPortalOnEntity(
      float radius, int life, LivingEntity entity, LivingEntity owner, @Nullable ManasSkill skill, boolean followRot, boolean track
   ) {
      Level level = entity.level();
      KamuiPortalEntity portal = new KamuiPortalEntity(level, owner);
      portal.setSize(radius);
      if (followRot) {
         portal.setRot(entity.getYHeadRot(), 0.0F);
         portal.setFollowingRotation(followRot);
      }

      portal.setTrackOwner(track);
      portal.setLife(life);
      Vec3 position = new Vec3(entity.getX(), entity.getY() + 1.0, entity.getZ());
      portal.setPos(position);
      portal.setSkill(SkillUtils.getSkillOrNull(entity, skill));
      entity.level().addFreshEntity(portal);
   }

   public static void summonPortalAtLocation(
      float radius, int life, LivingEntity owner, double x, double y, double z, @Nullable ManasSkill skill, boolean followRot, boolean track
   ) {
      Level level = owner.level();
      KamuiPortalEntity portal = new KamuiPortalEntity(level, owner);
      portal.setSize(radius);
      portal.setLife(life);
      if (followRot) {
         portal.setRot(owner.getYHeadRot(), 0.0F);
         portal.setFollowingRotation(followRot);
      }

      portal.setTrackOwner(track);
      portal.setPos(x, y, z);
      portal.setSkill(SkillUtils.getSkillOrNull(owner, skill));
      owner.level().addFreshEntity(portal);
   }
}
