package org.crypticdev.ancient_artifacts.registry;

import dev.architectury.registry.registries.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import org.crypticdev.ancient_artifacts.component.AttackerArmorComponent;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.component.UnlockComponent;

public final class ArtifactDataComponents {
   public static final DeferredRegister<DataComponentType<?>> REGISTRAR = DeferredRegister.create("ancient_artifacts", Registries.DATA_COMPONENT_TYPE);
   public static final Supplier<DataComponentType<RefinementComponent>> REFINEMENT = REGISTRAR.register(
      "refinement", () -> DataComponentType.builder().persistent(RefinementComponent.CODEC).networkSynchronized(RefinementComponent.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<UnlockComponent>> UNLOCKS = REGISTRAR.register(
      "unlocks", () -> DataComponentType.builder().persistent(UnlockComponent.CODEC).networkSynchronized(UnlockComponent.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<AttackerArmorComponent>> ATTACKER_ARMOR = REGISTRAR.register(
      "attacker_armor",
      () -> DataComponentType.builder().persistent(AttackerArmorComponent.CODEC).networkSynchronized(AttackerArmorComponent.STREAM_CODEC).build()
   );

   private ArtifactDataComponents() {
   }

   public static void init() {
      REGISTRAR.register();
   }
}
