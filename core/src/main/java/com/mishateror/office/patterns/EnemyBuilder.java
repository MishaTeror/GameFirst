package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Enemy;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Enemy builder.
 */
public class EnemyBuilder {
    private String name = "Unknown";
    private int hp = 10, ap = 3, block = 0;
    private String role = "MONSTER";
    private List<Ability> abilities = new ArrayList<>();

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public EnemyBuilder setName(String name) { this.name = name; return this; }

    /**
     * Sets stats.
     *
     * @param hp    the hp
     * @param ap    the ap
     * @param block the block
     * @return the stats
     */
    public EnemyBuilder setStats(int hp, int ap, int block) { this.hp = hp; this.ap = ap; this.block = block; return this; }

    /**
     * Sets role.
     *
     * @param role the role
     * @return the role
     */
    public EnemyBuilder setRole(String role) { this.role = role; return this; }

    /**
     * Add ability enemy builder.
     *
     * @param ability the ability
     * @return the enemy builder
     */
    public EnemyBuilder addAbility(Ability ability) { this.abilities.add(ability); return this; }

    /**
     * Build enemy.
     *
     * @return the enemy
     */
    public Enemy build() {
        Enemy enemy = new Enemy(name, hp, ap, block, role);
        for (Ability a : abilities) enemy.addAbility(a);
        return enemy;
    }
}
