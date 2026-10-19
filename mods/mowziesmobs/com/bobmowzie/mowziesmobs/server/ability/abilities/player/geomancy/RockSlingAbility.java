package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderProjectile;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityRockSling;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import software.bernie.geckolib.animation.Animation.LoopType;

public class RockSlingAbility extends PlayerAbility {
   public static final double SPAWN_BOULDER_REACH = 5.0;
   public BlockPos spawnBoulderPos = new BlockPos(0, 0, 0);
   public Vec3 lookPos = new Vec3(0.0, 0.0, 0.0);
   private BlockState spawnBoulderBlock = Blocks.DIRT.defaultBlockState();

   public RockSlingAbility(AbilityType<Player, ? extends Ability<?>> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 5),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 10),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 5)
         },
         15
      );
   }

   @Override
   public void start() {
      super.start();
      Vec3 from = this.getUser().getEyePosition(1.0F);
      Vec3 to = from.add(this.getUser().getLookAngle().scale(5.0));
      BlockHitResult result = this.getUser().level().clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this.getUser()));
      if (result.getType() == Type.BLOCK) {
         this.lookPos = result.getLocation();
      }

      this.spawnBoulderPos = result.getBlockPos();
      this.spawnBoulderBlock = this.getUser().level().getBlockState(this.spawnBoulderPos);
      this.playAnimation("rock_sling", LoopType.DEFAULT, true, true);
      if (!this.getUser().level().isClientSide()) {
         for (int i = 0; i < 3; i++) {
            Vec3 spawnPos = new Vec3(0.0, -1.0, 2.5)
               .yRot((float)Math.toRadians(-this.getUser().getYRot()))
               .yRot((float)Math.toRadians(-45 + i * 45))
               .add(this.getUser().position());
            EntityRockSling boulder = new EntityRockSling(
               (EntityType<? extends EntityBoulderProjectile>)EntityHandler.ROCK_SLING.get(),
               this.getUser().level(),
               this.getUser(),
               this.spawnBoulderBlock,
               this.spawnBoulderPos,
               EntityGeomancyBase.GeomancyTier.values()[1]
            );
            boulder.setPos(spawnPos.x() + 0.5, spawnPos.y() + 2.0, spawnPos.z() + 0.5);
            boulder.setLaunchVec(this.getUser().getViewVector(1.0F).multiply(1.0, 0.9F, 1.0));
            if (!this.getUser().level().isClientSide && boulder.checkCanSpawn()) {
               this.getUser().level().addFreshEntity(boulder);
            }
         }
      }
   }
}
