package cn.ksmcbrigade.btt;

import cn.ksmcbrigade.btt.config.Config;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod("btt")
@Mod.EventBusSubscriber
public class BetterTitle {

    public static final Logger LOGGER = LogManager.getLogger();

    public static Config config;

    static {
        try {
            config = new Config();
        } catch (Exception e) {
            LOGGER.info("Error in init the mod config",e);
            throw new RuntimeException(e);
        }
    }

    public BetterTitle() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event){
        event.getDispatcher().register(Commands.literal("t-config").executes(context -> {
            context.getSource().sendSuccess(Component.nullToEmpty("Enabled: "+config.enabled),false);
            context.getSource().sendSuccess(Component.nullToEmpty("Title: "+config.title),false);
            context.getSource().sendSuccess(Component.nullToEmpty("AddressedTitle: "+config.getTitle()),false);
            return 0;
        })
                .then(Commands.argument("enabled", BoolArgumentType.bool()).executes(context -> {
                    config.enabled = BoolArgumentType.getBool(context,"enabled");
                    context.getSource().sendSuccess(Component.nullToEmpty("Enabled: "+config.enabled),true);
                    try {
                        config.save();
                        return 0;
                    } catch (IOException e) {
                        LOGGER.error("Error in save the config",e);
                        return 1;
                    }
                }).then(Commands.argument("title", StringArgumentType.string()).executes(context -> {
                    String title = StringArgumentType.getString(context,"title");
                    if(!title.isEmpty()){
                        config.title = title;
                        context.getSource().sendSuccess(CommonComponents.GUI_DONE,true);
                        try {
                            config.save();
                            return 0;
                        } catch (IOException e) {
                            LOGGER.error("Error in save the config",e);
                            return 1;
                        }
                    }
                    return 1;
                }))));

        event.getDispatcher().register(Commands.literal("t-reload").executes(context -> {
            try {
                config = new Config();
                context.getSource().sendSuccess(CommonComponents.GUI_DONE,true);
                return 0;
            } catch (Exception e) {
                LOGGER.error("Error in reset the config",e);
                return 1;
            }
        }));
    }
}
