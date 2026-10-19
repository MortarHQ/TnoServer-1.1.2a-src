package net.unusual.block_factorys_bosses.data.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BossesRiseLangProvider extends LanguageProvider {
   public static final Map<String, String> AUTO_GENERATOR = new HashMap<>();

   public BossesRiseLangProvider(PackOutput output) {
      super(output, "block_factorys_bosses", "en_us");
   }

   protected final void addTranslations() {
      AUTO_GENERATOR.forEach(this::add);
      this.addCustomTranslations();
   }

   protected abstract void addCustomTranslations();

   public void addBlockAndDesc(DeferredBlock<Block> key, String name, String... desc) {
      this.add((Block)key.get(), name);

      for (int i = 0; i < desc.length; i++) {
         this.add(((Block)key.get()).getDescriptionId() + ".description_" + i, desc[i]);
      }
   }

   public void addItemAndDesc(DeferredItem<Item> key, String name, String... desc) {
      this.add((Item)key.get(), name);

      for (int i = 0; i < desc.length; i++) {
         this.add(((Item)key.get()).getDescriptionId() + ".description_" + i, desc[i]);
      }
   }

   public void addCreativeTab(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, String name) {
      this.add("item_group.block_factorys_bosses." + Objects.requireNonNull(tab.getId()).getPath(), name);
   }

   public void addAttribute(DeferredHolder<Attribute, Attribute> attribute, String name) {
      this.add("attribute.block_factorys_bosses." + Objects.requireNonNull(attribute.getId()).getPath(), name);
   }

   public void addKeyMapping(KeyMapping key, String name) {
      this.add(key.getName(), name);
   }

   public void addAdvancement(String key, String title, String desc) {
      this.add("advancement.block_factorys_bosses." + key, title);
      this.add("advancement.block_factorys_bosses." + key + ".desc", desc);
   }

   public void addEntityAndEgg(DeferredHolder<EntityType<?>, ? extends EntityType<?>> entity, String name) {
      this.addEntityType(entity, name);
      this.add("item.block_factorys_bosses." + entity.getId().getPath() + "_spawn_egg", name + " Spawn Egg");
   }

   public void addSubtitle(Holder<SoundEvent> sound, String name) {
      String[] splitSoundName = Objects.requireNonNull(sound.getKey()).location().getPath().split("\\.", 3);
      this.add("subtitles.block_factorys_bosses." + splitSoundName[0] + "." + splitSoundName[2], name);
   }

   public void addDamageType(ResourceKey<DamageType> damageType, String message, @Nullable String usingItemMessage, @Nullable String byPlayerMessage) {
      String baseKey = "death.attack.block_factorys_bosses." + damageType.location().getPath();
      this.add(baseKey, message);
      if (usingItemMessage != null) {
         this.add(baseKey + ".item", usingItemMessage);
      }

      if (byPlayerMessage != null) {
         this.add(baseKey + ".player", byPlayerMessage);
      }
   }
}
