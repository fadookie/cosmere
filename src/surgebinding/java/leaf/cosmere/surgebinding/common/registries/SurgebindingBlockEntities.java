/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.registries;

import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.blocks.VinebudBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SurgebindingBlockEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Surgebinding.MODID);

	public static final RegistryObject<BlockEntityType<VinebudBlockEntity>> VINEBUD_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("vinebud_entity", () -> BlockEntityType.Builder.of(VinebudBlockEntity::new, SurgebindingBlocks.VINEBUD_BLOCK.getBlock()).build(null));

}
