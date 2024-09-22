package net.woozy.deathpositionrevealmod;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;


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
            // เช็คว่าผู้ที่ตายเป็นผู้เล่นหรือไม่
            if (event.getEntity() instanceof ServerPlayer player) {
                // ดึงตำแหน่งของผู้เล่น
                BlockPos deathPos = player.blockPosition();

                // สร้างข้อความที่บอกพิกัดการตาย
                Component deathMessage = Component.literal("Player " + player.getName().getString()
                        + " Death position : X=" + deathPos.getX()
                        + ", Y=" + deathPos.getY()
                        + ", Z=" + deathPos.getZ());

                // ส่งข้อความนี้ในเซิร์ฟเวอร์
                MinecraftServer server = player.getServer();
                if (server != null) {
                    server.getPlayerList().broadcastSystemMessage(deathMessage, false);
                }
            }
        }
    }
}
