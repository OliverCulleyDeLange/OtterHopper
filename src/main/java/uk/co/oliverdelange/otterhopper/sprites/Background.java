package uk.co.oliverdelange.otterhopper.sprites;

public class Background {

    private Sprite sprite;

    public Background() {
        this.sprite = new Sprite(10,10,10,10);
    }


    public void update(float deltaTime) {
        this.sprite.setNewXPosition( this.sprite.getSpeed() * deltaTime);
    }
}
