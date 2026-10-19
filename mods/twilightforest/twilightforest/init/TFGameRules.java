package twilightforest.init;

import com.google.common.base.Suppliers;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
import net.minecraft.world.level.GameRules.Value;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.network.EnforceProgressionStatusPacket;

public class TFGameRules {
   private static final Set<Supplier<Key<?>>> GAME_RULES = new HashSet<>();
   public static final Supplier<Key<BooleanValue>> ENFORCED_PROGRESSION_RULE = register(
      "tfEnforcedProgression",
      Category.UPDATES,
      BooleanValue.create(
         true, (server, value) -> PacketDistributor.sendToAllPlayers(new EnforceProgressionStatusPacket(value.get()), new CustomPacketPayload[0])
      )
   );
   public static final Supplier<Key<IntegerValue>> RULE_PLAYERS_TF_PORTAL_DEFAULT_DELAY = register(
      "playersTfPortalDefaultDelay", Category.PLAYER, IntegerValue.create(60)
   );
   public static final Supplier<Key<IntegerValue>> RULE_PLAYERS_TF_PORTAL_CREATIVE_DELAY = register(
      "playersTfPortalCreativeDelay", Category.PLAYER, IntegerValue.create(1)
   );

   private static <T extends Value<T>> Supplier<Key<T>> register(String name, Category category, Type<T> type) {
      Supplier<Key<T>> supplier = Suppliers.memoize(() -> GameRules.register(name, category, type));
      GAME_RULES.add(supplier);
      return supplier;
   }

   public static void register() {
      GAME_RULES.forEach(gameRule -> Util.backgroundExecutor().execute(gameRule::get));
   }
}
