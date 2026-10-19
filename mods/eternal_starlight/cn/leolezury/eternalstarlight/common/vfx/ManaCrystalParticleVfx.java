package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import java.util.Arrays;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class ManaCrystalParticleVfx implements SyncedVfxType {
   private static final String TAG_TYPE = "type";
   private static final String TAG_X = "x";
   private static final String TAG_Y = "y";
   private static final String TAG_Z = "z";

   @Override
   public void spawnOnClient(CompoundTag tag) {
      String type = tag.getString("type");
      Vec3 pos = new Vec3(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"));
      Arrays.stream(ManaType.values()).forEach(t -> {
         if (t.getSerializedName().equals(type)) {
            EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(t, pos);
         }
      });
   }

   public static VfxInstance createInstance(ManaType type, Vec3 pos) {
      CompoundTag tag = new CompoundTag();
      tag.putString("type", type.getSerializedName());
      tag.putDouble("x", pos.x());
      tag.putDouble("y", pos.y());
      tag.putDouble("z", pos.z());
      return new VfxInstance(VfxRegistry.MANA_CRYSTAL_PARTICLE, tag);
   }
}
