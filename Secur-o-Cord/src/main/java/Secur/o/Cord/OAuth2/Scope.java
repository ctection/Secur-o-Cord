package Secur.o.Cord.OAuth2;

import java.util.Arrays;
import java.util.List;

public enum Scope {
    BOT("bot"),
    CONNECTIONS("connections"),
    EMAIL("email"),
    IDENTIFY("identify"),
    GUILDS("guilds"),
    GUILDS_JOIN("guilds.join"),
    GDM_JOIN("gdm.join"),
    MESSAGES_READ("messages.read"),
    RPC("rpc"),
    RPC_API("rpc.api"),
    RPC_NOTIFICATIONS_READ("rpc.notifications.read"),
    WEBHOOK_INCOMING("webhook.incoming"),
    APPLICATIONS_BUILDS_UPLOAD("applications.builds.upload"),
    APPLICATIONS_BUILD_READ("applications.builds.read"),
    APPLICATIONS_STORE_UPDATE("applications.store.update"),
    APPLICATIONS_ENTITLEMENTS("applications.entitlements"),
    RELATIONSHIPS_READ("relationships.read"),
    ACTIVITIES_READ("activities.read"),
    ACTIVITIES_WRITE("activities.write")
    ;
    private String name;

    Scope(String name) {
    this.name = name;
    }

    public String getName() {
        return name;
    }
    public static Scope getByName(String name) {
        List<Scope> scopes = Arrays.asList(Scope.values());
        for (Scope s: scopes) {
             if (s.getName().equals(name))
                 return s;
        }
        return null;
    }
}
