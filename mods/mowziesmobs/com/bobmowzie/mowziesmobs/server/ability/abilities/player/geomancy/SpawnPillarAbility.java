package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillar;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import software.bernie.geckolib.animation.RawAnimation;

public class SpawnPillarAbility extends PlayerAbility {
   private static int MAX_DURATION = 120;
   private static int MAX_RANGE_TO_GROUND = 12;
   private BlockPos spawnPillarPos;
   private BlockState spawnPillarBlock;
   private EntityPillar pillar;
   private static final RawAnimation PILLAR_SPAWN_ANIM = RawAnimation.begin().thenPlay("pillar_spawn");

   public SpawnPillarAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 2),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, MAX_DURATION)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      this.getUser().setDeltaMovement(this.getUser().getDeltaMovement().add(0.0, -2.0, 0.0));
   }

   @Override
   public boolean tryAbility() {
      Vec3 from = this.getUser().position();
      Vec3 to = from.subtract(0.0, MAX_RANGE_TO_GROUND, 0.0);
      BlockHitResult result = this.getUser().level().clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this.getUser()));
      if (result.getType() == Type.MISS) {
         return false;
      }

      this.spawnPillarPos = result.getBlockPos();
      this.spawnPillarBlock = this.getUser().level().getBlockState(this.spawnPillarPos);
      if (result.getDirection() != Direction.UP) {
         BlockState blockAbove = this.getUser().level().getBlockState(this.spawnPillarPos.above());
         if (blockAbove.isSuffocating(this.getUser().level(), this.spawnPillarPos.above()) || blockAbove.isAir()) {
            return false;
         }
      }

      return EffectGeomancy.isBlockUseable(this.spawnPillarBlock);
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      this.getUser().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3, 2, false, false));
   }

   @Override
   protected void beginSection(AbilitySection section) {
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.spawnPillar();
      }
   }

   private void spawnPillar() {
      this.pillar = new EntityPillar(
         (EntityType<? extends EntityPillar>)EntityHandler.PILLAR.get(), this.getUser().level(), this.getUser(), this.spawnPillarBlock, this.spawnPillarPos
      );
      this.pillar.setPos(this.spawnPillarPos.getX() + 0.5F, this.spawnPillarPos.getY() + 1, this.spawnPillarPos.getZ() + 0.5F);
      if (!this.getUser().level().isClientSide && this.pillar.checkCanSpawn()) {
         this.getUser().level().addFreshEntity(this.pillar);
      }
   }

   @Override
   public void end() {
      super.end();
      if (this.pillar != null) {
         this.pillar.stopRising();
      }

      this.pillar = null;
   }

   @Override
   public boolean canUse() {
      return EffectGeomancy.canUse(this.getUser()) && this.damageGauntlet() && super.canUse();
   }

   @Override
   protected boolean canContinueUsing() {
      return super.canContinueUsing()
         && this.damageGauntlet()
         && (this.pillar == null || this.getLevel().getEntities(this.pillar, this.pillar.getBoundingBox().inflate(0.0, 6.0, 0.0)).contains(this.getUser()));
   }

   @Override
   public void onJump(LivingJumpEvent event) {
      super.onJump(event);
      if (this.getUser().isCrouching() && !event.getEntity().level().isClientSide()) {
         AbilityHandler.INSTANCE.sendAbilityMessage(event.getEntity(), AbilityHandler.SPAWN_PILLAR_ABILITY);
      }
   }

   @Override
   public void onSneakUp(Player player) {
      super.onSneakUp(player);
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.isUsing()) {
         if (this.pillar != null) {
            this.pillar.stopRising();
         }

         this.nextSection();
      }
   }

   public boolean damageGauntlet() {
      ItemStack stack = this.getUser().getMainHandItem();
      if (!stack.is((Item)ItemHandler.EARTHREND_GAUNTLET.get())) {
         stack = this.getUser().getOffhandItem();
      }

      if (!stack.is((Item)ItemHandler.EARTHREND_GAUNTLET.get())) {
         return false;
      }

      if (stack.getItem() == ItemHandler.EARTHREND_GAUNTLET.get()) {
         InteractionHand handIn = this.getUser().getUsedItemHand();
         if (stack.getDamageValue() + 6 < stack.getMaxDamage()) {
            stack.hurtAndBreak(6, this.getUser(), LivingEntity.getSlotForHand(handIn));
            return true;
         }

         if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.breakable.get()) {
            stack.hurtAndBreak(6, this.getUser(), LivingEntity.getSlotForHand(handIn));
         }

         return false;
      } else {
         return false;
      }
   }
}
