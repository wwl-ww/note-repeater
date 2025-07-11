package chiww6.noterepeater.block;

import chiww6.noterepeater.state.GlobalBeatState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class NoteRepeater extends RepeaterBlock {
    public static final BooleanProperty LOCKED = Properties.LOCKED;
    // delay值到分频的直接映射
    private static final int[] DELAY_TO_DIVISION = {4, 8, 16, 32};

    public NoteRepeater(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
            .with(FACING, Direction.NORTH)
            .with(POWERED, false)
            .with(DELAY, 1)
            .with(LOCKED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, DELAY, LOCKED, POWERED);
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        // 直接使用映射获取分频值
        int division = DELAY_TO_DIVISION[state.get(DELAY) - 1];  // DELAY从1开始，所以要-1
        return GlobalBeatState.getTicksForDivision(division);
    }

    // 继承原版中继器的大部分逻辑
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean isLocked(WorldView world, BlockPos pos, BlockState state) {
        return super.isLocked(world, pos, state);
    }

    @Override
    protected boolean getSideInputFromGatesOnly() {
        return true;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}