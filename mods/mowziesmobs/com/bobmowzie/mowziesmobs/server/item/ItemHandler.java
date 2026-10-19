package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.PawType;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemHandler {
   private static final int NEGATE_ATTACK_DAMAGE = -2;
   private static final int NEGATE_ATTACK_SPEED = -4;
   public static Style TOOLTIP_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY));
   public static final DeferredRegister<Item> REG = DeferredRegister.create(Registries.ITEM, "mowziesmobs");
   public static final DeferredHolder<Item, ItemFoliaathSeed> FOLIAATH_SEED = REG.register("foliaath_seed", () -> new ItemFoliaathSeed(new Properties()));
   public static final DeferredHolder<Item, ItemMobRemover> MOB_REMOVER = REG.register("mob_remover", () -> new ItemMobRemover(new Properties()));
   public static final DeferredHolder<Item, ItemWroughtAxe> WROUGHT_AXE = REG.register(
      "wrought_axe", () -> new ItemWroughtAxe(new Properties().rarity(Rarity.UNCOMMON))
   );
   public static final DeferredHolder<Item, ItemWroughtHelm> WROUGHT_HELMET = REG.register(
      "wrought_helmet", () -> new ItemWroughtHelm(new Properties().rarity(Rarity.UNCOMMON).durability(Type.HELMET.getDurability(15)))
   );
   public static final DeferredHolder<Item, ItemUmvuthanaMask> UMVUTHANA_MASK_FURY = REG.register(
      "umvuthana_mask_fury", () -> new ItemUmvuthanaMask(MaskType.FURY, new Properties().durability(Type.HELMET.getDurability(5)))
   );
   public static final DeferredHolder<Item, ItemUmvuthanaMask> UMVUTHANA_MASK_FEAR = REG.register(
      "umvuthana_mask_fear", () -> new ItemUmvuthanaMask(MaskType.FEAR, new Properties().durability(Type.HELMET.getDurability(5)))
   );
   public static final DeferredHolder<Item, ItemUmvuthanaMask> UMVUTHANA_MASK_RAGE = REG.register(
      "umvuthana_mask_rage", () -> new ItemUmvuthanaMask(MaskType.RAGE, new Properties().durability(Type.HELMET.getDurability(5)))
   );
   public static final DeferredHolder<Item, ItemUmvuthanaMask> UMVUTHANA_MASK_BLISS = REG.register(
      "umvuthana_mask_bliss", () -> new ItemUmvuthanaMask(MaskType.BLISS, new Properties().durability(Type.HELMET.getDurability(5)))
   );
   public static final DeferredHolder<Item, ItemUmvuthanaMask> UMVUTHANA_MASK_MISERY = REG.register(
      "umvuthana_mask_misery", () -> new ItemUmvuthanaMask(MaskType.MISERY, new Properties().durability(Type.HELMET.getDurability(5)))
   );
   public static final DeferredHolder<Item, ItemUmvuthanaMask> UMVUTHANA_MASK_FAITH = REG.register(
      "umvuthana_mask_faith", () -> new ItemUmvuthanaMask(MaskType.FAITH, new Properties().durability(Type.HELMET.getDurability(5)))
   );
   public static final DeferredHolder<Item, ItemSolVisage> SOL_VISAGE = REG.register(
      "sol_visage", () -> new ItemSolVisage(new Properties().rarity(Rarity.RARE).durability(Type.HELMET.getDurability(7)))
   );
   public static final DeferredHolder<Item, ItemDart> DART = REG.register("dart", () -> new ItemDart(new Properties()));
   public static final DeferredHolder<Item, ItemSpear> SPEAR = REG.register("spear", () -> new ItemSpear(new Properties().stacksTo(1)));
   public static final DeferredHolder<Item, ItemBlowgun> BLOWGUN = REG.register("blowgun", () -> new ItemBlowgun(new Properties().stacksTo(1).durability(300)));
   public static final DeferredHolder<Item, ItemGrantSunsBlessing> GRANT_SUNS_BLESSING = REG.register(
      "grant_suns_blessing", () -> new ItemGrantSunsBlessing(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );
   public static final DeferredHolder<Item, ItemIceCrystal> ICE_CRYSTAL = REG.register(
      "ice_crystal",
      () -> new ItemIceCrystal(new Properties().durability(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.durabilityValue).rarity(Rarity.RARE))
   );
   public static final DeferredHolder<Item, ItemCapturedGrottol> CAPTURED_GROTTOL = REG.register(
      "captured_grottol", () -> new ItemCapturedGrottol(new Properties().stacksTo(1))
   );
   public static final DeferredHolder<Item, ItemGlowingJelly> GLOWING_JELLY = REG.register(
      "glowing_jelly", () -> new ItemGlowingJelly(new Properties().food(ItemGlowingJelly.GLOWING_JELLY_FOOD))
   );
   public static final DeferredHolder<Item, ItemNagaFang> NAGA_FANG = REG.register("naga_fang", () -> new ItemNagaFang(new Properties()));
   public static final DeferredHolder<Item, ItemNagaFangDagger> NAGA_FANG_DAGGER = REG.register(
      "naga_fang_dagger", () -> new ItemNagaFangDagger(new Properties())
   );
   public static final DeferredHolder<Item, ItemEarthrendGauntlet> EARTHREND_GAUNTLET = REG.register(
      "earthrend_gauntlet", () -> new ItemEarthrendGauntlet(new Properties().rarity(Rarity.RARE))
   );
   public static final DeferredHolder<Item, ItemSculptorStaff> SCULPTOR_STAFF = REG.register(
      "sculptor_staff", () -> new ItemSculptorStaff(new Properties().rarity(Rarity.RARE))
   );
   public static final DeferredHolder<Item, ItemSandRake> SAND_RAKE = REG.register("sand_rake", () -> new ItemSandRake(new Properties().durability(64)));
   public static final DeferredHolder<Item, ArmorItem> GEOMANCER_BEADS = REG.register(
      "geomancer_beads", () -> new ItemGeomancerArmor(Type.HELMET, new Properties().rarity(Rarity.UNCOMMON).durability(Type.HELMET.getDurability(33)))
   );
   public static final DeferredHolder<Item, ArmorItem> GEOMANCER_ROBE = REG.register(
      "geomancer_robe", () -> new ItemGeomancerArmor(Type.CHESTPLATE, new Properties().rarity(Rarity.UNCOMMON).durability(Type.CHESTPLATE.getDurability(33)))
   );
   public static final DeferredHolder<Item, ArmorItem> GEOMANCER_BELT = REG.register(
      "geomancer_belt", () -> new ItemGeomancerArmor(Type.LEGGINGS, new Properties().rarity(Rarity.UNCOMMON).durability(Type.LEGGINGS.getDurability(33)))
   );
   public static final DeferredHolder<Item, ArmorItem> GEOMANCER_SANDALS = REG.register(
      "geomancer_sandals", () -> new ItemGeomancerArmor(Type.BOOTS, new Properties().rarity(Rarity.UNCOMMON).durability(Type.BOOTS.getDurability(33)))
   );
   public static final DeferredHolder<Item, ItemBluffRod> BLUFF_ROD = REG.register("bluff_rod", () -> new ItemBluffRod(new Properties()));
   public static final DeferredHolder<Item, ItemElokosaPaw> ELOKOSA_PAW_FULL = REG.register(
      "elokosa_paw_full", () -> new ItemElokosaPaw(PawType.FULL, new Properties())
   );
   public static final DeferredHolder<Item, ItemElokosaPaw> ELOKOSA_PAW_GIBBOUS = REG.register(
      "elokosa_paw_gibbous", () -> new ItemElokosaPaw(PawType.GIBBOUS, new Properties())
   );
   public static final DeferredHolder<Item, ItemElokosaPaw> ELOKOSA_PAW_HALF = REG.register(
      "elokosa_paw_half", () -> new ItemElokosaPaw(PawType.HALF, new Properties())
   );
   public static final DeferredHolder<Item, ItemElokosaPaw> ELOKOSA_PAW_CRESCENT = REG.register(
      "elokosa_paw_crescent", () -> new ItemElokosaPaw(PawType.CRESCENT, new Properties())
   );
   public static final DeferredHolder<Item, ItemElokosaPaw> ELOKOSA_PAW_NEW = REG.register(
      "elokosa_paw_new", () -> new ItemElokosaPaw(PawType.NEW, new Properties())
   );
   public static final ImmutableList<DeferredHolder<Item, ItemElokosaPaw>> ELOKOSA_PAWS = ImmutableList.of(
      ELOKOSA_PAW_FULL, ELOKOSA_PAW_GIBBOUS, ELOKOSA_PAW_HALF, ELOKOSA_PAW_CRESCENT, ELOKOSA_PAW_NEW
   );
   public static final DeferredHolder<Item, Item> LOGO = REG.register("logo", () -> new Item(new Properties()));
   public static final DeferredHolder<Item, Item> PETIOLE_MUSIC_DISC = REG.register(
      "music_disc_petiole", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MMSounds.JUKEBOX_PETIOLE))
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> FOLIAATH_SPAWN_EGG = REG.register(
      "foliaath_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.FOLIAATH, 4705339, 12598220, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> WROUGHTNAUT_SPAWN_EGG = REG.register(
      "wroughtnaut_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.WROUGHTNAUT, 9211020, 16777215, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> UMVUTHANA_SPAWN_EGG = REG.register(
      "umvuthana_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.UMVUTHANA_MINION, 12214046, 3813167, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> UMVUTHANA_RAPTOR_SPAWN_EGG = REG.register(
      "umvuthana_raptor_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.UMVUTHANA_RAPTOR, 12214046, 16184049, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> UMVUTHANA_CRANE_SPAWN_EGG = REG.register(
      "umvuthana_crane_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.UMVUTHANA_CRANE, 12214046, 16637046, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> UMVUTHI_SPAWN_EGG = REG.register(
      "umvuthi_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.UMVUTHI, 16184049, 12214046, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> FROSTMAW_SPAWN_EGG = REG.register(
      "frostmaw_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.FROSTMAW, 16251647, 11521535, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> GROTTOL_SPAWN_EGG = REG.register(
      "grottol_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.GROTTOL, 7829367, 12378367, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> LANTERN_SPAWN_EGG = REG.register(
      "lantern_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.LANTERN, 7203328, 2316816, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> NAGA_SPAWN_EGG = REG.register(
      "naga_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.NAGA, 1394768, 9295705, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> SCULPTOR_SPAWN_EGG = REG.register(
      "sculptor_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.SCULPTOR, 12886327, 16774631, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> BLUFF_SPAWN_EGG = REG.register(
      "bluff_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.BLUFF, 6570550, 7123502, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> ELOKOSA_SPAWN_EGG = REG.register(
      "elokosa_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.ELOKOSA_FOLLOWER_TO_HOWLER, 1315350, 12666664, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> ELOKOSA_HOWLER_SPAWN_EGG = REG.register(
      "elokosa_howler_spawn_egg", () -> new DeferredSpawnEggItem(EntityHandler.ELOKOSA_HOWLER, 1315350, 6051049, new Properties())
   );

   public static void modifyComponents(ModifyDefaultComponentsEvent event) {
      if (!ConfigHandler.COMMON_CONFIG.isLoaded()) {
         MMCommon.LOGGER.error("Could not modify default components due to config not being loaded yet");
      } else {
         event.modify(
            (ItemLike)WROUGHT_AXE.get(),
            builder -> {
               if (!(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get()) {
                  builder.remove(DataComponents.MAX_DAMAGE);
                  builder.remove(DataComponents.DAMAGE);
               }

               builder.set(
                  DataComponents.ATTRIBUTE_MODIFIERS,
                  AxeItem.createAttributes(
                     Tiers.IRON,
                     -2.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue(),
                     -4.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackSpeed.get()).floatValue()
                  )
               );
            }
         );
         event.modify(
            (ItemLike)SPEAR.get(),
            builder -> builder.set(
               DataComponents.ATTRIBUTE_MODIFIERS,
               DiggerItem.createAttributes(
                     Tiers.STONE,
                     -2.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SPEAR.toolConfig.attackDamage.get()).floatValue(),
                     -4.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SPEAR.toolConfig.attackSpeed.get()).floatValue()
                  )
                  .withModifierAdded(
                     Attributes.ENTITY_INTERACTION_RANGE,
                     new AttributeModifier(ItemSpear.SPEAR_REACH_ID, 1.5, Operation.ADD_VALUE),
                     EquipmentSlotGroup.MAINHAND
                  )
            )
         );
         event.modify(
            (ItemLike)NAGA_FANG_DAGGER.get(),
            builder -> builder.set(
               DataComponents.ATTRIBUTE_MODIFIERS,
               DiggerItem.createAttributes(
                  Tiers.STONE,
                  -2.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.NAGA_FANG_DAGGER.toolConfig.attackDamage.get()).floatValue(),
                  -4.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.NAGA_FANG_DAGGER.toolConfig.attackSpeed.get()).floatValue()
               )
            )
         );
         event.modify(
            (ItemLike)EARTHREND_GAUNTLET.get(),
            builder -> {
               if (!(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.breakable.get()) {
                  builder.remove(DataComponents.MAX_DAMAGE);
                  builder.remove(DataComponents.DAMAGE);
               }

               builder.set(
                  DataComponents.ATTRIBUTE_MODIFIERS,
                  DiggerItem.createAttributes(
                     Tiers.STONE,
                     -2.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.toolConfig.attackDamage.get()).floatValue(),
                     -4.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHREND_GAUNTLET.toolConfig.attackSpeed.get()).floatValue()
                  )
               );
            }
         );
         event.modify((ItemLike)SOL_VISAGE.get(), builder -> {
            if (!(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get()) {
               builder.remove(DataComponents.MAX_DAMAGE);
               builder.remove(DataComponents.DAMAGE);
            }
         });
         event.modify((ItemLike)WROUGHT_HELMET.get(), builder -> {
            if (!(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.breakable.get()) {
               builder.remove(DataComponents.MAX_DAMAGE);
               builder.remove(DataComponents.DAMAGE);
            }
         });
         event.modify(
            (ItemLike)SCULPTOR_STAFF.get(),
            builder -> {
               builder.set(
                  DataComponents.ATTRIBUTE_MODIFIERS,
                  DiggerItem.createAttributes(
                     Tiers.STONE,
                     -2.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SCULPTOR_STAFF.toolConfig.attackDamage.get()).floatValue(),
                     -4.0F + ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SCULPTOR_STAFF.toolConfig.attackSpeed.get()).floatValue()
                  )
               );
               builder.set(DataComponents.MAX_DAMAGE, 200);
            }
         );
         UnmodifiableIterator var1 = ELOKOSA_PAWS.iterator();

         while (var1.hasNext()) {
            DeferredHolder<Item, ItemElokosaPaw> item = (DeferredHolder<Item, ItemElokosaPaw>)var1.next();
            event.modify(
               (ItemLike)item.get(),
               builder -> builder.set(DataComponents.MAX_DAMAGE, ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ELOKOSA_PAW.numberOfUses.getAsInt())
            );
         }
      }
   }

   public static void initializeDispenserBehaviors() {
      DispenserBlock.registerBehavior((ItemLike)DART.get(), new ProjectileDispenseBehavior((Item)DART.get()));
   }
}
