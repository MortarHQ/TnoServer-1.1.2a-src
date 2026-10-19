package de.ellpeck.naturesaura.api.aura.container;

import de.ellpeck.naturesaura.api.aura.type.IAuraType;

public interface IAuraContainer {
   int storeAura(int var1, boolean var2);

   int drainAura(int var1, boolean var2);

   int getStoredAura();

   int getMaxAura();

   int getAuraColor();

   boolean isAcceptableType(IAuraType var1);
}
