package twilightforest.loot.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import net.neoforged.fml.ModList;
import twilightforest.init.TFLoot;

public class ModExistsCondition implements LootItemCondition {
   public static final MapCodec<ModExistsCondition> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.STRING.fieldOf("mod_id").forGetter(o -> o.modID)).apply(instance, ModExistsCondition::new)
   );
   private final boolean exists;
   private final String modID;

   public ModExistsCondition(String modID) {
      this.exists = ModList.get().isLoaded(modID);
      this.modID = modID;
   }

   public LootItemConditionType getType() {
      return (LootItemConditionType)TFLoot.MOD_EXISTS.get();
   }

   public boolean test(LootContext context) {
      return this.exists;
   }

   public static Builder builder(String modid) {
      return () -> new ModExistsCondition(modid);
   }
}
