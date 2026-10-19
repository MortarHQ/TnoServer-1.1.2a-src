package com.github.b4ndithelps.tennogamenolife.command;

import com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate.DragonRoarStrikeSequence;
import com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate.GodlySmiteVfx;
import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class LaserRainDebugCommand {
   private static final double TARGET_RANGE = 64.0;

   private LaserRainDebugCommand() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ngl").requires(source -> source.hasPermission(2)))
            .then(
               ((LiteralArgumentBuilder)Commands.literal("debug").then(Commands.literal("laser_rain").executes(LaserRainDebugCommand::execute)))
                  .then(Commands.literal("godly_smite").executes(LaserRainDebugCommand::executeGodlySmite))
            )
      );
   }

   private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      ServerLevel level = player.serverLevel();
      Vec3 targetPos = getTargetPosition(player);
      DragonRoarCircle strikeVfx = new DragonRoarCircle((EntityType<?>)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get(), level);
      strikeVfx.setPos(targetPos);
      strikeVfx.setStrikeVfx(true);
      strikeVfx.setVfxSeed(level.random.nextInt());
      strikeVfx.setVfxDuration(120);
      strikeVfx.setVfxStartGameTime(level.getGameTime());
      strikeVfx.setVfxStartHeight((float)(player.getY() + 200.0 - targetPos.y));
      strikeVfx.setLife(123);
      level.addFreshEntity(strikeVfx);
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("已在准星锁定点播放激光雨特效（无爆炸）"), false);
      return 1;
   }

   private static int executeGodlySmite(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      ServerLevel level = player.serverLevel();
      DragonRoarCircle vfx = GodlySmiteVfx.spawnCharge(level, player);
      DragonRoarStrikeSequence.start(level, 1200, () -> {
         Vec3 targetPos = GodlySmiteVfx.getReleaseImpactSurface(player);
         Vec3 origin = GodlySmiteVfx.getReleaseOrigin(player);
         if (!vfx.isRemoved()) {
            GodlySmiteVfx.beginStrike(level, vfx, targetPos, origin);
         } else {
            GodlySmiteVfx.spawnStrike(level, targetPos, origin);
         }

         level.playSound(null, targetPos.x, targetPos.y, targetPos.z, SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 8.0F, 0.72F);
         DragonRoarStrikeSequence.start(level, 120, () -> {
            GodlySmiteVfx.spawnImpactParticles(level, targetPos);
            level.playSound(null, targetPos.x, targetPos.y, targetPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 8.0F, 0.62F);
         });
      });
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Godly Smite VFX preview started (no damage)."), false);
      return 1;
   }

   private static Vec3 getTargetPosition(ServerPlayer player) {
      Vec3 eyePos = player.getEyePosition();
      Vec3 endPos = eyePos.add(player.getLookAngle().scale(64.0));
      ClipContext context = new ClipContext(eyePos, endPos, Block.OUTLINE, Fluid.NONE, player);
      BlockHitResult result = player.level().clip(context);
      Vec3 aimedPosition = result.getType() == Type.MISS ? endPos : result.getLocation();
      BlockHitResult groundResult = player.level()
         .clip(
            new ClipContext(
               aimedPosition.add(0.0, 1.0, 0.0),
               new Vec3(aimedPosition.x, player.level().getMinBuildHeight() - 1.0, aimedPosition.z),
               Block.COLLIDER,
               Fluid.NONE,
               player
            )
         );
      return groundResult.getType() == Type.MISS ? aimedPosition : groundResult.getLocation();
   }
}
