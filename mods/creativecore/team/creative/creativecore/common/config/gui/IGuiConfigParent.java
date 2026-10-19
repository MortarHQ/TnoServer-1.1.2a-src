package team.creative.creativecore.common.config.gui;

import net.minecraft.core.HolderLookup.Provider;

public interface IGuiConfigParent {
   Provider provider();

   void setCustomData(Object var1);

   Object getCustomData();

   void changed();
}
