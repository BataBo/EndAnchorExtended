package de.kxmischesdomi.just_end_anchor.enums;

/**
 * @author BataBo | https://github.com/BataBo/EndAnchorExtended
 * @since 1.0
 */
public enum EndAnchorType {
    RespawnAnchor(1),
    LodeStoneTeleporter(2),
    RecoveryTeleporter(4);

    private final int value;

    private EndAnchorType(int value){
        this.value = value;
    }
    public int getValue() {
        return value;

    }

    public  static  EndAnchorType fromInt(int x)
    {
        return switch (x) {
            case 2 -> LodeStoneTeleporter;
            case 4 -> RecoveryTeleporter;
            default -> RespawnAnchor;
        };
    }
}
