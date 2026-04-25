package com.mishateror.office.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.mishateror.office.MainGame;
import com.mishateror.office.BattleManager;
import com.mishateror.office.characters.Player;
import com.mishateror.office.characters.Enemy;
import com.mishateror.office.ability.Ability;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.ability.PoisonAbility;
import com.mishateror.office.screens.BattleScreen;
import com.mishateror.office.screens.BattleRenderer;

/**
 * The type Battle ui.
 */
public class BattleUI {
    private MainGame game;
    private BattleManager battleManager;
    private BattleScreen parentScreen;

    private int menuW = 650, menuH = 120;
    private int menuX, menuY = 30;

    private Button btnBack;
    private AbilityBar abilityBar;

    private Texture hpPartTex, armorPartTex, actionPartTex, emptyPartTex;

    /**
     * Instantiates a new Battle ui.
     *
     * @param game          the game
     * @param battleManager the battle manager
     * @param parentScreen  the parent screen
     */
    public BattleUI(MainGame game, BattleManager battleManager, BattleScreen parentScreen) {
        this.game = game;
        this.battleManager = battleManager;
        this.parentScreen = parentScreen;

        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();
        menuX = (screenW - menuW) / 2;

        Player knight = battleManager.getPlayer();

        abilityBar = new AbilityBar(menuX, menuY, menuW, menuH);
        abilityBar.refreshAbilityBar(knight);
        abilityBar.show();

        btnBack = new Button(20, screenH - 60, 100, 40, "<- BACK");

        hpPartTex = new Texture("HpPart.png");
        armorPartTex = new Texture("ArmorPart.png");
        actionPartTex = new Texture("ActionPart.png");
        emptyPartTex = new Texture("EmptyPart.png");
    }

    /**
     * Handle input.
     *
     * @param mouseX   the mouse x
     * @param mouseY   the mouse y
     * @param renderer the renderer
     */
    public void handleInput(float mouseX, float mouseY, BattleRenderer renderer) {
        if (Gdx.input.justTouched()) {
            Player p = battleManager.getPlayer();
            Enemy e = battleManager.getEnemies().get(0);

            if (btnBack.isClicked(mouseX, mouseY)) {
                parentScreen.exitToMenu();
                return;
            }

            int index = abilityBar.getAbilityIndex(mouseX, mouseY);
            if (index >= 0 && index < p.getAbilities().size()) {
                Ability selectedAbility = p.getAbilities().get(index);
                if (p.getAp() >= selectedAbility.getApCost() && selectedAbility.isAvailable()) {
                    battleManager.onPlayerUseAbility(index, e);

                    if (selectedAbility instanceof AttackAbility || selectedAbility instanceof PoisonAbility) {
                        parentScreen.triggerAttackAnimation();
                    }

                    if(battleManager.checkWinCondition()){
                        parentScreen.onEnemyDefeated();
                    }
                } else if (!selectedAbility.isAvailable()) {
                    battleManager.setBattleLog(selectedAbility.getName() + " is on cooldown!");
                } else {
                    battleManager.setBattleLog("Not enough AP for " + selectedAbility.getName() + "!");
                }
            } else if (index == -2) {
                battleManager.endPlayerTurn();
                battleManager.executeEnemyTurn(renderer);
            }
        }
    }

    /**
     * Render.
     *
     * @param delta    the delta
     * @param renderer the renderer
     */
    public void render(float delta, BattleRenderer renderer) {
        abilityBar.update(delta);
        Player p = battleManager.getPlayer();
        Enemy e = battleManager.getEnemies().get(0);

        game.shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);
        abilityBar.drawShape(game.shapeRenderer);
        btnBack.drawShape(game.shapeRenderer);
        game.shapeRenderer.end();

        game.batch.begin();

        abilityBar.drawTexture(game.batch);

        float hudX = 20f;
        float hudY = Gdx.graphics.getHeight() - 180f;

        float barStartX = hudX + 92f;
        float barStartY_HP    = hudY + 68f;
        float barStartY_AP    = hudY + 41f;
        float barStartY_Armor = hudY + 14f;

        float partWidth = 14f;
        float partHeight = 16f;
        float gap = 2f;

        int maxHp = p.getMaxHealth();
        int currentHp = p.getHealth();
        for (int i = 0; i < maxHp; i++) {
            float xPos = barStartX + i * (partWidth + gap);
            if (i < currentHp) {
                game.batch.draw(hpPartTex, xPos, barStartY_HP, partWidth, partHeight);
            } else {
                game.batch.draw(emptyPartTex, xPos, barStartY_HP, partWidth, partHeight);
            }
        }

        int maxAp = p.getMaxAp();
        int currentAp = p.getAp();
        for (int i = 0; i < maxAp; i++) {
            float xPos = barStartX + i * (partWidth + gap);
            if (i < currentAp) {
                game.batch.draw(actionPartTex, xPos, barStartY_AP, partWidth, partHeight);
            } else {
                game.batch.draw(emptyPartTex, xPos, barStartY_AP, partWidth, partHeight);
            }
        }

        int currentArmor = p.getBlock();
        for (int i = 0; i < currentArmor; i++) {
            float xPos = barStartX + i * (partWidth + gap);
            game.batch.draw(armorPartTex, xPos, barStartY_Armor, partWidth, partHeight);
        }

        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, p.getHealth() + "/" + p.getMaxHealth(), barStartX + 5, barStartY_HP + 30);
        game.font.draw(game.batch, "AP: " + p.getAp(), barStartX + 5, barStartY_AP + 30);
        game.font.draw(game.batch, "Block: " + p.getBlock(), barStartX + 5, barStartY_Armor + 30);

        if (p.isPoisoned()) {
            game.font.setColor(Color.GREEN);
            game.font.draw(game.batch, "Poisoned: " + p.getPoisonTurns() + " turns", barStartX + 5, barStartY_HP + 50);
        }

        float enemyHpX = Gdx.graphics.getWidth() - 250f;
        float enemyHpY = Gdx.graphics.getHeight() - 40f;

        game.font.setColor(Color.RED);
        game.font.draw(game.batch, e.getName(), enemyHpX, enemyHpY + 20);
        game.font.draw(game.batch, "HP: " + e.getHealth() + " / " + e.getMaxHealth(), enemyHpX, enemyHpY);

        if (e.getBlock() > 0) {
            game.font.setColor(Color.CYAN);
            game.font.draw(game.batch, "Block: " + e.getBlock(), enemyHpX, enemyHpY - 20);
        }

        if (e.isPoisoned()) {
            game.font.setColor(Color.GREEN);
            game.font.draw(game.batch, "Poisoned: " + e.getPoisonTurns() + " turns", enemyHpX, enemyHpY - 40);
        }

        abilityBar.drawText(game.batch, game.font);
        btnBack.drawText(game.batch, game.font);

        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch, "LOG: " + battleManager.getBattleLog(), menuX + 20, menuY + 140);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        int hoverIndex = abilityBar.getAbilityIndex(mouseX, mouseY);
        if (hoverIndex >= 0 && hoverIndex < p.getAbilities().size()) {
            Ability hoverAb = p.getAbilities().get(hoverIndex);
            String cdText = hoverAb.getCurrentCooldown() > 0 ? " (CD: " + hoverAb.getCurrentCooldown() + ")" : "";
            game.font.setColor(hoverAb.getCurrentCooldown() > 0 ? Color.RED : Color.YELLOW);
            game.font.draw(game.batch, hoverAb.getDescription() + cdText, mouseX + 10, mouseY + 20);
        }

        game.batch.end();
    }
}
