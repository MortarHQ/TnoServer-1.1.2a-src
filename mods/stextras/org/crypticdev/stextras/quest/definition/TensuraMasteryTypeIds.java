package org.crypticdev.stextras.quest.definition;

import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.ability.magic.aspectual.AspectualMagic;
import io.github.manasmods.tensura.ability.magic.aspectual.AspectualMagic.AspectualType;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public final class TensuraMasteryTypeIds {
   private static final String TENSURA_NAMESPACE = "tensura";
   private static final ResourceLocation BATTLEWILL_ID = id("battlewill");

   public static ResourceLocation battlewillId() {
      return BATTLEWILL_ID;
   }

   public static ResourceLocation skillTypeId(SkillType type) {
      return id(type.getNamespace());
   }

   public static ResourceLocation magicTypeId(MagicType type) {
      return id(type.getNamespace());
   }

   public static Set<ResourceLocation> magicSubtypeIds(Magic magic) {
      Set<ResourceLocation> ids = new LinkedHashSet<>();
      if (magic instanceof AspectualMagic aspectualMagic) {
         ids.add(id("aspectual_" + aspectualMagic.getAspectualType().getNamespace()));
      }

      if (magic instanceof SpiritualMagic spiritualMagic) {
         ids.add(id("spiritual_" + spiritualMagic.getElemental().getNamespace()));
         ids.add(id("spiritual_" + spiritualMagic.getLevel().getNamespace()));
      }

      return ids;
   }

   public static boolean isSkillMasteryType(ResourceLocation id) {
      if (!isMinecraftAny(id) && !BATTLEWILL_ID.equals(id)) {
         for (SkillType type : SkillType.values()) {
            if (skillTypeId(type).equals(id)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean isMagicMasteryType(ResourceLocation id) {
      if (isMinecraftAny(id)) {
         return true;
      }

      for (MagicType type : MagicType.values()) {
         if (magicTypeId(type).equals(id)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isMagicMasterySubtype(ResourceLocation id) {
      if (isMinecraftAny(id)) {
         return true;
      }

      for (AspectualType type : AspectualType.values()) {
         if (id("aspectual_" + type.getNamespace()).equals(id)) {
            return true;
         }
      }

      for (Element element : Element.values()) {
         if (id("spiritual_" + element.getNamespace()).equals(id)) {
            return true;
         }
      }

      for (SpiritLevel level : SpiritLevel.values()) {
         if (id("spiritual_" + level.getNamespace()).equals(id)) {
            return true;
         }
      }

      return false;
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensura", path);
   }

   private static boolean isMinecraftAny(ResourceLocation id) {
      return "minecraft".equals(id.getNamespace()) && "any".equals(id.getPath());
   }

   private TensuraMasteryTypeIds() {
   }
}
