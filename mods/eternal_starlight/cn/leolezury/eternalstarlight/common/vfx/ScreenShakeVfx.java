package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.visual.ScreenShake;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScreenShakeVfx implements SyncedVfxType {
   private static final String TAG_DIMENSION = "dimension";
   private static final String TAG_X = "x";
   private static final String TAG_Y = "y";
   private static final String TAG_Z = "z";
   private static final String TAG_RADIUS = "radius";
   private static final String TAG_DURATION = "duration";
   private static final String TAG_HORIZONTAL_POWER = "horizontal_power";
   private static final String TAG_VERTICAL_POWER = "vertical_power";
   private static final String TAG_HORIZONTAL_FREQUENCY = "horizontal_frequency";
   private static final String TAG_VERTICAL_FREQUENCY = "vertical_frequency";

   @Override
   public void spawnOnClient(CompoundTag tag) {
      ESMiscUtil.runWhenOnClient(
         () -> () -> ESClientHandler.SCREEN_SHAKES
            .add(
               new ScreenShake(
                  ResourceLocation.parse(tag.getString("dimension")),
                  new Vec3(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z")),
                  tag.getFloat("radius"),
                  tag.getInt("duration"),
                  tag.getFloat("horizontal_power"),
                  tag.getFloat("vertical_power"),
                  tag.getFloat("horizontal_frequency"),
                  tag.getFloat("vertical_frequency")
               )
            )
      );
   }

   public static VfxInstance createInstance(
      ResourceKey<Level> dimension, Vec3 pos, float radius, int duration, float horizontalPower, float verticalPower, float horizontalFreq, float verticalFreq
   ) {
      CompoundTag tag = new CompoundTag();
      tag.putString("dimension", dimension.location().toString());
      tag.putDouble("x", pos.x());
      tag.putDouble("y", pos.y());
      tag.putDouble("z", pos.z());
      tag.putFloat("radius", radius);
      tag.putInt("duration", duration);
      tag.putFloat("horizontal_power", horizontalPower);
      tag.putFloat("vertical_power", verticalPower);
      tag.putFloat("horizontal_frequency", horizontalFreq);
      tag.putFloat("vertical_frequency", verticalFreq);
      return new VfxInstance(VfxRegistry.SCREEN_SHAKE, tag);
   }
}
