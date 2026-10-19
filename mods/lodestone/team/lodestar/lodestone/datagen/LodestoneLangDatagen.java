package team.lodestar.lodestone.datagen;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;

public class LodestoneLangDatagen extends LanguageProvider {
   public LodestoneLangDatagen(PackOutput output) {
      super(output, "lodestone", "en_us");
   }

   protected void addTranslations() {
      Set<DeferredHolder<Attribute, ? extends Attribute>> attributes = new HashSet<>(LodestoneAttributes.ATTRIBUTES.getEntries());
      attributes.forEach(a -> {
         String name = DataHelper.toTitleCase(a.getId().getPath(), "_");
         this.add("attribute.name.lodestone." + a.getId().getPath(), name);
      });
      this.addOption("screenshake_intensity", "Screenshake Intensity");
      this.addOptionTooltip("screenshake_intensity", "Controls how much screenshake is applied to your screen.");
      this.addOption("fire_offset", "Fire Overlay Offset");
      this.addOptionTooltip("fire_offset", "Offsets the fire overlay effect downwards, clearing up your vision.");
      this.addCommand("devsetup", "World setup for not-annoying development work");
      this.addCommand("screenshake", "Command Successful, enjoy your screenshake.");
   }

   public void addCommand(String command, String feedback) {
      this.add(getCommand(command), feedback);
   }

   public static String getCommand(String command) {
      return "command.lodestone." + command;
   }

   public void addOption(String option, String result) {
      this.add(getOption(option), result);
   }

   public static String getOption(String option) {
      return "options.lodestone." + option;
   }

   public void addOptionTooltip(String option, String result) {
      this.add(getOptionTooltip(option), result);
   }

   public static String getOptionTooltip(String option) {
      return "options.lodestone." + option + ".tooltip";
   }

   public String getName() {
      return "Lodestone Lang Entries";
   }
}
