package leaf.cosmere.surgebinding.common.blocks;

import leaf.cosmere.surgebinding.common.registries.SurgebindingBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VinebudBlock extends BaseEntityBlock {
    public static final IntegerProperty STATE;
    public static final VoxelShape SHAPE;

    public VinebudBlock() {
        super(BlockBehaviour.Properties.of(Material.GRASS).color(MaterialColor.COLOR_GREEN).noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(STATE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STATE);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

   /*
    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        if (pLevel.isClientSide) return;
        var isRaining = pLevel.isRainingAt(pPos.above());
        System.out.println(String.format("VinebudBlock#tick pState:%s pPos:%s isRaining:%b isThundering:%b",
                pState, pPos, isRaining, pLevel.isThundering()));
        BlockState newState;

//        if (pLevel.dimension().equals(SurgebindingDimensions.ROSHAR_DIM_KEY)) {
            if (isRaining && pLevel.isThundering()) {
                System.out.println("VinebudBlock#tick isThundering");
                newState = pState.setValue(STATE, 4);
            } else {
                System.out.println("VinebudBlock#tick no rain");
                newState = pState.setValue(STATE, 1);
            }
//        }
        pLevel.setBlockAndUpdate(pPos, newState);
    }
*/

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, SurgebindingBlockEntities.VINEBUD_BLOCK_ENTITY.get(), VinebudBlockEntity::tick);
    }

    @Override
    public InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.isClientSide) return InteractionResult.SUCCESS;

        pLevel.setBlockAndUpdate(pPos, pState.cycle(STATE));

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new VinebudBlockEntity(pPos, pState);
    }

    static {
        STATE = IntegerProperty.create("state", 1, 4);
        SHAPE = Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125);
    }
}
