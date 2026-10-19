package com.tensura_tno.mixin.client;

import com.tensura_tno.client.race.PlayerFoxSpiritRenderManager;
import com.tensura_tno.client.race.PlayerLizardmanRenderManager;
import com.tensura_tno.client.race.PlayerOrcRenderManager;
import com.tensura_tno.race.SlimeRaceHelper;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ReincarnationScreen.class, priority = 900)
public abstract class ReincarnationScreenSlimeBgMixin {
   private static final ResourceLocation SLIME_BACKGROUND = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/gui/reincarnation/slime_gui.png");
   private static final ResourceLocation TARATECT_BACKGROUND = ResourceLocation.fromNamespaceAndPath(
      "tensura_tno", "textures/gui/reincarnation/taratect_gui.png"
   );
   private static final ResourceLocation LIZARDMAN_BACKGROUND = ResourceLocation.fromNamespaceAndPath(
      "tensura_tno", "textures/gui/reincarnation/lizardman_gui.png"
   );
   private static final ResourceLocation ORC_BACKGROUND = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/gui/reincarnation/orc_gui.png");
   private static final ResourceLocation EX_MACHINA_BACKGROUND = ResourceLocation.fromNamespaceAndPath(
      "tensura_tno", "textures/gui/reincarnation/ex_machina_gui.png"
   );
   private static final ResourceLocation FOX_SPIRIT_BACKGROUND = ResourceLocation.fromNamespaceAndPath(
      "tensura_tno", "textures/gui/reincarnation/fox_spirit_gui.png"
   );
   private static final ResourceLocation SMALL_LESSER_TARATECT_ID = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "small_lesser_taratect");
   private static final Set<ResourceLocation> EX_MACHINA_RACE_IDS = Set.of(
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "machine"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "befehler"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "kampf"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "zeichen"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "pruefer"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "seher"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "hubie_dora"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "einzeig"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "emir_eins"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "horou"),
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "puraiya")
   );

   @Redirect(
      method = {"renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", "renderButtons"},
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"),
      require = 0
   )
   private void tensuraTno$swapBackgroundForSlime(GuiGraphics graphics, ResourceLocation texture, int x, int y, int u, int v, int w, int h) {
      graphics.blit(this.selectBackground(texture), x, y, u, v, w, h);
   }

   private ResourceLocation selectBackground(ResourceLocation fallback) {
      try {
         ReincarnationScreen self = (ReincarnationScreen)this;
         ReincarnationMenu menu = (ReincarnationMenu)self.getMenu();
         if (menu == null) {
            return fallback;
         }

         int index = menu.selectedManasRaceIndex.get();
         List<ManasRace> pool = menu.getRacePool();
         if (pool != null && index >= 0 && index < pool.size()) {
            ManasRace race = pool.get(index);
            if (race == null) {
               return fallback;
            } else {
               ResourceLocation id = race.getRegistryName();
               if (id == null) {
                  return fallback;
               } else if (SlimeRaceHelper.SLIME_RACE_IDS.contains(id)) {
                  return SLIME_BACKGROUND;
               } else if (PlayerLizardmanRenderManager.LIZARDMAN_RACE_IDS.contains(id)) {
                  return LIZARDMAN_BACKGROUND;
               } else if (PlayerOrcRenderManager.ORC_RACE_IDS.contains(id)) {
                  return ORC_BACKGROUND;
               } else if (SMALL_LESSER_TARATECT_ID.equals(id)) {
                  return TARATECT_BACKGROUND;
               } else if (EX_MACHINA_RACE_IDS.contains(id)) {
                  return EX_MACHINA_BACKGROUND;
               } else {
                  return PlayerFoxSpiritRenderManager.FOX_SPIRIT_RACE_IDS.contains(id) ? FOX_SPIRIT_BACKGROUND : fallback;
               }
            }
         } else {
            return fallback;
         }
      } catch (Throwable t) {
         return fallback;
      }
   }
}
