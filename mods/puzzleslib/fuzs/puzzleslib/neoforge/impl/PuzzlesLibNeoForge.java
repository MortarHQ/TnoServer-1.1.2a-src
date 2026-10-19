package fuzs.puzzleslib.neoforge.impl;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.impl.PuzzlesLibMod;
import net.neoforged.fml.common.Mod;

@Mod("puzzleslib")
public class PuzzlesLibNeoForge {
   public PuzzlesLibNeoForge() {
      ModConstructor.construct("puzzleslib", PuzzlesLibMod::new);
   }
}
