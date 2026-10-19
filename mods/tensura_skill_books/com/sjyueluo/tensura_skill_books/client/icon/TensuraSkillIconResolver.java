package com.sjyueluo.tensura_skill_books.client.icon;

import com.sjyueluo.tensura_skill_books.TensuraSkillBooksMod;
import com.sjyueluo.tensura_skill_books.catalog.SkillCatalog;
import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import com.sjyueluo.tensura_skill_books.util.SkillCompat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;

public final class TensuraSkillIconResolver {
   public static final String TAG_SKILL_ID = "skill_id";
   public static final ResourceLocation TAB_ICON_ALL = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/all");
   public static final ResourceLocation TAB_ICON_COMBAT = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/combat");
   public static final ResourceLocation TAB_ICON_UNIQUE = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/unique");
   public static final ResourceLocation TAB_ICON_ULTIMATE = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/ultimate");
   public static final ResourceLocation TAB_ICON_EXTRA = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/extra");
   public static final ResourceLocation TAB_ICON_COMMON = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/common");
   public static final ResourceLocation TAB_ICON_RESISTANCE = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/resistance");
   public static final ResourceLocation TAB_ICON_INTRINSIC = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/intrinsic");
   public static final ResourceLocation TAB_ICON_MAGIC_ASPECTUAL = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/magic_aspectual");
   public static final ResourceLocation TAB_ICON_MAGIC_SPIRITUAL = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/magic_spiritual");
   public static final ResourceLocation TAB_ICON_MAGIC_SUMMONING = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/magic_summoning");
   public static final ResourceLocation TAB_ICON_MAGIC_MISC = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "__tab_icon__/magic_misc");
   private static final ResourceLocation FALLBACK_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_skill_books", "textures/item/logo.png");
   private static final ResourceLocation TAB_ICON_COMBAT_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tensura", "textures/gui/battlewill/battlewill_tab_icon.png"
   );
   private static final ResourceLocation TAB_ICON_UNIQUE_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/unique.png");
   private static final ResourceLocation TAB_ICON_ULTIMATE_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/ultimate.png");
   private static final ResourceLocation TAB_ICON_EXTRA_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/extra.png");
   private static final ResourceLocation TAB_ICON_COMMON_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/common.png");
   private static final ResourceLocation TAB_ICON_RESISTANCE_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/resistance.png");
   private static final ResourceLocation TAB_ICON_INTRINSIC_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/intrinsic.png");
   private static final ResourceLocation TAB_ICON_MAGIC_ASPECTUAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tensura", "textures/gui/magic/aspectual_magic_button.png"
   );
   private static final ResourceLocation TAB_ICON_MAGIC_SPIRITUAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tensura", "textures/gui/magic/spiritual_magic_button.png"
   );
   private static final ResourceLocation TAB_ICON_MAGIC_SUMMONING_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tensura", "textures/gui/magic/summoning_magic_button.png"
   );
   private static final ResourceLocation TAB_ICON_MAGIC_MISC_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tensura", "textures/gui/magic/misc_magic_button.png"
   );
   private static final Map<ResourceLocation, IconData> CACHE = new ConcurrentHashMap<>();
   private static final Set<ResourceLocation> MISSING = ConcurrentHashMap.newKeySet();

   private TensuraSkillIconResolver() {
   }

   public static IconData resolve(ResourceLocation skillOrPseudoId) {
      return skillOrPseudoId == null
         ? IconData.fullTexture(FALLBACK_TEXTURE)
         : CACHE.computeIfAbsent(skillOrPseudoId, TensuraSkillIconResolver::resolveUncached);
   }

   public static List<ResourceLocation> missingIconsSample(int max) {
      return MISSING.stream().limit(max).collect(Collectors.toList());
   }

   public static ResourceLocation iconIdForCategory(SkillCategory category) {
      return switch (category) {
         case ALL -> TAB_ICON_ALL;
         case COMBAT -> TAB_ICON_COMBAT;
         case UNIQUE -> TAB_ICON_UNIQUE;
         case ULTIMATE -> TAB_ICON_ULTIMATE;
         case EXTRA -> TAB_ICON_EXTRA;
         case COMMON -> TAB_ICON_COMMON;
         case RESISTANCE -> TAB_ICON_RESISTANCE;
         case INTRINSIC -> TAB_ICON_INTRINSIC;
         case MAGIC_ASPECTUAL -> TAB_ICON_MAGIC_ASPECTUAL;
         case MAGIC_SPIRITUAL -> TAB_ICON_MAGIC_SPIRITUAL;
         case MAGIC_SUMMONING -> TAB_ICON_MAGIC_SUMMONING;
         case MAGIC_MISC -> TAB_ICON_MAGIC_MISC;
         case MISC -> null;
      };
   }

   private static IconData resolveUncached(ResourceLocation id) {
      if (TAB_ICON_ALL.equals(id)) {
         return IconData.fullTexture(FALLBACK_TEXTURE);
      } else if (TAB_ICON_COMBAT.equals(id)) {
         return IconData.fullTexture(TAB_ICON_COMBAT_TEXTURE);
      } else if (TAB_ICON_UNIQUE.equals(id)) {
         return topHalf(TAB_ICON_UNIQUE_TEXTURE);
      } else if (TAB_ICON_ULTIMATE.equals(id)) {
         return topHalf(TAB_ICON_ULTIMATE_TEXTURE);
      } else if (TAB_ICON_EXTRA.equals(id)) {
         return topHalf(TAB_ICON_EXTRA_TEXTURE);
      } else if (TAB_ICON_COMMON.equals(id)) {
         return topHalf(TAB_ICON_COMMON_TEXTURE);
      } else if (TAB_ICON_RESISTANCE.equals(id)) {
         return topHalf(TAB_ICON_RESISTANCE_TEXTURE);
      } else if (TAB_ICON_INTRINSIC.equals(id)) {
         return topHalf(TAB_ICON_INTRINSIC_TEXTURE);
      } else if (TAB_ICON_MAGIC_ASPECTUAL.equals(id)) {
         return topHalf(TAB_ICON_MAGIC_ASPECTUAL_TEXTURE);
      } else if (TAB_ICON_MAGIC_SPIRITUAL.equals(id)) {
         return topHalf(TAB_ICON_MAGIC_SPIRITUAL_TEXTURE);
      } else if (TAB_ICON_MAGIC_SUMMONING.equals(id)) {
         return topHalf(TAB_ICON_MAGIC_SUMMONING_TEXTURE);
      } else if (TAB_ICON_MAGIC_MISC.equals(id)) {
         return topHalf(TAB_ICON_MAGIC_MISC_TEXTURE);
      } else {
         Object skill = SkillCatalog.get(id);
         if (skill == null) {
            warnMissing(id, "skill not found in registry");
            return IconData.fullTexture(FALLBACK_TEXTURE);
         } else {
            ResourceLocation icon = SkillCompat.getSkillIcon(skill);
            if (icon == null) {
               warnMissing(id, "null icon");
               return IconData.fullTexture(FALLBACK_TEXTURE);
            } else {
               return IconData.fullTexture(icon, 16, 16);
            }
         }
      }
   }

   private static void warnMissing(ResourceLocation id, String reason) {
      if (MISSING.add(id)) {
         TensuraSkillBooksMod.LOGGER.warn("[{}] Missing icon for {} ({}); using fallback", "tensura_skill_books", id, reason);
      }
   }

   private static IconData topHalf(ResourceLocation texture) {
      return new IconData(texture, 32, 32, 0.0F, 0.0F, 1.0F, 0.5F);
   }
}
