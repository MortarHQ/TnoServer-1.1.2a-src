package cn.leolezury.eternalstarlight.common.resource.gatekeeper;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

public class TheGatekeeperNameManager extends SimpleJsonResourceReloadListener {
   private static final ResourceLocation NAMES_LOCATION = EternalStarlight.id("eternal_starlight/gatekeeper_names/gatekeeper_names.json");
   private static final RandomSource RANDOM = RandomSource.create();
   public static final Gson GSON_INSTANCE = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final List<String> names = Lists.newArrayList();
   private static final String FOLDER = "eternal_starlight/gatekeeper_names";

   public TheGatekeeperNameManager() {
      super(GSON_INSTANCE, "eternal_starlight/gatekeeper_names");
   }

   protected void apply(Map<ResourceLocation, JsonElement> resourceList, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
      for (Resource iresource : resourceManagerIn.getResourceStack(NAMES_LOCATION)) {
         try (
            InputStream inputstream = iresource.open();
            Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
         ) {
            JsonObject jsonobject = (JsonObject)GsonHelper.fromJson(GSON_INSTANCE, reader, JsonObject.class);
            boolean replace = jsonobject.get("replace").getAsBoolean();
            if (replace) {
               this.names.clear();
            }

            for (JsonElement entry : jsonobject.get("values").getAsJsonArray()) {
               this.names.add(entry.getAsString());
            }
         } catch (RuntimeException | IOException var17) {
         }
      }
   }

   @Nullable
   public String getTheGatekeeperName() {
      return this.names.get(RANDOM.nextInt(this.names.size()));
   }
}
