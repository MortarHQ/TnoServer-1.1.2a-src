package com.foxablazeultimate.registry;

import com.foxablazeultimate.item.TimeCrystalItem;
import com.foxablazeultimate.item.WisdomCrystalItem;
import com.foxablazeultimate.item.beelzebub.CapturedEntityItem;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public final class FoxAblazeUltimateItems {
   public static final Items ITEMS = DeferredRegister.createItems("foxablazeultimate");
   public static final DeferredHolder<Item, CapturedEntityItem> CAPTURED_ENTITY = ITEMS.register(
      "captured_entity", () -> new CapturedEntityItem(new Properties())
   );
   public static final DeferredHolder<Item, WisdomCrystalItem> WISDOM_CRYSTAL = ITEMS.register(
      "wisdom_crystal",
      () -> new WisdomCrystalItem(
         new Properties(),
         () -> (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get(),
         List.of(
            new WisdomCrystalItem.MasteryBoost(ResourceLocation.fromNamespaceAndPath("tensura", "great_sage"), 0.25),
            new WisdomCrystalItem.MasteryBoost(ResourceLocation.fromNamespaceAndPath("tensura", "sage"), 0.25)
         ),
         0.1,
         12000
      )
   );
   public static final DeferredHolder<Item, WisdomCrystalItem> GLUTTONY_CRYSTAL = ITEMS.register(
      "gluttony_crystal",
      () -> new WisdomCrystalItem(
         new Properties(),
         () -> (ManasSkill)FoxAblazeUltimateSkills.BEELZEBUB.get(),
         List.of(
            new WisdomCrystalItem.MasteryBoost(ResourceLocation.fromNamespaceAndPath("tensura", "gluttony"), 0.25),
            new WisdomCrystalItem.MasteryBoost(ResourceLocation.fromNamespaceAndPath("tensura", "predator"), 0.25)
         )
      )
   );
   public static final DeferredHolder<Item, WisdomCrystalItem> COVENANT_CRYSTAL = ITEMS.register(
      "covenant_crystal",
      () -> new WisdomCrystalItem(
         new Properties(),
         () -> (ManasSkill)FoxAblazeUltimateSkills.URIEL.get(),
         List.of(new WisdomCrystalItem.MasteryBoost(ResourceLocation.fromNamespaceAndPath("tensura", "infinity_prison"), 0.25))
      )
   );
   public static final DeferredHolder<Item, WisdomCrystalItem> WRATH_CRYSTAL = ITEMS.register(
      "wrath_crystal", () -> new WisdomCrystalItem(new Properties(), () -> (ManasSkill)FoxAblazeUltimateSkills.SATANAEL.get())
   );
   public static final DeferredHolder<Item, WisdomCrystalItem> HARVEST_CRYSTAL = ITEMS.register(
      "harvest_crystal", () -> new WisdomCrystalItem(new Properties(), () -> (ManasSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get())
   );
   public static final DeferredHolder<Item, TimeCrystalItem> TIME_CRYSTAL = ITEMS.register("time_crystal", () -> new TimeCrystalItem(new Properties()));
   public static final DeferredHolder<Item, WisdomCrystalItem> FLAME_CRYSTAL = ITEMS.register(
      "flame_crystal", () -> new WisdomCrystalItem(new Properties(), () -> (ManasSkill)FoxAblazeUltimateSkills.VELGRYND.get())
   );
   public static final DeferredHolder<Item, WisdomCrystalItem> VOID_CRYSTAL = ITEMS.register(
      "void_crystal", () -> new WisdomCrystalItem(new Properties(), () -> (ManasSkill)FoxAblazeUltimateSkills.AZATHOTH.get())
   );

   private FoxAblazeUltimateItems() {
   }

   public static void register(IEventBus modBus) {
      ITEMS.register(modBus);
   }
}
