package io.github.Memoires.mysticism.storage.entity;

import java.util.UUID;

public interface IMysticismEntity {
   void setButcherMarks(int var1);

   void setLastMarkApplier(UUID var1);

   int getButcherMarks();

   void incrementButcherMarks();

   void clearButcherMarks();

   UUID getLastMarkApplier();

   void markDirty();
}
