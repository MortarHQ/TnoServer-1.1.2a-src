package com.sjyueluo.tensura_skill_books;

import com.sjyueluo.tensura_skill_books.catalog.SkillCatalog;
import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import com.sjyueluo.tensura_skill_books.command.SkillBooksCommands;
import com.sjyueluo.tensura_skill_books.registry.ModCreativeTabs;
import com.sjyueluo.tensura_skill_books.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("tensura_skill_books")
public final class TensuraSkillBooksMod {
   public static final String MOD_ID = "tensura_skill_books";
   public static final Logger LOGGER = LogManager.getLogger("tensura_skill_books");

   public TensuraSkillBooksMod(IEventBus modBus, ModContainer modContainer) {
      modContainer.registerConfig(Type.COMMON, TensuraSkillBooksConfig.SPEC);
      ModItems.register(modBus);
      ModCreativeTabs.register(modBus);
      modBus.addListener(this::commonSetup);
      NeoForge.EVENT_BUS.addListener(SkillBooksCommands::onRegisterCommands);
   }

   private void commonSetup(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         SkillCatalog.invalidate();
         int total = SkillCatalog.all().size();

         for (SkillCategory category : SkillCategory.values()) {
            LOGGER.info("[{}] SkillCatalog {}: {}", "tensura_skill_books", category, SkillCatalog.byCategory().get(category).size());
         }

         LOGGER.info("[{}] SkillCatalog total: {}", "tensura_skill_books", total);
      });
   }
}
