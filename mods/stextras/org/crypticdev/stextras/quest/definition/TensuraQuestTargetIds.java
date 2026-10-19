package org.crypticdev.stextras.quest.definition;

import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import net.minecraft.resources.ResourceLocation;

public final class TensuraQuestTargetIds {
   private static final String TENSURA_NAMESPACE = "tensura";

   public static ResourceLocation elementId(Element element) {
      return id(element.getNamespace());
   }

   public static ResourceLocation spiritLevelId(SpiritLevel level) {
      return id(level.getNamespace());
   }

   public static boolean isSpiritElement(ResourceLocation id) {
      if (isMinecraftAny(id)) {
         return true;
      }

      for (Element element : Element.values()) {
         if (elementId(element).equals(id)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isSpiritLevel(ResourceLocation id) {
      if (isMinecraftAny(id)) {
         return true;
      }

      for (SpiritLevel level : SpiritLevel.values()) {
         if (spiritLevelId(level).equals(id)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isMinecraftAny(ResourceLocation id) {
      return "minecraft".equals(id.getNamespace()) && "any".equals(id.getPath());
   }

   public static ResourceLocation parseTensuraDefault(String raw) {
      return raw.contains(":") ? ResourceLocation.parse(raw) : id(raw);
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensura", path);
   }

   private TensuraQuestTargetIds() {
   }
}
