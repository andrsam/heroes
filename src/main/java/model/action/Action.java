package model.action;

import model.unit.Unit;

/**
 * Задаёт общее поведение для всех действий
 */
public interface Action {
    /**
     * Запускает действие.
     *
     * @param unit - Юнит, к которому следует  применить действие
     */
    void execute(Unit unit);

    /**
     * Возвращает наименование юнита для логирования
     *
     * @return наименование юнита
     */
    String getName();

    /**
     * Возвращает коэффициент для
     * изменения величины урона при наложении магии
     *
     * @return коэффициент
     */
    float getImprovementRate();

    /**
     * Возвращает признак, является ли действие изменением характеристик персонажа
     *
     * @return
     */
    boolean isActionChangesState();
}
