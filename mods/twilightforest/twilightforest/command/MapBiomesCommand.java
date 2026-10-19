package twilightforest.command;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.fml.loading.FMLEnvironment;
import twilightforest.beanification.Component;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFDataMaps;
import twilightforest.util.ColorUtil;
import twilightforest.util.datamaps.MagicMapBiomeColor;

@Component
public class MapBiomesCommand {
   private final DecimalFormat numberFormat = new DecimalFormat("#.00");
   private final HashMap<ResourceLocation, MapBiomesCommand.BiomeMapColor> BIOME2COLOR = new HashMap<>();

   private void init() {
      this.BIOME2COLOR.put(TFBiomes.STREAM.location(), new MapBiomesCommand.BiomeMapColor(0, 0, 255));
      this.BIOME2COLOR.put(TFBiomes.LAKE.location(), new MapBiomesCommand.BiomeMapColor(0, 0, 255));
      this.BIOME2COLOR.put(TFBiomes.CLEARING.location(), new MapBiomesCommand.BiomeMapColor(132, 245, 130));
      this.BIOME2COLOR.put(TFBiomes.OAK_SAVANNAH.location(), new MapBiomesCommand.BiomeMapColor(239, 245, 130));
      this.BIOME2COLOR.put(TFBiomes.FOREST.location(), new MapBiomesCommand.BiomeMapColor(0, 255, 0));
      this.BIOME2COLOR.put(TFBiomes.DENSE_FOREST.location(), new MapBiomesCommand.BiomeMapColor(0, 170, 0));
      this.BIOME2COLOR.put(TFBiomes.FIREFLY_FOREST.location(), new MapBiomesCommand.BiomeMapColor(88, 252, 102));
      this.BIOME2COLOR.put(TFBiomes.ENCHANTED_FOREST.location(), new MapBiomesCommand.BiomeMapColor(0, 255, 255));
      this.BIOME2COLOR.put(TFBiomes.SPOOKY_FOREST.location(), new MapBiomesCommand.BiomeMapColor(119, 0, 255));
      this.BIOME2COLOR.put(TFBiomes.MUSHROOM_FOREST.location(), new MapBiomesCommand.BiomeMapColor(204, 0, 139));
      this.BIOME2COLOR.put(TFBiomes.DENSE_MUSHROOM_FOREST.location(), new MapBiomesCommand.BiomeMapColor(184, 48, 184));
      this.BIOME2COLOR.put(TFBiomes.SWAMP.location(), new MapBiomesCommand.BiomeMapColor(0, 204, 187));
      this.BIOME2COLOR.put(TFBiomes.FIRE_SWAMP.location(), new MapBiomesCommand.BiomeMapColor(140, 0, 0));
      this.BIOME2COLOR.put(TFBiomes.DARK_FOREST.location(), new MapBiomesCommand.BiomeMapColor(25, 61, 13));
      this.BIOME2COLOR.put(TFBiomes.DARK_FOREST_CENTER.location(), new MapBiomesCommand.BiomeMapColor(157, 79, 0));
      this.BIOME2COLOR.put(TFBiomes.SNOWY_FOREST.location(), new MapBiomesCommand.BiomeMapColor(255, 255, 255));
      this.BIOME2COLOR.put(TFBiomes.GLACIER.location(), new MapBiomesCommand.BiomeMapColor(130, 191, 245));
      this.BIOME2COLOR.put(TFBiomes.HIGHLANDS.location(), new MapBiomesCommand.BiomeMapColor(100, 65, 0));
      this.BIOME2COLOR.put(TFBiomes.THORNLANDS.location(), new MapBiomesCommand.BiomeMapColor(128, 100, 90));
      this.BIOME2COLOR.put(TFBiomes.FINAL_PLATEAU.location(), new MapBiomesCommand.BiomeMapColor(128, 128, 128));
   }

   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("biomepng")
               .requires(cs -> cs.hasPermission(2)))
            .executes(context -> this.createMap((CommandSourceStack)context.getSource(), 4096, 4096, true)))
         .then(
            ((RequiredArgumentBuilder)Commands.argument("width", IntegerArgumentType.integer(0))
                  .executes(
                     context -> this.createMap(
                        (CommandSourceStack)context.getSource(),
                        IntegerArgumentType.getInteger(context, "width"),
                        IntegerArgumentType.getInteger(context, "width"),
                        true
                     )
                  ))
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("height", IntegerArgumentType.integer(0))
                        .executes(
                           context -> this.createMap(
                              (CommandSourceStack)context.getSource(),
                              IntegerArgumentType.getInteger(context, "width"),
                              IntegerArgumentType.getInteger(context, "height"),
                              true
                           )
                        ))
                     .then(
                        Commands.argument("showBiomePercents", BoolArgumentType.bool())
                           .executes(
                              context -> this.createMap(
                                 (CommandSourceStack)context.getSource(),
                                 IntegerArgumentType.getInteger(context, "width"),
                                 IntegerArgumentType.getInteger(context, "height"),
                                 BoolArgumentType.getBool(context, "showBiomePercents")
                              )
                           )
                     )
               )
         );
   }

   private int createMap(CommandSourceStack source, int width, int height, boolean showBiomePercents) {
      if (FMLEnvironment.dist.isDedicatedServer()) {
         return -1;
      }

      if (this.BIOME2COLOR.isEmpty()) {
         this.init();
      }

      Map<Holder<Biome>, Integer> biomeCount = new HashMap<>();
      NativeImage img = new NativeImage(width, height, false);
      int progressUpdate = img.getHeight() / 8;

      for (int x = 0; x < img.getHeight(); x++) {
         for (int z = 0; z < img.getWidth(); z++) {
            ServerLevel level = source.getLevel();
            Holder<Biome> b = level.getNoiseBiome(x - img.getWidth() / 2, 0, z - img.getHeight() / 2);
            ResourceLocation key = level.registryAccess().registryOrThrow(Registries.BIOME).getKey((Biome)b.value());
            MapBiomesCommand.BiomeMapColor color = this.BIOME2COLOR.get(key);
            if (color == null) {
               int colorInt = getBiomeColor(b);
               if (colorInt == 0) {
                  colorInt = ((Biome)b.value()).getGrassColor(0.0, 0.0);
               }

               this.BIOME2COLOR.put(key, color = new MapBiomesCommand.BiomeMapColor(colorInt | 0xFF000000));
            }

            if (!biomeCount.containsKey(b)) {
               biomeCount.put(b, 0);
            } else {
               biomeCount.put(b, biomeCount.get(b) + 1);
            }

            img.setPixelRGBA(x, z, ColorUtil.argbToABGR(color.getARGB()));
         }

         if (x % progressUpdate == 0) {
            int finalX = x;
            double percentComplete = (double)finalX / img.getHeight() * 100.0;
            String percentDisplay = this.numberFormat.format(percentComplete);
            source.sendSuccess(
               () -> net.minecraft.network.chat.Component.translatable("commands.tffeature.biomepng.progress", new Object[]{percentDisplay}), false
            );
         }
      }

      if (showBiomePercents) {
         source.sendSuccess(
            () -> net.minecraft.network.chat.Component.translatable("commands.tffeature.biomepng.counts_header", new Object[]{width, height}), false
         );
         int totalCount = biomeCount.values().stream().mapToInt(i -> i).sum();
         biomeCount.forEach(
            (biome, integer) -> source.sendSuccess(
               () -> net.minecraft.network.chat.Component.literal(
                     source.getLevel().registryAccess().registryOrThrow(Registries.BIOME).getKey((Biome)biome.value()).toString()
                  )
                  .append(": " + integer + ChatFormatting.GRAY + " (" + this.numberFormat.format((double)integer.intValue() / totalCount * 100.0) + "%)"),
               false
            )
         );
      }

      int startX = Mth.floor(source.getPosition().x()) - img.getWidth() / 2;
      int startZ = Mth.floor(source.getPosition().z()) - img.getHeight() / 2;
      Path path = source.getLevel()
         .getServer()
         .getWorldPath(LevelResource.GENERATED_DIR)
         .resolve("biomemaps")
         .resolve(String.valueOf(source.getLevel().getSeed()))
         .resolve("biome_map-" + source.getLevel().getSeed() + "-(" + startX + "." + startZ + ")-(" + (startX + width) + "." + (startZ + height) + ").png")
         .normalize();

      try {
         if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.write(path, img.asByteArray());
         }
      } catch (IOException e) {
         e.printStackTrace();
         source.sendFailure(net.minecraft.network.chat.Component.translatable("commands.tffeature.biomepng.save_failed"));
         return 0;
      }

      source.sendSuccess(() -> net.minecraft.network.chat.Component.translatable("commands.tffeature.biomepng.save_success"), false);
      return 1;
   }

   public static int getBiomeColor(Holder<Biome> biome) {
      MagicMapBiomeColor c = (MagicMapBiomeColor)biome.getData(TFDataMaps.MAGIC_MAP_BIOME_COLOR);
      return c != null ? getMapColor(c) : -16777216;
   }

   public static int getMapColor(MagicMapBiomeColor color) {
      int j = color.color().col >> 16 & 0xFF;
      int k = color.color().col >> 8 & 0xFF;
      int l = color.color().col & 0xFF;
      return 0xFF000000 | l << 16 | k << 8 | j;
   }

   public static class BiomeMapColor {
      private final int value;

      public BiomeMapColor(int r, int g, int b) {
         this(r, g, b, 255);
      }

      public BiomeMapColor(int r, int g, int b, int a) {
         this.value = (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
      }

      public BiomeMapColor(int rgb) {
         this.value = 0xFF000000 | rgb;
      }

      public int getARGB() {
         return this.value;
      }
   }
}
