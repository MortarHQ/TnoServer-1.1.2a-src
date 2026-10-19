package net.unusual.block_factorys_bosses.entity.decoration;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.neoforged.neoforge.registries.DeferredItem;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.projectile.CannonballEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.item.CannonEntityItem;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.stateless.StatelessAnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CannonEntity extends BlockAttachedEntity implements GeoEntity {
   public static String NO_BALLS_MESSAGE = "message.block_factorys_bosses.cannon.no_balls";
   protected static final EntityDataAccessor<Integer> HURT_ANIMATION_TIMER = SynchedEntityData.defineId(CannonEntity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> HURT_ANIMATION_DIRECTION = SynchedEntityData.defineId(CannonEntity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> CORE_Y_ROT = SynchedEntityData.defineId(CannonEntity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(CannonEntity.class, EntityDataSerializers.FLOAT);
   protected static final float Y_VIEW_HALF_RANGE = 40.0F;
   protected static final float X_VIEW_HALF_RANGE = 60.0F;
   protected static final int ANIM_TIME = 60;
   protected static final int RELOAD_SOUND_AT = 50;
   private final DeferredItem<CannonEntityItem> item;
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   @Nullable
   public Vector3d bonePos = null;
   @Nullable
   protected BlockPos attachment = null;
   protected int attachDiff = -1;
   protected int timer = -1;
   public static final RawAnimation FIRE = RawAnimation.begin().thenPlay("animation.bf_br_kraken_canon_tpp.fire");
   public static final RawAnimation RELOAD = RawAnimation.begin().thenPlay("animation.bf_br_kraken_canon_tpp.reload");

   public CannonEntity(EntityType<CannonEntity> type, Level world, DeferredItem<CannonEntityItem> item) {
      super(type, world);
      this.item = item;
   }

   public void setAttachDiff(int attachDiff) {
      this.attachDiff = attachDiff;
   }

   @Nullable
   public ItemStack getPickResult() {
      return this.item.toStack();
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(HURT_ANIMATION_TIMER, 0);
      builder.define(HURT_ANIMATION_DIRECTION, 1);
      builder.define(DAMAGE, 0.0F);
      builder.define(CORE_Y_ROT, 0);
   }

   public void setHurtTime(int hurtTime) {
      this.entityData.set(HURT_ANIMATION_TIMER, hurtTime);
   }

   public void setHurtDir(int hurtDir) {
      this.entityData.set(HURT_ANIMATION_DIRECTION, hurtDir);
   }

   public void setDamage(float damage) {
      this.entityData.set(DAMAGE, damage);
   }

   public float getDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public int getHurtTime() {
      return (Integer)this.entityData.get(HURT_ANIMATION_TIMER);
   }

   public int getHurtDir() {
      return (Integer)this.entityData.get(HURT_ANIMATION_DIRECTION);
   }

   public void setCoreYRot(int coreYRot) {
      this.entityData.set(CORE_Y_ROT, coreYRot);
   }

   public int getCoreYRot() {
      return (Integer)this.entityData.get(CORE_Y_ROT);
   }

   public boolean causeFallDamage(float l, float d, DamageSource source) {
      return false;
   }

   protected void recalculateBoundingBox() {
      Vec3 center = this.position();
      double width = this.getBbWidth();
      double height = this.getBbHeight();
      this.setBoundingBox(new AABB(center.x - width / 2.0, center.y, center.z - width / 2.0, center.x + width / 2.0, center.y + height, center.z + width / 2.0));
   }

   public boolean survives() {
      return !this.level().getBlockState(this.blockPosition().relative(Direction.UP, this.attachDiff)).canBeReplaced();
   }

   public void tick() {
      if (this.getHurtTime() > 0) {
         this.setHurtTime(this.getHurtTime() - 1);
      }

      if (this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      super.tick();
      if (this.isControlledByLocalInstance() && this.level().isClientSide) {
         this.controlCannon();
      }

      if (this.level() instanceof ServerLevel) {
         if (this.timer == 50) {
            this.playSound((SoundEvent)BossesRiseSounds.CANNON_RELOAD.value(), 1.0F, 1.0F);
         }

         this.timer--;
      }
   }

   private void controlCannon() {
      if (this.getControllingPassenger() != null) {
         this.xRotO = this.getXRot();
         this.setXRot(Mth.approachDegrees(this.getXRot(), this.getControllingPassenger().getXRot(), 4.0F));
         this.setYRot(Mth.approachDegrees(this.getYRot() + 90.0F, this.getControllingPassenger().getYRot() + 90.0F, 4.0F) - 90.0F);
      }
   }

   public void move(MoverType type, Vec3 pos) {
   }

   public boolean skipAttackInteraction(Entity entity) {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.getControllingPassenger() instanceof KrakenTentacleEntity tentacle) {
         return tentacle.hurt(source, amount);
      } else {
         if (this.level().isClientSide || this.isRemoved()) {
            return true;
         }

         if (this.isInvulnerableTo(source)) {
            return false;
         }

         Player sourcePlayer = source.getEntity() instanceof Player p ? p : null;
         if (sourcePlayer != null && !sourcePlayer.getAbilities().mayBuild) {
            return false;
         }

         if (sourcePlayer != null && sourcePlayer.getWeaponItem().is(Items.MELEE_WEAPON_TOOLS)) {
            return false;
         }

         this.setHurtDir(-this.getHurtDir());
         this.setHurtTime(10);
         this.markHurt();
         this.setDamage(this.getDamage() + amount * 10.0F);
         this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
         boolean creativePlayer = sourcePlayer != null && sourcePlayer.getAbilities().instabuild;
         if (!creativePlayer && this.getDamage() > 40.0F) {
            this.kill();
            this.dropItem(sourcePlayer);
         } else if (creativePlayer) {
            this.discard();
         }

         return true;
      }
   }

   public boolean ignoreExplosion(Explosion explosion) {
      return true;
   }

   public boolean fireImmune() {
      return true;
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return this.getFirstPassenger() instanceof LivingEntity living ? living : null;
   }

   protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
      if (entity instanceof KrakenTentacleEntity tentacle) {
         return new Vec3(-1.75, -12.0, 0.0).yRot((-tentacle.getYRot() - 180.0F) * (float) (Math.PI / 180.0));
      } else {
         Vec3 pivotPoint = new Vec3(0.0, this.getEyeHeight(), 0.0);
         Vec3 offset = new Vec3(0.0, 0.75, 1.75);
         return pivotPoint.add(offset.xRot(this.getXRot() * (float) (Math.PI / 180.0)).yRot((-this.getYRot() - 180.0F) * (float) (Math.PI / 180.0)));
      }
   }

   public void onPassengerTurned(Entity entityToUpdate) {
      if (!(entityToUpdate instanceof KrakenTentacleEntity)) {
         this.clampRotation(entityToUpdate);
      }
   }

   protected void clampRotation(Entity entityToUpdate) {
      entityToUpdate.setYBodyRot(this.getYRot());
      float yWrap = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getCoreYRot());
      float yClamp = Mth.clamp(yWrap, -40.0F, 40.0F);
      entityToUpdate.yRotO += yClamp - yWrap;
      entityToUpdate.setYRot(entityToUpdate.getYRot() + yClamp - yWrap);
      entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
      entityToUpdate.setXRot(Math.clamp(entityToUpdate.getXRot(), -60.0F, 60.0F));
      entityToUpdate.xRotO = Math.clamp(entityToUpdate.xRotO, -60.0F, 60.0F);
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      InteractionResult interactionresult = super.interact(player, hand);
      if (interactionresult != InteractionResult.PASS) {
         return interactionresult;
      }

      if (player.isSecondaryUseActive()) {
         return InteractionResult.PASS;
      }

      if (this.level().isClientSide) {
         return InteractionResult.SUCCESS;
      }

      if (this.getControllingPassenger() == player) {
         if (this.timer >= 0) {
            return InteractionResult.PASS;
         }

         if (!player.hasInfiniteMaterials()) {
            ItemStack cannonBall = null;
            if (player.getItemInHand(hand).is((Item)BossesRiseItems.CANNONBALL.get())) {
               cannonBall = player.getItemInHand(hand);
            } else {
               Inventory inventory = player.getInventory();

               for (ItemStack stack : inventory.items) {
                  if (stack.is((Item)BossesRiseItems.CANNONBALL.get())) {
                     cannonBall = stack;
                     break;
                  }
               }
            }

            if (cannonBall == null) {
               player.displayClientMessage(Component.translatable(NO_BALLS_MESSAGE), true);
               return InteractionResult.SUCCESS;
            }

            cannonBall.shrink(1);
         }

         this.triggerAnim("main_controller", "fire");
         CannonballEntity cannonball = new CannonballEntity((EntityType<? extends CannonballEntity>)BossesRiseEntities.CANNONBALL.get(), this.level());
         cannonball.setOwner(player);
         cannonball.setPos(this.getX(), this.getEyeY(), this.getZ());
         cannonball.shootFromRotation(this, this.getXRot(), this.getYRot(), 0.0F, 2.5F, 1.0F);
         cannonball.setYRot(this.getYRot());
         cannonball.setXRot(this.getXRot());
         this.level().addFreshEntity(cannonball);
         this.playSound((SoundEvent)BossesRiseSounds.CANNON_FIRE.value(), 1.0F, 1.0F);
         this.timer = 60;
         return InteractionResult.SUCCESS;
      } else if (player.startRiding(this)) {
         this.playSound((SoundEvent)BossesRiseSounds.CANNON_SEAT.value(), 1.0F, 1.0F);
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }

   public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
      this.playSound((SoundEvent)BossesRiseSounds.CANNON_DISMOUNT.value(), 1.0F, 1.0F);
      return this.position().add(new Vec3(0.0, 0.0, 2.0).yRot((-this.getCoreYRot() - 180.0F) * (float) (Math.PI / 180.0)));
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("hurt", (Integer)this.entityData.get(HURT_ANIMATION_TIMER));
      compound.putInt("hurt_dir", (Integer)this.entityData.get(HURT_ANIMATION_DIRECTION));
      compound.putFloat("damage", (Float)this.entityData.get(DAMAGE));
      compound.putInt("core_y_rot", (Integer)this.entityData.get(CORE_Y_ROT));
      compound.putInt("attachment", this.attachDiff);
      compound.putInt("anim_time", this.timer);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      if (compound.contains("hurt")) {
         this.entityData.set(HURT_ANIMATION_TIMER, compound.getInt("hurt"));
      }

      if (compound.contains("hurt_dir")) {
         this.entityData.set(HURT_ANIMATION_DIRECTION, compound.getInt("hurt_dir"));
      }

      if (compound.contains("damage")) {
         this.entityData.set(DAMAGE, compound.getFloat("damage"));
      }

      if (compound.contains("core_y_rot")) {
         this.entityData.set(CORE_Y_ROT, compound.getInt("core_y_rot"));
      }

      if (compound.contains("attachment")) {
         this.attachDiff = compound.getInt("attachment");
      }

      if (compound.contains("anim_time")) {
         this.timer = compound.getInt("anim_time");
      }

      this.pos = this.blockPosition();
   }

   public float rotate(Rotation transformRotation) {
      this.setCoreYRot((int)SpatialUtil.rotateDegrees(this.getCoreYRot(), transformRotation));
      return super.rotate(transformRotation);
   }

   public float mirror(Mirror transformMirror) {
      this.setCoreYRot((int)SpatialUtil.mirrorDegrees(this.getCoreYRot(), transformMirror));
      return super.mirror(transformMirror);
   }

   public void dropItem(@Nullable Entity entity) {
      if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
         this.playSound(SoundEvents.HEAVY_CORE_BREAK, 1.0F, 1.0F);
         if (entity instanceof Player player && player.hasInfiniteMaterials()) {
            return;
         }

         this.spawnAtLocation((ItemLike)this.item.get());
      }
   }

   public boolean canRiderInteract() {
      return true;
   }

   public boolean canCollideWith(Entity entity) {
      return true;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public AABB getBoundingBoxForCulling() {
      return this.getBoundingBox().inflate(2.0);
   }

   public void setNoGravity(boolean ignored) {
      super.setNoGravity(true);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      StatelessAnimationController controller = new StatelessAnimationController(this, "main_controller");
      controller.triggerableAnim("fire", FIRE);
      controller.triggerableAnim("reload", RELOAD);
      controllers.add(controller);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class Listener {
      @SubscribeEvent
      public static void onEmptyInteract(RightClickEmpty event) {
         if (event.getEntity().getVehicle() instanceof CannonEntity cannon && Minecraft.getInstance().gameMode != null) {
            Minecraft.getInstance().gameMode.interact(event.getEntity(), cannon, event.getHand());
         }
      }
   }
}
