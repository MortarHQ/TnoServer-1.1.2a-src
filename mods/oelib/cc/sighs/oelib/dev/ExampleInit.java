package cc.sighs.oelib.dev;

import cc.sighs.oelib.bless.BlessToastCommand;
import cc.sighs.oelib.dev.example.ClientRainbowBarComponent;
import cc.sighs.oelib.dev.example.ExampleCreativeTab;
import cc.sighs.oelib.dev.example.ExampleMenus;
import cc.sighs.oelib.dev.example.ExampleRegistry;
import cc.sighs.oelib.dev.example.RainbowBarComponent;
import cc.sighs.oelib.dev.example.TestPacketCommand;
import cc.sighs.oelib.dev.example.event.FluidRenderExampleClient;
import cc.sighs.oelib.registry.extra.ClientTooltipComponentRegister;
import cc.sighs.oelib.registry.extra.RenderTypeRegister;
import net.minecraft.client.renderer.RenderType;

public class ExampleInit {
   public static void init() {
      if (DevConfig.UNIT.get().enableExampleContent()) {
         TestPacketCommand.register();
         ExampleRegistry.init();
         ExampleCreativeTab.init();
         ExampleCreativeTab.registerCreativeTabEntries();
         ExampleCreativeTab.modifyCreativeTab();
         ExampleRegistry.registerFuel();
         ExampleMenus.init();
      }
   }

   public static void initClient() {
      if (DevConfig.UNIT.get().enableExampleContent()) {
         FluidRenderExampleClient.onRegisterKeys();
         BlessToastCommand.register();
         RenderTypeRegister.registerBlocks(RenderType.cutout(), ExampleRegistry.EXAMPLE_BLOCK);
         ClientTooltipComponentRegister.register(RainbowBarComponent.class, ClientRainbowBarComponent::new);
         ExampleMenus.registerScreens();
      }
   }
}
