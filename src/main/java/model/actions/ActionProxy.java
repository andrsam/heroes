package model.actions;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.unit.Unit;

@AllArgsConstructor
@Slf4j
public class ActionProxy {
    private Action action;

    private Unit srcUnit;

    private Unit dstUnit;

    public void execute() {
        Action magic = srcUnit.getMagic();

        boolean isAttack = action instanceof Attack;
        boolean isAttackAndImprovement = isAttack && magic != null;

        if (isAttackAndImprovement) {
            action.setImprovementRate(magic.getImprovementRate());
        }

        log.info("{} {} {}", srcUnit.getName(), action.getName(), dstUnit.getNameGenitive());

        action.execute(dstUnit);

        if (dstUnit.getMagic() != null) {
            log.info("{} здоровье: {} магия: {}", dstUnit.getName(), dstUnit.getHealth(), dstUnit.getMagic().getName());
        } else {
            log.info("{} здоровье: {}", dstUnit.getName(), dstUnit.getHealth());
        }

        if (isAttackAndImprovement) {
            magic.restoreMagic(srcUnit);
        }

    }
}
