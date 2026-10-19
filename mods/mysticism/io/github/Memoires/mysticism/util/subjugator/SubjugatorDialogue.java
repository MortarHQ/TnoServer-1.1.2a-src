package io.github.Memoires.mysticism.util.subjugator;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.IntFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public final class SubjugatorDialogue {
   protected static final UniqueMysticismSkillConfig.Subjugator CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .Subjugator;
   private static final String DIALOG_ROOT = "Ctrl.Dialogue";
   private static final String DIALOG_ACTIVE = "Ctrl.Active";
   private static final String K_AWAITING = "Awaiting";
   private static final String K_REPLY = "ReplyIndex";
   private static final String K_RESP_PREF = "ResponseKeyPrefix";
   private static final String K_OPTIONS = "Options";
   private static final float CASUAL_CHANCE = CONFIG.casualConversationChance;
   private static final String CASUAL_PREFIX = "mysticism.skill.subjugator.casual";
   private static final LinkedHashMap<String, SubjugatorDialogue.DialogueDef> DIALOGUES = new LinkedHashMap<>();

   private SubjugatorDialogue() {
   }

   public static void bootstrap() {
      if (DIALOGUES.isEmpty()) {
         register(
            new SubjugatorDialogue.DialogueDef(
               "casual_1", "mysticism.skill.subjugator.casual1.opening", "mysticism.skill.subjugator.casual1.response_", 3, i -> {
                  return switch (i) {
                     case 1 -> "mysticism.skill.subjugator.casual1.result_1";
                     case 2, 3 -> "mysticism.skill.subjugator.casual1.result_other";
                     default -> "mysticism.skill.subjugator.casualfallback";
                  };
               }
            )
         );
         register(
            new SubjugatorDialogue.DialogueDef(
               "casual_2", "mysticism.skill.subjugator.casual2.opening", "mysticism.skill.subjugator.casual2.response_", 2, i -> {
                  return switch (i) {
                     case 1 -> "mysticism.skill.subjugator.casual2.result_1";
                     case 2 -> "mysticism.skill.subjugator.casual2.result_2";
                     default -> "mysticism.skill.subjugator.casualfallback";
                  };
               }
            )
         );
         register(simple("casual_3", 2));
         register(simple("casual_4", 3));
         register(simple("casual_5", 2));
         register(simple("casual_6", 4));
         register(simple("casual_7", 3));
         register(simple("casual_8", 2));
      }
   }

   public static void register(SubjugatorDialogue.DialogueDef def) {
      DIALOGUES.put(def.id(), def);
   }

   private static SubjugatorDialogue.DialogueDef simple(String id, int options) {
      return new SubjugatorDialogue.DialogueDef(
         id,
         "mysticism.skill.subjugator.casual." + id + ".opening",
         "mysticism.skill.subjugator.casual." + id + ".response_",
         options,
         i -> "mysticism.skill.subjugator.casual." + id + ".result_" + i
      );
   }

   public static void tickRandom(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance instance, LivingEntity entity, boolean awakened) {
      if (awakened) {
         if (entity.level() instanceof ServerLevel) {
            if (getActiveId(skill, instance) == null) {
               if (!(entity.getRandom().nextFloat() >= CASUAL_CHANCE)) {
                  if (entity instanceof Player p) {
                     SubjugatorDialogue.DialogueDef d = pickRandom(entity);
                     if (d != null) {
                        startCasual(skill, instance, p, d);
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean tryStartRandom(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance instance, Player p, boolean awakened) {
      if (!awakened) {
         p.displayClientMessage(Component.translatable("mysticism.skill.subjugator.voice_unavailable").withStyle(ChatFormatting.RED), false);
         return false;
      } else if (getActiveId(skill, instance) != null) {
         p.displayClientMessage(Component.translatable("mysticism.skill.subjugator.convo_busy").withStyle(ChatFormatting.RED), true);
         return false;
      } else {
         SubjugatorDialogue.DialogueDef d = pickRandom(p);
         return d != null && startCasual(skill, instance, p, d);
      }
   }

   public static boolean tryStartById(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance instance, Player p, String id) {
      SubjugatorDialogue.DialogueDef d = DIALOGUES.get(id);
      return d != null && startCasual(skill, instance, p, d);
   }

   public static boolean startMilestone(
      SubjugatorDialogue.ManasSkillLike skill,
      ManasSkillInstance instance,
      Player p,
      String id,
      String openingKey,
      String responsePrefix,
      int options,
      int defaultIdx
   ) {
      if (getActiveId(skill, instance) != null) {
         return false;
      }

      boolean ok = startConversation(skill, instance, id, responsePrefix, options, defaultIdx);
      if (ok) {
         p.displayClientMessage(Component.translatable(openingKey).withStyle(ChatFormatting.GRAY), false);
      }

      return ok;
   }

   public static void onScroll(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance instance, LivingEntity e, double delta, int mode) {
      if (mode == 2) {
         if (e.isShiftKeyDown()) {
            if (e.level() instanceof ServerLevel) {
               String active = getActiveId(skill, instance);
               if (active != null) {
                  CompoundTag conv = getConv(skill, instance, active);
                  if (conv.getBoolean("Awaiting")) {
                     int options = Math.max(1, conv.getInt("Options"));
                     int old = clampIndex(conv.getInt("ReplyIndex"), options);
                     int step = (int)Math.signum(delta);
                     if (step != 0) {
                        int next = 1 + Math.floorMod(old - 1 + step, options);
                        conv.putInt("ReplyIndex", next);
                        skill.markDirty(instance);
                        if (e instanceof Player p) {
                           String prefix = conv.getString("ResponseKeyPrefix");
                           if (!prefix.isEmpty()) {
                              p.displayClientMessage(Component.translatable(prefix + next).withStyle(ChatFormatting.DARK_AQUA), true);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void onRelease(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance instance, LivingEntity e, int mode) {
      if (mode == 2) {
         if (e instanceof Player p) {
            String active = getActiveId(skill, instance);
            if (active != null) {
               CompoundTag conv = getConv(skill, instance, active);
               if (conv.getBoolean("Awaiting")) {
                  if (e.isShiftKeyDown()) {
                     cancelConversation(skill, instance, active);
                     p.displayClientMessage(Component.translatable("mysticism.skill.subjugator.convo_cancelled").withStyle(ChatFormatting.DARK_GRAY), false);
                     p.playNotifySound((SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.6F, 0.8F);
                  } else {
                     int options = Math.max(1, conv.getInt("Options"));
                     int idx = clampIndex(conv.getInt("ReplyIndex"), options);
                     String prefix = conv.getString("ResponseKeyPrefix");
                     if (prefix.isEmpty()) {
                        prefix = "mysticism.skill.subjugator.casual.response_";
                     }

                     p.displayClientMessage(
                        Component.translatable(prefix + idx).withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.BOLD}), false
                     );
                     p.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.8F, 1.2F);
                     SubjugatorDialogue.DialogueDef d = DIALOGUES.get(active);
                     if (d != null) {
                        String resultKey = d.resultKeyForIdx().apply(idx);
                        if (resultKey != null && !resultKey.isEmpty()) {
                           p.displayClientMessage(Component.translatable(resultKey).withStyle(ChatFormatting.GRAY), false);
                        }
                     }

                     cancelConversation(skill, instance, active);
                  }
               }
            }
         }
      }
   }

   public static boolean prepareReply(
      SubjugatorDialogue.ManasSkillLike SK, ManasSkillInstance inst, Player p, String id, String responseKeyPrefix, int options, int defaultIndex
   ) {
      String active = getActiveId(SK, inst);
      if (active != null && !active.isEmpty() && !active.equals(id)) {
         return false;
      }

      CompoundTag c = ensureConv(SK, inst, id);
      c.putBoolean("Awaiting", true);
      c.putInt("Options", Math.max(1, options));
      c.putInt("ReplyIndex", Math.max(1, Math.min(options, defaultIndex)));
      c.putString("ResponseKeyPrefix", responseKeyPrefix);
      setActiveId(SK, inst, id);
      SK.markDirty(inst);
      p.displayClientMessage(Component.translatable(responseKeyPrefix + c.getInt("ReplyIndex")).withStyle(ChatFormatting.DARK_AQUA), true);
      return true;
   }

   private static int clampIndex(int i, int options) {
      return i < 1 ? 1 : Math.min(i, Math.max(1, options));
   }

   private static SubjugatorDialogue.DialogueDef pickRandom(LivingEntity e) {
      if (DIALOGUES.isEmpty()) {
         return null;
      }

      int i = e.getRandom().nextInt(DIALOGUES.size());
      return new ArrayList<>(DIALOGUES.values()).get(i);
   }

   private static boolean startCasual(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance instance, Player p, SubjugatorDialogue.DialogueDef d) {
      boolean ok = startConversation(skill, instance, d.id(), d.responsePrefix(), d.options(), 1);
      if (ok) {
         p.displayClientMessage(Component.translatable(d.openingKey()).withStyle(ChatFormatting.GRAY), false);
      }

      return ok;
   }

   private static CompoundTag root(SubjugatorDialogue.ManasSkillLike SK, ManasSkillInstance inst) {
      CompoundTag tag = SK.getOrCreateTag(inst);
      return tag.getCompound("Ctrl.Dialogue");
   }

   private static CompoundTag ensureRoot(SubjugatorDialogue.ManasSkillLike SK, ManasSkillInstance inst) {
      CompoundTag tag = SK.getOrCreateTag(inst);
      CompoundTag r = tag.getCompound("Ctrl.Dialogue");
      tag.put("Ctrl.Dialogue", r);
      return r;
   }

   private static CompoundTag getConv(SubjugatorDialogue.ManasSkillLike SK, ManasSkillInstance inst, String id) {
      return root(SK, inst).getCompound(id);
   }

   private static CompoundTag ensureConv(SubjugatorDialogue.ManasSkillLike SK, ManasSkillInstance inst, String id) {
      CompoundTag r = ensureRoot(SK, inst);
      CompoundTag c = r.getCompound(id);
      r.put(id, c);
      return c;
   }

   @Nullable
   private static String getActiveId(SubjugatorDialogue.ManasSkillLike SK, ManasSkillInstance inst) {
      String s = SK.getOrCreateTag(inst).getString("Ctrl.Active");
      return s.isEmpty() ? null : s;
   }

   private static void setActiveId(SubjugatorDialogue.ManasSkillLike SK, ManasSkillInstance inst, @Nullable String id) {
      CompoundTag tag = SK.getOrCreateTag(inst);
      if (id == null) {
         tag.remove("Ctrl.Active");
      } else {
         tag.putString("Ctrl.Active", id);
      }

      SK.markDirty(inst);
   }

   private static void setConv(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance inst, String id, CompoundTag c) {
      CompoundTag r = ensureRoot(skill, inst);
      r.put(id, c);
   }

   private static void clearActiveId(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance inst) {
      skill.getOrCreateTag(inst).remove("Ctrl.Active");
      skill.markDirty(inst);
   }

   private static boolean startConversation(
      SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance inst, String id, String responsePrefix, int options, int defaultIdx
   ) {
      if (getActiveId(skill, inst) != null) {
         return false;
      }

      CompoundTag c = new CompoundTag();
      c.putBoolean("Awaiting", true);
      c.putInt("ReplyIndex", clampIndex(defaultIdx, Math.max(1, options)));
      c.putString("ResponseKeyPrefix", responsePrefix);
      c.putInt("Options", Math.max(1, options));
      setConv(skill, inst, id, c);
      setActiveId(skill, inst, id);
      return true;
   }

   private static void cancelConversation(SubjugatorDialogue.ManasSkillLike skill, ManasSkillInstance inst, String id) {
      CompoundTag tag = skill.getOrCreateTag(inst);
      CompoundTag r = tag.getCompound("Ctrl.Dialogue");
      r.remove(id);
      if (r.getAllKeys().isEmpty()) {
         tag.remove("Ctrl.Dialogue");
      }

      String active = getActiveId(skill, inst);
      if (id.equals(active)) {
         clearActiveId(skill, inst);
      }

      skill.markDirty(inst);
   }

   public record DialogueDef(String id, String openingKey, String responsePrefix, int options, IntFunction<String> resultKeyForIdx) {
   }

   public interface ManasSkillLike {
      CompoundTag getOrCreateTag(Object var1);

      void markDirty(Object var1);
   }
}
