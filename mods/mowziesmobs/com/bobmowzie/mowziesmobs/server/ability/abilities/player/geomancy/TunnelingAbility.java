package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.sound.IGeomancyRumbler;
import com.bobmowzie.mowziesmobs.datagen.MMBlockTags;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBlockSwapper;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.item.ItemEarthrendGauntlet;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

public class TunnelingAbility extends PlayerAbility implements IGeomancyRumbler {
   private int doubleTapTimer = 0;
   public boolean prevUnderground;
   public BlockState justDug = Blocks.DIRT.defaultBlockState();
   boolean underground = false;
   private float spinAmount = 0.0F;
   private float pitch = 0.0F;
   private int timeUnderground = 0;
   private int timeAboveGround = 0;
   private InteractionHand whichHand;
   private ItemStack gauntletStack;
   private boolean isRumbling;
   private static final RawAnimation FALL_ANIM = RawAnimation.begin().thenPlayAndHold("tunneling_fall");
   private static final RawAnimation DRILL_ANIM = RawAnimation.begin().thenLoop("tunneling_drill");

   public TunnelingAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(abilityType, user, new AbilitySection[]{new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE)});
   }

   @Override
   public void tickNotUsing() {
      super.tickNotUsing();
      if (this.doubleTapTimer > 0) {
         this.doubleTapTimer--;
      }
   }

   public void playGauntletAnimation() {
      if (this.getUser() != null
         && !this.getLevel().isClientSide()
         && this.gauntletStack != null
         && this.gauntletStack.getItem() == ItemHandler.EARTHREND_GAUNTLET.get()) {
         Player player = this.getUser();
         ((ItemEarthrendGauntlet)ItemHandler.EARTHREND_GAUNTLET.get())
            .triggerAnim(player, GeoItem.getOrAssignId(this.gauntletStack, (ServerLevel)player.level()), "controller", "open");
      }
   }

   public void stopGauntletAnimation() {
      if (this.getUser() != null
         && !this.getLevel().isClientSide()
         && this.gauntletStack != null
         && this.gauntletStack.getItem() == ItemHandler.EARTHREND_GAUNTLET.get()) {
         Player player = this.getUser();
         ((ItemEarthrendGauntlet)ItemHandler.EARTHREND_GAUNTLET.get())
            .triggerAnim(player, GeoItem.getOrAssignId(this.gauntletStack, (ServerLevel)player.level()), "controller", "idle");
      }
   }

   @Override
   public void start() {
      super.start();
      this.underground = false;
      this.prevUnderground = false;
      if (this.getUser().onGround()) {
         this.getUser().push(0.0, 0.8F, 0.0);
      }

      this.whichHand = this.getUser().getUsedItemHand();
      this.gauntletStack = this.getUser().getUseItem();
      if (this.getUser().level().isClientSide()) {
         this.spinAmount = 0.0F;
         this.pitch = 0.0F;
      }

      if (this.getLevel().isClientSide()) {
         MMCommon.PROXY.playGeomancyRumbleSound(this);
      }
   }

   public boolean damageGauntlet() {
      ItemStack stack = this.getUser().getUseItem();
      if (stack.getItem() == ItemHandler.EARTHREND_GAUNTLET.get()) {
         InteractionHand handIn = this.getUser().getUsedItemHand();
         if (stack.getDamageValue() + 5 < stack.getMaxDamage()) {
            stack.hurtAndBreak(5, this.getUser(), LivingEntity.getSlotForHand(handIn));
            return true;
         }

         if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.breakable.get()) {
            stack.hurtAndBreak(5, this.getUser(), LivingEntity.getSlotForHand(handIn));
         }

         return false;
      } else {
         return false;
      }
   }

   public void restoreGauntlet(ItemStack stack) {
      if (stack.getItem() == ItemHandler.EARTHREND_GAUNTLET.get() && !(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.breakable.get()) {
         stack.setDamageValue(Math.max(stack.getDamageValue() - 1, 0));
      }
   }

   @Override
   public void tick() {
      super.tick();
      AbilityData data = DataHandler.getData(this.getUser(), DataHandler.ABILITY_DATA);
      if (data.getActiveAbility() == null
         || data.getActiveAbility().getAbilityType() != AbilityHandler.SPAWN_PILLAR_ABILITY
            && data.getActiveAbility().getAbilityType() != AbilityHandler.TUNNELING_ABILITY) {
         Player player = this.getUser();

         for (ItemStack stack : player.getInventory().items) {
            this.restoreGauntlet(stack);
         }

         for (ItemStack stack : player.getInventory().offhand) {
            this.restoreGauntlet(stack);
         }
      }
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      this.getUser().getAbilities().flying = false;
      this.underground = !this.getUser()
         .level()
         .getEntitiesOfClass(EntityBlockSwapper.EntityBlockSwapperTunneling.class, this.getUser().getBoundingBox().inflate(0.3))
         .isEmpty();
      Vec3 lookVec = this.getUser().getLookAngle();
      float tunnelSpeed = 0.3F;
      ItemStack stack = this.getUser().getUseItem();
      boolean usingGauntlet = stack.getItem() == ItemHandler.EARTHREND_GAUNTLET.get();
      if (this.underground) {
         this.timeUnderground++;
         if (usingGauntlet && this.damageGauntlet()) {
            this.getUser().setDeltaMovement(lookVec.normalize().scale(tunnelSpeed));
         } else {
            this.getUser().setDeltaMovement(lookVec.multiply(0.3, 0.0, 0.3).add(0.0, 1.0, 0.0).normalize().scale(tunnelSpeed));
         }

         for (LivingEntity entityHit : this.getEntityLivingBaseNearby(this.getUser(), 2.0, 2.0, 2.0, 2.0)) {
            DamageSource damageSource = this.getUser().damageSources().playerAttack(this.getUser());
            entityHit.hurt(damageSource, 3.0F * ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.attackMultiplier.get()).floatValue());
         }
      } else {
         this.timeAboveGround++;
         this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().subtract(0.0, 0.07, 0.0));
         if (this.getUser().getDeltaMovement().y() < -1.3) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().x(), -1.3, this.getUser().getDeltaMovement().z());
         }
      }

      if (this.underground && (this.prevUnderground || lookVec.y < 0.0) && this.timeAboveGround > 5
         || this.getTicksInUse() > 1 && usingGauntlet && lookVec.y < 0.0 && stack.getDamageValue() + 5 < stack.getMaxDamage()) {
         Vec3 userCenter = this.getUser().position().add(0.0, this.getUser().getBbHeight() / 2.0F, 0.0);
         float radius = 2.0F;
         AABB aabb = new AABB(-radius, -radius, -radius, radius, radius, radius);
         aabb = aabb.move(userCenter);

         for (int i = 0; i < this.getUser().getDeltaMovement().length() * 4.0; i++) {
            for (int x = (int)Math.floor(aabb.minX); x <= Math.floor(aabb.maxX); x++) {
               for (int y = (int)Math.floor(aabb.minY); y <= Math.floor(aabb.maxY); y++) {
                  for (int z = (int)Math.floor(aabb.minZ); z <= Math.floor(aabb.maxZ); z++) {
                     Vec3 posVec = new Vec3(x, y, z);
                     if (!(posVec.add(0.5, 0.5, 0.5).subtract(userCenter).lengthSqr() > radius * radius)) {
                        Vec3 motionScaled = this.getUser().getDeltaMovement().normalize().scale(i);
                        posVec = posVec.add(motionScaled);
                        BlockPos pos = new BlockPos((int)posVec.x, (int)posVec.y, (int)posVec.z);
                        BlockState blockState = this.getUser().level().getBlockState(pos);
                        if (EffectGeomancy.checkBlock(blockState, MMBlockTags.GEOMANCY_TUNNELABLE) && blockState.getBlock() != Blocks.BEDROCK) {
                           this.justDug = blockState;
                           if (!this.getLevel().isClientSide) {
                              EntityBlockSwapper.EntityBlockSwapperTunneling swapper = new EntityBlockSwapper.EntityBlockSwapperTunneling(
                                 (EntityType<? extends EntityBlockSwapper.EntityBlockSwapperTunneling>)EntityHandler.BLOCK_SWAPPER_TUNNELING.get(),
                                 this.getLevel(),
                                 pos,
                                 Blocks.AIR.defaultBlockState(),
                                 15,
                                 false,
                                 false,
                                 this.getUser()
                              );
                              this.getLevel().addFreshEntity(swapper);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      this.isRumbling = this.underground;
      if (!this.prevUnderground && this.underground) {
         this.timeUnderground = 0;
         this.getUser()
            .playSound(
               (SoundEvent)((DeferredHolder)MMSounds.EFFECT_GEOMANCY_BREAK_MEDIUM.get(this.rand.nextInt(3))).get(), 1.0F, 0.9F + this.rand.nextFloat() * 0.1F
            );
         if (this.getUser().level().isClientSide) {
            AdvancedParticleBase.spawnParticle(
               this.getUser().level(),
               ParticleHandler.RING2,
               (float)this.getUser().getX(),
               (float)this.getUser().getY() + 0.02F,
               (float)this.getUser().getZ(),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               3.5,
               0.83F,
               1.0,
               0.39F,
               1.0,
               1.0,
               10.0,
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(10.0F, 30.0F), false
                  )
               }
            );
         }

         this.playGauntletAnimation();
      }

      if (this.prevUnderground && !this.underground) {
         this.timeAboveGround = 0;
         this.getUser().playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK.get(), 1.0F, 0.9F + this.rand.nextFloat() * 0.1F);
         if (this.getUser().level().isClientSide) {
            AdvancedParticleBase.spawnParticle(
               this.getUser().level(),
               ParticleHandler.RING2,
               (float)this.getUser().getX(),
               (float)this.getUser().getY() + 0.02F,
               (float)this.getUser().getZ(),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               3.5,
               0.83F,
               1.0,
               0.39F,
               1.0,
               1.0,
               10.0,
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(10.0F, 30.0F), false
                  )
               }
            );
         }

         if (this.timeUnderground > 10) {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().scale(10.0));
         } else {
            this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().multiply(3.0, 7.0, 3.0));
         }

         for (int i = 0; i < 6; i++) {
            if (this.justDug == null) {
               this.justDug = Blocks.DIRT.defaultBlockState();
            }

            EntityFallingBlock fallingBlock = new EntityFallingBlock((EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.getUser().level(), 80, this.justDug);
            fallingBlock.setPos(this.getUser().getX(), this.getUser().getY() + 1.0, this.getUser().getZ());
            fallingBlock.setDeltaMovement(
               this.getUser().getRandom().nextFloat() * 0.8F - 0.4F,
               0.4F + this.getUser().getRandom().nextFloat() * 0.8F,
               this.getUser().getRandom().nextFloat() * 0.8F - 0.4F
            );
            this.getUser().level().addFreshEntity(fallingBlock);
         }

         this.stopGauntletAnimation();
      }

      this.prevUnderground = this.underground;
   }

   @Override
   public void end() {
      super.end();
      this.stopGauntletAnimation();
   }

   @Override
   public boolean canUse() {
      return (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.enableTunneling.get()
         && super.canUse()
         && (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.enableTunneling.get();
   }

   @Override
   protected boolean canContinueUsing() {
      ItemStack stack = this.getUser().getUseItem();
      boolean usingGauntlet = stack.getItem() == ItemHandler.EARTHREND_GAUNTLET.get();
      return this.whichHand == null
         ? false
         : (this.getTicksInUse() <= 1 || !this.getUser().onGround() && (!this.getUser().isInWater() || usingGauntlet) || this.underground)
            && this.getUser().getItemInHand(this.whichHand).getItem() == ItemHandler.EARTHREND_GAUNTLET.get()
            && super.canContinueUsing();
   }

   @Override
   public boolean preventsItemUse(ItemStack stack) {
      return stack.getItem() == ItemHandler.EARTHREND_GAUNTLET.get() ? false : super.preventsItemUse(stack);
   }

   @Override
   public <E extends GeoEntity> PlayState animationPredicate(AnimationState<E> e, GeckoPlayer.Perspective perspective) {
      e.getController().transitionLength(4);
      if (perspective == GeckoPlayer.Perspective.THIRD_PERSON) {
         float yMotionThreshold = this.getUser() == MMCommon.PROXY.getLocalPlayer() ? 1.0F : 2.0F;
         if (!this.underground
            && this.getUser().getUseItem().getItem() != ItemHandler.EARTHREND_GAUNTLET.get()
            && this.getUser().getDeltaMovement().y() < yMotionThreshold) {
            e.getController().setAnimation(FALL_ANIM);
         } else {
            e.getController().setAnimation(DRILL_ANIM);
         }
      }

      return PlayState.CONTINUE;
   }

   @Override
   public void codeAnimations(MowzieGeoModel<? extends GeoEntity> model, float partialTick) {
      super.codeAnimations(model, partialTick);
      float faceMotionController = 1.0F - model.getControllerValueInverted("FaceVelocityController");
      Vec3 moveVec = this.getUser().getDeltaMovement().normalize();
      this.pitch = (float)Mth.lerp(0.3 * partialTick, this.pitch, moveVec.y());
      MowzieGeoBone com = model.getMowzieBone("CenterOfMass");
      com.setRotX((float)((-Math.PI / 2) + (Math.PI / 2) * this.pitch) * faceMotionController);
      float spinSpeed = 0.35F;
      if (faceMotionController < 1.0F && this.spinAmount < 6.2731853071795864 && this.spinAmount > 0.01) {
         float f = (float)(((Math.PI * 2) - this.spinAmount) / (Math.PI * 2));
         f = (float)Math.pow(f, 0.5);
         this.spinAmount += partialTick * spinSpeed * f;
         if (this.spinAmount > Math.PI * 2) {
            this.spinAmount = 0.0F;
         }
      } else {
         this.spinAmount += faceMotionController * partialTick * spinSpeed;
         this.spinAmount = (float)(this.spinAmount % (Math.PI * 2));
      }

      MowzieGeoBone waist = model.getMowzieBone("Waist");
      waist.addRotY(-this.spinAmount);
   }

   @Override
   public CompoundTag writeNBT() {
      CompoundTag compound = super.writeNBT();
      if (this.isUsing() && this.whichHand != null) {
         compound.putInt("whichHand", this.whichHand.ordinal());
      }

      return compound;
   }

   @Override
   public void readNBT(Tag nbt) {
      super.readNBT(nbt);
      if (this.isUsing()) {
         CompoundTag compound = (CompoundTag)nbt;
         this.whichHand = InteractionHand.values()[compound.getInt("whichHand")];
      }
   }

   @Override
   public void onFall(LivingFallEvent event) {
      super.onFall(event);
      if (event.getEntity() == this.getUser() && this.isUsing()) {
         event.setDamageMultiplier(0.0F);
      }
   }

   @Override
   public boolean isRumbling() {
      return this.isRumbling && this.isUsing();
   }

   @Override
   public boolean isFinishedRumbling() {
      return !this.isUsing();
   }

   @Override
   public float getRumblerX() {
      return (float)this.getUser().getX();
   }

   @Override
   public float getRumblerY() {
      return (float)this.getUser().getY();
   }

   @Override
   public float getRumblerZ() {
      return (float)this.getUser().getZ();
   }
}
