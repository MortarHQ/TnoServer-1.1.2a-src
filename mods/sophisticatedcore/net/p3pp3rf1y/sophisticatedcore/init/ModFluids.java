package net.p3pp3rf1y.sophisticatedcore.init;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Flowing;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Source;
import net.neoforged.neoforge.fluids.FluidType.Properties;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {
   public static final ResourceLocation EXPERIENCE_TAG_NAME = ResourceLocation.fromNamespaceAndPath("c", "experience");
   public static final TagKey<Fluid> EXPERIENCE_TAG = TagKey.create(Registries.FLUID, EXPERIENCE_TAG_NAME);
   public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, "sophisticatedcore");
   public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, "sophisticatedcore");
   public static final Supplier<FlowingFluid> XP_STILL = FLUIDS.register("xp_still", () -> new Source(fluidProperties()));
   public static final Supplier<FlowingFluid> XP_FLOWING = FLUIDS.register("xp_flowing", () -> new Flowing(fluidProperties()));
   public static final Supplier<FluidType> XP_FLUID_TYPE = FLUID_TYPES.register(
      "experience", () -> new FluidType(Properties.create().lightLevel(10).density(800).viscosity(1500))
   );
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "sophisticatedcore");
   public static final Supplier<Item> XP_BUCKET = ITEMS.register(
      "xp_bucket", () -> new BucketItem((Fluid)XP_STILL.get(), new net.minecraft.world.item.Item.Properties().stacksTo(1))
   );
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
      Registries.CREATIVE_MODE_TAB.location(), "sophisticatedcore"
   );
   public static final Supplier<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register(
      "main",
      () -> CreativeModeTab.builder()
         .icon(() -> new ItemStack((ItemLike)XP_BUCKET.get()))
         .title(Component.translatable("itemGroup.sophisticatedcore"))
         .displayItems((featureFlags, output) -> output.accept(new ItemStack((ItemLike)XP_BUCKET.get())))
         .build()
   );

   private ModFluids() {
   }

   private static net.neoforged.neoforge.fluids.BaseFlowingFluid.Properties fluidProperties() {
      return new net.neoforged.neoforge.fluids.BaseFlowingFluid.Properties(XP_FLUID_TYPE, XP_STILL, XP_FLOWING).bucket(XP_BUCKET);
   }

   public static void registerHandlers(IEventBus modBus) {
      FLUIDS.register(modBus);
      FLUID_TYPES.register(modBus);
      ITEMS.register(modBus);
      CREATIVE_MODE_TABS.register(modBus);
   }
}
