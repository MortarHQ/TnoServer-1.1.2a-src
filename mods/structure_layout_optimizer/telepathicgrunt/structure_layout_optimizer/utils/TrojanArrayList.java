package telepathicgrunt.structure_layout_optimizer.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;

public class TrojanArrayList<E> extends ArrayList<E> {
   public final Set<StructurePoolElement> elementsAlreadyParsed = new HashSet<>();
}
