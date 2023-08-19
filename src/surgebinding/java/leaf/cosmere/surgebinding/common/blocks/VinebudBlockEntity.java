package leaf.cosmere.surgebinding.common.blocks;

import leaf.cosmere.surgebinding.common.registries.SurgebindingBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VinebudBlockEntity extends BlockEntity {
    protected int ticksUntilNextAnimationTriggerCheck = 0;
    protected boolean animationIsOpening = false;
    protected Long animationStartTimeTk = null;
    static final float ANIMATION_DURATION_TK = 2 * 20;
    public VinebudBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SurgebindingBlockEntities.VINEBUD_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
        if (pLevel.isClientSide) return;

        VinebudBlockEntity pVinebudBlockEntity = (VinebudBlockEntity)pBlockEntity;
        --pVinebudBlockEntity.ticksUntilNextAnimationTriggerCheck;

        var oldState = pState.getValue(VinebudBlock.STATE);
        int newState = oldState;

        // Handle animation if it's running
        if (pVinebudBlockEntity.animationStartTimeTk != null) {
            var ticksElapsedSinceAnimationStart = pLevel.getGameTime() - pVinebudBlockEntity.animationStartTimeTk;
            var percentageAnimationComplete = ticksElapsedSinceAnimationStart / ANIMATION_DURATION_TK;
            if (pVinebudBlockEntity.animationIsOpening) {
                if (percentageAnimationComplete < 0.25) {
                    newState = 4;
                } else if (percentageAnimationComplete >= 0.25 && percentageAnimationComplete < 0.5) {
                    newState = 3;
                } else if (percentageAnimationComplete >= 0.5 && percentageAnimationComplete < 0.75) {
                    newState = 2;
                } else {
                    newState = 1;
                    pVinebudBlockEntity.animationStartTimeTk = null;
                }
            } else {
                if (percentageAnimationComplete < 0.25) {
                    newState = 1;
                } else if (percentageAnimationComplete >= 0.25 && percentageAnimationComplete < 0.5) {
                    newState = 2;
                } else if (percentageAnimationComplete >= 0.5 && percentageAnimationComplete < 0.75) {
                    newState = 3;
                } else {
                    newState = 4;
                    pVinebudBlockEntity.animationStartTimeTk = null;
                }
            }
//            System.out.printf("VinebudBlockEntity#tick level:%s pos:%s animation oldState:%s newState:%d ticksElapsedSinceAnimationStart:%d percentageAnimationComplete:%f%n", pLevel, pPos, oldState, newState, ticksElapsedSinceAnimationStart, percentageAnimationComplete);
        } else if (pVinebudBlockEntity.ticksUntilNextAnimationTriggerCheck <= 0) {
            // Check if we need to open or close
            pVinebudBlockEntity.ticksUntilNextAnimationTriggerCheck = pLevel.getRandom().nextInt(10, 50);
//        System.out.println(String.format("VinebudBlockEntity#tick pos:%s state:%s", pPos, pState));
            // TODO Optimize to reduce number of updates
            var isRaining = pLevel.isRainingAt(pPos.above());
//            System.out.println(String.format("VinebudBlockEntity#tick updateCheck pState:%s pPos:%s isRaining:%b isThundering:%b",
//                    pState, pPos, isRaining, pLevel.isThundering()));

//        if (pLevel.dimension().equals(SurgebindingDimensions.ROSHAR_DIM_KEY)) {
            if (isRaining && pLevel.isThundering()) {
                if (oldState != 4 && pVinebudBlockEntity.animationStartTimeTk == null) {
//                    System.out.printf("VinebudBlockEntity#tick level:%s pos:%s isThundering, oldState=%d, start animation%n", pLevel, pPos, oldState);
                    pVinebudBlockEntity.animationIsOpening = false;
                    pVinebudBlockEntity.animationStartTimeTk = pLevel.getGameTime();
                }
            } else {
                if (oldState != 1 && pVinebudBlockEntity.animationStartTimeTk == null) {
//                    System.out.printf("VinebudBlockEntity#tick level:%s pos:%s no rain, oldState=%d, start animation%n", pLevel, pPos, oldState);
                    pVinebudBlockEntity.animationIsOpening = true;
                    pVinebudBlockEntity.animationStartTimeTk = pLevel.getGameTime();
                }
            }
//        }
        }

        // Save state change if needed
        if (!oldState.equals(newState)) {
//            System.out.println("VinebudBlockEntity#tick write state update");
            var newStateObject = pState.setValue(VinebudBlock.STATE, newState);
            pLevel.setBlockAndUpdate(pPos, newStateObject);
            setChanged(pLevel, pPos, newStateObject);
        }
    }
}
