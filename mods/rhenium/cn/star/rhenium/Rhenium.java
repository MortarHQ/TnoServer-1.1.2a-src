package cn.star.rhenium;

import cn.star.rhenium.access.StructureTemplateManagerAccess;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod("rhenium")
public class Rhenium {
   public static final String MODID = "rhenium";

   public Rhenium(IEventBus modEventBus, ModContainer modContainer) {
      modEventBus.addListener(Config::onLoad);
      NeoForge.EVENT_BUS.addListener(this::registerCommands);
      modContainer.registerConfig(Type.COMMON, Config.SPEC);
   }

   private void registerCommands(RegisterCommandsEvent event) {
      event.getDispatcher().register((LiteralArgumentBuilder)Commands.literal("rhenium").then(Commands.literal("info").executes(context -> {
         StructureTemplateManager manager = ((CommandSourceStack)context.getSource()).getServer().getStructureManager();
         int cachedTemplateCount = ((StructureTemplateManagerAccess)manager).rhenium$getCachedTemplateCount();
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Cached structure templates: " + cachedTemplateCount), false);
         return 1;
      })));
   }
}
