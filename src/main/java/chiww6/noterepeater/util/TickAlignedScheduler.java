package chiww6.noterepeater.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import chiww6.noterepeater.block.NoteRepeater;
import chiww6.noterepeater.state.GlobalBeatState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TickAlignedScheduler {
    private static final Map<BlockPos, NoteState> noteStates = new ConcurrentHashMap<>();
    private static MinecraftServer currentServer = null;
    
    private static class NoteState {
        long targetTick;  // 目标tick
        boolean targetState;  // 目标状态
        int division;  // 分频
    }
    
    static {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            currentServer = server;
            long currentTick = server.getTicks();
            
            noteStates.forEach((pos, state) -> {
                if (currentTick == state.targetTick) {
                    if (server.getOverworld().isChunkLoaded(pos)) {
                        BlockState blockState = server.getOverworld().getBlockState(pos);
                        if (blockState.getBlock() instanceof NoteRepeater) {
                            if (blockState.get(NoteRepeater.POWERED) != state.targetState) {
                                server.getOverworld().setBlockState(pos, blockState.with(NoteRepeater.POWERED, state.targetState));
                            }
                            
                            int ticksToNext = GlobalBeatState.getTicksForDivision(state.division);
                            if (ticksToNext >= GlobalBeatState.getMinimumValidTicks()) {
                                state.targetTick = currentTick + ticksToNext;
                            } else {
                                noteStates.remove(pos);
                            }
                        } else {
                            noteStates.remove(pos);
                        }
                    }
                }
            });
        });
    }
    
    public static void scheduleNote(BlockPos pos, boolean targetState, int division) {
        if (currentServer == null) return;
        
        int ticksToNext = GlobalBeatState.getTicksForDivision(division);
        if (ticksToNext >= GlobalBeatState.getMinimumValidTicks()) {
            NoteState state = new NoteState();
            state.targetState = targetState;
            state.division = division;
            state.targetTick = currentServer.getTicks() + ticksToNext;
            noteStates.put(pos, state);
        }
    }
    
    public static void cancelSchedule(BlockPos pos) {
        noteStates.remove(pos);
    }
    
    public static void clear() {
        noteStates.clear();
    }
} 