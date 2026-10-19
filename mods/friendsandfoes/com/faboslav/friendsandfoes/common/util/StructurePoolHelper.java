package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.mixin.StructurePoolAccessor;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;

public final class StructurePoolHelper {
   public static void addLegacyElementToPool(Registry<StructureTemplatePool> templatePoolRegistry, ResourceLocation poolRL, String name, int weight) {
      StructureTemplatePool pool = (StructureTemplatePool)templatePoolRegistry.get(poolRL);
      if (pool != null) {
         SinglePoolElement piece = (SinglePoolElement)SinglePoolElement.legacy(FriendsAndFoes.makeStringID(name)).apply(Projection.RIGID);

         for (int i = 0; i < weight; i++) {
            ((StructurePoolAccessor)pool).getElements().add(piece);
         }

         List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(((StructurePoolAccessor)pool).getElementCounts());
         listOfPieceEntries.add(new Pair(piece, weight));
         ((StructurePoolAccessor)pool).setElementCounts(listOfPieceEntries);
      }
   }

   public static void addSingleElementToPool(Registry<StructureTemplatePool> templatePoolRegistry, ResourceLocation poolRL, String name, int weight) {
      StructureTemplatePool pool = (StructureTemplatePool)templatePoolRegistry.get(poolRL);
      if (pool != null) {
         SinglePoolElement piece = (SinglePoolElement)SinglePoolElement.single(FriendsAndFoes.makeStringID(name)).apply(Projection.RIGID);

         for (int i = 0; i < weight; i++) {
            ((StructurePoolAccessor)pool).getElements().add(piece);
         }

         List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(((StructurePoolAccessor)pool).getElementCounts());
         listOfPieceEntries.add(new Pair(piece, weight));
         ((StructurePoolAccessor)pool).setElementCounts(listOfPieceEntries);
      }
   }
}
