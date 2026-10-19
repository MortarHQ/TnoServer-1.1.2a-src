package cc.sighs.oelib.bless;

import cc.sighs.oelib.bless.render.ChongYangOverlay;
import cc.sighs.oelib.bless.render.NewYearOverlay;
import cc.sighs.oelib.bless.render.ValentineOverlay;
import cc.sighs.oelib.dev.DevConfig;
import cc.sighs.oelib.registry.extra.CommandRegister;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;

public class BlessToastCommand {
   public static void register() {
      CommandRegister.registerClient(BlessToastCommand::registerClientCommand);
   }

   private static void registerClientCommand(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, CommandSelection environment) {
      if (DevConfig.UNIT.get().enableExampleContent()) {
         dispatcher.register(
            (LiteralArgumentBuilder)LiteralArgumentBuilder.literal("chongyang_toast")
               .executes(
                  ctx -> {
                     if (ShaderToastResources.getSpiritToastShader() != null
                        && FestivalToastConfig.get().enabled()
                        && FestivalToastConfig.get().chineseFestivalsOnlyForChineseLanguage()) {
                        ChongYangOverlay.INSTANCE.show();
                     }

                     return 1;
                  }
               )
         );
         dispatcher.register(
            (LiteralArgumentBuilder)LiteralArgumentBuilder.literal("new_year_toast")
               .executes(
                  ctx -> {
                     if (ShaderToastResources.getNewYearShader() != null
                        && FestivalToastConfig.get().enabled()
                        && FestivalToastConfig.get().chineseFestivalsOnlyForChineseLanguage()) {
                        NewYearOverlay.INSTANCE.show();
                     }

                     return 1;
                  }
               )
         );
         dispatcher.register((LiteralArgumentBuilder)LiteralArgumentBuilder.literal("valentine_toast").executes(ctx -> {
            if (ShaderToastResources.getValentineShader() != null && FestivalToastConfig.get().enabled()) {
               ValentineOverlay.INSTANCE.show();
            }

            return 1;
         }));
      }
   }
}
