package noobanidus.mods.lootr.neoforge.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack.Position;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import noobanidus.mods.lootr.common.api.LootrAPI;

@EventBusSubscriber(modid = "lootr")
public class HandleResourcePacks {
   @SubscribeEvent
   public static void onResourcePacks(AddPackFindersEvent event) {
      if (event.getPackType() == PackType.CLIENT_RESOURCES) {
         event.addPackFinders(
            LootrAPI.rl("resourcepacks/old_textures"),
            PackType.CLIENT_RESOURCES,
            Component.literal("Lootr - Old Textures"),
            PackSource.BUILT_IN,
            false,
            Position.BOTTOM
         );
      } else if (event.getPackType() == PackType.SERVER_DATA) {
         event.addPackFinders(
            LootrAPI.rl("datapacks/lootr_no_advancements"),
            PackType.SERVER_DATA,
            Component.literal("Disable Lootr Advancements"),
            PackSource.FEATURE,
            false,
            Position.TOP
         );
         event.addPackFinders(
            LootrAPI.rl("datapacks/lootr_no_suspicious_blocks"),
            PackType.SERVER_DATA,
            Component.literal("Disable Lootr Converting Suspicious Blocks"),
            PackSource.FEATURE,
            false,
            Position.TOP
         );
      }
   }
}
