package Server.collection;

public interface HumanBeingShower {
    static String toStrView(ServerHumanBeing hb) {
        return "HumanBeing{id=" + hb.getId() +
                ", name='" + hb.getName() +
                ", realHero=" + hb.isRealHero() +
                ", hasToothPick=" +
                hb.isHasToothpick() + ", mood=" +
                hb.getMood() + "', username = '" +
                hb.getUsername() + "'}}}\n";
    }

    static String toStrViewFull(ServerHumanBeing hb) {
        return "HumanBeing{id=" + hb.getId() +
                ", name='" + hb.getName() +
                "', realHero=" + hb.isRealHero() +
                ", hasToothPick=" +
                hb.isHasToothpick() + ", mood=" +
                hb.getMood() + ", weaponType="+ hb.getWeaponType()+
                ", creationDate: "+hb.getCreationDate()+
                ", username = " +
                hb.getUsername() + "'}}}\n";
    }
}

