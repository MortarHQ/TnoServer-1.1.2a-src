package net.p3pp3rf1y.sophisticatedcore.util;

import com.mojang.authlib.GameProfile;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;

public class CoreFakePlayer extends FakePlayer {
   private final NonNullList<ItemStack> fakePlayerHandInventory = NonNullList.withSize(2, ItemStack.EMPTY);
   private static final String FAKE_PLAYER_USERNAME = "sophisticated_core_fake_player";
   private static Map<ServerLevel, CoreFakePlayer> fakePlayers = new HashMap<>();
   private Vec3 position = Vec3.ZERO;
   private BlockPos blockPosition = BlockPos.ZERO;

   public static CoreFakePlayer get(ServerLevel level) {
      return fakePlayers.computeIfAbsent(level, CoreFakePlayer::new);
   }

   public static void onDimensionUnload(Unload event) {
      if (event.getLevel() instanceof ServerLevel level) {
         fakePlayers.remove(level);
      }
   }

   private CoreFakePlayer(ServerLevel level) {
      this(level, new GameProfile(UUID.nameUUIDFromBytes("sophisticated_core_fake_player".getBytes()), "sophisticated_core_fake_player"));
   }

   private CoreFakePlayer(ServerLevel level, GameProfile name) {
      super(level, name);
   }

   protected void onEffectAdded(MobEffectInstance effect, @Nullable Entity entity) {
   }

   protected void onEffectUpdated(MobEffectInstance effect, boolean updateAttributes, @Nullable Entity entity) {
   }

   protected void onEffectRemoved(MobEffectInstance effect) {
   }

   public Vec3 position() {
      return this.position;
   }

   public BlockPos blockPosition() {
      return this.blockPosition;
   }

   public void setPosition(Vec3 position) {
      this.position = position;
      this.blockPosition = BlockPos.containing(position);
   }
}
