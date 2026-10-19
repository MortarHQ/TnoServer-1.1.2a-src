package de.ellpeck.naturesaura.compat.patchouli;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.multiblock.Matcher;
import de.ellpeck.naturesaura.compat.ICompat;
import de.ellpeck.naturesaura.events.ClientEvents;
import de.ellpeck.naturesaura.renderers.SupporterFancyHandler;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import vazkii.patchouli.api.BookDrawScreenEvent;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliCompat implements ICompat {
   public static final ResourceLocation BOOK = ResourceLocation.fromNamespaceAndPath("naturesaura", "book");
   private static final Map<ResourceLocation, IMultiblock> MULTIBLOCKS = new HashMap<>();

   public static void addPatchouliMultiblock(ResourceLocation name, String[][] pattern, Object... rawMatchers) {
      for (int i = 1; i < rawMatchers.length; i += 2) {
         if (rawMatchers[i] instanceof Matcher matcher) {
            Matcher.ICheck check = matcher.check();
            if (check == null) {
               rawMatchers[i] = PatchouliAPI.get().anyMatcher();
            } else {
               rawMatchers[i] = PatchouliAPI.get().predicateMatcher(matcher.defaultState(), state -> check.matches(null, null, null, null, state, '\u0000'));
            }
         }
      }

      MULTIBLOCKS.put(name, PatchouliAPI.get().makeMultiblock(pattern, rawMatchers));
   }

   public static <T extends Recipe<?>> T getRecipe(String type, String name) {
      RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
      ResourceLocation res = ResourceLocation.parse(name);
      ResourceLocation pre = ResourceLocation.fromNamespaceAndPath(res.getNamespace(), type + "/" + res.getPath());
      RecipeHolder<?> holder = (RecipeHolder<?>)manager.byKey(pre).orElse(null);
      return (T)(holder != null ? holder.value() : null);
   }

   public static IVariable ingredientVariable(Ingredient ingredient, Provider registries) {
      return IVariable.wrapList(Arrays.stream(ingredient.getItems()).map(i -> IVariable.from(i, registries)).collect(Collectors.toList()), registries);
   }

   @Override
   public void setup(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         for (Entry<ResourceLocation, IMultiblock> entry : MULTIBLOCKS.entrySet()) {
            PatchouliAPI.get().registerMultiblock(entry.getKey(), entry.getValue());
         }

         PatchouliAPI.get().setConfigFlag("naturesaura:rf_converter", (Boolean)ModConfig.instance.rfConverter.get());
         PatchouliAPI.get().setConfigFlag("naturesaura:chunk_loader", (Boolean)ModConfig.instance.chunkLoader.get());
      });
   }

   @Override
   public void setupClient() {
      NeoForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onBookDraw(BookDrawScreenEvent event) {
      ResourceLocation book = event.getBook();
      Screen gui = event.getScreen();
      if (book != null && book.equals(BOOK)) {
         LocalDateTime now = LocalDateTime.now();
         if (now.getMonth() == Month.MAY && now.getDayOfMonth() == 21) {
            int x = gui.width / 2 + 136 - 16;
            int y = gui.height / 2 - 90 - 26;
            event.getGraphics().blit(ClientEvents.BOOK_GUI, x, y, 469.0F, 0.0F, 43, 42, 512, 256);
            if (event.getMouseX() >= x && event.getMouseY() >= y && event.getMouseX() < x + 43 && event.getMouseY() < y + 42) {
               event.getGraphics()
                  .renderTooltip(
                     Minecraft.getInstance().font,
                     Collections.singletonList(Component.literal("It's the author Ellpeck's birthday!").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD))),
                     Optional.empty(),
                     event.getMouseX(),
                     event.getMouseY()
                  );
            }
         } else if (now.getMonth() == Month.JUNE) {
            int x = gui.width / 2 + 136;
            int y = gui.height / 2 + 32;
            event.getGraphics().blit(ClientEvents.BOOK_GUI, x, y, 424.0F, 0.0F, 45, 26, 512, 256);
            if (event.getMouseX() >= x && event.getMouseY() >= y && event.getMouseX() < x + 45 && event.getMouseY() < y + 26) {
               event.getGraphics()
                  .renderTooltip(
                     gui.getMinecraft().font,
                     Collections.singletonList(Component.literal("§6Happy §4P§6r§ei§2d§9e§5!")),
                     Optional.empty(),
                     event.getMouseX(),
                     event.getMouseY()
                  );
            }
         }

         String name = gui.getMinecraft().player.getName().getString();
         SupporterFancyHandler.FancyInfo info = SupporterFancyHandler.FANCY_INFOS.get(name);
         if (info != null) {
            int x = gui.width / 2 - 136 + 20;
            int y = gui.height / 2 + 90;
            event.getGraphics().blit(ClientEvents.BOOK_GUI, x, y, 496.0F, 44.0F, 16, 18, 512, 256);
            if (info.tier() == 1) {
               event.getGraphics().blit(ClientEvents.BOOK_GUI, x, y, 480.0F, 44.0F, 16, 18, 512, 256);
            } else {
               float r = (info.color() >> 16 & 0xFF) / 255.0F;
               float g = (info.color() >> 8 & 0xFF) / 255.0F;
               float b = (info.color() & 0xFF) / 255.0F;
               RenderSystem.setShaderColor(r, g, b, 1.0F);
               event.getGraphics().blit(ClientEvents.BOOK_GUI, x, y, 464.0F, 44.0F, 16, 18, 512, 256);
            }

            if (event.getMouseX() >= x && event.getMouseY() >= y && event.getMouseX() < x + 16 && event.getMouseY() < y + 18) {
               event.getGraphics()
                  .renderTooltip(
                     gui.getMinecraft().font,
                     Collections.singletonList(
                        Component.literal("Thanks for your support, " + name + "!").setStyle(Style.EMPTY.applyFormat(ChatFormatting.YELLOW))
                     ),
                     Optional.empty(),
                     event.getMouseX(),
                     event.getMouseY()
                  );
            }
         }
      }
   }
}
