package it.hurts.sskirillss.relics.mixin;

import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.items.relics.feet.SpringyBootItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
   @Inject(method = "fallOn", at = @At("HEAD"), cancellable = true)
   public void onEntityFall(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
      if (entity instanceof Player player) {
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.SPRINGY_BOOT.get());
         if (stack.getItem() instanceof SpringyBootItem relic && relic.isAbilityTicking(stack, "bounce")) {
            Vec3 motion = player.getKnownMovement();
            double speed = motion.multiply(0.0, 1.0, 0.0).y();
            if (!(speed > -0.5)) {
               player.causeFallDamage(fallDistance, 0.0F, level.damageSources().fall());
               ci.cancel();
            }
         }
      }
   }

   @Inject(method = "updateEntityAfterFallOn", at = @At("HEAD"), cancellable = true)
   public void onEntityFall(BlockGetter getter, Entity entity, CallbackInfo ci) {
      if (entity instanceof Player player) {
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.SPRINGY_BOOT.get());
         if (stack.getItem() instanceof SpringyBootItem relic && relic.isAbilityTicking(stack, "bounce")) {
            Vec3 motion = player.getKnownMovement();
            double speed = motion.multiply(0.0, 1.0, 0.0).y();
            if (!(speed > -0.5)) {
               Level level = player.getCommandSenderWorld();
               if (!level.isClientSide()) {
                  relic.spreadRelicExperience(player, stack, 1);
               }

               speed = Math.abs(speed);
               double power = relic.getStatValue(stack, "bounce", "power");
               player.setDeltaMovement(motion.multiply(1.0, -power, 1.0));
               RandomSource random = level.getRandom();
               level.playSound(
                  player,
                  player.blockPosition(),
                  (SoundEvent)SoundRegistry.SPRING_BOING.get(),
                  SoundSource.PLAYERS,
                  (float)Math.min(2.0, 0.25 + speed * 0.5),
                  (float)Math.max(0.1F, 2.0 - speed * 0.75)
               );

               for (float i = 0.0F; i < speed * 3.0; i += 0.1F) {
                  level.addParticle(
                     ParticleTypes.CLOUD,
                     player.getX(),
                     player.getY(),
                     player.getZ(),
                     MathUtils.randomFloat(random) * speed * 0.15F,
                     0.0,
                     MathUtils.randomFloat(random) * speed * 0.15F
                  );
               }

               ci.cancel();
            }
         }
      }
   }
}
