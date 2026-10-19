package com.benji.netherman.client.events;

import com.benji.netherman.client.sound.ZoneAmbientSoundInstance;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModSounds;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "netherman", value = Dist.CLIENT)
public class ClientEffectEvents {
   private static int clickTimer = 0;
   private static double rotationAngle = 0.0;
   private static ZoneAmbientSoundInstance whisperSound;

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() == Minecraft.getInstance().player) {
         LocalPlayer player = (LocalPlayer)event.getEntity();
         if (player.hasEffect(ModEffects.MANIPULATION)) {
            if (whisperSound == null || whisperSound.isStopped()) {
               whisperSound = new ZoneAmbientSoundInstance((SoundEvent)ModSounds.WHISPER.get(), player, ModEffects.MANIPULATION, true);
               Minecraft.getInstance().getSoundManager().play(whisperSound);
            }

            rotationAngle += 0.05;
            player.setYRot(player.getYRot() + (float)Math.sin(rotationAngle) * 0.8F);
            player.setXRot(player.getXRot() + (float)Math.cos(rotationAngle * 0.5) * 0.3F);
            clickTimer--;
            if (clickTimer <= 0) {
               KeyMapping.click(Minecraft.getInstance().options.keyAttack.getKey());
               clickTimer = player.getRandom().nextInt(40) + 20;
            }
         }
      }
   }

   @SubscribeEvent
   public static void onMovementInput(MovementInputUpdateEvent event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.hasEffect(ModEffects.MANIPULATION)) {
         BlockPos playerPos = player.blockPosition();
         BlockPos dangerPos = null;

         for (BlockPos pos : BlockPos.betweenClosed(playerPos.offset(-10, -5, -10), playerPos.offset(10, 5, 10))) {
            if (player.level().getBlockState(pos).is(Blocks.LAVA) || player.level().getBlockState(pos).is(Blocks.FIRE)) {
               dangerPos = pos.immutable();
               break;
            }
         }

         if (dangerPos != null) {
            Vec3 lookVec = player.getLookAngle();
            Vec3 targetVec = new Vec3(dangerPos.getX() + 0.5 - player.getX(), 0.0, dangerPos.getZ() + 0.5 - player.getZ()).normalize();
            double dot = lookVec.x * targetVec.x + lookVec.z * targetVec.z;
            double det = lookVec.x * targetVec.z - lookVec.z * targetVec.x;
            double angle = Math.atan2(det, dot);
            event.getInput().forwardImpulse = angle > -Math.PI / 4 && angle < Math.PI / 4 ? 1.0F : 0.0F;
            event.getInput().leftImpulse = angle >= Math.PI / 4 && angle < Math.PI * 3.0 / 4.0
               ? 1.0F
               : (angle <= -Math.PI / 4 && angle > -Math.PI * 3.0 / 4.0 ? -1.0F : 0.0F);
         } else if (player.tickCount % 15 == 0) {
            event.getInput().forwardImpulse = player.getRandom().nextFloat() * 2.0F - 1.0F;
            event.getInput().leftImpulse = player.getRandom().nextFloat() * 2.0F - 1.0F;
         }
      }
   }
}
