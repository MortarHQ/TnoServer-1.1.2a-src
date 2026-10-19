package dev.latvian.mods.kubejs.core;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.mods.kubejs.entity.KubeRayTraceResult;
import dev.latvian.mods.kubejs.level.LevelBlock;
import dev.latvian.mods.kubejs.player.EntityArrayList;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.ScriptTypeHolder;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface EntityKJS extends WithPersistentData, MessageSenderKJS, ScriptTypeHolder {
   default Entity kjs$self() {
      return (Entity)this;
   }

   default Level kjs$getLevel() {
      return this.kjs$self().level();
   }

   @Nullable
   default MinecraftServer kjs$getServer() {
      return this.kjs$getLevel().getServer();
   }

   default String kjs$getType() {
      return this.kjs$self().getType().kjs$getId();
   }

   default GameProfile kjs$getProfile() {
      return new GameProfile(this.kjs$self().getUUID(), this.kjs$self().getScoreboardName());
   }

   @Override
   default Component kjs$getName() {
      return this.kjs$self().getName();
   }

   @Override
   default Component kjs$getDisplayName() {
      return this.kjs$self().getDisplayName();
   }

   @Override
   default void kjs$tell(Component message) {
      this.kjs$self().sendSystemMessage(message);
   }

   @Override
   default void kjs$runCommand(String command) {
      if (this.kjs$getLevel() instanceof ServerLevel level) {
         level.getServer().getCommands().performPrefixedCommand(this.kjs$self().createCommandSourceStack(), command);
      }
   }

   @Override
   default void kjs$runCommandSilent(String command) {
      if (this.kjs$getLevel() instanceof ServerLevel level) {
         level.getServer().getCommands().performPrefixedCommand(this.kjs$self().createCommandSourceStack().withSuppressedOutput(), command);
      }
   }

   default boolean kjs$isPlayer() {
      return false;
   }

   @Nullable
   default ItemStack kjs$getItem() {
      return null;
   }

   default boolean kjs$isFrame() {
      return this instanceof ItemFrame;
   }

   default boolean kjs$isLiving() {
      return false;
   }

   default boolean kjs$isMonster() {
      return !this.kjs$self().getType().getCategory().isFriendly();
   }

   default boolean kjs$isAnimal() {
      return this.kjs$self().getType().getCategory().isPersistent();
   }

   default boolean kjs$isAmbientCreature() {
      return this.kjs$self().getType().getCategory() == MobCategory.AMBIENT;
   }

   default boolean kjs$isWaterCreature() {
      return this.kjs$self().getType().getCategory() == MobCategory.WATER_CREATURE;
   }

   default boolean kjs$isPeacefulCreature() {
      return this.kjs$self().getType().getCategory().isFriendly();
   }

   default void kjs$setX(double x) {
      this.kjs$setPosition(x, this.kjs$self().getY(), this.kjs$self().getZ());
   }

   default void kjs$setY(double y) {
      this.kjs$setPosition(this.kjs$self().getX(), y, this.kjs$self().getZ());
   }

   default void kjs$setZ(double z) {
      this.kjs$setPosition(this.kjs$self().getX(), this.kjs$self().getY(), z);
   }

   default double kjs$getMotionX() {
      return this.kjs$self().getDeltaMovement().x;
   }

   default void kjs$setMotionX(double x) {
      Vec3 m = this.kjs$self().getDeltaMovement();
      this.kjs$self().setDeltaMovement(x, m.y, m.z);
   }

   default double kjs$getMotionY() {
      return this.kjs$self().getDeltaMovement().y;
   }

   default void kjs$setMotionY(double y) {
      Vec3 m = this.kjs$self().getDeltaMovement();
      this.kjs$self().setDeltaMovement(m.x, y, m.z);
   }

   default double kjs$getMotionZ() {
      return this.kjs$self().getDeltaMovement().z;
   }

   default void kjs$setMotionZ(double z) {
      Vec3 m = this.kjs$self().getDeltaMovement();
      this.kjs$self().setDeltaMovement(m.x, m.y, z);
   }

   default void kjs$teleportTo(ResourceLocation dimension, double x, double y, double z, float yaw, float pitch) {
      Level previousLevel = this.kjs$getLevel();
      ServerLevel level = this.kjs$getServer().getLevel(ResourceKey.create(Registries.DIMENSION, dimension));
      if (level == null) {
         throw new IllegalArgumentException("Invalid dimension!");
      }

      if (!Level.isInSpawnableBounds(BlockPos.containing(x, y, z))) {
         throw new IllegalArgumentException("Invalid coordinates!");
      }

      if (Float.isNaN(yaw) || Float.isNaN(pitch)) {
         throw new IllegalArgumentException("Invalid rotation!");
      }

      if (level == previousLevel) {
         this.kjs$setPositionAndRotation(x, y, z, yaw, pitch);
      } else {
         try {
            TeleportCommand.performTeleport(this.kjs$self().createCommandSourceStack(), this.kjs$self(), level, x, y, z, Set.of(), yaw, pitch, null);
         } catch (CommandSyntaxException e) {
            throw new IllegalArgumentException(e.getRawMessage().getString());
         }
      }
   }

   default void kjs$setPosition(LevelBlock block) {
      this.kjs$teleportTo(block.getDimension(), block.getX(), block.getY(), block.getZ(), this.kjs$self().getYRot(), this.kjs$self().getXRot());
   }

   default void kjs$setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
      this.kjs$self().moveTo(x, y, z, yaw, pitch);
   }

   default void kjs$setPosition(double x, double y, double z) {
      this.kjs$setPositionAndRotation(x, y, z, this.kjs$self().getYRot(), this.kjs$self().getXRot());
   }

   default void kjs$setRotation(float yaw, float pitch) {
      this.kjs$setPositionAndRotation(this.kjs$self().getX(), this.kjs$self().getY(), this.kjs$self().getZ(), yaw, pitch);
   }

   default EntityArrayList kjs$getPassengers() {
      return new EntityArrayList(this.kjs$self().getPassengers());
   }

   default String kjs$getTeamId() {
      PlayerTeam team = this.kjs$self().getTeam();
      return team == null ? "" : team.getName();
   }

   default boolean kjs$isOnScoreboardTeam(String teamId) {
      Team team = this.kjs$self().getCommandSenderWorld().getScoreboard().getPlayerTeam(teamId);
      return team != null && this.kjs$self().isAlliedTo(team);
   }

   default Direction kjs$getFacing() {
      if (this.kjs$self().getXRot() > 45.0F) {
         return Direction.DOWN;
      } else {
         return this.kjs$self().getXRot() < -45.0F ? Direction.UP : this.kjs$self().getDirection();
      }
   }

   default LevelBlock kjs$getBlock() {
      return this.kjs$getLevel().kjs$getBlock(this.kjs$self().blockPosition());
   }

   default CompoundTag kjs$getNbt() {
      CompoundTag nbt = new CompoundTag();
      this.kjs$self().saveWithoutId(nbt);
      return nbt;
   }

   default void kjs$setNbt(@Nullable CompoundTag nbt) {
      if (nbt != null) {
         this.kjs$self().load(nbt);
      }
   }

   default Entity kjs$mergeNbt(@Nullable CompoundTag tag) {
      if (tag != null && !tag.isEmpty()) {
         CompoundTag nbt = this.kjs$getNbt();

         for (String k : tag.getAllKeys()) {
            Tag t = tag.get(k);
            if (t != null && t != EndTag.INSTANCE) {
               nbt.put(k, tag.get(k));
            } else {
               nbt.remove(k);
            }
         }

         this.kjs$setNbt(nbt);
         return this.kjs$self();
      } else {
         return this.kjs$self();
      }
   }

   default void kjs$playSound(SoundEvent id, float volume, float pitch) {
      this.kjs$getLevel()
         .playSound(null, this.kjs$self().getX(), this.kjs$self().getY(), this.kjs$self().getZ(), id, this.kjs$self().getSoundSource(), volume, pitch);
   }

   default void kjs$playSound(SoundEvent id) {
      this.kjs$playSound(id, 1.0F, 1.0F);
   }

   default void kjs$spawn() {
      this.kjs$getLevel().addFreshEntity(this.kjs$self());
   }

   default void kjs$attack(float hp) {
      this.kjs$self().hurt(this.kjs$self().damageSources().generic(), hp);
   }

   default double kjs$getDistance(double x, double y, double z) {
      return Math.sqrt(this.kjs$self().distanceToSqr(x, y, z));
   }

   default double kjs$getDistanceSq(BlockPos pos) {
      return this.kjs$self().distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
   }

   default double kjs$getDistance(BlockPos pos) {
      return Math.sqrt(this.kjs$getDistanceSq(pos));
   }

   default KubeRayTraceResult kjs$rayTrace(double distance, boolean fluids) {
      Entity entity = this.kjs$self();
      HitResult hitResult = entity.pick(distance, 0.0F, fluids);
      Vec3 eyePosition = entity.getEyePosition();
      Vec3 lookVector = entity.getViewVector(1.0F);
      Vec3 traceEnd = eyePosition.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
      AABB bound = entity.getBoundingBox().expandTowards(lookVector.scale(distance)).inflate(1.0, 1.0, 1.0);
      double distanceSquared = hitResult.getType() != Type.MISS ? hitResult.getLocation().distanceToSqr(eyePosition) : distance * distance;
      EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
         entity, eyePosition, traceEnd, bound, ent -> !ent.isSpectator() && ent.isPickable(), distanceSquared
      );
      if (entityHitResult != null) {
         double entityDistanceSquared = eyePosition.distanceToSqr(entityHitResult.getLocation());
         if (entityDistanceSquared < distanceSquared || hitResult.getType() == Type.MISS) {
            hitResult = entityHitResult;
         }
      }

      return new KubeRayTraceResult(entity, hitResult, distance);
   }

   default KubeRayTraceResult kjs$rayTrace(double distance) {
      return this.kjs$rayTrace(distance, true);
   }

   @Nullable
   default Entity kjs$rayTraceEntity(double distance, Predicate<Entity> filter) {
      double d0 = Double.MAX_VALUE;
      Entity entity = null;
      Vec3 start = this.kjs$self().getEyePosition();
      Vec3 end = start.add(this.kjs$self().getLookAngle().scale(distance));

      for (Entity entity1 : this.kjs$self().level().getEntities(this.kjs$self(), new AABB(start, end), filter == null ? UtilsJS.ALWAYS_TRUE : filter)) {
         AABB aabb = entity1.getBoundingBox();
         Optional<Vec3> optional = aabb.clip(start, end);
         double d1;
         if (!optional.isEmpty() && (d1 = start.distanceToSqr(optional.get())) < d0) {
            entity = entity1;
            d0 = d1;
         }
      }

      return entity;
   }

   @HideFromJS
   @Nullable
   default CompoundTag kjs$getRawPersistentData() {
      throw new NoMixinException();
   }

   @HideFromJS
   default void kjs$setRawPersistentData(@Nullable CompoundTag tag) {
      throw new NoMixinException();
   }

   @Override
   default ScriptType kjs$getScriptType() {
      return this.kjs$getLevel().kjs$getScriptType();
   }
}
