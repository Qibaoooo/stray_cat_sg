package nus.iss.sa57.csc_android.payload;

public class CatSightingResponse {
    private int catSighting;
    private int cat;

    public CatSightingResponse() {
    }

    public int getCatSighting() {
        return catSighting;
    }

    public void setCatSighting(int catSighting) {
        this.catSighting = catSighting;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }
}
