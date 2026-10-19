package mezz.jei.neoforge.platform;

import com.mojang.blaze3d.platform.InputConstants.Key;
import mezz.jei.common.input.keys.IJeiKeyMappingCategoryBuilder;
import mezz.jei.common.platform.IPlatformInputHelper;
import mezz.jei.neoforge.input.ForgeJeiKeyMappingCategoryBuilder;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.client.ClientTooltipFlag;

public class InputHelper implements IPlatformInputHelper {
   @Override
   public boolean isActiveAndMatches(KeyMapping keyMapping, Key key) {
      return keyMapping.isActiveAndMatches(key);
   }

   @Override
   public IJeiKeyMappingCategoryBuilder createKeyMappingCategoryBuilder(String name) {
      return new ForgeJeiKeyMappingCategoryBuilder(name);
   }

   @Override
   public TooltipFlag getClientTooltipFlag(TooltipFlag tooltipFlag) {
      Minecraft minecraft = Minecraft.getInstance();
      return minecraft != null && !(tooltipFlag instanceof ClientTooltipFlag) ? ClientTooltipFlag.of(tooltipFlag) : tooltipFlag;
   }
}
