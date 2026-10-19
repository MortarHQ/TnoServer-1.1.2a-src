package net.unusual.block_factorys_bosses.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseDamageTypes;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseRegistryGen extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, BossesRiseRegistryGen::bootstrapDamageTypes);

   public BossesRiseRegistryGen(PackOutput output, CompletableFuture<Provider> provider) {
      super(output, provider, BUILDER, Set.of("minecraft", "block_factorys_bosses"));
   }

   public static void bootstrapDamageTypes(BootstrapContext<DamageType> context) {
      context.register(BossesRiseDamageTypes.CANNONBALL_HIT, new DamageType(deathMessageId(BossesRiseDamageTypes.CANNONBALL_HIT), 0.0F));
      context.register(BossesRiseDamageTypes.KRAKEN_TENTACLE_SMASH, new DamageType(deathMessageId(BossesRiseDamageTypes.KRAKEN_TENTACLE_SMASH), 0.1F));
   }

   private static String deathMessageId(ResourceKey<DamageType> forResourceKey) {
      ResourceLocation location = forResourceKey.location();
      return location.getNamespace() + "." + location.getPath();
   }
}
