package nadiendev.constructionwand.basics;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ConfigClient {
   private static final Builder BUILDER = new Builder();
   public static final IntValue OPT_KEY = BUILDER.defineInRange("OptKey", 341, 0, 350);
   public static final BooleanValue SHIFTOPT_MODE = BUILDER.define("ShiftOpt", false);
   public static final BooleanValue SHIFTOPT_GUI = BUILDER.define("ShiftOptGUI", true);
   public static final ModConfigSpec SPEC = BUILDER.build();

   static {
      BUILDER.comment(
         new String[]{
            "This is the Client config for ConstructionWand.",
            "If you're not familiar with Forge's new split client/server config, let me explain:",
            "Client config is stored in the /config folder and only contains client specific settings like graphics and keybinds.",
            "Mod behavior is configured in the Server config, which is world-specific and thus located",
            "in the /saves/myworld/serverconfig folder. If you want to change the serverconfig for all",
            "new worlds, copy the config files in the /defaultconfigs folder."
         }
      );
      BUILDER.push("keys");
      BUILDER.comment("Key code of OPTKEY (Default: Left Control). Look up key codes under https://www.glfw.org/docs/3.3/group__keys.html");
      BUILDER.comment("Press SNEAK+OPTKEY instead of SNEAK for changing wand mode/direction lock");
      BUILDER.comment("Press SNEAK+OPTKEY instead of SNEAK for opening wand GUI");
      BUILDER.pop();
   }
}
