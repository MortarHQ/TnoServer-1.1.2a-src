package dev.ftbqlang.server;

import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.translation.TranslationManager;
import dev.ftbqlang.mixin.TranslationManagerAccessor;
import dev.ftbqlang.network.LanguageStatePayload;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public final class LanguageService {
   private LanguageService() {
   }

   public static void query(ServerPlayer player, boolean bookOpened) {
      respond(player, bookOpened, null);
   }

   public static void select(ServerPlayer player, String requested) {
      respond(player, false, requested);
   }

   private static void respond(ServerPlayer player, boolean bookOpened, String requested) {
      ServerQuestFile file = ServerQuestFile.INSTANCE;
      if (file != null && file.isValid()) {
         LanguageCatalog catalog = LanguageCatalog.scan(file.getFolder().resolve("lang"));
         TranslationManager translations = file.getTranslationManager();
         LanguagePolicy.initializeMissingTables(((TranslationManagerAccessor)translations).ftbqlang$getTables(), catalog.tables());
         List<String> languages = catalog.languages();
         LanguagePreferences preferences = LanguagePreferences.get(player.serverLevel().getServer());
         LanguagePolicy.Preference previous = preferences.get(player.getUUID());
         String requestedLocale = LanguagePolicy.normalize(requested);
         if (requested != null && LanguagePolicy.isValidLocale(requested) && catalog.tables().containsKey(requestedLocale)) {
            previous = new LanguagePolicy.Preference(requestedLocale, true);
         }

         LanguagePolicy.Decision decision = LanguagePolicy.decide(languages, previous, player.clientInformation().language(), bookOpened);
         preferences.put(player.getUUID(), new LanguagePolicy.Preference(decision.selected(), decision.prompted()));
         LinkedHashSet<String> toSend = new LinkedHashSet<>(List.of("en_us", file.getFallbackLocale(), decision.selected()));
         toSend.remove("");

         for (String locale : toSend) {
            translations.sendTableToPlayer(player, locale);
         }

         PacketDistributor.sendToPlayer(player, new LanguageStatePayload(languages, decision.selected(), decision.prompt()), new CustomPacketPayload[0]);
      } else {
         PacketDistributor.sendToPlayer(player, new LanguageStatePayload(List.of(), "", false), new CustomPacketPayload[0]);
      }
   }
}
