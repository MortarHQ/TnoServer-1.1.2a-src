package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderProjectile;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import software.bernie.geckolib.animation.Animation.LoopType;

public class SpawnBoulderAbility extends PlayerAbility {
   private static final int MAX_CHARGE = 60;
   public static final double SPAWN_BOULDER_REACH = 5.0;
   public BlockPos spawnBoulderPos = new BlockPos(0, 0, 0);
   public Vec3 lookPos = new Vec3(0.0, 0.0, 0.0);
   private BlockState spawnBoulderBlock = Blocks.DIRT.defaultBlockState();
   private int spawnBoulderCharge = 0;
   private EntityGeomancyBase.GeomancyTier boulderSize = EntityGeomancyBase.GeomancyTier.SMALL;
   private static final AbilitySection NO_CHARGE_SECTION = new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 5);
   private static final AbilitySection SMALL_CHARGE_SECTION = new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 8);
   private static final AbilitySection MEDIUM_CHARGE_SECTION = new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 8);
   private static final AbilitySection LARGE_CHARGE_SECTION = new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 8);
   private static final AbilitySection HUGE_CHARGE_SECTION = new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 20);

   public SpawnBoulderAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            NO_CHARGE_SECTION,
            SMALL_CHARGE_SECTION,
            MEDIUM_CHARGE_SECTION,
            LARGE_CHARGE_SECTION,
            HUGE_CHARGE_SECTION,
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 12)
         }
      );
   }

   @Override
   public InteractionHand getActiveHand() {
      if (this.getUser().getMainHandItem().is((Item)ItemHandler.EARTHREND_GAUNTLET.get())) {
         return InteractionHand.MAIN_HAND;
      } else {
         return this.getUser().getOffhandItem().is((Item)ItemHandler.EARTHREND_GAUNTLET.get()) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      }
   }

   @Override
   public void start() {
      super.start();
      this.boulderSize = EntityGeomancyBase.GeomancyTier.SMALL;
      if (this.getLevel().isClientSide()) {
         this.playAnimation("spawn_boulder_start", LoopType.DEFAULT, true, false);
         if (this.getUser().getUsedItemHand() == InteractionHand.MAIN_HAND) {
            this.heldItemMainHandVisualOverride = this.getUser().getUseItem();
         } else {
            this.heldItemOffHandVisualOverride = this.getUser().getUseItem();
         }
      }
   }

   @Override
   public boolean tryAbility() {
      Vec3 from = this.getUser().getEyePosition(1.0F);
      Vec3 to = from.add(this.getUser().getLookAngle().scale(5.0));
      BlockHitResult result = this.getUser().level().clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this.getUser()));
      if (result.getType() == Type.BLOCK) {
         this.lookPos = result.getLocation();
      }

      this.spawnBoulderPos = result.getBlockPos();
      this.spawnBoulderBlock = this.getUser().level().getBlockState(this.spawnBoulderPos);
      if (result.getDirection() == Direction.DOWN) {
         return false;
      }

      BlockState blockAbove = this.getUser().level().getBlockState(this.spawnBoulderPos.above());
      return blockAbove.blocksMotion() ? false : EffectGeomancy.isBlockUseable(this.spawnBoulderBlock);
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         this.spawnBoulderCharge++;
         if (this.spawnBoulderCharge > 2) {
            this.getUser().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3, 0, false, false));
         }

         if (this.spawnBoulderCharge == 1 && this.getUser().level().isClientSide) {
            MMCommon.PROXY.playBoulderChargeSound(this.getUser());
         }

         if (this.spawnBoulderCharge == 45) {
            if (this.getUser().level().isClientSide) {
               AdvancedParticleBase.spawnParticle(
                  this.getUser().level(),
                  ParticleHandler.RING2,
                  (float)this.getUser().getX(),
                  (float)this.getUser().getY() + this.getUser().getBbHeight() / 2.0F,
                  (float)this.getUser().getZ(),
                  0.0,
                  0.0,
                  0.0,
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  3.5,
                  0.83F,
                  1.0,
                  0.39F,
                  1.0,
                  1.0,
                  20.0,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.7F, 0.0F), false
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(0.0F, 40.0F), false
                     )
                  }
               );
            }

            this.getUser().playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.0F, 1.0F);
         }

         int size = this.getBoulderSize().index + 1;
         if (size > 4) {
            size = 4;
         }

         EntityDimensions dim = EntityBoulderBase.SIZE_MAP.get(EntityGeomancyBase.GeomancyTier.values()[size]);
         if (!this.getUser()
               .level()
               .noCollision(dim.makeBoundingBox(this.spawnBoulderPos.getX() + 0.5F, this.spawnBoulderPos.getY() + 2, this.spawnBoulderPos.getZ() + 0.5F))
            || this.getUser().distanceToSqr(this.spawnBoulderPos.getX(), this.spawnBoulderPos.getY(), this.spawnBoulderPos.getZ()) > 36.0) {
            this.jumpToSection(5);
         }
      }
   }

   @Override
   protected void beginSection(AbilitySection section) {
      if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP && section != NO_CHARGE_SECTION && this.getUser().level().isClientSide) {
         float scale = 5.0F;
         if (section == MEDIUM_CHARGE_SECTION) {
            scale = 8.0F;
         } else if (section == LARGE_CHARGE_SECTION) {
            scale = 12.0F;
         } else if (section == HUGE_CHARGE_SECTION) {
            scale = 16.0F;
         }

         AdvancedParticleBase.spawnParticle(
            this.getUser().level(),
            ParticleHandler.RING2,
            this.spawnBoulderPos.getX() + 0.5F,
            this.spawnBoulderPos.getY() + 1.01,
            this.spawnBoulderPos.getZ() + 0.5F,
            0.0,
            0.0,
            0.0,
            false,
            0.0,
            Math.PI / 2,
            0.0,
            0.0,
            scale,
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
                  ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.7F, 0.0F), false
               )
            }
         );
      }

      if (section == MEDIUM_CHARGE_SECTION) {
         this.boulderSize = EntityGeomancyBase.GeomancyTier.MEDIUM;
      }

      if (section == LARGE_CHARGE_SECTION) {
         this.boulderSize = EntityGeomancyBase.GeomancyTier.LARGE;
      }

      if (section == HUGE_CHARGE_SECTION) {
         this.boulderSize = EntityGeomancyBase.GeomancyTier.HUGE;
      }

      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.spawnBoulder();
      }
   }

   private EntityGeomancyBase.GeomancyTier getBoulderSize() {
      return this.boulderSize;
   }

   private void spawnBoulder() {
      if (this.spawnBoulderCharge <= 2) {
         this.playAnimation("spawn_boulder_instant", LoopType.DEFAULT, true, false);
      } else {
         this.playAnimation("spawn_boulder_end", LoopType.DEFAULT, true, false);
      }

      EntityBoulderProjectile boulder = new EntityBoulderProjectile(
         (EntityType<? extends EntityBoulderProjectile>)EntityHandler.BOULDER_PROJECTILE.get(),
         this.getUser().level(),
         this.getUser(),
         this.spawnBoulderBlock,
         this.spawnBoulderPos,
         this.getBoulderSize()
      );
      boulder.setPos(this.spawnBoulderPos.getX() + 0.5F, this.spawnBoulderPos.getY() + 2, this.spawnBoulderPos.getZ() + 0.5F);
      if (!this.getUser().level().isClientSide && boulder.checkCanSpawn()) {
         this.getUser().level().addFreshEntity(boulder);
      }

      if (this.spawnBoulderCharge > 2) {
         Vec3 playerEyes = this.getUser().getEyePosition(1.0F);
         Vec3 vec = playerEyes.subtract(this.lookPos).normalize();
         float yaw = (float)Math.atan2(vec.z, vec.x);
         float pitch = (float)Math.asin(vec.y);
         this.getUser().setYRot((float)(yaw * 180.0F / Math.PI + 90.0));
         this.getUser().setXRot((float)(pitch * 180.0F / Math.PI));
      }

      this.spawnBoulderCharge = 0;
   }

   @Override
   public void onRightMouseUp(Player player) {
      super.onRightMouseUp(player);
      if (this.isUsing()
         && this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP
         && this.getCurrentSection() != HUGE_CHARGE_SECTION) {
         if (player.distanceToSqr(this.spawnBoulderPos.getX(), this.spawnBoulderPos.getY(), this.spawnBoulderPos.getZ()) < 36.0) {
            this.jumpToSection(5);
         } else {
            this.spawnBoulderCharge = 0;
         }
      }
   }

   @Override
   public boolean canUse() {
      return EffectGeomancy.canUse(this.getUser()) && super.canUse();
   }

   @Override
   public void end() {
      this.spawnBoulderCharge = 0;
      super.end();
   }

   @Override
   public void readNBT(Tag nbt) {
      super.readNBT(nbt);
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         this.spawnBoulderCharge = this.getTicksInUse();
      }
   }

   @Override
   public void onRightClickBlock(RightClickBlock event) {
      super.onRightClickBlock(event);
      if (!event.getLevel().isClientSide()) {
         AbilityHandler.INSTANCE.sendAbilityMessage(event.getEntity(), AbilityHandler.SPAWN_BOULDER_ABILITY);
      }
   }

   @Override
   public void onRightClickEmpty(RightClickEmpty event) {
      super.onRightClickEmpty(event);
      AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SPAWN_BOULDER_ABILITY);
   }

   @Override
   public void onRenderTick(RenderFrameEvent event) {
      super.onRenderTick(event);
      if (this.isUsing() && this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP && this.getTicksInUse() > 1) {
         Vec3 playerEyes = this.getUser().getEyePosition(event.getPartialTick().getGameTimeDeltaPartialTick(false));
         Vec3 vec = playerEyes.subtract(this.lookPos).normalize();
         float yaw = (float)Math.atan2(vec.z, vec.x);
         float pitch = (float)Math.asin(vec.y);
         this.getUser().setYRot((float)(yaw * 180.0F / Math.PI + 90.0));
         this.getUser().setXRot((float)(pitch * 180.0F / Math.PI));
         this.getUser().yHeadRot = this.getUser().getYRot();
         this.getUser().yRotO = this.getUser().getYRot();
         this.getUser().xRotO = this.getUser().getXRot();
         this.getUser().yHeadRotO = this.getUser().yHeadRot;
      }
   }
}
