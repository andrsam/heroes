package model.action;

import model.action.actions.Attack;
import model.unit.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Выполняет промежуточные операции до
 * и после запуска действия юнита
 * (логирование, наложение и снятие улучшения)
 */
public class ActionProxy {
    private static final Logger log = LoggerFactory.getLogger(ActionProxy.class);

    public ActionProxy(Action action, Unit srcUnit, Unit dstUnit) {
        this.action = action;
        this.srcUnit = srcUnit;
        this.dstUnit = dstUnit;
    }

    /**
     * Действие
     */
    private Action action;

    /**
     * Юнит, выполняющий действие
     */
    private Unit srcUnit;

    /**
     * Юнит назначения
     */
    private Unit dstUnit;

    /**
     * Запуск действия
     */
    public void execute() {
        //Улучшение
        Action magic = srcUnit.getMagic();

        //Признак того, что атака осуществляется с улучшением
        boolean isAttackAndImprovement = action instanceof Attack && magic != null;

        //Если улучшение присутствует, выставляем повышающий коэффициент
        if (isAttackAndImprovement) {
            ((Attack) action).setImprovementRate(magic.getImprovementRate());
        }

        log.info("{} {} {}", srcUnit.getName(), action.getName(), dstUnit.getNameGenitive());

        action.execute(dstUnit);

        if (dstUnit.getMagic() != null) {
            log.info("{} здоровье: {} магия: {}", dstUnit.getName(), dstUnit.getHealth(), dstUnit.getMagic().getName());
        } else {
            log.info("{} здоровье: {}", dstUnit.getName(), dstUnit.getHealth());
        }

        //Снимаем улучшение(проклятие) с персонажа
        if (isAttackAndImprovement) {
            srcUnit.restoreMagic();
        }

    }
}
