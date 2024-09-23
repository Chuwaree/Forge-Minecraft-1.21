package net.woozy.deathpositionrevealmod;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
// The value here should match an entry in the META-INF/mods.toml file
@Mod(DeathpositionrevealMod.MOD_ID)

public class DeathpositionrevealMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "deathpositionrevealmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    @Mod.EventBusSubscriber(modid = DeathpositionrevealMod.MOD_ID)

    public class DeathEventHandler {
        // เมื่อผู้เล่นตายจะเรียกใช้งานฟังก์ชันนี้

        @SubscribeEvent
        public static void onPlayerDeath(LivingDeathEvent event) {
            // Checking player still alive or not
            if (event.getEntity() instanceof ServerPlayer player) {
                //get position of player
                BlockPos deathPos = player.blockPosition();
                ResourceKey<Level> dimension = player.level().dimension();
                String dimentionMessage = "";
                // สร้างข้อความที่บอกพิกัดการตาย
                if (dimension.equals(Level.NETHER)) {
                    // Run code specific for Nether
                    dimentionMessage = " at the nether";
                }
                else if (dimension.equals(Level.OVERWORLD)) {
                    // Run code specific for Overworld
                    dimentionMessage = " at the overworld";
                }
                else if (dimension.equals(Level.END)) {
                    // Run code specific for The End
                    dimentionMessage = " at the end";
                    }
                Component deathMessage = Component.literal( player.getName().getString()
                        + " Death position : X:" + deathPos.getX()
                        + " Y:" + deathPos.getY()
                        + " Z:" + deathPos.getZ() + dimentionMessage ).withStyle(ChatFormatting.GOLD);
                //Send text to server chat
                MinecraftServer server = player.getServer();
                if (server != null) {
                    server.getPlayerList().broadcastSystemMessage(deathMessage, false);
                }
            }
        }
    }
}

