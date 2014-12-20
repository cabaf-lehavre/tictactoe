public interface ModeleMorpionListener {
    void setCase(int x, int y, ModeleMorpion.Etat etat);
    void endGame(ModeleMorpion.Etat gagnant);
}
