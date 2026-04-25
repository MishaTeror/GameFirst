package com.mishateror.office.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The type Animation manager.
 */
public class AnimationManager {
    /**
     * The enum State.
     */
    public enum State {
        /**
         * Idle state.
         */
        IDLE,
        /**
         * Walk forward state.
         */
        WALK_FORWARD,
        /**
         * Attack state.
         */
        ATTACK,
        /**
         * Walk back state.
         */
        WALK_BACK,
        /**
         * Dead state.
         */
        DEAD}
    private State currentState = State.IDLE;

    private Texture idleSheet, deadSheet, attackSheet, walkSheet;
    private Animation<TextureRegion> idleAnimation, deadAnimation, attackAnimation, walkAnimation;

    private float stateTime = 0f;

    private final float MOVE_DURATION = 0.6f;

    /**
     * Instantiates a new Animation manager.
     *
     * @param idleName     the idle name
     * @param idleFrames   the idle frames
     * @param deadName     the dead name
     * @param deadFrames   the dead frames
     * @param attackName   the attack name
     * @param attackFrames the attack frames
     * @param walkName     the walk name
     * @param walkFrames   the walk frames
     */
    public AnimationManager(String idleName, int idleFrames,
                            String deadName, int deadFrames,
                            String attackName, int attackFrames,
                            String walkName, int walkFrames){

        idleSheet = new Texture(idleName);
        deadSheet = new Texture(deadName);
        attackSheet = new Texture(attackName);
        walkSheet = new Texture(walkName);

        //IDLE
        TextureRegion[][] tmp = TextureRegion.split(idleSheet, idleSheet.getWidth() / idleFrames, idleSheet.getHeight());
        TextureRegion[] frames = new TextureRegion[idleFrames];
        for (int j = 0; j < idleFrames; j++) frames[j] = tmp[0][j];
        idleAnimation = new Animation<TextureRegion>(0.15f, frames);

        //DEAD
        tmp = TextureRegion.split(deadSheet, deadSheet.getWidth() / deadFrames, deadSheet.getHeight());
        frames = new TextureRegion[deadFrames];
        for (int j = 0; j < deadFrames; j++) frames[j] = tmp[0][j];
        deadAnimation = new Animation<TextureRegion>(0.15f, frames);

        //ATTACK
        tmp = TextureRegion.split(attackSheet, attackSheet.getWidth() / attackFrames, attackSheet.getHeight());
        frames = new TextureRegion[attackFrames];
        for (int j = 0; j < attackFrames; j++) frames[j] = tmp[0][j];
        attackAnimation = new Animation<TextureRegion>(0.1f, frames);

        //WALK
        tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / walkFrames, walkSheet.getHeight());
        frames = new TextureRegion[walkFrames];
        for (int j = 0; j < walkFrames; j++) frames[j] = tmp[0][j];
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);
    }

    /**
     * Update.
     *
     * @param delta the delta
     */
    public void update(float delta){
        stateTime += delta;
    }

    /**
     * Start walking to target.
     */
    public void startWalkingToTarget(){
        if (currentState != State.DEAD) {
            currentState = State.WALK_FORWARD;
            stateTime = 0f;
        }
    }

    /**
     * Play dead.
     */
    public void playDead() {
        if (currentState == State.DEAD) return;
        currentState = State.DEAD;
        stateTime = 0f;
    }

    /**
     * Get current frame texture region.
     *
     * @return the texture region
     */
    public TextureRegion getCurrentFrame(){
        if(currentState == State.WALK_FORWARD) {
            if(stateTime >= MOVE_DURATION) {
                currentState = State.ATTACK;
                stateTime = 0f;
                return attackAnimation.getKeyFrame(stateTime, false);
            }
            return walkAnimation.getKeyFrame(stateTime, true);
        }
        else if(currentState == State.ATTACK){
            if(attackAnimation.isAnimationFinished(stateTime)) {
                currentState = State.WALK_BACK;
                stateTime = 0f;
            }
            return attackAnimation.getKeyFrame(stateTime, false);
        }
        else if(currentState == State.WALK_BACK) {
            if(stateTime >= MOVE_DURATION) {
                currentState = State.IDLE;
                stateTime = 0f;
                return idleAnimation.getKeyFrame(stateTime, true);
            }
            float totalDuration = walkAnimation.getAnimationDuration();
            float reverseTime = totalDuration - (stateTime % totalDuration);
            return walkAnimation.getKeyFrame(reverseTime, true);
        }
        else if(currentState == State.DEAD){
            return deadAnimation.getKeyFrame(stateTime, false);
        }
        else {
            return idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    /**
     * Gets dash progress.
     *
     * @return the dash progress
     */
    public float getDashProgress() {
        if (currentState == State.WALK_FORWARD) {
            return Math.min(1f, stateTime / MOVE_DURATION);
        } else if (currentState == State.ATTACK) {
            return 1f;
        } else if (currentState == State.WALK_BACK) {
            return 1f - Math.min(1f, stateTime / MOVE_DURATION);
        }
        return 0f;
    }

    /**
     * Dispose.
     */
    public void dispose() {
        idleSheet.dispose(); attackSheet.dispose(); deadSheet.dispose(); walkSheet.dispose();
    }
}
