package twilightforest.client.model.block.aurorablock;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;

public class NoiseVaryingModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T> {
   private final List<T> variants = new ArrayList<>();

   public NoiseVaryingModelBuilder(T parent, ExistingFileHelper existingFileHelper) {
      super(TwilightForestMod.prefix("noise_varying"), parent, existingFileHelper, false);
   }

   public NoiseVaryingModelBuilder<T> add(T builder) {
      builder.assertExistence();
      this.variants.add(builder);
      return this;
   }

   public NoiseVaryingModelBuilder<T> addAll(T[] builders) {
      Arrays.<T>stream(builders).forEach(this::add);
      return this;
   }

   public T end() {
      Preconditions.checkArgument(!this.variants.isEmpty(), "Noise Varying builder cannot have zero variants.");
      return (T)super.end();
   }

   public JsonObject toJson(JsonObject json) {
      JsonObject mainJson = super.toJson(json);
      JsonArray variants = new JsonArray();
      this.variants.stream().map(ModelFile::getLocation).map(ResourceLocation::toString).forEach(variants::add);
      mainJson.add("variants", variants);
      return mainJson;
   }
}
