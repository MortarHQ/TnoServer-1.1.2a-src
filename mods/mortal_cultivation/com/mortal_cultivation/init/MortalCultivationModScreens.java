package com.mortal_cultivation.init;

import com.mortal_cultivation.event.client.KeyInputEventHandler;
import com.mortal_cultivation.gui.screen.AlchemyFurnaceScreen;
import com.mortal_cultivation.gui.screen.AncientScholarTableScreen;
import com.mortal_cultivation.gui.screen.RuneTableScreen;
import com.mortal_cultivation.gui.screen.TalismanMakingScreen;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class MortalCultivationModScreens {
   @SubscribeEvent
   public static void registerMenuScreens(RegisterMenuScreensEvent event) {
      KeyInputEventHandler.init();
      event.register((MenuType)MortalCultivationModMenus.TALISMAN_MAKING.get(), TalismanMakingScreen::new);
      event.register((MenuType)MortalCultivationModMenus.ALCHEMY_FURNACE.get(), AlchemyFurnaceScreen::new);
      event.register((MenuType)MortalCultivationModMenus.ANCIENT_SCHOLAR_TABLE.get(), AncientScholarTableScreen::new);
      event.register((MenuType)MortalCultivationModMenus.RUNE_TABLE.get(), RuneTableScreen::new);
   }
}
