package twilightforest.entity.monster;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFItems;
import twilightforest.util.entities.EntityUtil;

public class GiantMiner extends Monster {
   public GiantMiner(EntityType<? extends GiantMiner> type, Level world) {
      super(type, world);

      for (EquipmentSlot slot : EquipmentSlot.values()) {
         this.setDropChance(slot, 0.0F);
      }
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 80.0)
         .add(Attributes.MOVEMENT_SPEED, 0.23)
         .add(Attributes.ATTACK_DAMAGE, 2.0)
         .add(Attributes.FOLLOW_RANGE, 40.0)
         .add(Attributes.STEP_HEIGHT, 1.2);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn) {
      SpawnGroupData data = super.finalizeSpawn(accessor, difficulty, reason, spawnDataIn);
      this.populateDefaultEquipmentSlots(accessor.getRandom(), difficulty);
      this.populateDefaultEquipmentEnchantments(accessor, accessor.getRandom(), difficulty);
      return data;
   }

   protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)TFItems.GIANT_PICKAXE.get()));
   }

   protected void enchantSpawnedWeapon(ServerLevelAccessor accessor, RandomSource random, DifficultyInstance instance) {
   }

   protected void enchantSpawnedArmor(ServerLevelAccessor accessor, RandomSource random, EquipmentSlot slot, DifficultyInstance instance) {
   }

   public boolean doHurtTarget(Entity entity) {
      return EntityUtil.properlyApplyCustomDamageSource(this, entity, TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.ANT, this), null);
   }

   public int getMaxSpawnClusterSize() {
      return 1;
   }

   public boolean checkSpawnRules(LevelAccessor accessor, MobSpawnType reason) {
      List<GiantMiner> giantsNearby = accessor.getEntitiesOfClass(GiantMiner.class, this.getBoundingBox().inflate(100.0, 10.0, 100.0));
      return giantsNearby.size() < 5;
   }

   public static boolean canSpawn(EntityType<? extends GiantMiner> type, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource rand) {
      return accessor.getBlockState(pos.below()).is(BlockTagGenerator.GIANTS_SPAWNABLE_ON);
   }

   protected boolean canRide(Entity entity) {
      return false;
   }
}
