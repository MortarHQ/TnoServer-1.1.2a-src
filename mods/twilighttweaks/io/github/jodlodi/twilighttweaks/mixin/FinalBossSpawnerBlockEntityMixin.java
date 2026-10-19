package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.TweakConfig;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.block.entity.spawner.BossSpawnerBlockEntity;
import twilightforest.block.entity.spawner.FinalBossSpawnerBlockEntity;
import twilightforest.entity.boss.PlateauBoss;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(FinalBossSpawnerBlockEntity.class)
public abstract class FinalBossSpawnerBlockEntityMixin extends BossSpawnerBlockEntity<PlateauBoss> {
   protected FinalBossSpawnerBlockEntityMixin(BlockEntityType<?> type, EntityType<PlateauBoss> entityType, BlockPos pos, BlockState state) {
      super(type, entityType, pos, state);
   }

   @Inject(method = "spawnMyBoss", at = @At("HEAD"), cancellable = true, remap = false)
   private void spawnMyBossMixin(final ServerLevelAccessor accessor, CallbackInfoReturnable<Boolean> cir) {
      final BlockPos pos = this.getBlockPos();
      BaseCommandBlock commandBlock = new BaseCommandBlock() {
         @NotNull
         public ServerLevel getLevel() {
            return accessor.getLevel();
         }

         public void onUpdated() {
         }

         @NotNull
         public Vec3 getPosition() {
            return Vec3.atCenterOf(pos);
         }

         @NotNull
         public CommandSourceStack createCommandSourceStack() {
            return new CommandSourceStack(
               this, this.getPosition(), Vec2.ZERO, this.getLevel(), 2, this.getName().getString(), this.getName(), this.getLevel().getServer(), null
            );
         }

         public boolean shouldInformAdmins() {
            return false;
         }

         public boolean isValid() {
            return true;
         }
      };
      commandBlock.setCommand(TweakConfig.commandCustom);
      cir.setReturnValue(commandBlock.performCommand(accessor.getLevel()));
   }
}
