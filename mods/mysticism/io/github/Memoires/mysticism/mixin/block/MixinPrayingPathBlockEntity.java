package io.github.Memoires.mysticism.mixin.block;

import io.github.Memoires.mysticism.race.elemental.LesserElementalRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.block.entity.PrayingPathBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PrayingPathBlockEntity.class)
public abstract class MixinPrayingPathBlockEntity {
   @Shadow
   @Nullable
   protected abstract ServerPlayer getCurrentPrayingPlayer(Level var1);

   @Inject(
      method = "tick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lio/github/manasmods/tensura/block/entity/PrayingPathBlockEntity;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void mysticism$cancelElementalPrayer(Level level, BlockPos pos, BlockState state, PrayingPathBlockEntity path, CallbackInfo ci) {
      ServerPlayer player = ((MixinPrayingPathBlockEntity)path).getCurrentPrayingPlayer(level);
      if (player != null) {
         RaceAPI.getRaceFrom(player).getRace().ifPresent(r -> {
            if (r.getRace() instanceof LesserElementalRace) {
               ci.cancel();
            }
         });
      }
   }
}
