package com.yungnickyoung.minecraft.yungsapi.world.structure.terrainadaptation.beardifier;

import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import javax.annotation.Nullable;
import net.minecraft.world.level.levelgen.NoiseChunk;

public interface EnhancedBeardifierData {
   ObjectListIterator<EnhancedBeardifierRigid> getEnhancedPieceIterator();

   void setEnhancedPieceIterator(ObjectListIterator<EnhancedBeardifierRigid> var1);

   ObjectListIterator<EnhancedJigsawJunction> getEnhancedJunctionIterator();

   void setEnhancedJunctionIterator(ObjectListIterator<EnhancedJigsawJunction> var1);

   @Nullable
   NoiseChunk getNoiseChunk();

   void setNoiseChunk(NoiseChunk var1);
}
