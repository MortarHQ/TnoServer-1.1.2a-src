package io.github.manasmods.tensura_neb.neoforge.data;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;
import io.github.manasmods.tensura_neb.registry.NebItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class NebLanguageProvider extends LanguageProvider {
   public NebLanguageProvider(PackOutput output) {
      super(output, "tensura_neb", "en_us");
   }

   protected void addTranslations() {
      this.addItem(NebItems.NIGHT_ROSE, "Night Rose");
      this.addItem(NebItems.WHITE_TIGER_AZURE_DRAGON_HALBERD, "White Tiger Azure Dragon Halberd");
      this.addEntityAndSpawnEgg(NebEntityTypes.RIMURU_OGRE_FIGHT, "Rimuru Tempest");
      this.addEntityAndSpawnEgg(NebEntityTypes.RIMURU_CLONE, "Rimuru Clone");
      this.addEntityAndSpawnEgg(NebEntityTypes.CARRION, "Carrion");
      this.addEntityAndSpawnEgg(NebEntityTypes.LUMINOUS_VALENTINE, "Luminous Valentine");
      this.addEntityAndSpawnEgg(NebEntityTypes.VELDORA, "Veldora Tempest");
      this.addAdvancement(NebAdvancementGenerator.MONSTER_RULER, "The Monster Ruler", "Defeat Rimuru.");
      this.addAdvancement(NebAdvancementGenerator.LION_KING, "The Lion King", "Defeat Carrion.");
      this.addAdvancement(NebAdvancementGenerator.QUEEN_OF_NIGHTMARES, "The Queen of Nightmares", "Defeat Luminous Valentine.");
   }

   protected void addEntityAndSpawnEgg(RegistrySupplier<? extends EntityType<?>> entity, String name) {
      String path = entity.getId().getPath().replace('/', '.');
      this.add(String.format("entity.%s.%s", entity.getId().getNamespace(), path), name);
      this.add(String.format("item.%s.%s_spawn_egg", entity.getId().getNamespace(), path), name + " Spawn Egg");
   }

   protected void addAdvancement(ResourceLocation location, String name, String description) {
      this.add("tensura.advancements." + location.getPath() + ".title", name);
      this.add("tensura.advancements." + location.getPath() + ".description", description);
   }
}
