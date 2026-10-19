package telepathicgrunt.structure_layout_optimizer.neoforge.entrypoints;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import telepathicgrunt.structure_layout_optimizer.StructureLayoutOptimizerMod;

@Mod("structure_layout_optimizer")
public class Main {
   public Main(ModContainer modContainer) {
      StructureLayoutOptimizerMod.init();
   }
}
