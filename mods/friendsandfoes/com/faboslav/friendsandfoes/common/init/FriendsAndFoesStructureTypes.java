package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.world.structures.CitadelStructure;
import com.faboslav.friendsandfoes.common.world.structures.IceologerCabinStructure;
import com.faboslav.friendsandfoes.common.world.structures.IllusionerShackStructure;
import com.faboslav.friendsandfoes.common.world.structures.IllusionerTrainingGroundsStructure;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public final class FriendsAndFoesStructureTypes {
   public static final ResourcefulRegistry<StructureType<?>> STRUCTURE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.STRUCTURE_TYPE, "friendsandfoes");
   public static final RegistryEntry<StructureType<CitadelStructure>> CITADEL_STRUCTURE = STRUCTURE_TYPES.register(
      "citadel_structure", () -> () -> CitadelStructure.CODEC
   );
   public static final RegistryEntry<StructureType<IceologerCabinStructure>> ICEOLOGER_CABIN_STRUCTURE = STRUCTURE_TYPES.register(
      "iceologer_cabin_structure", () -> () -> IceologerCabinStructure.CODEC
   );
   public static final RegistryEntry<StructureType<IllusionerShackStructure>> ILLUSIONER_SHACK_STRUCTURE = STRUCTURE_TYPES.register(
      "illusioner_shack_structure", () -> () -> IllusionerShackStructure.CODEC
   );
   public static final RegistryEntry<StructureType<IllusionerTrainingGroundsStructure>> ILLUSIONER_TRAINING_GROUNDS_STRUCTURE = STRUCTURE_TYPES.register(
      "illusioner_training_grounds_structure", () -> () -> IllusionerTrainingGroundsStructure.CODEC
   );

   private FriendsAndFoesStructureTypes() {
   }
}
