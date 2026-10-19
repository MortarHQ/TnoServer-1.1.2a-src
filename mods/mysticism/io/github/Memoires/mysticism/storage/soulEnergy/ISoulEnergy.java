package io.github.Memoires.mysticism.storage.soulEnergy;

public interface ISoulEnergy {
   void setSoulEnergy(double var1);

   void reduceSoulEnergy(double var1);

   void increaseSoulEnergy(double var1);

   double getSoulEnergy();

   void setNamingBoost(boolean var1);

   boolean getNamingBoost();

   void markDirty();
}
