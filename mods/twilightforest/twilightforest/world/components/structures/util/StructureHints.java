package twilightforest.world.components.structures.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.Filterable;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFDataComponents;

public interface StructureHints {
   String CODEC_NAME = "hint_creature";
   String BOOK_AUTHOR = "twilightforest.book.author";

   default ItemStack createHintBook(RegistryAccess registryAccess) {
      ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
      this.addBookInformation(book);
      return book;
   }

   default void addBookInformation(ItemStack book) {
      addBookInformationStatic(book, "unknown", 2);
   }

   static void addBookInformationStatic(ItemStack book, @Nullable String name, int pageCount) {
      String key = name == null ? "unknown" : name;
      Function<Integer, Filterable<Component>> pageGenerationFunc = index -> Filterable.passThrough(
         Component.translatable("twilightforest.book." + key + "." + (index + 1))
      );
      List<Filterable<Component>> list = Stream.<Integer>iterate(0, index -> index + 1).limit(pageCount).map(pageGenerationFunc).toList();
      book.set(
         DataComponents.WRITTEN_BOOK_CONTENT,
         new WrittenBookContent(Filterable.passThrough("twilightforest.book." + key), "twilightforest.book.author", 3, list, true)
      );
      book.set(TFDataComponents.TRANSLATABLE_BOOK, Unit.INSTANCE);
   }

   default void trySpawnHintMonster(Level world, Player player) {
      this.trySpawnHintMonster(world, player, player.blockPosition());
   }

   void trySpawnHintMonster(Level var1, Player var2, BlockPos var3);

   static void tryHintForStructure(Player player, ServerLevel level, ResourceKey<Structure> forStructure) {
      Optional<Registry<Structure>> optStructureReg = level.registryAccess().registry(Registries.STRUCTURE);
      if (!optStructureReg.isEmpty() && optStructureReg.get().get(forStructure) instanceof StructureHints structureHints) {
         structureHints.trySpawnHintMonster(level, player);
      }
   }

   default boolean didSpawnHintMonster(Level world, Player player, BlockPos pos) {
      int dx = world.random.nextInt(16) - world.random.nextInt(16);
      int dy = world.random.nextInt(4) - world.random.nextInt(4);
      int dz = world.random.nextInt(16) - world.random.nextInt(16);
      Mob hinty = this.createHintMonster(world);
      if (hinty != null) {
         hinty.moveTo(pos.offset(dx, dy, dz), 0.0F, 0.0F);
         if (hinty.checkSpawnObstruction(world) && hinty.getSensing().hasLineOfSight(player)) {
            ItemStack book = this.createHintBook(world.registryAccess());
            if (!book.isEmpty()) {
               hinty.setItemSlot(EquipmentSlot.MAINHAND, book);
               hinty.setDropChance(EquipmentSlot.MAINHAND, 1.0F);
            }

            world.addFreshEntity(hinty);
            return true;
         }
      }

      return false;
   }

   @Nullable
   Mob createHintMonster(Level var1);

   record HintConfig(ItemStack hintItem, EntityType<? extends Mob> hintMob) {
      public static final Codec<StructureHints.HintConfig> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ItemStack.CODEC.fieldOf("hint_item").forGetter(StructureHints.HintConfig::hintItem),
               BuiltInRegistries.ENTITY_TYPE
                  .byNameCodec()
                  .comapFlatMap(StructureHints.HintConfig::checkCastMob, entityType -> entityType)
                  .fieldOf("hint_mob")
                  .forGetter(StructureHints.HintConfig::hintMob)
            )
            .apply(instance, StructureHints.HintConfig::new)
      );

      private static DataResult<EntityType<? extends Mob>> checkCastMob(EntityType<?> entityType) {
         return !entityType.getBaseClass().isAssignableFrom(Mob.class)
            ? DataResult.error(() -> "Configured Hint Entity " + entityType.toShortString() + " does not have a `Mob` superclass!")
            : DataResult.success(entityType);
      }

      public static ItemStack defaultBook() {
         return book("unknown", 2);
      }

      public static ItemStack book(String name, int pageCount) {
         ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
         StructureHints.addBookInformationStatic(book, name, pageCount);
         return book;
      }
   }
}
