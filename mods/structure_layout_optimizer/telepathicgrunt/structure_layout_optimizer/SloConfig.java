package telepathicgrunt.structure_layout_optimizer;

import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo.Link;

@ConfigInfo(
   title = "Structure Layout Optimizer",
   titleTranslation = "config.structure_layout_optimizer.title",
   description = "An attempt at optimizing jigsaw generation",
   descriptionTranslation = "config.structure_layout_optimizer.desc",
   icon = "layout-template",
   links = {
         @Link(text = "Curseforge", value = "https://www.curseforge.com/projects/1087831", icon = "curseforge"),
         @Link(text = "Modrinth", value = "https://modrinth.com/mod/ayPU0OHc", icon = "modrinth"),
         @Link(
            text = "Report a Bug",
            textTranslation = "config.structure_layout_optimizer.link.bug_report",
            value = "https://github.com/TelepathicGrunt/StructureLayoutOptimizer/issues",
            icon = "bug"
         ),
         @Link(text = "GitHub", value = "https://github.com/TelepathicGrunt/StructureLayoutOptimizer", icon = "github"),
         @Link(text = "Discord", value = "https://discord.gg/K8qRev3yKZ", icon = "gamepad-2"),
         @Link(
            text = "License",
            textTranslation = "config.structure_layout_optimizer.link.license",
            value = "https://github.com/TelepathicGrunt/StructureLayoutOptimizer/blob/HEAD/LICENSE.txt",
            icon = "copyright"
         )
   }
)
@Config("structure_layout_optimizer")
public class SloConfig {
   @Comment(
      value = "Whether to use an alternative strategy to make structure layouts generate slightly even faster than\nthe default optimization this mod has for template pool weights. This alternative strategy works by\nchanging the list of pieces that structures collect from the template pool to not have duplicate entries.\n\nThis will not break the structure generation, but it will make the structure layout different than\nif this config was off (breaking vanilla seed parity). The cost of speed may be worth it in large\nmodpacks where many structure mods are using very high weight values in their template pools.\n\nPros: Get a bit more performance from high weight Template Pool Structures.\nCons: Loses parity with vanilla seeds on the layout of the structure. (Structure layout is not broken, just different)\n",
      translation = "config.structure_layout_optimizer.deduplicate_shuffled_template_pool_element_list.desc"
   )
   @ConfigEntry(
      id = "deduplicateShuffledTemplatePoolElementList",
      translation = "config.structure_layout_optimizer.deduplicate_shuffled_template_pool_element_list"
   )
   public static boolean deduplicateShuffledTemplatePoolElementList = false;
}
