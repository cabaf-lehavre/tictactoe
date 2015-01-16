package fjorde;

public class Player
{
    private int id;
    private String name;
    private String color;
    private int score;

    public Player(int id, String name, String color)
    {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void plusScore(int delta) {
        setScore(getScore() + delta);
    }

    public void minusScore(int delta) {
        plusScore(-delta);
    }

    @Override
    public String toString()
    {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
