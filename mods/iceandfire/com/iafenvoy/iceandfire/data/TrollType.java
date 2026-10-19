package com.iafenvoy.iceandfire.data;

import com.google.common.collect.ImmutableList;
import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.item.armor.TrollArmorItem;
import com.iafenvoy.iceandfire.item.tool.TrollWeaponItem;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafRegistries;
import com.iafenvoy.uranus.util.RandomHelper;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus.Internal;

public class TrollType {
   public RegistrySupplier<Item> leather;
   public RegistrySupplier<Item> helmet;
   public RegistrySupplier<Item> chestplate;
   public RegistrySupplier<Item> leggings;
   public RegistrySupplier<Item> boots;
   private final String name;
   private final Holder<ArmorMaterial> material;
   private final TagKey<Biome> spawnBiomes;
   private final ResourceLocation lootTable;
   private final List<TrollType.ITrollWeapon> weapons;

   public TrollType(String name, Holder<ArmorMaterial> material, TagKey<Biome> spawnBiomes, TrollType.ITrollWeapon... weapons) {
      this.name = name;
      this.weapons = List.of(weapons);
      this.material = material;
      this.spawnBiomes = spawnBiomes;
      this.lootTable = ResourceLocation.fromNamespaceAndPath("iceandfire", "entities/troll_" + name);
   }

   public static TrollType getBiomeType(Holder<Biome> biome) {
      List<TrollType> types = IafRegistries.TROLL_TYPE.stream().filter(x -> x.allowSpawn(biome)).toList();
      return (TrollType)RandomHelper.randomOne(types.isEmpty() ? IafRegistries.TROLL_TYPE.stream().toList() : types);
   }

   public static TrollType.ITrollWeapon getWeaponForType(TrollType troll) {
      return troll.weapons.get(ThreadLocalRandom.current().nextInt(troll.weapons.size()));
   }

   public static void initArmors() {
      for (TrollType troll : IafRegistries.TROLL_TYPE) {
         troll.leather = IafItems.registerItem(
            String.format(Locale.ROOT, "troll_leather_%s", troll.name.toLowerCase(Locale.ROOT)), () -> new Item(new Properties())
         );
         troll.helmet = IafItems.registerArmor(TrollArmorItem.getName(troll, Type.HELMET), () -> new TrollArmorItem(troll, Type.HELMET));
         troll.chestplate = IafItems.registerArmor(TrollArmorItem.getName(troll, Type.CHESTPLATE), () -> new TrollArmorItem(troll, Type.CHESTPLATE));
         troll.leggings = IafItems.registerArmor(TrollArmorItem.getName(troll, Type.LEGGINGS), () -> new TrollArmorItem(troll, Type.LEGGINGS));
         troll.boots = IafItems.registerArmor(TrollArmorItem.getName(troll, Type.BOOTS), () -> new TrollArmorItem(troll, Type.BOOTS));
      }
   }

   public static List<TrollType> values() {
      return IafRegistries.TROLL_TYPE.stream().toList();
   }

   public static TrollType getByName(String name) {
      return (TrollType)IafRegistries.TROLL_TYPE.get(IceAndFire.id(name));
   }

   public String getName() {
      return this.name;
   }

   public ResourceLocation getLootTable() {
      return this.lootTable;
   }

   public Holder<ArmorMaterial> getMaterial() {
      return this.material;
   }

   public ResourceLocation getTexture() {
      return ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/troll/troll_" + this.name + ".png");
   }

   public ResourceLocation getStatueTexture() {
      return ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/troll/troll_" + this.name + "_stone.png");
   }

   public ResourceLocation getEyesTexture() {
      return ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/troll/troll_" + this.name + "_eyes.png");
   }

   public boolean allowSpawn(Holder<Biome> biome) {
      return biome.is(this.spawnBiomes);
   }

   public enum BuiltinWeapon implements TrollType.ITrollWeapon {
      AXE,
      COLUMN,
      COLUMN_FOREST,
      COLUMN_FROST,
      HAMMER,
      TRUNK,
      TRUNK_FROST;

      private final RegistrySupplier<Item> item = IafItems.registerToolOrWeapon(
         "troll_weapon_" + this.name().toLowerCase(Locale.ROOT), () -> new TrollWeaponItem(this)
      );

      BuiltinWeapon() {
         TrollType.ITrollWeapon.addWeapons(this);
      }

      @Override
      public String getName() {
         return this.name().toLowerCase(Locale.ROOT);
      }

      @Override
      public ResourceLocation getTexture() {
         return ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/troll/weapon/weapon_" + this.name().toLowerCase(Locale.ROOT) + ".png");
      }

      @Override
      public Item getItem() {
         return (Item)this.item.get();
      }
   }

   public interface ITrollWeapon {
      @Internal
      List<TrollType.ITrollWeapon> WEAPONS = new ArrayList<>();
      @Internal
      Map<String, TrollType.ITrollWeapon> BY_NAME = new HashMap<>();

      static void addWeapons(TrollType.ITrollWeapon... weapons) {
         for (TrollType.ITrollWeapon weapon : weapons) {
            WEAPONS.add(weapon);
            BY_NAME.put(weapon.getName(), weapon);
         }
      }

      static List<TrollType.ITrollWeapon> values() {
         return ImmutableList.copyOf(WEAPONS);
      }

      static TrollType.ITrollWeapon getByName(String name) {
         return BY_NAME.getOrDefault(name, TrollType.BuiltinWeapon.AXE);
      }

      String getName();

      ResourceLocation getTexture();

      Item getItem();
   }
}
