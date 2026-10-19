package xaero.hud.minimap.controls.key.function;

import net.minecraft.client.Minecraft;
import xaero.common.HudMod;
import xaero.common.effect.Effects;
import xaero.common.misc.Misc;
import xaero.hud.controls.key.function.KeyMappingFunction;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.lib.client.config.ClientConfigManager;

public class ToggleMapFunction extends KeyMappingFunction {
   protected ToggleMapFunction() {
      super(false);
   }

   @Override
   public void onPress() {
      Minecraft mc = Minecraft.getInstance();
      if (!Misc.hasEffect(mc.player, Effects.NO_MINIMAP) && !Misc.hasEffect(mc.player, Effects.NO_MINIMAP_HARMFUL)) {
         ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
         boolean currentDisplayMinimap = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.DISPLAY_MINIMAP);
         configManager.getCurrentProfile().set(MinimapProfiledConfigOptions.DISPLAY_MINIMAP, !currentDisplayMinimap);
         HudMod.INSTANCE.getHudConfigs().getClientConfigProfileIO().save(configManager.getCurrentProfile());
      }
   }

   @Override
   public void onRelease() {
   }
}
