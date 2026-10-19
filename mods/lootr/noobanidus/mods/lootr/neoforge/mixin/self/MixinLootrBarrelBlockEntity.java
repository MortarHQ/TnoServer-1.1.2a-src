package noobanidus.mods.lootr.neoforge.mixin.self;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.extensions.IBlockEntityExtension;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.block.entity.LootrBarrelBlockEntity;
import noobanidus.mods.lootr.common.client.ClientHooks;
import noobanidus.mods.lootr.neoforge.block.ModelDataConstants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LootrBarrelBlockEntity.class)
public abstract class MixinLootrBarrelBlockEntity implements IBlockEntityExtension {
   public ModelData getModelData() {
      ILootrInfoProvider provider = (ILootrInfoProvider)this;
      Player player = ClientHooks.getPlayer();
      return player != null && provider.hasClientOpened(player.getUUID()) ? ModelDataConstants.OPENED_MODEL_DATA : ModelDataConstants.CLOSED_MODEL_DATA;
   }
}
