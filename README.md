[![Build Status](https://travis-ci.org/andrsam/heroes.svg?branch=master)](https://travis-ci.org/andrsam/heroes)
[![codecov](https://codecov.io/gh/andrsam/heroes/branch/master/graph/badge.svg)](https://codecov.io/gh/andrsam/heroes)
# Герои

**Прототип игры-консольного приложения на языке Java**

<p>В игре создается отряд определенной расы, состоящий из одного мага,
трех лучников и четырех бойцов. Предусмотрено четыре расы: эльфы, люди,
орки, нежить. Эльфы и люди играют против орков и нежити.</p>

<p>В начале игры случайным образом создаются два враждующих отряда той или иной расы.
Все персонажи отряда делятся на две группы: обычные и привилегированные (с улучшенными показателями). </p>
Персонаж при наложении на него улучшения перемещается в привилегированную группу. 
Величина наносимого урона для персонажей привилегированной группы увеличивается в полтора раза. 

<p>Порядок ходов для рас определяется случайным образом. За один ход каждый персонаж отряда может выполнить действие (действие определяется случайным образом): сначала из привилегированной группы, а если она пуста, тогда из общего списка персонажей отряда рандомно. Персонаж из привилегированной группы после выполнения одного действия перемещается в общую группу. </p>

**Возможности персонажей:**

| Раса | Юнит  | Действие | Урон, HP |
| --- | --- | --- | --- |
|**Эльфы** |*Маг*|наложение улучшения на персонажа своего отряда|-
|||урон магией| 10
||*Лучник*|стрелять из лука|7
||*Воин*|атаковать мечом|15
**Люди**|*Маг*|наложение улучшения на персонажа своего отряда|-|
|||атаковать магией|4
||*Арбалетчик*|стрелять из арбалета|5
|||атаковать|3
||*Воин*|атаковать мечом|18
**Орки**|*Шаман*|наложение улучшения на персонажа своего отряда|-
|||наложение проклятия (снятие улучшения с персонажа противника для следующего хода)|-
||*Лучник*|стрелять из лука|3
||*Гоблин*|атака дубиной|3
**Нежить**|*Некромант*|наслать недуг (уменьшение силы урона персонажа противника на 50% на один ход)|-
|||атака|5
||*Охотник*|стрелять из лука|4
|||атаковать|2
||*Зомби*|удар копьем|18

С начала игры каждый персонаж имеет уровень жизни равный 100 HP.
