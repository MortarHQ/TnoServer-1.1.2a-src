package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

public class ESTrimMaterials {
   public static final ResourceKey<TrimMaterial> RED_STARLIGHT_CRYSTAL = create("red_starlight_crystal");
   public static final ResourceKey<TrimMaterial> BLUE_STARLIGHT_CRYSTAL = create("blue_starlight_crystal");
   public static final ResourceKey<TrimMaterial> STARCORE = create("starcore");
   public static final ResourceKey<TrimMaterial> THIOQUARTZ = create("thioquartz");
   public static final ResourceKey<TrimMaterial> AETHERSENT = create("aethersent");
   public static final ResourceKey<TrimMaterial> THERMAL_SPRINGSTONE = create("thermal_springstone");
   public static final ResourceKey<TrimMaterial> GLACITE = create("glacite");
   public static final ResourceKey<TrimMaterial> STARLIT_DIAMOND = create("starlit_diamond");
   public static final ResourceKey<TrimMaterial> DEEPSILVER = create("deepsilver");
   public static final ResourceKey<TrimMaterial> UNREALIUM = create("unrealium");
   public static final ResourceKey<TrimMaterial> MALARITE = create("malarite");
   public static final ResourceKey<TrimMaterial> STARFIRE = create("starfire");
   public static final ResourceKey<TrimMaterial> FLOWGLAZE = create("flowglaze");
   public static final ResourceKey<TrimMaterial> AMARAMBER = create("amaramber");
   public static final ResourceKey<TrimMaterial> GOLEM_STEEL = create("golem_steel");
   public static final ResourceKey<TrimMaterial> MOONRING = create("moonring");
   public static final ResourceKey<TrimMaterial> DOOMEDEN_BONE = create("doomeden_bone");
   public static final List<ResourceKey<TrimMaterial>> TRIM_MATERIALS = List.of(
      RED_STARLIGHT_CRYSTAL,
      BLUE_STARLIGHT_CRYSTAL,
      STARCORE,
      THIOQUARTZ,
      AETHERSENT,
      THERMAL_SPRINGSTONE,
      GLACITE,
      STARLIT_DIAMOND,
      DEEPSILVER,
      UNREALIUM,
      MALARITE,
      STARFIRE,
      FLOWGLAZE,
      AMARAMBER,
      GOLEM_STEEL,
      MOONRING,
      DOOMEDEN_BONE
   );

   public static void bootstrap(BootstrapContext<TrimMaterial> context) {
      register(context, RED_STARLIGHT_CRYSTAL, ESItems.RED_STARLIGHT_CRYSTAL_SHARD.asHolder(), 11939952, 1.0F);
      register(context, BLUE_STARLIGHT_CRYSTAL, ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.asHolder(), 3182518, 0.8F);
      register(context, STARCORE, ESItems.STARCORE.asHolder(), 14706750, 0.5F);
      register(context, THIOQUARTZ, ESItems.THIOQUARTZ_SHARD.asHolder(), 14148520, 0.7F);
      register(context, AETHERSENT, ESItems.AETHERSENT_INGOT.asHolder(), 9461416, 1.0F);
      register(context, THERMAL_SPRINGSTONE, ESItems.THERMAL_SPRINGSTONE_INGOT.asHolder(), 16629111, 0.5F);
      register(context, GLACITE, ESItems.GLACITE_SHARD.asHolder(), 13303551, 0.8F);
      register(context, STARLIT_DIAMOND, ESItems.STARLIT_DIAMOND.asHolder(), 9798884, 0.8F);
      register(context, DEEPSILVER, ESItems.DEEPSILVER_INGOT.asHolder(), 8886200, 0.2F);
      register(context, UNREALIUM, ESItems.UNREALIUM_INGOT.asHolder(), 8468633, 1.0F);
      register(context, MALARITE, ESItems.MALARITE.asHolder(), 8016266, 1.0F);
      register(context, STARFIRE, ESItems.STARFIRE.asHolder(), 16743741, 0.5F);
      register(context, FLOWGLAZE, ESItems.FLOWGLAZE.asHolder(), 9361072, 0.8F);
      register(context, AMARAMBER, ESItems.AMARAMBER_INGOT.asHolder(), 12805246, 1.0F);
      register(context, GOLEM_STEEL, ESItems.GOLEM_STEEL_INGOT.asHolder(), 3766954, 0.2F);
      register(context, MOONRING, ESItems.TENACIOUS_PETAL.asHolder(), 10710430, 0.8F);
      register(context, DOOMEDEN_BONE, ESItems.BROKEN_DOOMEDEN_BONE.asHolder(), 9929090, 0.1F);
   }

   private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> key, Holder<Item> trimItem, int color, float itemModelIndex) {
      TrimMaterial material = new TrimMaterial(
         key.location().getPath(),
         trimItem,
         itemModelIndex,
         Map.of(),
         Component.translatable(Util.makeDescriptionId("trim_material", key.location())).withStyle(Style.EMPTY.withColor(color))
      );
      context.register(key, material);
   }

   private static ResourceKey<TrimMaterial> create(String name) {
      return ResourceKey.create(Registries.TRIM_MATERIAL, EternalStarlight.id(name));
   }
}
