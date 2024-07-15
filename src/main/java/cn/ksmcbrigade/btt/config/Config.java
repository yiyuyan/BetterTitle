package cn.ksmcbrigade.btt.config;

import cn.ksmcbrigade.btt.FileUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Config {

            /*%x%  x location
            %y%  y location
            %z%  z location
            %user%  user name
            %uuid%  user uuid
            %token%  user access token
            %mode%  game mode
            %def%  Minecraft
            %ver%  1.18.1
            %yyyy%  2024?
            %yy%    24?
            %mm%  now mouth
            %dd%  now day
            %h%  now time hour
            %h2%  now time hour
            %m%  now time minute
            %s%  now time second*/

    public File path = new File("config/bt-config.json");

    public boolean enabled;
    public String title;

    public Config() throws Exception {
        if(!path.exists()){
            JsonObject obj = new JsonObject();
            obj.addProperty("enabled",true);
            obj.addProperty("title","%def% Client|%ver% - %yyyy%.%mm%.%dd% - %h2%:%m%:%s%");
            FileUtils.write(path, obj.toString());
        }
        JsonObject object = JsonParser.parseString(FileUtils.readAsciiFile(path)).getAsJsonObject();
        this.enabled = object.get("enabled").getAsBoolean();
        this.title = object.get("title").getAsString();
    }

    public String getTitle(){
        String TITLE = title.replace("%def%","Minecraft");
        TITLE = TITLE.replace("%ver%","1.18.1");

        Minecraft MC = Minecraft.getInstance();

        TITLE = TITLE.replace("%user%",MC.getUser().getName());
        TITLE = TITLE.replace("%uuid%",MC.getUser().getUuid());
        TITLE = TITLE.replace("%token%",MC.getUser().getAccessToken());

        if(MC.player!=null){
            TITLE = TITLE.replace("%x%",String.valueOf((int)MC.player.getX()));
            TITLE = TITLE.replace("%y%", String.valueOf((int)MC.player.getY()));
            TITLE = TITLE.replace("%z%", String.valueOf((int)MC.player.getZ()));
        }
        else{
            TITLE = TITLE.replace("%x%","");
            TITLE = TITLE.replace("%y%", "");
            TITLE = TITLE.replace("%z%", "");
        }

        if(MC.gameMode!=null){
            TITLE = TITLE.replace("%mode%",MC.gameMode.getPlayerMode().getName());
        }
        else{
            TITLE = TITLE.replace("%mode%","");
        }

        LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        TITLE = TITLE.replace("%yyyy%",String.valueOf(now.getYear()));
        TITLE = TITLE.replace("%yy%",String.valueOf(now.getYear()).substring(2));

        TITLE = TITLE.replace("%mm%",String.format("%02d",now.getMonthValue()));
        TITLE = TITLE.replace("%dd%",String.format("%02d",now.getDayOfMonth()));

        GregorianCalendar calendar = new GregorianCalendar();

        TITLE = TITLE.replace("%h%",String.format("%02d",calendar.get(Calendar.HOUR)));
        TITLE = TITLE.replace("%h2%",String.format("%02d",calendar.get(Calendar.HOUR_OF_DAY)));

        TITLE = TITLE.replace("%m%",String.format("%02d",calendar.get(Calendar.MINUTE)));
        TITLE = TITLE.replace("%s%",String.format("%02d",calendar.get(Calendar.SECOND)));

        return TITLE;
    }

    public void save() throws IOException {
        JsonObject obj = new JsonObject();
        obj.addProperty("enabled",this.enabled);
        obj.addProperty("title",this.title);
        FileUtils.write(path,obj.toString());
    }
}
