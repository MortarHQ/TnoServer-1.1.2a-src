package team.creative.creativecore.common.config.api;

import team.creative.creativecore.Side;

public interface IConfigObject extends ICreativeConfig {
   boolean isDefault(Side var1);

   void restoreDefault(Side var1, boolean var2);
}
