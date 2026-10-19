package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.client.render.IBoneLight;
import java.util.Arrays;
import java.util.List;
import software.bernie.geckolib.cache.object.GeoBone;

public class LichBoneLight implements IBoneLight {
   @Override
   public int getLightForBone(GeoBone bone, int packedLight) {
      List<String> boneNames = Arrays.asList("crown_crystals", "crystal", "leftEye", "rightEye");
      return boneNames.contains(bone.getName()) ? 15728880 : packedLight;
   }
}
