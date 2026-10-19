package com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import software.bernie.geckolib.animation.RawAnimation;

public class SunstrikeAbility extends HeliomancyAbilityBase {
   private static final double REACH = 15.0;
   private static final int SUNSTRIKE_RECOVERY = 15;
   protected BlockHitResult rayTrace;
   private static final RawAnimation SUNSTRIKE_ANIM = RawAnimation.begin().thenPlay("sunstrike");

   public SunstrikeAbility(AbilityType<Player, SunstrikeAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 15)
         }
      );
   }

   private static BlockHitResult rayTrace(LivingEntity entity, double reach) {
      Vec3 pos = entity.getEyePosition(0.0F);
      Vec3 segment = entity.getLookAngle();
      segment = pos.add(segment.x * reach, segment.y * reach, segment.z * reach);
      return entity.level().clip(new ClipContext(pos, segment, Block.COLLIDER, Fluid.NONE, entity));
   }

   @Override
   public boolean tryAbility() {
      super.tryAbility();
      LivingEntity user = this.getUser();
      BlockHitResult raytrace = rayTrace(user, 15.0);
      if (raytrace.getType() == Type.BLOCK && raytrace.getDirection() == Direction.UP) {
         this.rayTrace = raytrace;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      super.start();
      LivingEntity user = this.getUser();
      if (!user.level().isClientSide()) {
         BlockPos hit = this.rayTrace.getBlockPos();
         EntitySunstrike sunstrike = new EntitySunstrike(
            (EntityType<? extends EntitySunstrike>)EntityHandler.SUNSTRIKE.get(), user.level(), user, hit.getX(), hit.getY(), hit.getZ()
         );
         sunstrike.onSummon();
         user.level().addFreshEntity(sunstrike);
      }

      this.playAnimation(SUNSTRIKE_ANIM);
   }

   @Override
   public boolean preventsBlockBreakingBuilding() {
      return false;
   }

   @Override
   public boolean preventsAttacking() {
      return false;
   }

   @Override
   public boolean preventsInteracting() {
      return false;
   }
}
