package Server.collection;


import general.HumanBeing;

public class ServerHumanBeing extends HumanBeing {
    public ServerHumanBeing(HumanBeing hb, long id) {
        super(hb.getName(), hb.getCoordinates(), hb.isRealHero(),hb.isHasToothpick(),hb.getImpactSpeed(), hb.getWeaponType(), hb.getMood(), hb.getCar(),hb.getUsername());
        setId(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
