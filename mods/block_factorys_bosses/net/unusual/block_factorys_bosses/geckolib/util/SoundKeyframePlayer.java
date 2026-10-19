package net.unusual.block_factorys_bosses.geckolib.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController.SoundKeyframeHandler;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;

public class SoundKeyframePlayer<A extends GeoAnimatable> implements SoundKeyframeHandler<A> {
   public void handle(SoundKeyframeEvent<A> event) {
      Level level = this.getLevel((A)event.getAnimatable());
      if (level.isClientSide()) {
         String[] data = event.getKeyframeData().getSound().split("\\|");
         if (data.length >= 1) {
            ResourceLocation soundLocation = ResourceLocation.parse(data[0]);
            float volume = data.length >= 2 ? Float.parseFloat(data[1]) : 1.5F;
            float pitch = data.length >= 3 ? Float.parseFloat(data[2]) : 1.0F;
            SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(soundLocation);
            if (event.getAnimatable() instanceof Entity entity) {
               level.playLocalSound(entity.blockPosition(), soundEvent, entity.getSoundSource(), volume, pitch, false);
            } else {
               if (!(event.getAnimatable() instanceof BlockEntity blockEntity)) {
                  throw new IllegalArgumentException("Unsupported GeoAnimatable type: " + event.getAnimatable());
               }

               level.playLocalSound(blockEntity.getBlockPos(), soundEvent, SoundSource.BLOCKS, volume, pitch, false);
            }
         }
      }
   }

   protected Level getLevel(A animatable) {
      if (animatable instanceof Entity entity) {
         return entity.level();
      } else if (animatable instanceof BlockEntity blockEntity) {
         return blockEntity.getLevel();
      } else {
         throw new IllegalArgumentException("Unsupported GeoAnimatable type: " + animatable);
      }
   }
}
