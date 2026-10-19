package com.github.wal_bos.moreutils.command;

import com.github.wal_bos.moreutils.data.MoreUtilsData;
import com.github.wal_bos.moreutils.data.SkillBanType;
import com.github.wal_bos.moreutils.util.MoreUtilsPurge;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class MoreUtilsCommands {
   private MoreUtilsCommands() {
   }

   public static void register(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tmu").requires(source -> source.hasPermission(2)))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                                               "skill"
                                                            )
                                                            .then(skillBanTree("ban", SkillBanType.LEARN, false)))
                                                         .then(skillBanTree("banAndClean", SkillBanType.OWN, true)))
                                                      .then(skillBanTree("blacklist", SkillBanType.FULL, true)))
                                                   .then(
                                                      Commands.literal("banAll")
                                                         .executes(context -> banAllSkills((CommandSourceStack)context.getSource(), SkillBanType.LEARN, false))
                                                   ))
                                                .then(
                                                   Commands.literal("banAndCleanAll")
                                                      .executes(context -> banAllSkills((CommandSourceStack)context.getSource(), SkillBanType.OWN, true))
                                                ))
                                             .then(
                                                Commands.literal("blacklistAll")
                                                   .executes(context -> banAllSkills((CommandSourceStack)context.getSource(), SkillBanType.FULL, true))
                                             ))
                                          .then(
                                             Commands.literal("unban")
                                                .then(
                                                   Commands.argument("skill", StringArgumentType.string())
                                                      .suggests((context, builder) -> suggestBlockedSkills(builder))
                                                      .executes(
                                                         context -> unbanSkill(
                                                            (CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "skill")
                                                         )
                                                      )
                                                )
                                          ))
                                       .then(Commands.literal("unbanAll").executes(context -> unbanAllSkills((CommandSourceStack)context.getSource()))))
                                    .then(
                                       Commands.literal("clean")
                                          .then(
                                             Commands.argument("skill", StringArgumentType.string())
                                                .suggests((context, builder) -> suggestAllSkills(builder))
                                                .executes(
                                                   context -> cleanSkill(
                                                      (CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "skill")
                                                   )
                                                )
                                          )
                                    ))
                                 .then(Commands.literal("cleanAll").executes(context -> cleanAllBannedSkills((CommandSourceStack)context.getSource()))))
                              .then(
                                 Commands.literal("remove")
                                    .then(
                                       Commands.argument("targets", EntityArgument.entities())
                                          .then(
                                             Commands.argument("skill", StringArgumentType.string())
                                                .suggests((context, builder) -> suggestAllSkills(builder))
                                                .executes(
                                                   context -> removeSkill(
                                                      (CommandSourceStack)context.getSource(),
                                                      EntityArgument.getEntities(context, "targets"),
                                                      StringArgumentType.getString(context, "skill")
                                                   )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              Commands.literal("check")
                                 .then(
                                    Commands.argument("skill", StringArgumentType.string())
                                       .suggests((context, builder) -> suggestAllSkills(builder))
                                       .executes(context -> checkSkill((CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "skill")))
                                 )
                           ))
                        .then(Commands.literal("list").executes(context -> listSkills((CommandSourceStack)context.getSource())))
                  ))
               .then(
                  ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                                   "engraving"
                                                )
                                                .then(engravingBanTree("ban", false)))
                                             .then(engravingBanTree("banAndClean", true)))
                                          .then(
                                             Commands.literal("banAll").executes(context -> banAllEngravings((CommandSourceStack)context.getSource(), false))
                                          ))
                                       .then(
                                          Commands.literal("banAndCleanAll")
                                             .executes(context -> banAllEngravings((CommandSourceStack)context.getSource(), true))
                                       ))
                                    .then(
                                       Commands.literal("unban")
                                          .then(
                                             Commands.argument("engraving", StringArgumentType.string())
                                                .suggests((context, builder) -> suggestBlockedEngravings(builder))
                                                .executes(
                                                   context -> unbanEngraving(
                                                      (CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "engraving")
                                                   )
                                                )
                                          )
                                    ))
                                 .then(Commands.literal("unbanAll").executes(context -> unbanAllEngravings((CommandSourceStack)context.getSource()))))
                              .then(
                                 Commands.literal("clean")
                                    .then(
                                       Commands.argument("engraving", StringArgumentType.string())
                                          .suggests((context, builder) -> suggestAllEngravings((CommandSourceStack)context.getSource(), builder))
                                          .executes(
                                             context -> cleanEngraving(
                                                (CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "engraving")
                                             )
                                          )
                                    )
                              ))
                           .then(Commands.literal("cleanAll").executes(context -> cleanAllBannedEngravings((CommandSourceStack)context.getSource()))))
                        .then(
                           Commands.literal("check")
                              .then(
                                 Commands.argument("engraving", StringArgumentType.string())
                                    .suggests((context, builder) -> suggestAllEngravings((CommandSourceStack)context.getSource(), builder))
                                    .executes(
                                       context -> checkEngraving((CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "engraving"))
                                    )
                              )
                        ))
                     .then(Commands.literal("list").executes(context -> listEngravings((CommandSourceStack)context.getSource())))
               )
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> skillBanTree(String name, SkillBanType type, boolean cleanNow) {
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal(name)
         .then(
            Commands.argument("skill", StringArgumentType.string())
               .suggests((context, builder) -> suggestAllSkills(builder))
               .executes(context -> banSkill((CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "skill"), type, cleanNow))
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> engravingBanTree(String name, boolean cleanNow) {
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal(name)
         .then(
            Commands.argument("engraving", StringArgumentType.string())
               .suggests((context, builder) -> suggestAllEngravings((CommandSourceStack)context.getSource(), builder))
               .executes(context -> banEngraving((CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "engraving"), cleanNow))
         );
   }

   private static int banSkill(CommandSourceStack source, String rawId, SkillBanType type, boolean cleanNow) {
      ResourceLocation id = resolveSkillId(rawId);
      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
      if (skill == null) {
         source.sendFailure(Component.literal("Unknown skill: " + rawId + ". Use tab complete."));
         return 0;
      }

      MoreUtilsData.banSkill(source.getServer(), id, type);
      int removed = cleanNow ? MoreUtilsPurge.purgeSkillOnline(source.getServer(), id) : 0;
      if (type == SkillBanType.LEARN) {
         send(source, "Banned future obtainment for skill: " + id + ".");
      } else if (type == SkillBanType.OWN) {
         send(source, "Banned and cleaned skill: " + id + ". Removed from " + removed + " online player(s).");
      } else {
         send(source, "Blacklisted skill: " + id + ". It cannot be learned, owned, equipped, or plundered. Removed from " + removed + " online player(s).");
      }

      return 1;
   }

   private static int banAllSkills(CommandSourceStack source, SkillBanType type, boolean cleanNow) {
      List<ResourceLocation> ids = SkillAPI.getSkillRegistry()
         .entrySet()
         .stream()
         .map(entry -> ((ResourceKey)entry.getKey()).location())
         .filter(id -> id != null)
         .sorted(Comparator.comparing(ResourceLocation::toString))
         .toList();
      MoreUtilsData.banSkills(source.getServer(), ids, type);
      int removed = cleanNow ? MoreUtilsPurge.purgeAllBannedSkillsOnline(source.getServer()) : 0;
      if (type == SkillBanType.LEARN) {
         send(source, "Banned future obtainment for all " + ids.size() + " registered skills.");
      } else if (type == SkillBanType.OWN) {
         send(source, "Banned and cleaned all " + ids.size() + " registered skills. Removed " + removed + " online skill entry/entries.");
      } else {
         send(source, "Blacklisted all " + ids.size() + " registered skills. Removed " + removed + " online skill entry/entries.");
      }

      return 1;
   }

   private static int unbanSkill(CommandSourceStack source, String rawId) {
      ResourceLocation id = resolveSkillId(rawId);
      boolean wasBlocked = MoreUtilsData.isSkillBanned(id);
      MoreUtilsData.unbanSkill(source.getServer(), id);
      if (wasBlocked) {
         send(source, "Unbanned skill: " + id + ".");
      } else {
         send(source, "Skill was already unbanned: " + id + ".");
      }

      return 1;
   }

   private static int unbanAllSkills(CommandSourceStack source) {
      int count = MoreUtilsData.skillBans().size();
      MoreUtilsData.clearSkillBans(source.getServer());
      send(source, "Unbanned all skills. Removed " + count + " skill ban(s).");
      return 1;
   }

   private static int cleanSkill(CommandSourceStack source, String rawId) {
      ResourceLocation id = resolveSkillId(rawId);
      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
      if (skill == null) {
         source.sendFailure(Component.literal("Unknown skill: " + rawId + ". Use tab complete."));
         return 0;
      } else {
         int removed = MoreUtilsPurge.purgeSkillOnline(source.getServer(), id);
         send(source, "Cleaned skill: " + id + ". Removed from " + removed + " online player(s).");
         return 1;
      }
   }

   private static int cleanAllBannedSkills(CommandSourceStack source) {
      int removed = MoreUtilsData.hasOwningSkillBans() ? MoreUtilsPurge.purgeAllBannedSkillsOnline(source.getServer()) : 0;
      send(source, "Skill cleanup finished. Removed " + removed + " banned skill entry/entries.");
      return 1;
   }

   private static int removeSkill(CommandSourceStack source, Collection<? extends Entity> targets, String rawId) {
      ResourceLocation id = resolveSkillId(rawId);
      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
      if (skill == null) {
         source.sendFailure(Component.literal("Unknown skill: " + rawId + ". Use tab complete."));
         return 0;
      }

      int removed = 0;

      for (Entity entity : targets) {
         if (entity instanceof LivingEntity living && MoreUtilsPurge.purgeSkillFromEntity(living, skill)) {
            removed++;
         }
      }

      send(source, "Removed skill " + id + " from " + removed + " entity/entities and refunded online players. This did not ban it.");
      return 1;
   }

   private static int checkSkill(CommandSourceStack source, String rawId) {
      ResourceLocation id = resolveSkillId(rawId);
      SkillBanType type = MoreUtilsData.getSkillBan(id);
      if (type == null) {
         send(source, id + " is unbanned.");
      } else {
         send(source, id + " is banned as: " + readableSkillType(type) + ".");
      }

      return 1;
   }

   private static int listSkills(CommandSourceStack source) {
      if (MoreUtilsData.skillBans().isEmpty()) {
         send(source, "No skill bans.");
         return 1;
      } else {
         send(source, "Skill bans:");
         MoreUtilsData.skillBans()
            .entrySet()
            .stream()
            .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
            .forEach(
               entry -> source.sendSuccess(
                  () -> Component.literal("- " + entry.getKey() + " = " + readableSkillType((SkillBanType)entry.getValue())).withStyle(ChatFormatting.GRAY),
                  false
               )
            );
         return 1;
      }
   }

   private static int banEngraving(CommandSourceStack source, String rawId, boolean cleanNow) {
      ResourceLocation id = resolveEngravingId(source, rawId);
      if (id == null) {
         source.sendFailure(Component.literal("Unknown engraving: " + rawId + ". Use tab complete."));
         return 0;
      }

      MoreUtilsData.banEngraving(source.getServer(), id);
      int changed = cleanNow ? MoreUtilsPurge.purgeEngravingOnline(source.getServer(), id) : 0;
      if (cleanNow) {
         send(source, "Banned and cleaned engraving: " + id + ". Changed " + changed + " online item stack(s).");
      } else {
         send(source, "Banned engraving: " + id + ". Future engraving is blocked.");
      }

      return 1;
   }

   private static int banAllEngravings(CommandSourceStack source, boolean cleanNow) {
      Registry<Enchantment> registry = source.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
      List<ResourceLocation> ids = new ArrayList<>();

      for (String string : TensuraEnchantmentHelper.ENGRAVEMENT_ID_LIST) {
         ResourceLocation id = resolveEngravingId(source, string);
         if (id != null && !ids.contains(id)) {
            ids.add(id);
         }
      }

      registry.keySet().stream().filter(idx -> idx.getNamespace().equals("tensura")).sorted(Comparator.comparing(ResourceLocation::toString)).forEach(idx -> {
         if (!ids.contains(idx)) {
            ids.add(idx);
         }
      });
      MoreUtilsData.banEngravings(source.getServer(), ids);
      int changed = cleanNow ? MoreUtilsPurge.purgeAllBannedEngravingsOnline(source.getServer()) : 0;
      if (cleanNow) {
         send(source, "Banned and cleaned all " + ids.size() + " Tensura engravings. Changed " + changed + " item stack(s).");
      } else {
         send(source, "Banned all " + ids.size() + " Tensura engravings.");
      }

      return 1;
   }

   private static int unbanEngraving(CommandSourceStack source, String rawId) {
      ResourceLocation id = resolveEngravingId(source, rawId);
      if (id == null) {
         source.sendFailure(Component.literal("Unknown engraving: " + rawId + "."));
         return 0;
      }

      boolean wasBlocked = MoreUtilsData.isEngravingBanned(id);
      MoreUtilsData.unbanEngraving(source.getServer(), id);
      if (wasBlocked) {
         send(source, "Unbanned engraving: " + id + ".");
      } else {
         send(source, "Engraving was already unbanned: " + id + ".");
      }

      return 1;
   }

   private static int unbanAllEngravings(CommandSourceStack source) {
      int count = MoreUtilsData.engravingBans().size();
      MoreUtilsData.clearEngravingBans(source.getServer());
      send(source, "Unbanned all engravings. Removed " + count + " engraving ban(s).");
      return 1;
   }

   private static int cleanEngraving(CommandSourceStack source, String rawId) {
      ResourceLocation id = resolveEngravingId(source, rawId);
      if (id == null) {
         source.sendFailure(Component.literal("Unknown engraving: " + rawId + "."));
         return 0;
      } else {
         int changed = MoreUtilsPurge.purgeEngravingOnline(source.getServer(), id);
         send(source, "Cleaned engraving: " + id + ". Changed " + changed + " online item stack(s).");
         return 1;
      }
   }

   private static int cleanAllBannedEngravings(CommandSourceStack source) {
      int changed = MoreUtilsData.hasEngravingBans() ? MoreUtilsPurge.purgeAllBannedEngravingsOnline(source.getServer()) : 0;
      send(source, "Engraving cleanup finished. Changed " + changed + " item stack(s).");
      return 1;
   }

   private static int checkEngraving(CommandSourceStack source, String rawId) {
      ResourceLocation id = resolveEngravingId(source, rawId);
      if (id == null) {
         source.sendFailure(Component.literal("Unknown engraving: " + rawId + "."));
         return 0;
      }

      if (MoreUtilsData.isEngravingBanned(id)) {
         send(source, id + " is banned.");
      } else {
         send(source, id + " is unbanned.");
      }

      return 1;
   }

   private static int listEngravings(CommandSourceStack source) {
      if (MoreUtilsData.engravingBans().isEmpty()) {
         send(source, "No engraving bans.");
         return 1;
      } else {
         send(source, "Engraving bans:");
         MoreUtilsData.engravingBans()
            .stream()
            .sorted(Comparator.comparing(ResourceLocation::toString))
            .forEach(id -> source.sendSuccess(() -> Component.literal("- " + id).withStyle(ChatFormatting.GRAY), false));
         return 1;
      }
   }

   private static ResourceLocation resolveSkillId(String rawId) {
      String value = rawId.trim();

      try {
         ResourceLocation direct = ResourceLocation.parse(value);
         if (SkillAPI.getSkillRegistry().get(direct) != null) {
            return direct;
         }
      } catch (Exception var7) {
      }

      try {
         ResourceLocation tensura = ResourceLocation.fromNamespaceAndPath("tensura", value);
         if (SkillAPI.getSkillRegistry().get(tensura) != null) {
            return tensura;
         }
      } catch (Exception var6) {
      }

      try {
         ResourceLocation moreutils = ResourceLocation.fromNamespaceAndPath("tensuramoreutils", value);
         if (SkillAPI.getSkillRegistry().get(moreutils) != null) {
            return moreutils;
         }
      } catch (Exception var5) {
      }

      for (Entry<ResourceKey<ManasSkill>, ManasSkill> entry : SkillAPI.getSkillRegistry().entrySet()) {
         ResourceLocation id = entry.getKey().location();
         if (id.getPath().equals(value)) {
            return id;
         }
      }

      return ResourceLocation.parse(value);
   }

   private static ResourceLocation resolveEngravingId(CommandSourceStack source, String rawId) {
      Registry<Enchantment> registry = source.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
      String value = rawId.trim();

      try {
         ResourceLocation direct = ResourceLocation.parse(value);
         if (registry.containsKey(direct)) {
            return direct;
         }
      } catch (Exception var8) {
      }

      try {
         ResourceLocation tensura = ResourceLocation.fromNamespaceAndPath("tensura", value);
         if (registry.containsKey(tensura)) {
            return tensura;
         }
      } catch (Exception var7) {
      }

      try {
         ResourceLocation minecraft = ResourceLocation.withDefaultNamespace(value);
         if (registry.containsKey(minecraft)) {
            return minecraft;
         }
      } catch (Exception var6) {
      }

      for (ResourceLocation id : registry.keySet()) {
         if (id.getPath().equals(value)) {
            return id;
         }
      }

      return null;
   }

   private static CompletableFuture<Suggestions> suggestAllSkills(SuggestionsBuilder builder) {
      Set<String> suggestions = new LinkedHashSet<>();
      SkillAPI.getSkillRegistry()
         .entrySet()
         .stream()
         .map(entry -> ((ResourceKey)entry.getKey()).location())
         .sorted(Comparator.comparing(ResourceLocation::toString))
         .forEach(id -> {
            suggestions.add(id.toString());
            suggestions.add(id.getPath());
         });
      return SharedSuggestionProvider.suggest(suggestions, builder);
   }

   private static CompletableFuture<Suggestions> suggestBlockedSkills(SuggestionsBuilder builder) {
      Set<String> suggestions = new LinkedHashSet<>();
      MoreUtilsData.skillBans().keySet().stream().sorted(Comparator.comparing(ResourceLocation::toString)).forEach(id -> {
         suggestions.add(id.toString());
         suggestions.add(id.getPath());
      });
      return SharedSuggestionProvider.suggest(suggestions, builder);
   }

   private static CompletableFuture<Suggestions> suggestAllEngravings(CommandSourceStack source, SuggestionsBuilder builder) {
      Set<String> suggestions = new LinkedHashSet<>();
      Registry<Enchantment> registry = source.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
      registry.keySet().stream().sorted(Comparator.comparing(ResourceLocation::toString)).forEach(idx -> {
         suggestions.add(idx.toString());
         suggestions.add(idx.getPath());
      });

      for (String id : TensuraEnchantmentHelper.ENGRAVEMENT_ID_LIST) {
         suggestions.add(id);

         try {
            suggestions.add(ResourceLocation.parse(id).getPath());
         } catch (Exception var7) {
         }
      }

      return SharedSuggestionProvider.suggest(suggestions, builder);
   }

   private static CompletableFuture<Suggestions> suggestBlockedEngravings(SuggestionsBuilder builder) {
      Set<String> suggestions = new LinkedHashSet<>();
      MoreUtilsData.engravingBans().stream().sorted(Comparator.comparing(ResourceLocation::toString)).forEach(id -> {
         suggestions.add(id.toString());
         suggestions.add(id.getPath());
      });
      return SharedSuggestionProvider.suggest(suggestions, builder);
   }

   private static String readableSkillType(SkillBanType type) {
      return switch (type) {
         case LEARN -> "future obtainment only";
         case OWN -> "ban and clean";
         case FULL -> "blacklist";
      };
   }

   private static void send(CommandSourceStack source, String message) {
      source.sendSuccess(() -> Component.literal(message).withStyle(ChatFormatting.AQUA), true);
   }
}
