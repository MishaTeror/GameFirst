package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Enemy;

public interface StageFactory {
    Enemy createEnemy(int floor);
    Ability createStageReward();
}
