package com.tensura_tno.mixin.client;

import com.tensura_tno.ftb.STExtrasHelper;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "io.github.manasmods.tensura.client.screen.MainScreen", remap = false, priority = 1100)
public class STExtrasPrestigeConditionsLocalizationMixin {
   private static final Map<String, String> LABEL_KEYS = Map.of(
      "Max Evo",
      "tensura_tno.prestige.label.max_evo",
      "Awakened",
      "tensura_tno.prestige.label.awakened",
      "Essence",
      "tensura_tno.prestige.label.essence",
      "Boss Kills",
      "tensura_tno.prestige.label.boss_kills",
      "Quests",
      "tensura_tno.prestige.label.quests"
   );
   private static final String COMPLETED_KEY = "tensura_tno.prestige.value.completed";
   private static final String NOT_COMPLETED_KEY = "tensura_tno.prestige.value.not_completed";
   @Unique
   private static long tensuraTno$lastPrestigeSyncRequestMs;
   private static final Map<Character, Character> SMALL_CAPS_MAP = Map.ofEntries(
      Map.entry('a', 'ᴀ'),
      Map.entry('b', 'ʙ'),
      Map.entry('c', 'ᴄ'),
      Map.entry('d', 'ᴅ'),
      Map.entry('e', 'ᴇ'),
      Map.entry('f', 'ꜰ'),
      Map.entry('g', 'ɢ'),
      Map.entry('h', 'ʜ'),
      Map.entry('i', 'ɪ'),
      Map.entry('j', 'ᴊ'),
      Map.entry('k', 'ᴋ'),
      Map.entry('l', 'ʟ'),
      Map.entry('m', 'ᴍ'),
      Map.entry('n', 'ɴ'),
      Map.entry('o', 'ᴏ'),
      Map.entry('p', 'ᴘ'),
      Map.entry('q', 'ǫ'),
      Map.entry('r', 'ʀ'),
      Map.entry('s', 'ѕ'),
      Map.entry('t', 'ᴛ'),
      Map.entry('u', 'ᴜ'),
      Map.entry('v', 'ᴠ'),
      Map.entry('w', 'ᴡ'),
      Map.entry('x', 'x'),
      Map.entry('y', 'ʏ'),
      Map.entry('z', 'ᴢ')
   );

   @Inject(method = "stextras$buildPrestigeRequirements", at = @At("HEAD"), remap = false, require = 0)
   private void tensuraTno$requestFreshPrestigeQuestState(CallbackInfoReturnable<Component> cir) {
      long now = System.currentTimeMillis();
      if (now - tensuraTno$lastPrestigeSyncRequestMs >= 1000L) {
         tensuraTno$lastPrestigeSyncRequestMs = now;
         STExtrasHelper.requestPrestigeScreenSync();
      }
   }

   @Inject(method = "stextras$formatEntry", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private void tensuraTno$localizeEntry(String label, String value, boolean isMet, CallbackInfoReturnable<Component> cir) {
      Language lang = Language.getInstance();
      String labelKey = LABEL_KEYS.get(label);
      if (labelKey != null) {
         String translatedLabel = lang.has(labelKey) ? lang.getOrDefault(labelKey) : label;
         String translatedValue;
         if ("Completed".equals(value)) {
            if (isMet) {
               translatedValue = lang.has("tensura_tno.prestige.value.completed") ? lang.getOrDefault("tensura_tno.prestige.value.completed") : value;
            } else {
               translatedValue = lang.has("tensura_tno.prestige.value.not_completed") ? lang.getOrDefault("tensura_tno.prestige.value.not_completed") : "未完成";
            }
         } else {
            translatedValue = value;
         }

         MutableComponent bullet = Component.literal("  ● ").withStyle(isMet ? ChatFormatting.GREEN : ChatFormatting.RED);
         MutableComponent labelComp = Component.literal(translatedLabel + ": ").withStyle(ChatFormatting.GRAY);
         MutableComponent valueComp = Component.literal(translatedValue).withStyle(isMet ? ChatFormatting.WHITE : ChatFormatting.RED);
         MutableComponent icon = Component.literal(isMet ? " ✔" : " ✘").withStyle(isMet ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED);
         cir.setReturnValue(bullet.append(labelComp).append(valueComp).append(icon));
      }
   }

   @Redirect(
      method = "stextras$buildPrestigeRequirements",
      at = @At(value = "INVOKE", target = "Lorg/crypticdev/stextras/utils/TextUtil;toSmallCaps(Ljava/lang/String;)Ljava/lang/String;"),
      remap = false,
      require = 0
   )
   private String tensuraTno$localizePrestigeTitle(String input) {
      Language lang = Language.getInstance();
      if ("Prestige Requirements".equals(input)) {
         String key = "tensura_tno.prestige.title";
         String translated = lang.has(key) ? lang.getOrDefault(key) : input;
         return isAscii(translated) ? tensuraTno$toSmallCaps(translated) : translated;
      } else if ("Ready to Prestige".equals(input)) {
         String key = "tensura_tno.prestige.ready";
         String translated = lang.has(key) ? lang.getOrDefault(key) : input;
         return isAscii(translated) ? tensuraTno$toSmallCaps(translated) : translated;
      } else {
         return input;
      }
   }

   private static boolean isAscii(String s) {
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) > 127) {
            return false;
         }
      }

      return true;
   }

   private static String tensuraTno$toSmallCaps(String input) {
      StringBuilder result = new StringBuilder();
      boolean skipNext = false;

      for (char c : input.toCharArray()) {
         if ((c == 167 || c == '&') && !skipNext) {
            skipNext = true;
            result.append(c);
         } else if (skipNext) {
            skipNext = false;
            result.append(c);
         } else {
            result.append(SMALL_CAPS_MAP.getOrDefault(Character.toLowerCase(c), c));
         }
      }

      return result.toString();
   }
}
