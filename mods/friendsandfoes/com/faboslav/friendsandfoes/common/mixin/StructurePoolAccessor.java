package com.faboslav.friendsandfoes.common.mixin;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureTemplatePool.class)
public interface StructurePoolAccessor {
   @Accessor("rawTemplates")
   List<Pair<StructurePoolElement, Integer>> getElementCounts();

   @Mutable
   @Accessor("rawTemplates")
   void setElementCounts(List<Pair<StructurePoolElement, Integer>> var1);

   @Accessor("templates")
   ObjectArrayList<StructurePoolElement> getElements();

   @Mutable
   @Accessor("templates")
   void setElements(ObjectArrayList<StructurePoolElement> var1);
}
