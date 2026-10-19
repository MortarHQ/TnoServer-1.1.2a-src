package zank.mods.efficient_hashing.neoforge;

import net.neoforged.fml.common.Mod;
import zank.mods.efficient_hashing.EfficientHashing;

@Mod("efficient_hashing")
public class EfficientHashingMod {
   public EfficientHashingMod() {
      EfficientHashing.init();
   }
}
