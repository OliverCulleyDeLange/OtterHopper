package uk.co.oliverdelange.otterhopper.sprites;

import java.util.ArrayList;

public class SpritePool<T extends Sprite> {

    private ArrayList<T> freeSprites;
    private ArrayList<T> spritesInUse = new ArrayList<>();
    private ArrayList<T> spritesToBeFreed = new ArrayList<>();

    public SpritePool(ArrayList<T> sprites) {
        this.freeSprites = sprites;
    }

    public T getSprite() throws IndexOutOfBoundsException {
        freeSpritesToBeFreed();

        if (freeSprites.isEmpty()) {
            throw new IndexOutOfBoundsException("No free sprites! ");
        } else {
            T sprite = freeSprites.get(0);
            freeSprites.remove(sprite);
            spritesInUse.add(sprite);
            return sprite;
        }
    }

    protected void freeSpritesToBeFreed() {
        if (!spritesToBeFreed.isEmpty()) {
            for (T sprite : spritesToBeFreed) {
                sprite.reset();
                spritesInUse.remove(sprite);
                freeSprites.add(sprite);
            }
            spritesToBeFreed.clear();
        }
    }

    private void putSprite(T sprite) {
        spritesInUse.remove(sprite);
        freeSprites.add(sprite);
    }

    public ArrayList<T> getSpritesInUse() {
        return spritesInUse;
    }

    public void markSpriteForFreeing(T sprite) {
        if (!spritesToBeFreed.contains(sprite))
            spritesToBeFreed.add(sprite);
    }
}
